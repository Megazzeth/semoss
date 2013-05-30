package prerna.ui.main.listener.impl;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;

import prerna.util.Constants;
import prerna.util.DIHelper;

public class StartTrainingListener extends AbstractListener {
	

	@Override
	public void actionPerformed(ActionEvent actionevent) {
		String workingDir = System.getProperty("user.dir");
		JButton htmlButton= (JButton)DIHelper.getInstance().getLocalProp(Constants.HTML_TRAINING_BUTTON);
		JButton pptButton= (JButton)DIHelper.getInstance().getLocalProp(Constants.PPT_TRAINING_BUTTON);
		File file = null;
		if (actionevent.getSource() == htmlButton)
		{
			file = new File(workingDir+"/training/html/Level1-Training/index.html");
		}
		else if (actionevent.getSource() == pptButton)
		{
			file = new File(workingDir+"/training/powerpoint/MHSGraphTool-L1-20130301.ppsx");
		}
		
		Desktop desktop = null;
		 if (Desktop.isDesktopSupported()) {
		        desktop = Desktop.getDesktop();
		        try {
					desktop.open(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 
		
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub

	}
}