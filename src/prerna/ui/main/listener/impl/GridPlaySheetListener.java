package prerna.ui.main.listener.impl;

import javax.swing.JInternalFrame;
import javax.swing.JToggleButton;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;

import prerna.ui.components.GridPlaySheet;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

public class GridPlaySheetListener implements InternalFrameListener {

	// singleton
	
	public static GridPlaySheetListener listener = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected GridPlaySheetListener()
	{
		
	}
	
	public static GridPlaySheetListener getInstance()
	{
		if(listener == null)
			listener = new GridPlaySheetListener();
		return listener;
	}
	
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// get the playsheet that is being activated
		logger.info("Internal Frame Activated >>>> ");
		JInternalFrame jf = e.getInternalFrame();
		GridPlaySheet ps = (GridPlaySheet)jf;

		//  setting up active playsheet
		QuestionPlaySheetStore.getInstance().setActiveSheet(ps);
		
		// this should also enable the extend and overlay buttons
		JToggleButton append = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);
		append.setEnabled(true);

		JToggleButton extend = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.EXTEND);
		extend.setEnabled(true);
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
		GridPlaySheet ps = (GridPlaySheet)jf;
		String questionID = ps.getQuestionID();
		
		// fill the nodetype list so that they can choose from
		// remove from store
		// this will also clear out active sheet
		QuestionPlaySheetStore.getInstance().remove(questionID);

		// disable the overlay and extend
		// this should also enable the extend and overlay buttons
		/*
		JToggleButton append = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);
		append.setEnabled(false);
		append.setSelected(false);

		JToggleButton extend = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.EXTEND);
		extend.setEnabled(false);
		extend.setSelected(false);
		*/
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

		JToggleButton extend = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.EXTEND);
		extend.setEnabled(true);

	}

}
