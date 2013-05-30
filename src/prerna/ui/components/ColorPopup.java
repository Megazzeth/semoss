package prerna.ui.components;

import javax.swing.JMenu;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.ui.main.listener.impl.ColorMenuListener;

public class ColorPopup extends JMenu{
	
	// need a way to cache this for later
	// sets the visualization viewer
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());
	public ColorPopup(String name, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(name);
		// need to get this to read from popup menu
		this.ps = ps;
		this.pickedVertex = pickedVertex;
		showColors();
	}
	
	public void showColors()
	{
		TypeColorShapeTable tcst = TypeColorShapeTable.getInstance();
		String [] colors = tcst.getAllColors();
		
		for(int colorIndex = 0;colorIndex < colors.length;colorIndex++)
		{
			ColorMenuItem item = new ColorMenuItem(colors[colorIndex], ps, pickedVertex);
			add(item);
			item.addActionListener(ColorMenuListener.getInstance());
		}
	}
	}
