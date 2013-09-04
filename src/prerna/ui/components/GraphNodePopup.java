package prerna.ui.components;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.RemoteSparqlEngine;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.main.listener.impl.AdjacentPopupMenuListener;
import prerna.ui.main.listener.impl.DistanceDownstreamListener;
import prerna.ui.main.listener.impl.GraphNodeRankListener;
import prerna.ui.main.listener.impl.GraphPlaySheetExportListener;
import prerna.ui.main.listener.impl.HideVertexPopupMenuListener;
import prerna.ui.main.listener.impl.IslandIdentifierListener;
import prerna.ui.main.listener.impl.LoopIdentifierListener;
import prerna.ui.main.listener.impl.MSTPopupMenuListener;
import prerna.ui.main.listener.impl.MousePickingPopupMenuListener;
import prerna.ui.main.listener.impl.MouseTransformPopupMenuListener;
import prerna.ui.main.listener.impl.NodeInfoPopupListener;
import prerna.ui.main.listener.impl.UnHideVertexPopupMenuListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class GraphNodePopup extends JPopupMenu {
	
	// sets the visualization viewer
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());
	Component comp = null;
	int x;
	int y;
	
	// core class for neighbor hoods etc.
	public GraphNodePopup(IPlaySheet ps, DBCMVertex [] pickedVertex, Component comp, int x, int y)
	{
		super();
		// need to get this to read from popup menu
		this.ps = ps;
		this.pickedVertex = pickedVertex;
		this.comp = comp;
		this.x = x;
		this.y = y;
		JMenuItem item = null;
		
		/*NeighborhoodDataPainter dlistener = new NeighborhoodDataPainter();
		dlistener.setPlaysheet(ps);
		dlistener.setDBCMVertex(pickedVertex);
		logger.info("Picked Vertex set to " + pickedVertex);
		item = add("Append Data To System");
		item.setEnabled(pickedVertex.length > 0);
		item.addActionListener(dlistener);
		*/
		
		MSTPopupMenuListener mstListener = new MSTPopupMenuListener();
		mstListener.setPlaysheet(ps);
		item = add("Show Minimum Spanning Tree");
		item.addActionListener(mstListener);

		JMenu highAdj = new JMenu("Highlight Adjacent");
		item = add(highAdj);
		item.setEnabled(true);
		
		AdjacentPopupMenuListener aListener = new AdjacentPopupMenuListener();
		aListener.setPlaysheet(ps);
		aListener.setDBCMVertex(pickedVertex);
		
		JMenuItem highAdjBoth = new JMenuItem("Upstream and Downstream");
		highAdjBoth.setName("Upstream and Downstream");//used by the listener
		highAdjBoth.setEnabled(pickedVertex.length > 0);
		highAdjBoth.addActionListener(aListener);
		

		JMenuItem highAdjDown = new JMenuItem("Downstream");
		highAdjDown.setName("Downstream");//used by the listener
		highAdjDown.setEnabled(pickedVertex.length > 0);
		highAdjDown.addActionListener(aListener);

		JMenuItem highAdjUp = new JMenuItem("Upstream");
		highAdjUp.setName("Upstream");//used by the listener
		highAdjUp.setEnabled(pickedVertex.length > 0);
		highAdjUp.addActionListener(aListener);
		
		highAdj.add(highAdjBoth);
		highAdj.add(highAdjDown);
		highAdj.add(highAdjUp);

		addSeparator();
		
		MouseTransformPopupMenuListener mtl = new MouseTransformPopupMenuListener();
		mtl.setPlaysheet(ps);
		item = add("Move Graph");
		item.addActionListener(mtl);

		MousePickingPopupMenuListener mpl = new MousePickingPopupMenuListener();
		mpl.setPlaysheet(ps);
		item = add("Pick Graph");
		item.addActionListener(mpl);

		addSeparator();
		
		GraphPlaySheetExportListener exp = new GraphPlaySheetExportListener();
		exp.setPlaysheet(ps);
		item = add("Convert to Table");
		item.addActionListener(exp);
		
		NodeInfoPopupListener nip = new NodeInfoPopupListener((GraphPlaySheet) ps, pickedVertex);
		item = add("Show Selected Node Information");
		item.addActionListener(nip);
		/*
		GraphExportListener2 exp2 = new GraphExportListener2();
		item = add("Export Graph2");
		item.addActionListener(exp2);
		*/
		addSeparator();
		
		//JCheckBoxMenuItem relTypes = new JCheckBoxMenuItem("Relation Types");
		/*RelationPopup popup = new RelationPopup(ps,this.pickedVertex);
		item = add(popup);
		item.setEnabled(pickedVertex.length > 0);
	
		//JCheckBoxMenuItem relTypes = new JCheckBoxMenuItem("Relation Types");
		RelationInstancePopup popup2 = new RelationInstancePopup(ps,this.pickedVertex);
		item = add(popup2);
		item.setEnabled(pickedVertex.length > 0);

		SubjectPopup popup3 = new SubjectPopup(ps,this.pickedVertex);
		item = add(popup3);
		item.setEnabled(pickedVertex.length > 0);

		SubjectInstancePopup popup4 = new SubjectInstancePopup(ps,this.pickedVertex);
		item = add(popup4);
		item.setEnabled(pickedVertex.length > 0);

		RelationPredictPopup popup5 = new RelationPredictPopup("Predict Relation", ps,this.pickedVertex);
		item = add(popup5);
		item.setEnabled(pickedVertex.length == 2);
		*/
		
		String fromNode = "";
		if(this.pickedVertex.length > 0)
			fromNode = "All " + Utility.getClassName(pickedVertex[0].getURI()) + "(s) ";
		TFRelationPopup popup6 = new TFRelationPopup("Traverse Freely: " + fromNode, ps,this.pickedVertex);
		item = add(popup6);
		item.setEnabled(pickedVertex.length > 0);
		

		
		fromNode = "";
		if(this.pickedVertex.length > 0)
			fromNode = Utility.getInstanceName(pickedVertex[0].getURI());
		TFInstanceRelationPopup popup13 = new TFInstanceRelationPopup("Traverse Freely: " + fromNode, ps,this.pickedVertex);
		item = add(popup13);
		item.setEnabled(pickedVertex.length > 0);
		
		fromNode = "";
		if(this.pickedVertex.length > 0)
			fromNode = Utility.getInstanceName(pickedVertex[0].getURI());
		TFInstanceRelationInstancePopup tfrip = new TFInstanceRelationInstancePopup("Traverse Instance Freely: " + fromNode, ps,this.pickedVertex);
		item = add(tfrip);
		item.setEnabled(pickedVertex.length > 0);
		
		JMenu algoPop = new JMenu("Perform Algorithms");
		item = add(algoPop);
		item.setEnabled(true);
		
		GraphNodeRankListener gp = new GraphNodeRankListener();
		JMenuItem algoItemNodeRank = new JMenuItem("NodeRank Algorithm");
		algoItemNodeRank.addActionListener(gp);
		algoPop.add(algoItemNodeRank);

		DistanceDownstreamListener ddc = new DistanceDownstreamListener((GraphPlaySheet)ps, this.pickedVertex);
		JMenuItem algoItemDistanceDownstream  = new JMenuItem("Distance Downstream");
		algoItemDistanceDownstream.addActionListener(ddc);
		algoPop.add(algoItemDistanceDownstream);

		LoopIdentifierListener lil = new LoopIdentifierListener((GraphPlaySheet)ps, this.pickedVertex);
		JMenuItem algoItemLoopIdentifier  = new JMenuItem("Loop Identifier");
		algoItemLoopIdentifier.addActionListener(lil);
		algoPop.add(algoItemLoopIdentifier);

		IslandIdentifierListener iil = new IslandIdentifierListener((GraphPlaySheet)ps, this.pickedVertex);
		JMenuItem algoItemIslandIdentifier  = new JMenuItem("Island Identifier");
		algoItemIslandIdentifier.addActionListener(iil);
		algoPop.add(algoItemIslandIdentifier);

		addSeparator();
		ColorPopup popup7 = new ColorPopup("Modify Color ", ps, pickedVertex);
		item = add(popup7);
		item.setEnabled(pickedVertex.length > 0);
		ShapePopup popup8 = new ShapePopup("Modify Shape ", ps, pickedVertex);
		item = add(popup8);
		item.setEnabled(pickedVertex.length > 0);
		LayoutPopup popup9 = new LayoutPopup("Modify Layout ", ps);
		item = add(popup9);
		// add the hider
		addSeparator();
		item = add("Hide Nodes");
		HideVertexPopupMenuListener hvl = new HideVertexPopupMenuListener();
		hvl.setDBCMVertex(pickedVertex);
		hvl.setPlaysheet(ps);
		item.addActionListener(hvl);
		item.setEnabled(pickedVertex.length > 0);
		item = add("Unhide Nodes");
		UnHideVertexPopupMenuListener uhvl = new UnHideVertexPopupMenuListener();
		uhvl.setPlaysheet(ps);
		item.addActionListener(uhvl);

		
		//item.setEnabled(pickedVertex.length > 0);

		
		/*JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JToggleButton button = new JToggleButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				popup6.addRelations("");
				popup6.addRelations("_2");
			}
		});
		panel.add(button);
		panel.add(popup6);
		add(panel);*/
		

		//relTypes.add("A");
		//relTypes.add("A");
		//relTypes.add("A");		
		//add(relTypes);
		// add the relationship menu
		//RelationPopup popup = new RelationPopup(ps,pickedVertex);
		//item.add(popup);
		//relTypes.addActionListener(rl);
		

		
	}
	
	public String dbCheck(Integer nodeLength)
	{
		String dbType = "local";
		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		Object repo = list.getSelectedValue();
		IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repo+"");
		
		if (engine instanceof RemoteSparqlEngine && nodeLength>0)
		{
			dbType = "remote";
		}
		
		
		return dbType;
	}
	
	public boolean checkICD()
	{
		boolean icdCheck = false;
		VertexFilterData vfd = ps.getFilterData();
		String[] nodeTypeArray = vfd.getNodeTypes();
		for (int i=0; i<nodeTypeArray.length;i++)
		{
			if (nodeTypeArray[i].equals("InterfaceControlDocument"))
			{
				icdCheck=true;
				return icdCheck;
			}
		}
		return icdCheck;
		
	}
	
}
