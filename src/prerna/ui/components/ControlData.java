package prerna.ui.components;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import prerna.util.Constants;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class ControlData{
	
	// for a given types
	// keeps all the properties available
	// key is the vertex type
	// values are all of the properties within this vertex
	Hashtable <String, Hashtable> properties = new Hashtable<String, Hashtable>();
	ArrayList <String> selectedProperties = new ArrayList<String>();
	
	VisualizationViewer viewer = null;

	// this will be utilized for tooltips
	Object [][] toolTipRows = null;
	// this will be utilized for labels
	Object [][] labelRows = null;
	// this will be utilized for color
	Object [][] colorRows = null;
	// this will be utilized for edge color
	Object [][] edgeThicknessRows = null;
	
	// this is utilized for all the labels
	Hashtable <String, Vector> typePropertySelectedList = new Hashtable<String, Vector>();

	// this is utilized for all the labels
	Hashtable <String, String> typePropertyUnSelectedList = new Hashtable<String, String>();

	// this would be utilized for tooltips
	Hashtable <String, Vector> typePropertySelectedListTT = new Hashtable<String, Vector>();
	
	int rowCount = 0;
	
	// these are the properties that are always on
	Hashtable <String, String> propOn = new Hashtable<String, String>();
	
	Logger logger = Logger.getLogger(getClass());
	
	public ControlData()
	{
		// put what we want to show first in all of these things
		propOn.put(Constants.VERTEX_NAME, Constants.VERTEX_NAME);
		//propOn.put(Constants.EDGE_NAME, Constants.EDGE_NAME);
	}

	public void addProperty(String type, String property)
	{
		logger.debug("Adding " + type + "<>" + property);
		Hashtable <String, String> typeProp = new Hashtable<String,String>();
		if(properties.containsKey(type))
			typeProp = properties.get(type);
		if(!typeProp.containsKey(property))
		{
			typeProp.put(property, property);
			rowCount++;
			logger.debug("Adding " + type + "<>" + property);
		}
		properties.put(type, typeProp);
	}
	
	
	
	public void generateAllRows()
	{
		// clear everything first
		//typePropertySelectedList.clear();
		//typePropertySelectedListTT.clear();
		
		
		// columns are - Type - Property - Boolean
		toolTipRows = new Object[rowCount][4];
		labelRows = new Object[rowCount][4];
		
		// Color Rows and have columns
		// Type - Color
		colorRows = new Object[properties.size()][2];
		
		// edge thickness
		// type - Thickness
		edgeThicknessRows = new Object[properties.size()][2];
		
		Enumeration <String> types = properties.keys();
		int rowIndex = 0;
		for(int typeIndex=0;types.hasMoreElements();typeIndex++)
		{
			String type = types.nextElement();
			colorRows[typeIndex][0] = type;
			colorRows[typeIndex][1] = "TBD";

			edgeThicknessRows[typeIndex][0] = type;
			edgeThicknessRows[typeIndex][1] = "TBD";
			// get the next hashtable now
			Hashtable propTable = properties.get(type);
			Enumeration <String> props = propTable.keys();
			for(int propIndex=0;props.hasMoreElements();propIndex++)	
			{
				String prop = props.nextElement();
				if(propIndex == 0)
					toolTipRows[rowIndex][0] = type;
				else
					toolTipRows[rowIndex][0] = "";

				toolTipRows[rowIndex][1] = prop;
				
				boolean foundPropT = findIfPropSelected(typePropertySelectedListTT,type, prop);
				toolTipRows[rowIndex][2] = new Boolean(foundPropT);
				
				if(!foundPropT)
				{
					// show all tooltips by default
					Vector <String> typePropList = new Vector<String>();
					if(typePropertySelectedListTT.containsKey(type))
						typePropList = typePropertySelectedListTT.get(type);
					typePropList.addElement(toolTipRows[rowIndex][1]+"");
					typePropertySelectedListTT.put(type, typePropList);
				}
				toolTipRows[rowIndex][3] = type;
				

				if(propIndex == 0)
					labelRows[rowIndex][0] = type;
				else
					labelRows[rowIndex][0] = "";
				
				labelRows[rowIndex][1] = prop;
				
				boolean foundProp = findIfPropSelected(typePropertySelectedList,type, prop);
				
				labelRows[rowIndex][2] = new Boolean(foundProp);
				
				boolean preSelected = typePropertyUnSelectedList.containsKey(type);
				
				if((propOn.containsKey(prop) && !preSelected) && !foundProp)
				{
					labelRows[rowIndex][2] = new Boolean(true);
					Vector <String> typePropList2 = new Vector<String>();
					if(typePropertySelectedList.containsKey(type))
						typePropList2 = typePropertySelectedList.get(type);
					typePropList2.add(prop);
					typePropertySelectedList.put(type, typePropList2);
				}
				
				labelRows[rowIndex][3] = type;
				logger.debug("Adding Rows -- " + rowIndex + "<>" + type + "<>" + prop);
				rowIndex++;
			}
		}
		//propOn.clear();
	}
	
	private boolean findIfPropSelected(Hashtable <String, Vector> list, String type, String prop)
	{
		logger.debug("Trying to see if property " + prop + " for type " + type +  " is selected");
		boolean retBool = false;
		if(list.containsKey(type))
		{
			Vector <String> typePropList2 = list.get(type);
			for(int propIndex = 0;propIndex < typePropList2.size() && !retBool;propIndex++)
				if(typePropList2.elementAt(propIndex).equalsIgnoreCase(prop))
					retBool = true;
		}
		logger.debug(prop +" selection is " + retBool + "  for type " + type);
		return retBool;
	}

	private boolean findIfPropUnSelected(Hashtable <String, Vector> list, String type, String prop)
	{
		logger.debug("Trying to see if property " + prop + " for type " + type +  " is selected");
		boolean retBool = false;
		if(list.containsKey(type))
		{
			Vector <String> typePropList2 = list.get(type);
			for(int propIndex = 0;propIndex < typePropList2.size() && !retBool;propIndex++)
				if(typePropList2.elementAt(propIndex).equalsIgnoreCase(prop))
					retBool = true;
		}
		logger.debug(prop +" selection is " + retBool + "  for type " + type);
		return retBool;
	}

	public Object getLabelValueAt(int row, int column)
	{
		logger.debug(" Trying to return values - Label " + row + "<>" + column + "<>" + labelRows[row][column]);
		return labelRows[row][column];
	}

	public void setLabelValueAt(Object val, int row, int column)
	{
		String type = labelRows[row][3]+"";
		Vector <String> typePropList = new Vector<String>();
		Vector <String> typeUnPropList = new Vector<String>();

		if(typePropertySelectedList.containsKey(type))
			typePropList = typePropertySelectedList.get(type);
		if(val instanceof Boolean)
		{
			if((Boolean)val)
			{
				typePropList.addElement(labelRows[row][1]+"");
			}
			else
			{
				typePropList.removeElement(labelRows[row][1]+"");
				typePropertyUnSelectedList.put(type, type);
			}
		}
		labelRows[row][column] = val;
		typePropertySelectedList.put(type, typePropList);
		viewer.repaint();
	}

	public Object getTooltipValueAt(int row, int column)
	{
		return toolTipRows[row][column];
	}

	public void setTooltipValueAt(Object val, int row, int column)
	{
		String type = toolTipRows[row][3]+"";
		Vector <String> typePropList = new Vector<String>();
		if(typePropertySelectedListTT.containsKey(type))
			typePropList = typePropertySelectedListTT.get(type);
		if(val instanceof Boolean)
		{
			logger.debug("Value is currently boolean " + val);
			if((Boolean)val)
				typePropList.addElement(toolTipRows[row][1]+"");
			else
				typePropList.removeElement(toolTipRows[row][1]+"");
		}
		toolTipRows[row][column] = val;
		typePropertySelectedListTT.put(type, typePropList);
		viewer.repaint();
	}

	public Object getColorValueAt(int row, int column)
	{
		return colorRows[row][column];
	}

	public void setColorValueAt(Object val, int row, int column)
	{
		colorRows[row][column] = val;
	}

	public Object getEdgeThicknessValueAt(int row, int column)
	{
		return edgeThicknessRows[row][column];
	}

	public void setThicknessValueAt(Object val, int row, int column)
	{
		edgeThicknessRows[row][column] = val;
	}
	
	public Vector<String> getSelectedProperties(String type)
	{
		return typePropertySelectedList.get(type);
	}

	// gives all the tooltip specific properties
	public Vector<String> getSelectedPropertiesTT(String type)
	{
		return typePropertySelectedListTT.get(type);
	}

	public void setViewer(VisualizationViewer viewer)
	{
		this.viewer = viewer;
	}
}
