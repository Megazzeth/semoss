package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;


public class EdgeAdjusterTableModel extends AbstractTableModel {
	
	VertexFilterData data = null;
	Logger logger = Logger.getLogger(getClass());

	public EdgeAdjusterTableModel(VertexFilterData data)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	public void setVertexFilterData(VertexFilterData data)
	{
		this.data = data;
	}

	public String getColumnName(int index)
	{
		return this.data.edgeTypeNames[index];
	}

	@Override
	public int getRowCount() {
		return data.edgeTypes.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// get the value first
		return data.getEdgeAdjustValueAt(arg0, arg1);
	}
	
	public Class getColumnClass(int column)
	{
		if(column == 0)
			return String.class;
		else
			return Double.class;
	}

	public boolean isCellEditable(int row, int column)
	{
		if(column == 0)
			return false;
		else
			return true;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		data.setEdgeAdjustValueAt(value, row, column);
		fireTableDataChanged();
	}
}
