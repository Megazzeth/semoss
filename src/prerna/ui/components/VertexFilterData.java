package prerna.ui.components;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.util.Constants;


// core class that will be utilized for vertex filtering

public class VertexFilterData {
	
	// need to have vertex and type information
	// everytime a vertex is added here
	// need to figure out a type so that it can show
	// the types are not needed after or may be it is
	// we need a structure which keeps types with vector
	// the vector will have all of the vertex specific to the type
	// additionally, there needs to be another structure so that when I select or deselect something
	// it marks it on the matrix
	// need to come back and solve this one
	public Hashtable <String, Vector> typeHash = new Hashtable<String, Vector >();
	
	// all the edges of a specific type
	public Hashtable <String, Vector> edgeTypeHash = new Hashtable<String, Vector>();
	public String [] columnNames = {"Show", "Node", "Instance"};
	public String [] edgeColumnNames = {"Show", "Relation", "Instance"};

	// this is for the slider that adjusts the edge weight
	public String [] edgeTypeNames = {"Edge Type", "Filter"};
	
	// this is for all the properties
	public String [] propertyNames = {"Name ", "Value"};
	public String [] colorNames = {"Node", "Color"};
	
	// these are all the vertex types
	public String [] nodeTypes = null;

	
	public String [] edgeTypes = null;

	// keeps the DBCM vertex to properties data
	Hashtable <DBCMVertex, String[][]> propHash = new Hashtable<DBCMVertex, String [][]>();

	// keeps the DBCM Edge to properties data
	Hashtable <DBCMEdge, String[][]> edgeHash = new Hashtable<DBCMEdge, String [][]>();

	public Class[] classNames = {Boolean.class, Object.class, Object.class};
	// all the properties
	// object because some of this can be double
	public Class[] propClassNames = {Object.class, Object.class};

	
	// puts all the nodes that need to be filtered
	public Hashtable <String, String> filterNodes = new Hashtable<String, String>();
	public Hashtable <String, String> edgeFilterNodes = new Hashtable<String, String>();
	
	// these ensure you are not adding the same vertex more than once
	public Hashtable <String, DBCMVertex> checker = new Hashtable<String, DBCMVertex>();
	public Hashtable <String, DBCMEdge> edgeChecker = new Hashtable<String, DBCMEdge>();

	
	// Table rows for vertices
	String [][] rows = null;
	String [][] colorRows = null;
	// table rows for edges
	Object [][] edgeRows = null;
	
	// table row for edge types and sliders
	Object [][] edgeTypeRows = null;
	
	// just keep the filter string to be utilized for later
	// keep the filter string
	// may be I can lazy this or may be not :)
	// may be just keep all of them as filter, it should not hurt
	String filterString = "";
	
	// count of number of vertices
	public int count = 0;
	int edgeCount = 0;
	
	Logger logger = Logger.getLogger(getClass());
	
	public Object getValueAt(int row, int column)
	{
		String val = rows[row][column];
		if(column == 0)
			logger.debug(row + "<>" + column + "<>" + val);
		if(val != null && val.equalsIgnoreCase("true")) 
			return new Boolean(true);
		else if(val !=null && val.equalsIgnoreCase("false"))
			return new Boolean(false);
		else return val;
	}
	
	public String [] getNodeTypes()
	{
		return nodeTypes;
	}
	
	public Vector<DBCMVertex> getNodes(String nodeType)
	{
		return typeHash.get(nodeType);
	}
	
	public String getFilterString()
	{
		return this.filterString;
	}
	
	public void addVertex(DBCMVertex vert)
	{
		// do the process of adding the vertex
		// use the URI instead of the name
		if(!checker.containsKey(vert.getURI() + ""))
		{
			String vertType = (String)vert.getProperty(Constants.VERTEX_TYPE);
			Vector<DBCMVertex> typeVector = null;
			if(typeHash.containsKey(vertType))
				typeVector = typeHash.get(vertType);
			else
			{
				typeVector = new Vector<DBCMVertex>();
				count++;
			}
			typeVector.addElement(vert);
			typeHash.put(vertType, typeVector);
			checker.put(vert.getURI(), vert);
			count++;
			// add it to the filter string
			// I dont think I am using this anyways
			if(filterString.length() == 0)
				filterString = filterString + "\"" + vert.getProperty(Constants.VERTEX_NAME) + "\"";
			else
				filterString = filterString + ", " + "\"" + vert.getProperty(Constants.VERTEX_NAME) + "\"";
				
		}
	}
	
