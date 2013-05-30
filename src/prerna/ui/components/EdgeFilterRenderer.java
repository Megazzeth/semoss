package prerna.ui.components;

import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

public class EdgeFilterRenderer extends JComboBox implements TableCellRenderer, TableCellEditor {
	
	Logger logger = Logger.getLogger(getClass());
	protected Vector listeners = new Vector();
	int originalValue;
	static Double [] data = {new Double(100),new Double(200)};
	
	public EdgeFilterRenderer()
	{
		super(data);
		//super(JSlider.HORIZONTAL);//, 0, 100);
		//this.setMinimum(0);
		//this.setMaximum(100);
		
		//super()
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// if the column number is 3
		// and the value is not null
		// I need to make a slider set the value and give that back. 
		//Component retComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		//		row, column);
		Component retComponent = null;
		
		Double val = ((Double)value);
		int intVal = val.intValue();
		logger.debug("Value is " + value);
		logger.debug("Row and Column are " + row + "<>" + column);
		if(intVal != 0)
		{
			logger.debug("Value being set is " + intVal);
			setSelectedItem(value);
			//this.setValue(intVal);
			retComponent = this;
			/*this.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent arg0) {
					// TODO Auto-generated method stub
					logger.debug("State Changed >>>>>>>>>>>>> " );
					
				}
				
			});*/
		}
		else
		{
			retComponent = new JLabel("");
		}
		return retComponent;
	}

	@Override
	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		listeners.addElement(arg0);
		
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		fireEditingCanceled();
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return getSelectedItem();//new Integer(this.getValue());
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		listeners.removeElement(arg0);
		
	}

	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		fireEditingStopped();
		return true;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		//setValue(((Double)arg1).intValue());
		logger.debug("Value coming through " + arg1);
		arg0.setRowSelectionInterval(arg3, arg3);
		arg0.setColumnSelectionInterval(arg4, arg4);
		//originalValue = getValue();
		return this;
	}

	protected void fireEditingCanceled() {
		//setValue(originalValue);
		ChangeEvent ce = new ChangeEvent(this);
		for (int i = listeners.size(); i >= 0; i--) {
			((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
		}
	}
	protected void fireEditingStopped() {
		ChangeEvent ce = new ChangeEvent(this);
		for (int i = listeners.size() - 1; i >= 0; i--) {
			((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
		}
	}
}
