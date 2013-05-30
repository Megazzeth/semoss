package prerna.algorithm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import prerna.algorithm.api.IAlgorithm;
import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GridFilterData;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.transformer.ArrowDrawPaintTransformer;
import prerna.ui.transformer.EdgeArrowStrokeTransformer;
import prerna.ui.transformer.EdgeStrokeTransformer;
import prerna.ui.transformer.SearchEdgeStrokeTransformer;
import prerna.ui.transformer.SearchVertexLabelFontTransformer;
import prerna.ui.transformer.SearchVertexPaintTransformer;
import prerna.ui.transformer.VertexLabelFontTransformer;
import prerna.ui.transformer.VertexPaintTransformer;
import prerna.util.Constants;
import edu.uci.ics.jung.graph.DelegateForest;

public class LoopIdentifierProcessor implements IAlgorithm{

	DelegateForest forest = null;
	ArrayList<DBCMVertex> selectedVerts = new ArrayList<DBCMVertex>();
	GridFilterData gfd = new GridFilterData();
	GraphPlaySheet playSheet;
	Hashtable<String, DBCMEdge> nonLoopEdges = new Hashtable<String, DBCMEdge>();
	Hashtable<String, DBCMEdge> loopEdges = new Hashtable<String, DBCMEdge>();
	Hashtable<String, DBCMVertex> nonLoopVerts = new Hashtable<String, DBCMVertex>();
	Hashtable<String, DBCMVertex> loopVerts = new Hashtable<String, DBCMVertex>();
	String selectedNodes="";
	Vector<DBCMEdge> masterEdgeVector = new Vector();//keeps track of everything accounted for in the forest
	Vector<DBCMVertex> masterVertexVector = new Vector();
	Vector<DBCMVertex> currentPathVerts = new Vector<DBCMVertex>();//these are used for depth search first
	Vector<DBCMEdge> currentPathEdges = new Vector<DBCMEdge>();
	
	
	public void execute(){
		//All I have to do is go through every node in the forest
		//if the node has in and out, it could be part of a loop
		//if a node has only in or only out edges, it is not part of a loop
		//therefore, remove the vertex and all edges associated with it from the forest
		//once there are no edges getting removed, its time to stop
		//Then I run depth search first to validate the edges left
		Collection<DBCMVertex> allVerts = forest.getVertices();
		Vector<DBCMVertex> currentVertices = new Vector<DBCMVertex>();
		Vector<DBCMVertex> nextVertices = new Vector<DBCMVertex>();
		currentVertices.addAll(allVerts);
		nextVertices.addAll(currentVertices);
		Vector<DBCMEdge> newlyRemovedEdges = new Vector<DBCMEdge>();
		int count = 0;
		while(count==0 || newlyRemovedEdges.size()!=0){
			newlyRemovedEdges.clear();
			for(DBCMVertex vertex : currentVertices){
				Vector<DBCMEdge> inEdges = getValidEdges(vertex.getInEdges());
				int inEdgeCount = inEdges.size();
				Vector<DBCMEdge> outEdges = getValidEdges(vertex.getOutEdges());
				int outEdgeCount = outEdges.size();
				//if inEdges is 0, put the vert and its edges in hashtables and remove everything associated with it from the forest
				if(inEdgeCount == 0){
					nonLoopVerts.put((String) vertex.getProperty(Constants.URI), vertex);
					putEdgesInHash(outEdges, nonLoopEdges);
					newlyRemovedEdges.addAll(removeEdgesFromMaster(outEdges));
					nextVertices.remove(vertex);
					masterVertexVector.remove(vertex);
				}
				else if (outEdgeCount == 0){
					nonLoopVerts.put((String) vertex.getProperty(Constants.URI), vertex);
					putEdgesInHash(inEdges, nonLoopEdges);
					newlyRemovedEdges.addAll(removeEdgesFromMaster(inEdges));
					nextVertices.remove(vertex);
					masterVertexVector.remove(vertex);
				}
				count++;
			}
			currentVertices.clear();
			currentVertices.addAll(nextVertices);
		}
		//phase 1 is now complete.  The only vertices and edges left must have in and out edges
		//However, there is still the possiblity of fake edges and nodes that exist only between two loops
		//Now I will perform depth search first on all remaining nodes to ensure that every edge is a loop
		runDepthSearchFirst();
		//Everything that is left in nextVertices and the forest now must be loopers
		//lets put them in their respective hashtables and set the transformers
		setTransformers();
		
	}
	
	private void runDepthSearchFirst(){
		//for every vertex remaining in master vertex vector, I will get all possible full length paths
		//If a path return back to the starting node, put it in the loop hash
		for(DBCMVertex vertex : masterVertexVector){
			Vector <DBCMVertex> usedLeafVerts = new Vector<DBCMVertex>();//keeps track of all bottom nodes previously visited
			usedLeafVerts.add(vertex);
			
			Vector<DBCMVertex> currentNodes = new Vector<DBCMVertex>();
			//use next nodes as the future set of nodes to traverse down from.
			Vector<DBCMVertex> nextNodes = new Vector<DBCMVertex>();
			
			//check if there is a loop with itself
			if(checkIfCompletesLoop(vertex, vertex)){
				addPathAsLoop(currentPathEdges, currentPathVerts);
			}
			
			int levelIndex = 0;
			while(!currentPathVerts.isEmpty() || levelIndex == 0){
				int pathIndex = 0;
				currentNodes.add(vertex);
				currentPathVerts.clear();
				currentPathEdges.clear();
				while(!nextNodes.isEmpty() || pathIndex == 0){
					nextNodes.clear();
					while (!currentNodes.isEmpty()){
						DBCMVertex vert = currentNodes.remove(0);
						
						DBCMVertex nextNode = traverseDepthDownward(vert, usedLeafVerts);
						if(nextNode!=null)
							nextNodes.add(nextNode);
						
						pathIndex++;
					}
					currentNodes.addAll(nextNodes);
					
					levelIndex++;
				}
				//Now I should have a complete path.  I need to check to see it it can make it back to the root node.
				//If it can make it back to the root node, it is a loop and should be added to the loop hashtables
				if(currentPathVerts.size()>0){
					DBCMVertex leafVert = currentPathVerts.get(currentPathVerts.size()-1);
					if(checkIfCompletesLoop(leafVert, vertex)){
						//add loop to loop hashtables
						addPathAsLoop(currentPathEdges, currentPathVerts);
					}
					usedLeafVerts.add(leafVert);
				}
			}
			
		}
	}
	
