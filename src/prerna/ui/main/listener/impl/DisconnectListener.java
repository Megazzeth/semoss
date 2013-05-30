package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class DisconnectListener implements IChakraListener {

	JTextField view = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	
	public void setModel(JComponent model)
	{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		// get the selected repository
		Object [] repos = (Object [])list.getSelectedValues();

		for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
		{
			String repoName = repos[repoIndex] +"";
			IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repoName);
			if(engine.isConnected())
			{
				logger.info("Attempting to disconnect " + repoName);
				engine.closeDB();
				logger.info("Successfully disconnected " + repoName);
			}
			else
				logger.info(" Repository is not connected " + repoName);
		}
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.view = (JTextField)view;
		
	}



}
