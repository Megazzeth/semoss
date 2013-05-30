package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.LayoutMenuItem;

public class LayoutMenuListener implements ActionListener {

	public static LayoutMenuListener instance = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected LayoutMenuListener()
	{
		
	}
	
	public static LayoutMenuListener getInstance()
	{
		if(instance == null)
			instance = new LayoutMenuListener();
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		LayoutMenuItem item = (LayoutMenuItem)e.getSource();
		try{
			item.paintLayout();
		}catch(Exception ex){
			logger.warn(ex);
		}
	}
}
