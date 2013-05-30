package prerna.ui.components;

import javax.swing.JMenu;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.ui.main.listener.impl.ShapeMenuListener;

public class ShapePopup extends JMenu{
	
	// need a way to cache this for later
	// sets the visualization viewer
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());
	public ShapePopup(String name, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(name);
		// need to get this to read from popup menu
		this.ps = ps;
		this.pickedVertex = pickedVertex;
		showShapes();
	}
	
	public void showShapes()
	{
		TypeColorShapeTable tcst = TypeColorShapeTable.getInstance();
		String [] shapes = tcst.getAllShapes();
		
		for(int colorIndex = 0;colorIndex < shapes.length;colorIndex++)
		{
			ShapeMenuItem item = new ShapeMenuItem(shapes[colorIndex], ps, pickedVertex);
			add(item);
			item.addActionListener(ShapeMenuListener.getInstance());
		}
	}
	}
