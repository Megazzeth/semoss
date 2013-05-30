package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.algorithm.impl.LoopIdentifierProcessor;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import edu.uci.ics.jung.graph.DelegateForest;

public class LoopIdentifierListener implements ActionListener{

	GraphPlaySheet ps = null;
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());
	
	public LoopIdentifierListener(GraphPlaySheet p, DBCMVertex[] pickedV){
		ps = p;
		pickedVertex = pickedV;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		//get the forest
		DelegateForest forest = ps.forest;
		
		LoopIdentifierProcessor pro = new LoopIdentifierProcessor();
		pro.setForest(forest);
		pro.setSelectedNodes(pickedVertex);
		pro.setPlaySheet(ps);
	
		pro.execute();
		//pro.setGridFilterData();
		//pro.createTab();
			
	}

}
