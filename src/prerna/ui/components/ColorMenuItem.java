package prerna.ui.components;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;
import prerna.util.QuestionPlaySheetStore;

// abstract base class for performing all of the work around
// getting the specific queries and painting it etc. 

public class ColorMenuItem extends JMenuItem{
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	String color = null;
	Logger logger = Logger.getLogger(getClass());

	public ColorMenuItem(String color, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(color);
		this.ps = ps;
		this.color = color;
		this.pickedVertex = pickedVertex;
	}
	
	public void paintColor()
	{
		TypeColorShapeTable tcst = TypeColorShapeTable.getInstance();
		for(int vertIndex = 0;vertIndex < pickedVertex.length;vertIndex++)
		{
			tcst.addColor(""+pickedVertex[vertIndex].getProperty(Constants.VERTEX_NAME), color);
		}
		GraphPlaySheet playSheet = (GraphPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();

		playSheet.repaint();
	}
}
