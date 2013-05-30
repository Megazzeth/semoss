package prerna.ui.components;

import javax.swing.table.AbstractTableModel;


public class VertexFilterTableModel extends AbstractTableModel {
	
	VertexFilterData data = null;

	public VertexFilterTableModel(VertexFilterData data)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.data.columnNames.length;
	}
	
	public void setVertexFilterData(VertexFilterData data)
	{
		this.data = data;
	}
	
	

	public String getColumnName(int index)
	{
		return this.data.columnNames[index];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		// need to implement this
		return data.getNumRows();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// get the value first
		return data.getValueAt(arg0, arg1);
	}
	
	public void setValueAt(int x, int y)
	{
		// sets the value
	}
	
	public Class getColumnClass(int column)
	{
		return data.classNames[column];
	}
	public boolean isCellEditable(int row, int column)
	{
		if(column == 0)
			return true;
		return false;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		data.setValueAt(value, row, column);
		fireTableDataChanged();
	}
	
}
