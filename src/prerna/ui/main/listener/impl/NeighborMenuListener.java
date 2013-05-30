package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.NeighborMenuItem;

public class NeighborMenuListener implements ActionListener {

	public static NeighborMenuListener instance = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected NeighborMenuListener()
	{
		
	}
	
	public static NeighborMenuListener getInstance()
	{
		if(instance == null)
			instance = new NeighborMenuListener();
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		NeighborMenuItem item = (NeighborMenuItem)e.getSource();
		item.paintNeighborhood();
	}
}
