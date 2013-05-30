package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.alg.KruskalMinimumSpanningTree;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.transformer.EdgeStrokeTransformer;
import prerna.ui.transformer.SearchEdgeStrokeTransformer;
import prerna.util.Constants;
import prerna.util.Utility;


// implements the minimum spanning tree

public class MSTPopupMenuListener implements ActionListener {

	IPlaySheet ps = null;
	DBCMVertex [] vertices = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	public void setPlaysheet(IPlaySheet ps)
	{
		this.ps = ps;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// gets the view from the playsheet
		// gets the jGraphT graph
		// runs the kruskal on it
		// Creates the edges and sets it on the edge painter
		// repaints it
		// I cannot add this to the interface because not all of them will be forced to have it
		// yes, which means the menu cannot be generic too - I understand
		GraphPlaySheet ps2 = (GraphPlaySheet)ps;
		logger.debug("Getting the base graph");
		Graph graph = ps2.getGraph();
		KruskalMinimumSpanningTree<DBCMVertex, DBCMEdge> kmst = new KruskalMinimumSpanningTree<DBCMVertex, DBCMEdge>(graph);
		
		// get all the edges
		Iterator <DBCMEdge> csIterator = kmst.getEdgeSet().iterator();
		Hashtable <String, DBCMEdge> edgeHash = new Hashtable<String, DBCMEdge>();
		while(csIterator.hasNext())
		{
				DBCMEdge edge = csIterator.next();
				String edgeName = (String)edge.getProperty(Constants.URI);
				edgeHash.put(edgeName, edge);
		}

		if(ps2.searchPanel.btnHighlight.isSelected()){
			SearchEdgeStrokeTransformer tx = (SearchEdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(edgeHash);
		}
		else{
			EdgeStrokeTransformer tx = (EdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(edgeHash);
		}
		
		// repaint it
		ps2.getView().repaint();
		int originalSize = ps2.forest.getEdgeCount();
		int shortestPathSize = kmst.getEdgeSet().size();
		Utility.showMessage("Minimum Spanning Tree uses " + shortestPathSize + " edges out of " + originalSize+ " original edges");
	}
	
}
