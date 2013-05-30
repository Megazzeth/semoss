package prerna.ui.components;

import javax.swing.JTextField;

public class ParamField extends JTextField {
	
	String paramName = null;
	String paramType = null;
	
	public ParamField(String paramName)
	{
		setParamName(paramName);
		setText("TBD");
	}
	
	public void setParamName(String paramName)
	{
		this.paramName = paramName;
	}
	
	public String getParamName()
	{
		return this.paramName;
	}

}