	public void addEdge(DBCMEdge edge)
	{
		// do the process of adding the vertex
		if(!edgeChecker.containsKey(edge.getURI() + ""))
		{
			String edgeType = (String)edge.getProperty(Constants.EDGE_TYPE);
			Vector<DBCMEdge> typeVector = null;
			if(edgeTypeHash.containsKey(edgeType))
				typeVector = edgeTypeHash.get(edgeType);
			else
			{
				typeVector = new Vector<DBCMEdge>();
				edgeCount++;
			}
			typeVector.addElement(edge);
			edgeTypeHash.put(edgeType, typeVector);
			edgeChecker.put(edge.getURI(), edge);
			edgeCount++;				
		}
		
	}
	

	
	public int getNumRows()
	{
		// use this call to convert the thing to array
		return count;
	}
	
	public String [][] fillRows()
	{
			logger.info("Fill Rows Called >>>>>>>>>>>>>>" + count);
			// change this to type hash + count
			//count = count+typeHash.size();
			
			rows = new String [count][4];
			
			nodeTypes = new String[typeHash.size()];
			
			Enumeration <String> keys = typeHash.keys();
			int rowCount = 0;
			int keyCount = 0;
			while(keys.hasMoreElements())
			{
				String vertType = keys.nextElement();
				nodeTypes[keyCount] = vertType;
				Vector <DBCMVertex> vertVector = typeHash.get(vertType);
				
				for(int vertIndex = 0;vertIndex < vertVector.size();vertIndex++)
				{
					DBCMVertex vert = vertVector.elementAt(vertIndex);
					String vertName = (String)vert.getProperty(Constants.VERTEX_NAME);
					
					if(vertIndex == 0)
					{
						rows[rowCount][0] = "true";
						rows[rowCount][1] = vertType;
						rows[rowCount][2] = "Select All";
						rowCount++;
					}
					rows[rowCount][0] = "true";
					rows[rowCount][2] = vertName;
					logger.debug(">>> " + vertType + "<<>>" + vertName);
					if(filterNodes.contains(vertName))
						rows[rowCount][0] = "false";
					rows[rowCount][3] = vert.getURI();
					rowCount++;
				}	
				keyCount++;
			}
		logger.info("Fill Rows Complete");
		return rows;
	}
	
	// uses URI
	public void setValueAt(Object value, int row, int column)
	{
		rows[row][column] = value + "";
		//get the second column to see if its empty
		String nodeType = rows[row][1];
		if(nodeType != null && nodeType.length() > 0)
		{
			// get this nodetype and make all of them negative				
			Vector <DBCMVertex> vertVector = typeHash.get(nodeType);
			
			for(int vertIndex = 0;vertIndex < vertVector.size();vertIndex++)
			{
				DBCMVertex vert = vertVector.elementAt(vertIndex);
				String vertName = (String)vert.getProperty(Constants.VERTEX_NAME);				
				rows[row + vertIndex+1][0] = value+"";
				if(((Boolean)value))
					filterNodes.remove(vert.getURI()); 
				else
					filterNodes.put(vert.getURI(), vertName);
			}		
		}
		else
		{
			// only that node
			String vertName = rows[row][2];
			String vertURI = rows[row][3];
			DBCMVertex vert = checker.get(vertURI);
			if(vert != null)
			{
				if(((Boolean)value))
					filterNodes.remove(vert.getURI());
				else
					filterNodes.put(vert.getURI(), vertName);	
			}
		}
	}

