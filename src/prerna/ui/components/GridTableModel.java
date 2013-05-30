package prerna.ui.components;

import javax.swing.table.AbstractTableModel;


public class GridTableModel extends AbstractTableModel {
	
	GridFilterData data = null;

	public GridTableModel(GridFilterData data)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		return this.data.columnNames.length;
	}
	
	public void setGridFilterData(GridFilterData data)
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
	
	@Override
	public Class getColumnClass(int column)
	{
		Class returnValue = null;
		if ((column >= 0) && (column < getColumnCount())) {

			boolean exit = false;
			int rowCount = 0;
			while (!exit && rowCount<getRowCount())
			{
				if (getValueAt(rowCount, column)!=null)
				{
					exit = true;
					returnValue = getValueAt(rowCount, column).getClass();
				}
				rowCount++;
			}
			if (!exit)
			{
				returnValue = String.class;
			}
			
		} 
		else {
			returnValue = Object.class;
		}
		return returnValue;
	}
	public boolean isCellEditable(int row, int column)
	{
		return true;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		data.setValueAt(value, row, column);
		fireTableDataChanged();
	}
	
}
