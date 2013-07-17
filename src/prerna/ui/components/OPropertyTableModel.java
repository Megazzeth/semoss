package prerna.ui.components;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import prerna.util.Constants;
import prerna.util.DIHelper;


public class OPropertyTableModel extends AbstractTableModel {
	
	PropertySpecData data = null;
	Logger logger = Logger.getLogger(getClass());
	String uriVal = Constants.PREDICATE_URI;

	public OPropertyTableModel(PropertySpecData data)
	{
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return data.columnNames.length;
	}
	
	public void setControlData(PropertySpecData data)
	{
		this.data = data;
	}

	public String getColumnName(int index)
	{
		return this.data.columnNames2[index];
	}

	@Override
	public int getRowCount() {
		//return data.getPredicateRows();
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// get the value first
		//return data.getValueAt2(arg0, arg1);
		return null;
	}
	
	public Class getColumnClass(int column)
	{
		//logger.debug("Getting clolumn " + column);
		Object val = data.dataList2[0][column];
		return val.getClass();
	}

	public boolean isCellEditable(int row, int column)
	{
		if(column == 1 || column == 2)
			return true;
		else
			return false;
	}
		
	public void setValueAt(Object value, int row, int column)
	{
		logger.debug("Calling the edge filter set value at");
		//data.setValueAt2(uriVal, value, row, column);
		fireTableDataChanged();
		
		// need to figure out a better way to do this
		JTextField field = (JTextField)DIHelper.getInstance().getLocalProp(Constants.OBJECT_PROP_STRING);
		field.setText(DIHelper.getInstance().getProperty(Constants.PREDICATE_URI));

	}
	
}
