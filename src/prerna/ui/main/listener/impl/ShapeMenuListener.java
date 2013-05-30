package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.ShapeMenuItem;

public class ShapeMenuListener implements ActionListener {

	public static ShapeMenuListener instance = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected ShapeMenuListener()
	{
		
	}
	
	public static ShapeMenuListener getInstance()
	{
		if(instance == null)
			instance = new ShapeMenuListener();
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		ShapeMenuItem item = (ShapeMenuItem)e.getSource();
		item.paintShape();
	}
}