	// edge related stuff here
	public String [][] fillEdgeRows()
	{
			logger.info("Fill Edge Rows Called >>>>>>>>>>>>>>" + edgeCount);

			//edgeCount = edgeCount + edgeTypeHash.size();
			
			edgeTypes = new String[edgeTypeHash.size()];
			edgeTypeRows = new Object[edgeTypeHash.size()][2];
			edgeRows = new Object[edgeCount][5];
			
			Enumeration <String> keys = edgeTypeHash.keys();
			int rowCount = 0;
			int keyCount = 0;
			while(keys.hasMoreElements())
			{
				String edgeType = keys.nextElement();
				edgeTypes[keyCount] = edgeType;
				edgeTypeRows[keyCount][0] = edgeType;
				edgeTypeRows[keyCount][1] = new Double(100);

				Vector <DBCMEdge> edgeVector = edgeTypeHash.get(edgeType);
				
				for(int edgeIndex = 0;edgeIndex < edgeVector.size();edgeIndex++)
				{

					DBCMEdge edge = edgeVector.elementAt(edgeIndex);
					String edgeName = (String)edge.getProperty(Constants.EDGE_NAME);

					Double value = 0.0;
					if(edge.getProperty(Constants.WEIGHT) != null)
						value = (Double)edge.getProperty(Constants.WEIGHT);

					logger.debug("Adding edge with details of " + edgeType + "<>" + edgeName + "<>" + value);
					edgeRows[rowCount][0] = new Boolean(true);
					if(edgeIndex == 0)
					{
						edgeRows[rowCount][1] = edgeType;
						edgeRows[rowCount][2] = "Select All";
						rowCount++;
					}
					edgeRows[rowCount][0] = new Boolean(true);
					edgeRows[rowCount][2] = edgeName;
					if(value != null)
						edgeRows[rowCount][3] = value;
					else
						edgeRows[rowCount][3] = "NA";
					edgeRows[rowCount][4] = edge.getURI();
					rowCount++;
				}					
				keyCount++;
			}
		logger.info("Fill Rows Complete");
		return rows;
	}


	
	public Object getEdgeValueAt(int row, int column)
	{
		Object retVal = edgeRows[row][column];
		logger.debug(row + "<>" + column + "<>" + retVal);
		return retVal;
	}

	// also uses URI
	public void setEdgeValueAt(Object value, int row, int column)
	{
		edgeRows[row][column] = value;
		//get the second column to see if its empty
		// need to do a check to see if the value is for column 1
		logger.debug("Set adjust value called ");
		logger.debug("Edge Filter Nodes " + edgeFilterNodes.size());
		logger.info("The value set on column for weights is" + edgeRows[row][3]);
		String edgeType = (String) edgeRows[row][1];
		if(edgeType != null && edgeType.length() > 0 && column == 0)
		{
			// get this nodetype and make all of them negative				
			Vector <DBCMEdge> edgeVector = edgeTypeHash.get(edgeType);
			
			for(int edgeIndex = 0;edgeIndex < edgeVector.size();edgeIndex++)
			{
				DBCMEdge edge = edgeVector.elementAt(edgeIndex);
				String edgeName = (String)edge.getProperty(Constants.EDGE_NAME);				
				edgeRows[row + edgeIndex+1][0] = value;
				if(((Boolean)value))
					edgeFilterNodes.remove(edge.getURI()); 
				else
					edgeFilterNodes.put(edge.getURI(), edgeName);
			}		
		}
		else
		{
			// only that node
			String edgeName = edgeRows[row][2] + "";
			String edgeURI = edgeRows[row][4]+"";
			DBCMEdge edge = edgeChecker.get(edgeURI);
			logger.debug("Going to add the URI " + edgeURI);
			if(edge != null)
			{
				if(value instanceof Boolean)
				{
					if(((Boolean)value))
						edgeFilterNodes.remove(edge.getURI());
					else
						edgeFilterNodes.put(edge.getURI(), edgeName);	
				}
				if(value instanceof Double)
				{
					edgeRows[row][column] = value;					
				}
			}
		}
		logger.debug("Edge Filter Nodes " + edgeFilterNodes.size());
	}

	// this is for the slider that adjusts the edge weight
	public Object getEdgeAdjustValueAt(int row, int column)
	{
		Object retVal = edgeTypeRows[row][column];
		logger.debug(row + "<>" + column + "<>" + retVal);
		return retVal;
	}
	
