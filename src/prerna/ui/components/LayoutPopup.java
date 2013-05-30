package prerna.ui.components;

import javax.swing.JMenu;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IPlaySheet;
import prerna.ui.main.listener.impl.LayoutMenuListener;
import prerna.util.Constants;

public class LayoutPopup extends JMenu{
	
	// need a way to cache this for later
	// sets the visualization viewer
	IPlaySheet ps = null;
	String [] layoutNames = {Constants.FR, Constants.KK, Constants.SPRING, Constants.ISO, Constants.CIRCLE_LAYOUT, Constants.TREE_LAYOUT, Constants.RADIAL_TREE_LAYOUT};
	
	Logger logger = Logger.getLogger(getClass());
	public LayoutPopup(String name, IPlaySheet ps)
	{
		super(name);
		// need to get this to read from popup menu
		this.ps = ps;
		showLayouts();
	}
	
	public void showLayouts()
	{
		for(int layoutIndex = 0;layoutIndex < layoutNames.length;layoutIndex++)
		{
			LayoutMenuItem item = new LayoutMenuItem(layoutNames[layoutIndex], ps);
			add(item);
			item.addActionListener(LayoutMenuListener.getInstance());
		}
	}
	}
