package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class DBBrowseListener implements IChakraListener {

	
	Logger log = Logger.getLogger(getClass());
	
	
	public void setModel(JComponent model)
	{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField view = (JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_NAME_FIELD);
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
		//this.view = (JTextField)view;
		
	}


}
