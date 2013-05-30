package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IChakraListener;

public class FileBrowseListener implements IChakraListener {

	JTextField view = null;
	
	Logger log = Logger.getLogger(getClass());
	
	
	public void setModel(JComponent model)
	{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// I just need to show the file chooser and set the action performed to a file chooser class
		JFileChooser jfc = new JFileChooser();
		
		jfc.setMultiSelectionEnabled(true);
		jfc.setCurrentDirectory(new java.io.File("."));
		int retVal = jfc.showOpenDialog((JComponent)e.getSource());
		 //Handle open button action.
	    if (retVal == JFileChooser.APPROVE_OPTION) {
            File[] files = jfc.getSelectedFiles();
            //This is where a real application would open the file.
            String fileNames = "";
            String filePaths = "";
            for(File f : files){
            	fileNames = fileNames + f.getName() + ";";
            	filePaths = filePaths + f.getAbsolutePath() +";";
            }
            log.info("Opening: " + fileNames + ".");
            view.setText(filePaths);
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
