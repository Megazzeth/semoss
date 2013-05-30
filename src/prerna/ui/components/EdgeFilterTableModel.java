	package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;


public class EdgeFilterTableModel extends AbstractTableModel {
	
	VertexFilterData data = null;
	Logger logger = Logger.getLogger(getClass());

	public EdgeFilterTableModel(VertexFilterData data)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.data.edgeColumnNames.length;
	}
	
	public void setVertexFilterData(VertexFilterData data)
	{
		this.data = data;
	}
	
	

	public String getColumnName(int index)
	{
		return this.data.edgeColumnNames[index];
	}

	@Override
	public int getRowCount() {
		return data.edgeCount;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// get the value first
		return data.getEdgeValueAt(arg0, arg1);
	}
	
	public Class getColumnClass(int column)
	{
		logger.debug("Getting clolumn " + column);
		Object val = data.edgeRows[0][column];
		if(val == null)
		{
			val = "";
			logger.debug(" Value seems to be null " );
		}
		else
			logger.debug("Value is Valid");
		return val.getClass();
	}

	public boolean isCellEditable(int row, int column)
	{
		if(column == 0 || column == 3)
			return true;
		return false;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		logger.debug("Calling the edge filter set value at");
		data.setEdgeValueAt(value, row, column);
		fireTableDataChanged();
	}
	
}
