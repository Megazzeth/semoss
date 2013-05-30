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

public class IslandIdentifierProcessor implements IAlgorithm{

	DelegateForest forest = null;
	ArrayList<DBCMVertex> selectedVerts = new ArrayList<DBCMVertex>();
	GridFilterData gfd = new GridFilterData();
	GraphPlaySheet playSheet;
	public Hashtable masterHash = new Hashtable();//this will have key: node, object: hashtable with verts.  Also key: node + edgeHashKey and object: hastable with edges
	String selectedNodes="";
	Vector<DBCMEdge> masterEdgeVector = new Vector();//keeps track of everything accounted for in the forest
	Vector<DBCMVertex> masterVertexVector = new Vector();
	Hashtable islandVerts = new Hashtable();
	Hashtable islandEdges = new Hashtable();
	String edgeHashKey = "EdgeHashKey";
	
	//if a node is selected, highlight all nodes disconnected from that node
	//if no node is selected, highlight all node disconnected from the largest network of nodes
	public void execute(){
		
		Collection<DBCMVertex> forestRoots = new ArrayList();
		if(selectedVerts.size()!=0){
			int count = 0;
			for(DBCMVertex selectedVert : selectedVerts) {
				if(masterVertexVector.contains(selectedVert)){
					forestRoots.add(selectedVert);
					if(count > 0) selectedNodes = selectedNodes +", ";
					selectedNodes = selectedNodes + selectedVert.getProperty(Constants.VERTEX_NAME);
					masterVertexVector.remove(selectedVert);
					addNetworkToMasterHash(selectedVert);
					count++;
				}
			}
			addRemainingToIslandHash(masterEdgeVector, masterVertexVector);
		}
		else{
			selectedNodes = "All";
			forestRoots = forest.getRoots();
			for(DBCMVertex forestVert : forestRoots){
				if(masterVertexVector.contains(forestVert)){
					masterVertexVector.remove(forestVert);
					addNetworkToMasterHash(forestVert);
				}
			}
			differentiateMainlandFromIslands();
		}
		setTransformers();
	}
	
	private void differentiateMainlandFromIslands(){
		Iterator<String> masterHashIt = masterHash.keySet().iterator();
		String mainlandKey = "";
		int mainlandSize = 0;
		while(masterHashIt.hasNext()){
			String key = masterHashIt.next();
			if(!key.contains(edgeHashKey)){
				Hashtable vertHash = (Hashtable) masterHash.get(key);
				int vertHashSize = vertHash.size();
				if (vertHashSize>mainlandSize){
					mainlandSize = vertHashSize;
					mainlandKey = key;
				}
			}
		}
		//now we know the mainland and therefore the islands.  Time to set the island hastables

		masterHashIt = masterHash.keySet().iterator();
		while(masterHashIt.hasNext()){
			String key = masterHashIt.next();
			if(!key.contains(mainlandKey)){
				if(key.contains(edgeHashKey)){
					Hashtable islandEdgeHash = (Hashtable) masterHash.get(key);
					islandEdges.putAll(islandEdgeHash);
				}
				else{
					Hashtable islandVertHash = (Hashtable) masterHash.get(key);
					islandVerts.putAll(islandVertHash);
				}
			}
		}
	}
	
	private void setTransformers(){
		if(playSheet.searchPanel.btnHighlight.isSelected()){
			SearchEdgeStrokeTransformer tx = (SearchEdgeStrokeTransformer)playSheet.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(islandEdges);
			SearchVertexPaintTransformer vtx = (SearchVertexPaintTransformer)playSheet.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(islandVerts);
			SearchVertexLabelFontTransformer vlft = (SearchVertexLabelFontTransformer)playSheet.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(islandVerts);
		}
		else{
			EdgeStrokeTransformer tx = (EdgeStrokeTransformer)playSheet.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(islandEdges);
			EdgeArrowStrokeTransformer stx = (EdgeArrowStrokeTransformer)playSheet.getView().getRenderContext().getEdgeArrowStrokeTransformer();
			stx.setEdges(islandEdges);
			ArrowDrawPaintTransformer atx = (ArrowDrawPaintTransformer)playSheet.getView().getRenderContext().getArrowDrawPaintTransformer();
			atx.setEdges(islandEdges);
			VertexPaintTransformer vtx = (VertexPaintTransformer)playSheet.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(islandVerts);
			VertexLabelFontTransformer vlft = (VertexLabelFontTransformer)playSheet.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(islandVerts);
		}
		// repaint it
		playSheet.getView().repaint();
	}
	
