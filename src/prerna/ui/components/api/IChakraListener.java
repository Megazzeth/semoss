package prerna.ui.components.api;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * This is the interface used to standardize all of the listeners used on the main PlayPane.  When the PlayPane is created on 
 * startup, it initializes all of listeners that are tied to public UI components as specified in the 
 * Map.Properties file.  Each listener, when initialized, uses each of these functions with the bindings specified in the 
 * Map.Properties file so as to give the listener a customized startup.
 * 
 */
public interface IChakraListener extends ActionListener {

	// view component
	/**
	 * Sets a JComponent that the listener will access and/or modify when an action event occurs.  
	 * @param view the component that the listener will access
	 */
	public void setView(JComponent view);
	

}
