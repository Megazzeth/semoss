package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;


public class ShapeColorTableModel extends AbstractTableModel {
	
	VertexColorShapeData data = null;
	Logger logger = Logger.getLogger(getClass());

	public ShapeColorTableModel(VertexColorShapeData data)
	{
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return data.scColumnNames.length;
	}
	
	public String getColumnName(int index)
	{
		return data.scColumnNames[index] + "";
	}

	@Override
	public int getRowCount() {
		return data.count;
	}

	@Override
	public Object getValueAt(int row, int column) {
		
		return data.getValueAt(row, column);
	}
	
	public Class getColumnClass(int column)
	{
		return data.shapeColorRows[0][column].getClass();
	}

	public boolean isCellEditable(int row, int column)
	{
		if(column == 2 || column == 3)
			return true;
		else
			return false;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		logger.debug("Calling the shape color value set");
		data.setValueAt(value, row, column);
	}
	
}
