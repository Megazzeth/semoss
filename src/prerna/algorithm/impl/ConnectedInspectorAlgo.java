package prerna.algorithm.impl;

import prerna.algorithm.api.IAlgorithm;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IPlaySheet;

public class ConnectedInspectorAlgo implements IAlgorithm {

	GraphPlaySheet gps = null;
	
	@Override
	public void setPlaySheet(IPlaySheet graphPlaySheet) {
		gps = (GraphPlaySheet)graphPlaySheet;
	}

	@Override
	public String[] getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() {
		// get the forest
		// add the same nodes from the forest into this SimpleDirectedGraph
		// run the connectivity inspector
		// do the sets
		
	}

	@Override
	public String getAlgoName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
