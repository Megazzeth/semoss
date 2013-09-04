package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.EdgePropertyTableModel;
import prerna.ui.components.GraphNodePopup;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.VertexPropertyTableModel;
import prerna.ui.components.api.IChakraListener;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.transformer.SearchVertexLabelFontTransformer;
import prerna.ui.transformer.SearchVertexPaintTransformer;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalLensGraphMouse;
import edu.uci.ics.jung.visualization.picking.MultiPickedState;
import edu.uci.ics.jung.visualization.picking.PickedState;


public class GraphNodeListener extends ModalLensGraphMouse implements IChakraListener
{
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		logger.debug(" Came to action performed here");
	}
	/*
	public void mouseEntered(MouseEvent e)
	{
    	if (e.isMetaDown())
		{

			System.out.println("alksdfj");
    		

		}
		DBCMVertex v=null;
		super.mouseClicked(e);
		VisualizationViewer vCheck = (VisualizationViewer)e.getSource();
		System.out.println(vCheck.getClass());
		Iterator viCheck = vCheck.getPickedVertexState().getPicked().iterator();
		Iterator eCheck = vCheck.getPickedEdgeState().getPicked().iterator();		
        while(viCheck.hasNext())
		{
			v = (DBCMVertex)viCheck.next();
		}
        System.out.println("here");
	}
	*/
	public void mouseClicked(MouseEvent e)
	{
		super.mouseClicked(e);
		logger.debug(e.getModifiers());
		logger.debug(" Clicked");
		logger.debug(e.getSource());
		VisualizationViewer viewer = (VisualizationViewer)e.getSource();
		viewer.repaint();
		
		
		IPlaySheet ps3 = QuestionPlaySheetStore.getInstance().getActiveSheet();
		
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.PROP_TABLE);
		TableModel tm = new DefaultTableModel();
		table.setModel(tm);



		// handle the vertices
		PickedState <DBCMVertex> ps = viewer.getPickedVertexState();
		Iterator <DBCMVertex> it = ps.getPicked().iterator();
		boolean wasSelected = false;
		PickedState <DBCMEdge> ps2 = viewer.getPickedEdgeState();
		Iterator <DBCMEdge> it2 = ps2.getPicked().iterator();
		wasSelected = false;
		while(it2.hasNext())
		{
			DBCMEdge v = it2.next();
			logger.info(" Name  >>> " + v.getProperty(Constants.VERTEX_NAME));
			// this needs to invoke the property table model stuff
			EdgePropertyTableModel pm = new EdgePropertyTableModel(ps3.getFilterData(), v);
			table.setModel(pm);
			//table.repaint();
			pm.fireTableDataChanged();
			logger.debug("Add this in - Edge Table");	
			wasSelected = true;
		}

		DBCMVertex [] vertices = new DBCMVertex[ps.getPicked().size()];
		
		//Need vertex to highlight when click in skeleton mode... Here we need to get the already selected vertices
		//so that we can add to them
		Hashtable vertHash = new Hashtable();
		SearchVertexLabelFontTransformer vlft = null;
		if(((GraphPlaySheet)ps3).searchPanel.btnHighlight.isSelected()) {
			vlft = (SearchVertexLabelFontTransformer) viewer.getRenderContext().getVertexFontTransformer();
			vertHash.putAll(vlft.getVertHash());
		}
		// right now we assume only one is selected
		for(int vertIndex = 0;it.hasNext();vertIndex++)
		{
			DBCMVertex v = it.next();
			vertices[vertIndex] = v;
			//add selected vertices
			vertHash.put(v.getProperty(Constants.URI), v.getProperty(Constants.URI));
			
			logger.info(" Name  >>> " + v.getProperty(Constants.VERTEX_NAME));
			// this needs to invoke the property table model stuff
			
			VertexPropertyTableModel pm = new VertexPropertyTableModel(ps3.getFilterData(),v);
			table.setModel(pm);
			//table.repaint();
			pm.fireTableDataChanged();
			logger.debug("Add this in - Prop Table");
			if(((GraphPlaySheet)ps3).searchPanel.btnHighlight.isSelected()) {
				vlft.setVertHash(vertHash);
				SearchVertexPaintTransformer ptx = (SearchVertexPaintTransformer)viewer.getRenderContext().getVertexFillPaintTransformer();
				ptx.setVertHash(vertHash);
				viewer.repaint();
			}
			wasSelected = true;
		}

		if(e.getButton() == MouseEvent.BUTTON3)
		{
			logger.debug("Button 3 is pressed ");
			if(ps.getPicked().size()==0){
				final Point2D p = e.getPoint();
		        final Point2D ivp = p;

		        GraphElementAccessor pickSupport = viewer.getPickSupport();
		        if(pickSupport != null) {
		            DBCMVertex v = (DBCMVertex) pickSupport.getVertex(viewer.getGraphLayout(), ivp.getX(), ivp.getY());
		            if(v!=null){
		            	PickedState state = new MultiPickedState();
		            	System.out.println("Got vertex on right click");state.pick(v, true);
		            	viewer.setPickedVertexState(state);
		            	vertices = new DBCMVertex[1];
		            	vertices[0] = v;
		            }
		        }
			}
			
			GraphNodePopup popup = new GraphNodePopup(ps3, vertices, e.getComponent(), e.getX(), e.getY());
			
			popup.show(e.getComponent(), e.getX(), e.getY());
			// need to show a menu here
		}

		if(!wasSelected)
		{
			ps.clear();
			//if in the middle of performing data latency play, don't want to reset transformers
			GraphPlaySheet gps = null;
			try{
				gps = (GraphPlaySheet) ps3;
			}catch (Exception ex){
				//ignored
			}
			boolean resetTransformers = true;
		}
		// handle the vertices

		
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		
	}

}
