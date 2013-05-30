package prerna.ui.main.listener.impl;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.OPropertyTableModel;
import prerna.ui.components.PropertySpecData;
import prerna.ui.components.PropertyTableModel;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class PlaySheetOWLListener implements InternalFrameListener {

	// singleton
	// Allows setting up of properties and relationships
	
	public static PlaySheetOWLListener listener = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected PlaySheetOWLListener()
	{
		
	}
	
	public static PlaySheetOWLListener getInstance()
	{
		if(listener == null)
			listener = new PlaySheetOWLListener();
		return listener;
	}
	
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// get the playsheet that is being activated
		logger.info("Internal Frame Activated OWL Manipulator>>>> ");
		JInternalFrame jf = e.getInternalFrame();
		GraphPlaySheet ps = (GraphPlaySheet)jf;
		// get the filter data
		PropertySpecData psd = ps.getPredicateData();
		
		OPropertyTableModel model = new OPropertyTableModel(psd);
	
		logger.info("Lable count is " + model.getRowCount());
		// get the table
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.OBJECT_PROP_TABLE);
		table.setModel(model);
		//table.repaint();
		model.fireTableDataChanged();
		logger.debug("Added the Node filter table ");

		PropertyTableModel model2 = new PropertyTableModel(psd);

		// get the table
		JTable table2 = (JTable)DIHelper.getInstance().getLocalProp(Constants.DATA_PROP_TABLE);
		table2.setModel(model2);
		//table.repaint();
		model2.fireTableDataChanged();
		
		logger.debug("Added the Edge filter table ");
		logger.info("Internal Frame Activated OWL >>>> Complete ");
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
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.OBJECT_PROP_TABLE);
		table.setModel(model);
		JTable table3 = (JTable)DIHelper.getInstance().getLocalProp(Constants.DATA_PROP_TABLE);
		table3.setModel(model);
		logger.debug("Cleaned up the filter tables ");
		
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

	}

}
