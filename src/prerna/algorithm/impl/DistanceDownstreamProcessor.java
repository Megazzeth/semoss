package prerna.algorithm.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import prerna.algorithm.api.IAlgorithm;
import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GridFilterData;
import prerna.ui.components.GridTableModel;
import prerna.ui.components.NewScrollBarUI;
import prerna.ui.components.api.IPlaySheet;
import prerna.util.Constants;
import edu.uci.ics.jung.graph.DelegateForest;

public class DistanceDownstreamProcessor implements IAlgorithm {

	DelegateForest forest = null;
	ArrayList<DBCMVertex> selectedVerts = new ArrayList<DBCMVertex>();
	GridFilterData gfd = new GridFilterData();
	GraphPlaySheet playSheet;
	public Hashtable masterHash = new Hashtable();
	public String distanceString = "Distance";
	String pathString = "vertexPath";
	String edgePathString = "edgePathString";
	public String leafString = "leafString";
	String selectedNodes="";
	
	
	@Override
	public void execute() {
		//start with a hashtable of all of the roots, move downward without ever touching the same node twice
		//as we go, put in masterHash with vertHash.  vertHash has distance and path with the key being the actual vertex
		Collection<DBCMVertex> forestRoots = new ArrayList();
		if(selectedVerts.size()!=0){
			int count = 0;
			for(DBCMVertex selectedVert : selectedVerts) {
				forestRoots.add(selectedVert);
				if(count > 0) selectedNodes = selectedNodes +", ";
				selectedNodes = selectedNodes + selectedVert.getProperty(Constants.VERTEX_NAME);
				count++;
			}
		}
		else{
			selectedNodes = "All";
			forestRoots = forest.getRoots();
		}
		
		//use current nodes as the next set of nodes that I will have to traverse downward from.  Starts with root nodes
		ArrayList<DBCMVertex> currentNodes = new ArrayList<DBCMVertex>();
		//use next nodes as the future set of nodes to traverse down from.
		ArrayList<DBCMVertex> nextNodes = new ArrayList<DBCMVertex>();
		
		//start with the root nodes in the masterHash
		for(DBCMVertex vert: forestRoots) {
			Hashtable vertHash = new Hashtable();
			ArrayList<DBCMVertex> path = new ArrayList<DBCMVertex>();
			ArrayList<DBCMVertex> edgePath = new ArrayList<DBCMVertex>();
			path.add(vert);
			vertHash.put(distanceString, 0);
			vertHash.put(pathString, path);
			vertHash.put(edgePathString, edgePath);
			masterHash.put(vert, vertHash);
			currentNodes.add(vert);
		}
		
		int nodeIndex = 0;
		int levelIndex = 1;
		while(!nextNodes.isEmpty() || levelIndex == 1){
			nextNodes.clear();
			while (!currentNodes.isEmpty()){
				nodeIndex = 0;
				DBCMVertex vert = currentNodes.remove(nodeIndex);
				
				Hashtable vertHash = (Hashtable) masterHash.get(vert);
				ArrayList<DBCMVertex> parentPath = (ArrayList<DBCMVertex>) vertHash.get(pathString);
				ArrayList<DBCMEdge> parentEdgePath = (ArrayList<DBCMEdge>) vertHash.get(edgePathString);
				
				ArrayList<DBCMVertex> subsetNextNodes = traverseDownward(vert, levelIndex, parentPath, parentEdgePath);
				
				nextNodes.addAll(subsetNextNodes);
				
				nodeIndex++;
			}
			currentNodes.addAll(nextNodes);
			
			levelIndex++;
		}
		
		
	}
	
