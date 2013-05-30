package prerna.ui.main.listener.impl;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import prerna.ui.components.ParamComboBox;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class SparqlAreaListener extends AbstractListener {
	
	JTextArea sparql = null;
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		// TODO Auto-generated method stub
		// get to the parent view
		// get the param panel
		// get all the parameters
		// convert it into hashtable
		JPanel panel = (JPanel)DIHelper.getInstance().getLocalProp(Constants.PARAM_PANEL_FIELD);
		// get the currently visible panel
		Component [] comps = panel.getComponents();
		JComponent curPanel = null;
		for(int compIndex = 0;compIndex < comps.length && curPanel == null;compIndex++)
			if(comps[compIndex].isVisible())
				curPanel = (JComponent)comps[compIndex];
		
		// get all the param field
		Component [] fields = curPanel.getComponents();
		Hashtable paramHash = new Hashtable();
		for(int compIndex = 0;compIndex < fields.length;compIndex++)
		{
			if(fields[compIndex] instanceof ParamComboBox)
			{	
				String fieldName = ((ParamComboBox)fields[compIndex]).getParamName();
				String fieldValue = ((ParamComboBox)fields[compIndex]).getSelectedItem() + "";
				paramHash.put(fieldName, fieldValue);
			}	
		}
		// now get the text area
		this.sparql.setText(prerna.util.Utility.fillParam(this.sparql.getText(), paramHash));
		
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.sparql = (JTextArea)view;

	}

}
