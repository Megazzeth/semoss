package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import prerna.algorithm.impl.DistanceDownstreamProcessor;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import edu.uci.ics.jung.graph.DelegateForest;

public class DistanceDownstreamListener implements ActionListener {

	GraphPlaySheet ps = null;
	DBCMVertex [] pickedVertex = null;
	Hashtable nodeTable = new Hashtable();
	Hashtable edgeTable = new Hashtable();
	Logger logger = Logger.getLogger(getClass());
	
	public DistanceDownstreamListener(GraphPlaySheet p, DBCMVertex[] pickedV){
		ps = p;
		pickedVertex = pickedV;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		//get the forest
		DelegateForest forest = ps.forest;
		
		DistanceDownstreamProcessor pro = new DistanceDownstreamProcessor();
		pro.setForest(forest);
		pro.setSelectedNodes(pickedVertex);
		pro.setPlaySheet(ps);
	
		pro.execute();
		pro.setGridFilterData();
		pro.createTab();
			
	}
}
