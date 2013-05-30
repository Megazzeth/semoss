package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IPlaySheet;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;


// implements the minimum spanning tree

public class MousePickingPopupMenuListener implements ActionListener {

	IPlaySheet ps = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	public void setPlaysheet(IPlaySheet ps)
	{
		this.ps = ps;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// changes mouse based on what is selected		
		GraphPlaySheet ps2 = (GraphPlaySheet)ps;
		((ModalGraphMouse)ps2.getView().getGraphMouse()).setMode(Mode.PICKING);
	}
	
}
