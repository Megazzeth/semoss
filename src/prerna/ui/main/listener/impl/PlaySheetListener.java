package prerna.ui.main.listener.impl;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import prerna.ui.components.EdgeFilterTableModel;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.VertexFilterData;
import prerna.ui.components.VertexFilterTableModel;
import prerna.util.CSSApplication;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

public class PlaySheetListener implements InternalFrameListener {

	// singleton
	
	public static PlaySheetListener listener = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected PlaySheetListener()
	{
		
	}
	
	public static PlaySheetListener getInstance()
	{
		if(listener == null)
			listener = new PlaySheetListener();
		return listener;
	}
	
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// get the playsheet that is being activated
		logger.info("Internal Frame Activated >>>> ");
		JInternalFrame jf = e.getInternalFrame();
		GraphPlaySheet ps = (GraphPlaySheet)jf;
		// get the filter data
		VertexFilterData vfd = ps.getFilterData();
		
		VertexFilterTableModel model = new VertexFilterTableModel(vfd);	
		// get the table
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.FILTER_TABLE);
		table.setModel(model);
		//table.repaint();
		model.fireTableDataChanged();
		logger.debug("Added the Node filter table ");

		EdgeFilterTableModel model2 = new EdgeFilterTableModel(vfd);	
		// get the table
		JTable table2 = (JTable)DIHelper.getInstance().getLocalProp(Constants.EDGE_TABLE);
		// need to figure a way to put the renderer here
//<<<<<<< .mine
		//TableColumn col = table2.getColumnModel().getColumn(3);
		//table2.setDefaultRenderer(Double.class, new EdgeFilterRenderer());
		//table2.setModel(model2);
//=======
		table2.setModel(model2);
		/*TableColumn col = table2.getColumnModel().getColumn(3);
		String [] values = new String[]{"A", "B", "C"};
		col.setCellEditor(new DefaultCellEditor(new JComboBox(values)));*/
		//col.setCellRenderer(new EdgeFilterRenderer());
		
		//table2.setDefaultRenderer(Double.class, new EdgeFilterRenderer());
		//table2.setDefaultEditor(Double.class, new EdgeFilterRenderer());
//>>>>>>> .r1954
		//table.repaint();
		model2.fireTableDataChanged();
		logger.debug("Added the Edge filter table ");

		QuestionPlaySheetStore.getInstance().setActiveSheet(ps);

		// fill the nodetype list so that they can choose from
		//box.setEditable(true);
		
		// this should also enable the extend and overlay buttons
		JToggleButton append = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);
		append.setEnabled(true);
		CSSApplication css = new CSSApplication(append,".toggleButton");
		//append.repaint();
		logger.info("Internal Frame Activated >>>> Complete ");
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		// when closed
		// need to empty the tables
		// remove from the question playsheet store
		logger.info("Begin");
		JInternalFrame jf = e.getInternalFrame();
		GraphPlaySheet ps = (GraphPlaySheet)jf;
		String questionID = ps.getQuestionID();
		
		// get the table
		TableModel model = new DefaultTableModel();
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.FILTER_TABLE);
		table.setModel(model);
		JTable table3 = (JTable)DIHelper.getInstance().getLocalProp(Constants.EDGE_TABLE);
		table3.setModel(model);
		logger.debug("Cleaned up the filter tables ");
		
		// also clear the properties table
		JTable table2 = (JTable)DIHelper.getInstance().getLocalProp(Constants.PROP_TABLE);
		table2.setModel(model);
		logger.debug("Cleaned up the property tables ");
		
		// also clear out the extend box as well
		// fill the nodetype list so that they can choose from
		logger.debug("Cleaned up the node list ");

		// remove from store
		// this will also clear out active sheet
		QuestionPlaySheetStore.getInstance().remove(questionID);

		// disable the overlay and extend
		// this should also enable the extend and overlay buttons
		
		JToggleButton append = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);
		//append.setEnabled(false);
		
		/*
		JToggleButton extend = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.EXTEND);
		extend.setEnabled(false);
		extend.setSelected(false);
		*/
		//if the playsheet has a data latency popup associated with it, close it
		if(ps.dataLatencyPopUp!=null && !ps.dataLatencyPopUp.isClosed()){
			ps.dataLatencyPopUp.dispose();
		}
		//if the playsheet has a data latency play popup associated with it, close it
		
		logger.debug("Disabled the extend and append ");
		logger.info("Complete ");
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("Internal Frame opened");
		// this should also enable the extend and overlay buttons
		JToggleButton append = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);
		append.setEnabled(true);

	}

}
