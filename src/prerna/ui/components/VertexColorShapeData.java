package prerna.ui.components;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;


// core class that will be utilized for vertex filtering

public class VertexColorShapeData {
	
	// need to have vertex and type information
	// everytime a vertex is added here
	// need to figure out a type so that it can show
	// the types are not needed after or may be it is
	// we need a structure which keeps types with vector
	// the vector will have all of the vertex specific to the type
	// additionally, there needs to be another structure so that when I select or deselect something
	// it marks it on the matrix
	// need to come back and solve this one
	Hashtable <String, Vector> typeHash = new Hashtable<String, Vector >();
	
	public Object [] scColumnNames = {"Node", "Instance", "Shape", "Color"};

	public Class[] classNames = {Object.class, Object.class, Object.class};

	// Table rows for vertices
	String [][] shapeColorRows = null;
	
	int count = 0;
	Logger logger = Logger.getLogger(getClass());
	
	public void setTypeHash(Hashtable <String, Vector> typeHash)
	{
		this.typeHash = typeHash;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public Object getValueAt(int row, int column)
	{
		return shapeColorRows[row][column];
	}
		
	public int getNumRows()
	{
		// use this call to convert the thing to array
		return count;
	}
	
	public String [][] fillRows()
	{
			logger.info("Fill Rows Called >>>>>>>>>>>>>>" + count);
			shapeColorRows = new String [count][scColumnNames.length];
			
			Enumeration <String> keys = typeHash.keys();
			int rowCount = 0;
			int keyCount = 0;
			while(keys.hasMoreElements())
			{
				String vertType = keys.nextElement();
				Vector <DBCMVertex> vertVector = typeHash.get(vertType);
				
				for(int vertIndex = 0;vertIndex < vertVector.size();vertIndex++)
				{
					DBCMVertex vert = vertVector.elementAt(vertIndex);
					String vertName = (String)vert.getProperty(Constants.VERTEX_NAME);

					if(vertIndex == 0)
					{
						shapeColorRows[rowCount][0] = vertType;
						shapeColorRows[rowCount][1] = "Select All";					
						shapeColorRows[rowCount][2] = "TBD"; //TypeColorShapeTable.getInstance().getShapeAsString(vertName);
						shapeColorRows[rowCount][3] = "TBD"; //TypeColorShapeTable.getInstance().getColorAsString(vertName);;
						rowCount++;
					}	
					shapeColorRows[rowCount][1] = vertName;
					//shapeColorRows[rowCount][2] = "TBD";
					//shapeColorRows[rowCount][3] = "TBD";
					shapeColorRows[rowCount][2] = TypeColorShapeTable.getInstance().getShapeAsString(vertName);
					shapeColorRows[rowCount][3] = TypeColorShapeTable.getInstance().getColorAsString(vertName);;
					
					logger.debug(">>> " + vertType + "<<>>" + vertName);
					
					// do the logic of already selected color and shape here
					rowCount++;
				}	
				keyCount++;
			}
		logger.info("Fill Rows Complete");
		return shapeColorRows;
	}
	
	// uses URI
	public void setValueAt(Object value, int row, int column)
	{
		// almost always this is the case of setting the color or shape
		
		// if column = 2 = shape
		
		//get the second column to see if its empty
		shapeColorRows[row][column] = value+ "";
		String nodeType = shapeColorRows[row][0];
		
		if(nodeType != null && nodeType.length() > 0)
		{
			// get this nodetype and make all of them negative				
			Vector <DBCMVertex> vertVector = typeHash.get(nodeType);		
			for(int vertIndex = 0;vertIndex < vertVector.size();vertIndex++)
			{
				DBCMVertex vert = vertVector.elementAt(vertIndex);
				String vertName = (String)vert.getProperty(Constants.VERTEX_NAME);
				
				shapeColorRows[row + vertIndex+1][column] = value+"";
				logger.debug("Creating shape " + value + "   For Vertex Name " + vertName);
				addColorShape(column, vertName, value+"");
			}		
			// also add the node type to the store
			//addColorShape(column, nodeType, value+"");
		}
		else
		{
			// only that node
			shapeColorRows[row][column] = value+"";
			String vertName = shapeColorRows[row][1];
			logger.debug("Creating shape " + value + "   For Vertex Name " + vertName);
			addColorShape(column, vertName, value+"");
		}
	}
	
	private void addColorShape(int column, String vertName, String value)
	{
		if(column == 2) // this is shape
			TypeColorShapeTable.getInstance().addShape(vertName, value);
		else if (column == 3)
			TypeColorShapeTable.getInstance().addColor(vertName, value);
	}
}