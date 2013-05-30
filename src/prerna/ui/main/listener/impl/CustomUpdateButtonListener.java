package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import prerna.ui.components.UpdateProcessor;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class CustomUpdateButtonListener implements IChakraListener{

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int response = showWarning();
		if(response == 1)
		{
			JTextPane updateSparqlArea = (JTextPane) DIHelper.getInstance().getLocalProp(Constants.UPDATE_SPARQL_AREA);
			
			//get the query
			String query = updateSparqlArea.getText();
			
			//create UpdateProcessor class.  Set the query.  Let it run.
			UpdateProcessor processor = new UpdateProcessor();
			processor.setQuery(query);
			processor.processQuery();
		}
		
	}
	
	public int showWarning(){
		Object[] buttons = {"Cancel", "Continue"};
		JFrame playPane = (JFrame) DIHelper.getInstance().getLocalProp(Constants.MAIN_FRAME);
		int response = JOptionPane.showOptionDialog(playPane, "The update query you are about to run \n" +
				"cannot be undone.  Would you like to continue?", 
				"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[1]);
		
		return response;

	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		
	}


}
