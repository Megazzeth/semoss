package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import prerna.ui.components.api.IChakraListener;

public abstract class AbstractListener implements IChakraListener {
	
	JComponent parentView = null;
	JComponent rightView = null;
	

	@Override
	public abstract void actionPerformed(ActionEvent arg0); 
	
	@Override
	public abstract void setView(JComponent view);
	

}
