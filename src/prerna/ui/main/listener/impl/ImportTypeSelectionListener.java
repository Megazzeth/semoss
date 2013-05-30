package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.util.Constants;
import prerna.util.DIHelper;

public class ImportTypeSelectionListener extends AbstractListener {

	public JComponent view = null;
	
	// will have 2 string arrays one for the perspective and one for the question
	Hashtable model = null;
	Logger logger = Logger.getLogger(getClass());
	
	// needs to find what is being selected from event
	// based on that refresh the view of questions for that given perspective
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComboBox bx = (JComboBox)e.getSource();
		JPanel panel = (JPanel)DIHelper.getInstance().getLocalProp(Constants.IMPORT_PANEL);
		JLabel lbl1 = (JLabel)DIHelper.getInstance().getLocalProp(Constants.IMPORT_ENTERDB_LABEL);
		JLabel lbl2 = (JLabel)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_LABEL);
		JTextField jtf1 = (JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_NAME_FIELD);
		JTextField jtf2 = (JTextField)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_FIELD);
		JButton button1 = (JButton)DIHelper.getInstance().getLocalProp(Constants.IMPORT_BUTTON_BROWSE);
		JButton button2 = (JButton)DIHelper.getInstance().getLocalProp(Constants.IMPORT_BUTTON);
		JTextField mapText = (JTextField)DIHelper.getInstance().getLocalProp(Constants.MAP_TEXT_FIELD);
		JButton advancedButton = (JButton)DIHelper.getInstance().getLocalProp(Constants.ADVANCED_IMPORT_OPTIONS_BUTTON);
		JPanel advancedPanel = (JPanel)DIHelper.getInstance().getLocalProp(Constants.ADVANCED_IMPORT_OPTIONS_PANEL);
		String selection = bx.getSelectedItem() + "";
		if(selection.equals("Add to existing database engine"))
		{
			panel.setVisible(true);
			lbl1.setVisible(false);
			lbl2.setVisible(true);
			jtf1.setVisible(false);
			jtf2.setVisible(true);
			mapText.setVisible(false);
			advancedButton.setVisible(false);
			advancedPanel.setVisible(false);
		}
		else if (selection.equals("Create new database engine"))
		{
			panel.setVisible(true);
			lbl1.setVisible(true);
			lbl2.setVisible(true);
			jtf1.setVisible(true);
			jtf2.setVisible(true);
			mapText.setVisible(true);
			advancedButton.setVisible(true);
			if(!advancedPanel.isVisible() && advancedButton.getText().contains("Hide")) 
				advancedButton.setText(advancedButton.getText().replace("Hide", "Show"));
		}
		else if (selection.equals("Select a database import method"))
		{
			panel.setVisible(false);
		}

	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		logger.debug("View is set " + view);
		this.view = view;
		
	}

}
