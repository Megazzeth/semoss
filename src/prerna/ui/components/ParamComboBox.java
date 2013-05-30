package prerna.ui.components;

import javax.swing.JComboBox;

import aurelienribon.ui.components.AruiStyle;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.swing.SwingStyle;

public class ParamComboBox extends JComboBox {
	
	String fieldName = null;

	public ParamComboBox(String[] array)
	{
		super(array);
	}
	
	public void setParamName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getParamName()
	{
		return this.fieldName;
	}

	

}