	private void addRemainingToIslandHash(Vector<DBCMEdge> masterEdges, Vector<DBCMVertex> masterVerts){
		for(DBCMVertex vertex: masterVerts){
			islandVerts.put((String) vertex.getProperty(Constants.URI), vertex);
		}
		
		for(DBCMEdge edge : masterEdges){
			islandEdges.put((String) edge.getProperty(Constants.URI), edge);
		}
	}
		
	public void addNetworkToMasterHash(DBCMVertex vertex){
		String vertexName = (String) vertex.getProperty(Constants.VERTEX_NAME);
		//use current nodes as the next set of nodes that I will have to traverse downward from.  Starts with passed node
		ArrayList<DBCMVertex> currentNodes = new ArrayList<DBCMVertex>();
		currentNodes.add(vertex);
		Hashtable islandHash = new Hashtable();
		islandHash.put(vertex.getProperty(Constants.URI), vertex);
		Hashtable islandEdgeHash = new Hashtable();
		//use next nodes as the future set of nodes to traverse down from.
		ArrayList<DBCMVertex> nextNodes = new ArrayList<DBCMVertex>();
				
		int nodeIndex = 0;
		int levelIndex = 1;
		while(!nextNodes.isEmpty() || levelIndex == 1){
			nextNodes.clear();
			while (!currentNodes.isEmpty()){
				nodeIndex = 0;
				DBCMVertex vert = currentNodes.remove(nodeIndex);
				
				ArrayList<DBCMVertex> subsetNextNodes = traverseOutward(vert, islandHash, islandEdgeHash, vertexName);
				
				nextNodes.addAll(subsetNextNodes);
				
				nodeIndex++;
			}
			currentNodes.addAll(nextNodes);
			
			levelIndex++;
		}
	}
	
	public ArrayList<DBCMVertex> traverseOutward(DBCMVertex vert, Hashtable vertNetworkHash, Hashtable edgeNetworkHash, String vertKey){
		//get nodes downstream
		ArrayList<DBCMVertex> vertArray = new ArrayList<DBCMVertex>();
		Collection<DBCMEdge> edgeArray = forest.getOutEdges(vert);
		//add all edges
		putEdgesInHash(edgeArray, edgeNetworkHash);
		for (DBCMEdge edge: edgeArray){
			DBCMVertex inVert = edge.inVertex;
			if(masterVertexVector.contains(inVert)){
				vertArray.add(inVert);//this is going to be the returned array, so this is all set
				
				vertNetworkHash.put(inVert.getProperty(Constants.URI), inVert);
				removeAllEdgesAssociatedWithNode(inVert);
			}
		}
		//get nodes upstream
		Collection<DBCMEdge> inEdgeArray = forest.getInEdges(vert);
		putEdgesInHash(inEdgeArray, edgeNetworkHash);
		for (DBCMEdge edge: inEdgeArray){
			DBCMVertex outVert = edge.outVertex;
			if(masterVertexVector.contains(outVert)){
				vertArray.add(outVert);//this is going to be the returned array, so this is all set
				
				vertNetworkHash.put(outVert.getProperty(Constants.URI), outVert);
				edgeNetworkHash.put(edge.getProperty(Constants.URI), edge);
				removeAllEdgesAssociatedWithNode(outVert);
			}
		}
		
		masterHash.put(vertKey, vertNetworkHash);
		masterHash.put(vertKey+edgeHashKey, edgeNetworkHash);
		return vertArray;
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
	
	private void removeAllEdgesAssociatedWithNode(DBCMVertex vert){
		//remove vertex
		masterVertexVector.remove(vert);
		//remove downstream edges
		Collection<DBCMEdge> edgeArray = forest.getOutEdges(vert);
		for (DBCMEdge edge: edgeArray){
			if(masterEdgeVector.contains(edge)){
				masterEdgeVector.remove(edge);
			}
		}
		//remove upstream edges
		Collection<DBCMEdge> inEdgeArray = forest.getInEdges(vert);
		for (DBCMEdge edge: inEdgeArray){
			if(masterEdgeVector.contains(edge)){
				masterEdgeVector.remove(edge);
			}
		}
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
		return "Island Identifier";
	}


}
