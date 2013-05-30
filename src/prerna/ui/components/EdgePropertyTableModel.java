package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import prerna.om.DBCMEdge;


public class EdgePropertyTableModel extends AbstractTableModel {
	
	VertexFilterData data = null;
	DBCMEdge edge = null;

	public EdgePropertyTableModel(VertexFilterData data, DBCMEdge edge)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
		this.edge = edge;
	}
	@Override
	public int getColumnCount() {
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
		return data.getEdgeNumRows(edge);
	}

	@Override
	public Object getValueAt(int row, int column) {
		// get the value first
		return data.getPropValueAt(edge, row, column);
	}
	
	public void setValueAt(Object val, int row, int column)
	{
		data.setPropValueAt(edge, val+"", row, column);
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