	public void setEdgeAdjustValueAt(Object val, int row, int column)
	{
		edgeTypeRows[row][column] = val;
	}

	// gets all the edge types
	public String[] getEdgeTypes()
	{
		return edgeTypes;
	}
	
	// all of the property related method
	// need to figure out a way to give the DBCM vertex
	
	public int getPropertyNumRows(DBCMVertex vertex)
	{
		if(!propHash.contains(vertex.getProperty(Constants.VERTEX_NAME)))
			fillPropRows(vertex);
		return vertex.getProperty().size();
	}
	
	public void fillPropRows(DBCMVertex vert)
	{
		Hashtable <String, String> propHash = vert.getProperty();
		String [][] propertyRows = new String[propHash.size()][2];
		
		logger.debug(" Filling Property for vertex " + propHash.size());
		
		Enumeration <String> keys = propHash.keys();
		int keyCount = 0;
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			Object value = propHash.get(key);
			
			propertyRows[keyCount][0] = key;
			propertyRows[keyCount][1] = value + "";
			
			keyCount++;
		}
		this.propHash.put(vert, propertyRows);
		
	}

	// need to find which DBCM Vertex
	public Object getPropValueAt(DBCMVertex vert, int row, int column)
	{
		String [][] propertyRows = propHash.get(vert);
		String val = propertyRows[row][column];
		logger.debug(row + "<>" + column + "<>" + val);
		return val;
	}
	
	public void setPropValueAt(DBCMVertex vert, String val, int row, int column)
	{
		// will not be called for now
		// but when called it will set it
		String [][] propertyRows = propHash.get(vert);
		String key = propertyRows[row][0];
		propertyRows[row][column] = val;
		propHash.put(vert, propertyRows);
		vert.putProperty(key, val+"");
		
	
	}
	
	// edge related stuff
	// all of the property related method
	// need to figure out a way to give the DBCM vertex
	
	public int getEdgeNumRows(DBCMEdge edge)
	{
		if(!edgeHash.contains(edge))
			fillEdgeRows(edge);
		return edge.getProperty().size();
	}
	
	public void fillEdgeRows(DBCMEdge edge)
	{
		Hashtable <String, Object> propHash = edge.getProperty();
		String [][] propertyRows = new String[propHash.size()][2];
		
		
		logger.debug(" Number of Properties " + propHash.size());
		Enumeration <String> keys = propHash.keys();
		int keyCount = 0;
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			// need to convert this to actual string later
			String value = propHash.get(key)+"";
			
			propertyRows[keyCount][0] = key;
			propertyRows[keyCount][1] = value;
			
			keyCount++;
		}
		this.edgeHash.put(edge, propertyRows);
		
	}

	// need to find which DBCM Vertex
	public Object getPropValueAt(DBCMEdge edge, int row, int column)
	{
		String [][] propertyRows = edgeHash.get(edge);
		String val = propertyRows[row][column];
		logger.debug(row + "<>" + column + "<>" + val);
		return val;
	}
	
	public void setPropValueAt(DBCMEdge edge, String val, int row, int column)
	{
		// will not be called for now
		// but when called it will set it
		String [][] propertyRows = edgeHash.get(edge);
		String key = propertyRows[row][0];
		propertyRows[row][column] = val;
		edgeHash.put(edge, propertyRows);
		edge.putProperty(key, val+"");
	}
	
	public Hashtable<String, Vector> getEdgeTypeHash(){
		return this.edgeTypeHash;
	}
	public Hashtable<String, Vector> getTypeHash(){
		return this.typeHash;
	}
	
	public Vector<DBCMEdge> getEdges(String edgeType){
		return this.edgeTypeHash.get(edgeType);
	}
	
	// add node to filter so that it will be filtered
	public void addNodeToFilter(DBCMVertex vert)
	{
		String name = vert.getProperty(Constants.VERTEX_NAME) + "";
		filterNodes.put(vert.getURI(), name);
	}
	
	public void unfilterAll()
	{
		filterNodes.clear();
	}
}
