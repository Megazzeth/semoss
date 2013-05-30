package prerna.ui.components;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;


// core class that will be utilized for vertex filtering

public class GridFilterData {
	
	// need to have vertex and type information
	// everytime a vertex is added here
	// need to figure out a type so that it can show
	// the types are not needed after or may be it is
	// we need a structure which keeps types with vector
	// the vector will have all of the vertex specific to the type
	// additionally, there needs to be another structure so that when I select or deselect something
	// it marks it on the matrix
	// need to come back and solve this one
	Hashtable <String, Vector> typeHash = new Hashtable<String, Vector>();
	public String [] columnNames = null;
	
	public ArrayList<Object []> dataList = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	public Object getValueAt(int row, int column)
	{
		Object [] val = dataList.get(row);
		Object retVal = val[column];
		if(column == 0)
			logger.debug(row + "<>" + column + "<>" + retVal);
		return retVal;
	}
	
	public void setDataList(ArrayList <Object []> dataList)
	{
		this.dataList = dataList;
	}
	
	public void setColumnNames(String [] columnNames)
	{
		this.columnNames = columnNames;
	}
	
	public int getNumRows()
	{
		// use this call to convert the thing to array
		return dataList.size();
	}
	
	public void setValueAt(Object value, int row, int column)
	{
		// ignore this should never be called
	}
}
