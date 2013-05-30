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

public class ShapeMenuItem extends JMenuItem{
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	String shape = null;
	Logger logger = Logger.getLogger(getClass());

	public ShapeMenuItem(String shape, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(shape);
		this.ps = ps;
		this.shape = shape;
		this.pickedVertex = pickedVertex;
	}
	
	public void paintShape()
	{
		TypeColorShapeTable tcst = TypeColorShapeTable.getInstance();
		for(int vertIndex = 0;vertIndex < pickedVertex.length;vertIndex++)
		{
			tcst.addShape(""+pickedVertex[vertIndex].getProperty(Constants.VERTEX_NAME), shape);
		}
		GraphPlaySheet playSheet = (GraphPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();
		playSheet.repaint();
	}
}
