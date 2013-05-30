package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import prerna.om.DBCMVertex;


public class VertexPropertyTableModel extends AbstractTableModel {
	
	VertexFilterData data = null;
	DBCMVertex vertex = null;

	public VertexPropertyTableModel(VertexFilterData data, DBCMVertex vertex)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
		this.vertex = vertex;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.data.propertyNames.length;
	}
	
	public void setVertexFilterData(VertexFilterData data)
	{
		this.data = data;
	}

	public String getColumnName(int index)
	{
		return this.data.propertyNames[index];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		// need to implement this
		return data.getPropertyNumRows(vertex);
	}

	@Override
	public Object getValueAt(int row, int column) {
		// get the value first
		return data.getPropValueAt(vertex, row, column);
	}
	
	public void setValueAt(Object val, int row, int column)
	{
		data.setPropValueAt(vertex, val+"", row, column);
		fireTableDataChanged();
		// sets the value
	}
	
	public Class getColumnClass(int column)
	{
		return data.propClassNames[column];
	}
	public boolean isCellEditable(int row, int column)
	{
		if(column == 1)
			return true;
		return false;
	}	
}
