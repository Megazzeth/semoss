package prerna.ui.components;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;


public class TooltipTableModel extends AbstractTableModel {
	
	ControlData data = null;
	Logger logger = Logger.getLogger(getClass());
	String [] columnNames = {"Type", "Property", "Select"};

	public TooltipTableModel(ControlData data)
	{
		//super(data.getRows(), data.columnNames);
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}
	
	public void setControlData(ControlData data)
	{
		this.data = data;
	}
	
	

	public String getColumnName(int index)
	{
		return columnNames[index];
	}

	@Override
	public int getRowCount() {
		return data.rowCount;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// get the value first
		return data.getTooltipValueAt(arg0, arg1);
	}
	
	public Class getColumnClass(int column)
	{
		logger.debug("Getting clolumn " + column);
		Object val = data.toolTipRows[0][column];
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
		if(column == 2)
			return true;
		else
			return false;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		logger.debug("Calling the edge filter set value at");
		data.setTooltipValueAt(value, row, column);
		fireTableDataChanged();
	}
	
}
