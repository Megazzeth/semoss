package prerna.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;

import prerna.algorithm.impl.DistanceDownstreamProcessor;
import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;

public class GraphToTreeConverter extends DistanceDownstreamProcessor{

	Hashtable<String, ArrayList<DBCMVertex>> uriVertHash = new Hashtable();
	DelegateForest newForest;
	

	public GraphToTreeConverter(){
		super();
	}
	
	public GraphToTreeConverter(GraphPlaySheet p){
		super();
		this.playSheet=p;
	}
	
	public void setPlaySheet(GraphPlaySheet p){
		this.playSheet=p;
	}
	
	private void setSelectedNodes(){
		VisualizationViewer view = playSheet.view;
		PickedState ps = view.getPickedVertexState();
		Iterator <DBCMVertex> it = ps.getPicked().iterator();
		while(it.hasNext()){
			this.selectedVerts.add(it.next());
		}
	}
	
	private void resetConverter(){

		newForest = new DelegateForest();
		masterHash.clear();
		uriVertHash.clear();
		selectedVerts.clear();
	}

	@Override
	public void execute() {
		resetConverter();
		this.forest=playSheet.forest;
		setSelectedNodes();
		ArrayList<DBCMVertex> currentNodes = setRoots();
		performDownstreamProcessing(currentNodes);
		playSheet.setForest(newForest);
	}
	
	@Override
	public ArrayList<DBCMVertex> traverseDownward(DBCMVertex vert, int levelIndex, ArrayList<DBCMVertex> parentPath, ArrayList<DBCMEdge> parentEdgePath){
		ArrayList<DBCMVertex> vertArray = new ArrayList<DBCMVertex>();
		Collection<DBCMEdge> edgeArray = forest.getOutEdges(vert);
		for (DBCMEdge edge: edgeArray){
			DBCMVertex inVert = edge.inVertex;
			if(!masterHash.containsKey(inVert)){
				vertArray.add(inVert);//this is going to be the returned array, so this is all set
				addEdges(edge, vert, inVert);
				
				//now I have to add this new vertex to masterHash.  This requires using the vertHash of the parent child to get path
				Hashtable vertHash = new Hashtable();
				ArrayList<DBCMVertex> newPath = new ArrayList<DBCMVertex>();
				ArrayList<DBCMEdge> newEdgePath = new ArrayList<DBCMEdge>();
				newPath.addAll(parentPath);
				newEdgePath.addAll(parentEdgePath);
				newPath.add(inVert);
				newEdgePath.add(edge);
				vertHash.put(distanceString, levelIndex);
				vertHash.put(pathString, newPath);
				vertHash.put(edgePathString, newEdgePath);
				masterHash.put(inVert, vertHash);
			}
			//This is the key piece for creating a tree
			//if the node has already been added, but has been added on this level, I need to duplicate the node and add it
			else if (masterHash.containsKey(inVert) && nextNodes.contains(inVert)){
				addEdges(edge, vert, inVert);
				
			}
		}
		
		
		//if the vertArray is null, I'm going to add a key saying that it is a leaf of the tree
		//this will be used in giving network value in distancedownstreaminserter
		if(vertArray.size()==0){
			Hashtable parentHash = (Hashtable) masterHash.get(vert);
			parentHash.put(leafString, "Leaf");
		}
		
		return vertArray;
	}
	
	private void addEdges(DBCMEdge edge, DBCMVertex vert, DBCMVertex inVert){
		//need to get all vertices that exist with this uri and create edge downward to new instances of this invert
		String uri = vert.getURI();
		ArrayList<DBCMVertex> vertArray = uriVertHash.get(uri);
		if(vertArray==null){
			newForest.addEdge(new DBCMEdge(vert, inVert, edge.getURI()), vert, inVert);
			addToURIVertHash(inVert);
		}
		else{
			for(DBCMVertex vertex : vertArray){
				DBCMVertex newDownstreamVert = new DBCMVertex(inVert.getURI());
				newForest.addEdge(new DBCMEdge(vertex, newDownstreamVert, edge.getURI()), vertex, newDownstreamVert);
				addToURIVertHash(newDownstreamVert);
			}
		}
	}

	private void addToURIVertHash(DBCMVertex vert){
		String uri = vert.getURI();
		ArrayList<DBCMVertex> vertArray = null;
		if(uriVertHash.containsKey(uri)){ 
			vertArray = uriVertHash.get(uri);
			if(vertArray.contains(vert)){
				System.out.println("Seems like we are adding the same vertex twice...");
			}
			else{
				vertArray.add(vert);
			}
		}
		else{
			vertArray = new ArrayList();
			vertArray.add(vert);
		}
		uriVertHash.put(uri, vertArray);
	}
}
