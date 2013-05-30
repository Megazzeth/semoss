package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IPlaySheet;
import prerna.util.QuestionPlaySheetStore;

public class UndoListener extends SparqlAreaListener {
	
	

	/*
	 * 1. Get information from the textarea for the query
	 * 2. Process the query to create a graph
	 * 3. Create a playsheet and fill it with the respective information
	 * 4. Set all the controls reference within the PlaySheet
	 * 
	 * 
	 */
	// where all the parameters are set
	// this will implement a cardlayout and then on top of that the param panel
	JPanel paramPanel = null; 
	
	// right hand side panel
	JComponent rightPanel = null;
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
			// TODO Auto-generated method stub

			/*JPanel panel = (JPanel)DIHelper.getInstance().getLocalProp(Constants.PARAM_PANEL_FIELD);
			int level = (Integer)DIHelper.getInstance().getLocalProp(Constants.TRAVERSE_LEVEL);
			level--;
			DIHelper.getInstance().setLocalProperty(Constants.TRAVERSE_LEVEL, level);
			System.out.println(level);
			*/
			IPlaySheet playSheet = null;
			playSheet = (IPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();
			playSheet.undoView();
			//Thread playThread = new Thread(playSheet);
			//DIHelper.getInstance().setLocalProperty(Constants.UNDO_BOOLEAN, true);
			//playThread.run();
	}


	public void setRightPanel(JComponent view) {
		this.rightPanel = view;
	}

	
}
