package prerna.ui.main.listener.impl;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.ShapeColorTableModel;
import prerna.ui.components.VertexColorShapeData;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class PlaySheetColorShapeListener implements InternalFrameListener {

	// singleton
	// Allows setting up of properties and relationships
	
	public static PlaySheetColorShapeListener listener = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected PlaySheetColorShapeListener()
	{
		
	}
	
	public static PlaySheetColorShapeListener getInstance()
	{
		if(listener == null)
			listener = new PlaySheetColorShapeListener();
		return listener;
	}
	
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// get the playsheet that is being activated
		logger.info("Internal Frame Activated OWL Manipulator>>>> ");
		JInternalFrame jf = e.getInternalFrame();
		GraphPlaySheet ps = (GraphPlaySheet)jf;

		VertexColorShapeData vcsd = ps.getColorShapeData();
		
		ShapeColorTableModel model = new ShapeColorTableModel(vcsd);
	
		logger.info("Lable count is " + model.getRowCount());
		// get the table
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.COLOR_SHAPE_TABLE);
		table.setModel(model);
		
		TableColumn col = table.getColumnModel().getColumn(2);
		col.setCellEditor(new DefaultCellEditor(new JComboBox(TypeColorShapeTable.getInstance().getAllShapes())));

		TableColumn col2 = table.getColumnModel().getColumn(3);
		col2.setCellEditor(new DefaultCellEditor(new JComboBox(TypeColorShapeTable.getInstance().getAllColors())));
		
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
		JTable table = (JTable)DIHelper.getInstance().getLocalProp(Constants.COLOR_SHAPE_TABLE);
		table.setModel(model);
		logger.debug("Cleaned up the filter tables ");
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