	public ArrayList<DBCMVertex> traverseDownward(DBCMVertex vert, int levelIndex, ArrayList<DBCMVertex> parentPath, ArrayList<DBCMEdge> parentEdgePath){
		ArrayList<DBCMVertex> vertArray = new ArrayList<DBCMVertex>();
		Collection<DBCMEdge> edgeArray = forest.getOutEdges(vert);
		for (DBCMEdge edge: edgeArray){
			DBCMVertex inVert = edge.inVertex;
			if(!masterHash.containsKey(inVert)){
				vertArray.add(inVert);//this is going to be the returned array, so this is all set
				
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
		}
		
		//if the vertArray is null, I'm going to add a key saying that it is a leaf of the tree
		//this will be used in giving network value in distancedownstreaminserter
		if(vertArray.size()==0){
			Hashtable parentHash = (Hashtable) masterHash.get(vert);
			parentHash.put(leafString, "Leaf");
		}
		
		return vertArray;
	}
	
	public void setForest(DelegateForest f){
		forest = f;
	}
	
	public void setSelectedNodes(DBCMVertex[] pickedVertices){
		for (int idx = 0; idx< pickedVertices.length ; idx++){
			selectedVerts.add(pickedVertices[idx]);
		}
	}
	
	public void setRootNodesAsSelected(){
		Collection roots = forest.getRoots();
		Iterator<DBCMVertex> rootsIt = roots.iterator();
		while (rootsIt.hasNext()){
			selectedVerts.add(rootsIt.next());
		}
	}

	public boolean addSelectedNode(String pickedVertex, int position){
		Collection<DBCMVertex> vertices = forest.getVertices();
		for(DBCMVertex vert : vertices){
			if(pickedVertex.equals(vert.getProperty(Constants.VERTEX_NAME))){
				selectedVerts.add(position, vert);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setPlaySheet(IPlaySheet ps){
		playSheet = (GraphPlaySheet) ps;
	}

	public void createTab(){

		JTable table = new JTable();
		JInternalFrame nodeRankSheet = new JInternalFrame();
		GridTableModel model = new GridTableModel(gfd);
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		//table.getRowSorter().toggleSortOrder(2);
		//table.getRowSorter().toggleSortOrder(3);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getVerticalScrollBar().setUI(new NewScrollBarUI());
		scrollPane.setAutoscrolls(true);
		nodeRankSheet.setContentPane(scrollPane);
		
		//set tab on graphplaysheet
		playSheet.jTab.add("Hops From " + selectedNodes, nodeRankSheet);
		nodeRankSheet.setClosable(true);
		nodeRankSheet.setMaximizable(true);
		nodeRankSheet.setIconifiable(true);
		nodeRankSheet.setTitle("Hops Downstream From "+selectedNodes);
		playSheet.jTab.setSelectedComponent(nodeRankSheet);
	
		nodeRankSheet.pack();
		nodeRankSheet.setVisible(true);
	}

	public void setGridFilterData(){
		String[] colNames = new String[5];
		colNames[0] = "Vertex Name";
		colNames[1] = "Vertex Type";
		colNames[2] = "Hops";
		colNames[3] = "Root Node";

		//use masterHash to fill tableList and gfd
		ArrayList <Object []> tableList = new ArrayList();
		Iterator masterIt = masterHash.keySet().iterator();
		
		boolean weightCheck = false;
		while (masterIt.hasNext()){
			DBCMVertex vertex = (DBCMVertex) masterIt.next();
			Hashtable vertHash = (Hashtable) masterHash.get(vertex);
			
			int dist = (Integer) vertHash.get(distanceString);
			ArrayList path = (ArrayList) vertHash.get(pathString);
			String root = (String) ((DBCMVertex)path.get(0)).getProperty(Constants.VERTEX_NAME);

			String nodeName = (String) vertex.getProperty(Constants.VERTEX_NAME);
			String nodeType = (String) vertex.getProperty(Constants.VERTEX_TYPE);
			Double multWeight = getMultipliedWeight((ArrayList<DBCMEdge>) vertHash.get(edgePathString));
			Object[] rowArray = {nodeName, nodeType, dist, root, multWeight};
			tableList.add(rowArray);
			if (multWeight > 0) weightCheck = true;
		}

		if (weightCheck == true){
			colNames[4] = "Multiplied Weight";
		}
		else {
			colNames = new String[4];
			colNames[0] = "Vertex Name";
			colNames[1] = "Vertex Type";
			colNames[2] = "Hops";
			colNames[3] = "Root Node";
			//remove all weight columns
			tableList = removeColumn(tableList, 4);
		}
		gfd.setColumnNames(colNames);
		
		gfd.setDataList(tableList);
	}
	
	private ArrayList removeColumn(ArrayList tableList, int column){
		ArrayList newTableList = new ArrayList();
		for(int i = 0; i<tableList.size(); i++){
			Object[] row = (Object[]) tableList.get(i);
			Object[] newRow = new Object[row.length-1];
			int count = 0;
			for(int j = 0; j<row.length;j++){
				if(j!=column){
					newRow[count] = row[j];
					count++;
				}
			}
			newTableList.add(newRow);
		}
		return newTableList;
	}
	
	private Double getMultipliedWeight(ArrayList<DBCMEdge> edgePath){
		int count = 0;
		double total = 1.0;
		Iterator<DBCMEdge> edgeIt = edgePath.iterator();
		while (edgeIt.hasNext()){
			DBCMEdge edge = edgeIt.next();
			if (edge.getProperty().containsKey("weight")){
				total = total* (Double) edge.getProperty("weight");
				count++;
			}
		}
		if(count>0) return total;
		return 0.0;
	}
	
	private int translateString(String freqString){
		int freqInt = 0;
		if(freqString.equalsIgnoreCase("Real-time (user-initiated)")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (monthly)")) freqInt = 720;
		if(freqString.equalsIgnoreCase("Weekly")) freqInt = 168;
		if(freqString.equalsIgnoreCase("TBD")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Monthly")) freqInt = 720;
		if(freqString.equalsIgnoreCase("Batch (daily)")) freqInt = 24;
		if(freqString.equalsIgnoreCase("Batch(Daily)")) freqInt = 24;
		if(freqString.equalsIgnoreCase("Real-time")) freqInt = 0;
		if(freqString.equalsIgnoreCase("n/a")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Transactional")) freqInt = 0;
		if(freqString.equalsIgnoreCase("On Demand")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Event Driven (seconds-minutes)")) freqInt = 60;
		if(freqString.equalsIgnoreCase("TheaterFramework")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Event Driven (Seconds)")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Web services")) freqInt = 0;
		if(freqString.equalsIgnoreCase("TF")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (12/day)")) freqInt = 2;
		if(freqString.equalsIgnoreCase("SFTP")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (twice monthly)")) freqInt = 360;
		if(freqString.equalsIgnoreCase("Daily")) freqInt = 24;
		if(freqString.equalsIgnoreCase("Hourly")) freqInt = 1;
		if(freqString.equalsIgnoreCase("Near Real-time (transaction initiated)")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (three times a week)")) freqInt = 2;
		if(freqString.equalsIgnoreCase("Batch (weekly)")) freqInt = 7;
		if(freqString.equalsIgnoreCase("Near Real-time")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Real Time")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (bi-monthly)")) freqInt = 1440;
		if(freqString.equalsIgnoreCase("Batch (semiannually)")) freqInt = 4392;
		if(freqString.equalsIgnoreCase("Event Driven (Minutes-hours)")) freqInt = 1;
		if(freqString.equalsIgnoreCase("Annually")) freqInt = 8760;
		if(freqString.equalsIgnoreCase("Batch(Monthly)")) freqInt = 720;
		if(freqString.equalsIgnoreCase("Bi-Weekly")) freqInt = 336;
		if(freqString.equalsIgnoreCase("Daily at end of day")) freqInt = 24;
		if(freqString.equalsIgnoreCase("TCP")) freqInt = 0;
		if(freqString.equalsIgnoreCase("event-driven (Minutes-hours)")) freqInt = 1;
		if(freqString.equalsIgnoreCase("Interactive")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Weekly Quarterly")) freqInt = 2184;
		if(freqString.equalsIgnoreCase("Weekly Daily Weekly Weekly Weekly Weekly Daily Daily Daily")) freqInt = 168;
		if(freqString.equalsIgnoreCase("Weekly Daily")) freqInt = 168;
		if(freqString.equalsIgnoreCase("Periodic")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (4/day)")) freqInt = 6;
		if(freqString.equalsIgnoreCase("Batch(Daily/Monthly)")) freqInt = 720;
		if(freqString.equalsIgnoreCase("Weekly; Interactive; Interactive")) freqInt = 168;
		if(freqString.equalsIgnoreCase("interactive")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch (quarterly)")) freqInt = 2184;
		if(freqString.equalsIgnoreCase("Every 8 hours (KML)/On demand (HTML)")) freqInt = 8;
		if(freqString.equalsIgnoreCase("Monthly at beginning of month, or as user initiated")) freqInt = 720;
		if(freqString.equalsIgnoreCase("On demad")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Monthly Bi-Monthly Weekly Weekly")) freqInt = 720;
		if(freqString.equalsIgnoreCase("Quarterly")) freqInt = 2184;
		if(freqString.equalsIgnoreCase("On-demand")) freqInt = 0;
		if(freqString.equalsIgnoreCase("user upload")) freqInt = 0;
		if(freqString.equalsIgnoreCase("1/hour (KML)/On demand (HTML)")) freqInt = 1;
		if(freqString.equalsIgnoreCase("DVD")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Real-time ")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Weekly ")) freqInt = 168;
		if(freqString.equalsIgnoreCase("Annual")) freqInt = 8760;
		if(freqString.equalsIgnoreCase("Daily Interactive")) freqInt = 24;
		if(freqString.equalsIgnoreCase("NFS, Oracle connection")) freqInt = 0;
		if(freqString.equalsIgnoreCase("Batch(Weekly)")) freqInt = 168;
		if(freqString.equalsIgnoreCase("Batch(Quarterly)")) freqInt = 2184;
		if(freqString.equalsIgnoreCase("Batch (yearly)")) freqInt = 8760;
		if(freqString.equalsIgnoreCase("Each user login instance")) freqInt = 0;
		return freqInt;
	}

	@Override
	public String[] getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAlgoName() {
		// TODO Auto-generated method stub
		return "Distance Downstream";
	}
	
}
