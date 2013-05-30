package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.ColorMenuItem;

public class ColorMenuListener implements ActionListener {

	public static ColorMenuListener instance = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected ColorMenuListener()
	{
		
	}
	
	public static ColorMenuListener getInstance()
	{
		if(instance == null)
			instance = new ColorMenuListener();
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		ColorMenuItem item = (ColorMenuItem)e.getSource();
		item.paintColor();
	}
}