	private boolean checkIfCompletesLoop(DBCMVertex leaf, DBCMVertex root){
		boolean retBool = false;
		if(leaf == null) return false;

		Collection<DBCMEdge> edgeArray = getValidEdges(forest.getOutEdges(leaf));
		for (DBCMEdge edge: edgeArray){
			DBCMVertex inVert = edge.inVertex;
			if(inVert.equals(root)) {
				currentPathEdges.add(edge);
				currentPathVerts.add(root);
				return true;
			}
		}
		return retBool;
	}
	
	private DBCMVertex traverseDepthDownward(DBCMVertex vert, Vector<DBCMVertex> usedLeafVerts){
		DBCMVertex nextVert = null;
		Collection<DBCMEdge> edgeArray = getValidEdges(forest.getOutEdges(vert));
		for (DBCMEdge edge: edgeArray){
			DBCMVertex inVert = edge.inVertex;
			if(masterVertexVector.contains(inVert) && !usedLeafVerts.contains(inVert) && !currentPathVerts.contains(inVert)){
				nextVert = inVert;//this is going to be the returned vert, so this is all set
				currentPathVerts.add(inVert);
				currentPathEdges.add(edge);
				return nextVert;
			}
		}
		return nextVert;
	}
	private Vector<DBCMEdge> getValidEdges(Collection<DBCMEdge> vector){
		Vector<DBCMEdge> validEdges = new Vector<DBCMEdge>();
		if (vector==null) return validEdges;
		for(DBCMEdge edge : vector){
			if(masterEdgeVector.contains(edge))
				validEdges.add(edge);
		}
		return validEdges;
	}
	
	private void setTransformers(){
		if(playSheet.searchPanel.btnHighlight.isSelected()){
			SearchEdgeStrokeTransformer tx = (SearchEdgeStrokeTransformer)playSheet.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(loopEdges);
			SearchVertexPaintTransformer vtx = (SearchVertexPaintTransformer)playSheet.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(loopVerts);
			SearchVertexLabelFontTransformer vlft = (SearchVertexLabelFontTransformer)playSheet.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(loopVerts);
		}
		else{
			EdgeStrokeTransformer tx = (EdgeStrokeTransformer)playSheet.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(loopEdges);
			EdgeArrowStrokeTransformer stx = (EdgeArrowStrokeTransformer)playSheet.getView().getRenderContext().getEdgeArrowStrokeTransformer();
			stx.setEdges(loopEdges);
			ArrowDrawPaintTransformer atx = (ArrowDrawPaintTransformer)playSheet.getView().getRenderContext().getArrowDrawPaintTransformer();
			atx.setEdges(loopEdges);
			VertexPaintTransformer vtx = (VertexPaintTransformer)playSheet.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(loopVerts);
			VertexLabelFontTransformer vlft = (VertexLabelFontTransformer)playSheet.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(loopVerts);
		}
		// repaint it
		playSheet.getView().repaint();
	}
	
	private void addPathAsLoop(Vector<DBCMEdge> edges, Vector<DBCMVertex> verts){
		for(DBCMVertex vertex: verts){
			loopVerts.put((String) vertex.getProperty(Constants.URI), vertex);
		}
		
		for(DBCMEdge edge : edges){
			loopEdges.put((String) edge.getProperty(Constants.URI), edge);
		}
	}
	
	private Vector<DBCMEdge> removeEdgesFromMaster(Vector <DBCMEdge> edges){
		Vector<DBCMEdge> removedEdges = new Vector<DBCMEdge>();
		for(int edgeIndex = 0;edgeIndex < edges.size();edgeIndex++)
		{
			DBCMEdge edge = edges.elementAt(edgeIndex);
			if(masterEdgeVector.contains(edge)){
				removedEdges.add(edge);
				masterEdgeVector.remove(edge);
			}
		}
		return removedEdges;
	}
	
	private Hashtable <String, DBCMEdge> putEdgesInHash(Collection <DBCMEdge> edges, Hashtable <String, DBCMEdge> hash)
	{
		Iterator edgeIt = edges.iterator();
		while(edgeIt.hasNext())
		{
			DBCMEdge edge = (DBCMEdge) edgeIt.next();
			hash.put((String)edge.getProperty(Constants.URI), edge);
		}
		return hash;
	}
	
	public void setForest(DelegateForest f){
		forest = f;
		Collection<DBCMEdge> edges = f.getEdges();
		Collection<DBCMVertex> v = f.getVertices();
		masterEdgeVector.addAll(edges);
		masterVertexVector.addAll(v);
	}
	
	public void setSelectedNodes(DBCMVertex[] pickedVertices){
		for (int idx = 0; idx< pickedVertices.length ; idx++){
			selectedVerts.add(pickedVertices[idx]);
		}
	}

	public void setPlaySheet(IPlaySheet ps){
		playSheet = (GraphPlaySheet) ps;
	}

	@Override
	public String[] getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAlgoName() {
		// TODO Auto-generated method stub
		return "Loop Identifier";
	}

}
