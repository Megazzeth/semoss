package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.NeighborRelationMenuItem;

public class NeighborRelationMenuListener implements ActionListener {

	public static NeighborRelationMenuListener instance = null;
	Logger logger = Logger.getLogger(getClass());
	
	protected NeighborRelationMenuListener()
	{
		
	}
	
	public static NeighborRelationMenuListener getInstance()
	{
		if(instance == null)
			instance = new NeighborRelationMenuListener();
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		NeighborRelationMenuItem item = (NeighborRelationMenuItem)e.getSource();
		item.paintNeighborhood();
	}
}
