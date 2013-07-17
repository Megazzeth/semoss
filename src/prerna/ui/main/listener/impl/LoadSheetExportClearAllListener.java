package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class LoadSheetExportClearAllListener implements IChakraListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(int i = 1; i <= Constants.MAX_EXPORTS; i++) {
			JComboBox subject = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_SUBJECT_NODE_TYPE_COMBOBOX + i);
			JComboBox object = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + i);
			JComboBox relationship = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_NODE_RELATIONSHIP_COMBOBOX + i);
			
			if(i != 1) {
				DefaultComboBoxModel model = new DefaultComboBoxModel(new String[0]);
				subject.setModel(model);
				subject.setEditable(false);
				object.setModel(model);
				object.setEditable(false);
				relationship.setModel(model);
				relationship.setEditable(false);
				
				subject.setVisible(false);
				object.setVisible(false);
				relationship.setVisible(false);
			}
			
			JList list = (JList) DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			RepoSelectionListener.getInstance().populateLoadSheetExportComboBoxes(list);
			
			JButton addExportButton = (JButton) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_ADD_EXPORT_BUTTON);
			JLabel maxLimitLabel = (JLabel)DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_MAX_LIMIT_MESSAGE);
			addExportButton.setVisible(true);
			maxLimitLabel.setVisible(false);
		}
	}

	@Override
	public void setView(JComponent view) {
	}

}
