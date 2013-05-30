package prerna.ui.components;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IPlaySheet;
import prerna.util.Utility;

// abstract base class for performing all of the work around
// getting the specific queries and painting it etc. 

public class LayoutMenuItem extends JMenuItem{
	IPlaySheet ps = null;
	Logger logger = Logger.getLogger(getClass());
	String layout = null;
	
	public LayoutMenuItem(String layout, IPlaySheet ps)
	{
		super(layout);
		this.ps = ps;
		this.layout = layout;
	}
	
	public void paintLayout()
	{
		String oldLayout = ((GraphPlaySheet)ps).getLayoutName();
		((GraphPlaySheet)ps).setLayout(layout);
		boolean success = ((GraphPlaySheet)ps).createLayout();
		if (success) ((GraphPlaySheet)ps).refreshView();
		else {
			Utility.showError("This layout cannot be used with the current graph");
			((GraphPlaySheet)ps).setLayout(oldLayout);
		}
	}
}
