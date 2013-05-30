package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class MapBrowseListener implements IChakraListener {

	JTextField view = null;
	
	Logger log = Logger.getLogger(getClass());
	
	
	public void setModel(JComponent model)
	{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//get the name of the source to know which text field to populate
		JButton button = (JButton) e.getSource();
		if(button.getName().equals(Constants.MAP_BROWSE_BUTTON)) 
			view = (JTextField) DIHelper.getInstance().getLocalProp(Constants.MAP_TEXT_FIELD);
		else if (button.getName().equals(Constants.DB_PROP_BROWSE_BUTTON))
			view = (JTextField) DIHelper.getInstance().getLocalProp(Constants.DB_PROP_TEXT_FIELD);
		else if (button.getName().equals(Constants.QUESTION_BROWSE_BUTTON))
			view = (JTextField) DIHelper.getInstance().getLocalProp(Constants.QUESTION_TEXT_FIELD);
		// I just need to show the file chooser and set the action performed to a file chooser class
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new java.io.File("."));
		int retVal = jfc.showOpenDialog((JComponent)e.getSource());
		 //Handle open button action.
	    if (retVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            //This is where a real application would open the file.
            log.info("Opening: " + file.getName() + ".");
            view.setText(file.getAbsolutePath());
        } else {
            log.info("Open command cancelled by user.");
        }
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.view = (JTextField)view;
		
	}


}
