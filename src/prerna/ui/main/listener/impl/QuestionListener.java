package prerna.ui.main.listener.impl;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import org.apache.log4j.Logger;

import prerna.ui.components.ParamPanel;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class QuestionListener implements IChakraListener {

	// listens for the change in questions and based on that does a couple of things
	// refreshes the sparql area with the actual question in SPARQL so you can see
	// parses the SPARQL to find out all the parameters
	// adds 
	// refreshes the panel with all the parameters
	
	// list of strings as required by the perspective listener
	// additionally, the SPARQL for each of the question
	Hashtable model = null;
	JPanel view = null; // reference to the param panel
	JTextArea sparqlArea = null;
	Hashtable panelHash = new Hashtable();
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		// TODO Auto-generated method stub
		JComboBox questionBox = (JComboBox)actionevent.getSource();
		// get the currently selected index
		String question = (String)questionBox.getSelectedItem();
		// get the question Hash from the DI Helper to get the question name
		// get the ID for the question
		if(question != null)
		{
			String id = DIHelper.getInstance().getIDForQuestion(question);
			
			// now get the SPARQL query for this id
			String sparql = DIHelper.getInstance().getProperty(id + "_" + Constants.QUERY);
			Properties prop = DIHelper.getInstance().getEngineProp();
			
			String keyToSearch = id + "_" + Constants.LAYOUT;
			String layoutValue = prop.getProperty(keyToSearch);
			JRadioButton rdBtnGraph = (JRadioButton)DIHelper.getInstance().getLocalProp(Constants.RADIO_GRAPH);
			JRadioButton rdBtnGrid = (JRadioButton)DIHelper.getInstance().getLocalProp(Constants.RADIO_GRID);
			JRadioButton rdBtnRaw = (JRadioButton)DIHelper.getInstance().getLocalProp(Constants.RADIO_RAW);
			JToggleButton spql = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.SPARQLBTN);
			logger.info("Sparql is " + sparql);
			if(spql.isSelected()) spql.doClick();
			
			if (layoutValue!=null && layoutValue.equals("prerna.ui.components.GraphPlaySheet"))
			{
				rdBtnGraph.setSelected(true);
				rdBtnGrid.setSelected(false);
				rdBtnRaw.setSelected(false);
			}
			else if (layoutValue!=null && layoutValue.equals("prerna.ui.components.GridPlaySheet"))
			{
				rdBtnGraph.setSelected(false);
				rdBtnGrid.setSelected(true);
				rdBtnRaw.setSelected(false);
			}
			else if (layoutValue!=null && layoutValue.equals("prerna.ui.components.GridRAWPlaySheet"))
			{
				rdBtnGraph.setSelected(false);
				rdBtnGrid.setSelected(false);
				rdBtnRaw.setSelected(true);
			}
			// get all the parameters and names from the SPARQL
			Hashtable paramHash = Utility.getParams(sparql);
			// for each of the params pick out the type now
			Enumeration keys = paramHash.keys();
			Hashtable paramHash2 = new Hashtable();
			while(keys.hasMoreElements())
			{	
				String key = (String)keys.nextElement();
				StringTokenizer tokens = new StringTokenizer(key, "-");
				// the first token is the name of the variable
				String varName = tokens.nextToken();
				String varType = Constants.EMPTY;
				if(tokens.hasMoreTokens())
					varType = tokens.nextToken();
				logger.debug(varName + "<<>>" + varType);
				paramHash2.put(varName, varType);
				paramHash.put(key,"@" + varName + "@");
			}
			sparql = Utility.fillParam(sparql, paramHash);
			logger.debug(sparql  + "<<<");
			Hashtable paramHash3 = Utility.getParams(sparql);
			
			ParamPanel panel = new ParamPanel();
			panel.setParams(paramHash3);
			panel.setParamType(paramHash2);
			panel.paintParam();
			
			// finally add the param to the core panel
			// confused about how to add this need to revisit
			JPanel mainPanel = (JPanel)DIHelper.getInstance().getLocalProp(Constants.PARAM_PANEL_FIELD);
			mainPanel.add(panel, question + "_1"); // mark it to the question index
			CardLayout layout = (CardLayout)mainPanel.getLayout();
			layout.show(mainPanel, question + "_1");
			// get the control panel and get the sparql area
			JTextArea area = (JTextArea)DIHelper.getInstance().getLocalProp(Constants.SPARQL_AREA_FIELD);
			area.setText(sparql);
		}
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.view = (JPanel)view;

	}
}
