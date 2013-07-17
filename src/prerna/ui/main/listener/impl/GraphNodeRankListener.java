package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GridFilterData;
import prerna.ui.components.GridScrollPane;
import prerna.util.QuestionPlaySheetStore;
import edu.uci.ics.jung.algorithms.scoring.PageRank;


// implements the minimum spanning tree

public class GraphNodeRankListener implements ActionListener {

	GraphPlaySheet ps = null;
	DBCMVertex [] vertices = null;
	
	Logger logger = Logger.getLogger(getClass());
	public void setPlaysheet(GraphPlaySheet ps)
	{
		this.ps = ps;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		GraphPlaySheet playSheet = (GraphPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();
		
		//set up page rank
		double alpha = 0.15;
		double tolerance = 0.001;
		int maxIterations = 100;
		PageRank<DBCMVertex, Integer> ranker = new PageRank<DBCMVertex, Integer>(playSheet.forest, alpha);
		
		ranker.setTolerance(tolerance) ;
		ranker.setMaxIterations(maxIterations);
		ranker.evaluate();
		
		Collection<String> col =  playSheet.forest.getVertices();
		
		Iterator it = col.iterator();
		GridFilterData gfd = new GridFilterData();
		JInternalFrame nodeRankSheet = new JInternalFrame();
		String[] colNames = new String[3];
		colNames[0] = "Vertex Name";
		colNames[1] = "Vertex Type";
		colNames[2] = "Page Rank Score";
		gfd.setColumnNames(colNames);
		ArrayList <Object []> list = new ArrayList();
		ArrayList numList = new ArrayList();
		int count = 0;
		
		//process through graph and list out all nodes, type, pagerank
		while (it.hasNext()) {
			DBCMVertex v= (DBCMVertex) it.next();
			String url = v.getURI();
			String[] urlSplit = url.split("/");
			double r = ranker.getVertexScore(v);
			System.out.println(url+r);
			String [] scores = new String[colNames.length];
			scores[0] = urlSplit[urlSplit.length-1];
			scores[1] = urlSplit[urlSplit.length-2];
			scores[2] = Double.toString(r);
			numList.add(r);
			list.add(count, scores);
			count++;
		}
		//need to sort the list so highest page rank shows on top

		//set list
		GridScrollPane dataPane = new GridScrollPane(colNames, list);
		nodeRankSheet.setContentPane(dataPane);
		
		//set tab on graphplaysheet
		playSheet.jTab.add("NodeRank Scores", nodeRankSheet);
		nodeRankSheet.setClosable(true);
		nodeRankSheet.setMaximizable(true);
		nodeRankSheet.setIconifiable(true);
		nodeRankSheet.setTitle("NodeRank Scores");
		nodeRankSheet.pack();
		nodeRankSheet.setVisible(true);
	}
	
	
			
}
