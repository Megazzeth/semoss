package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.components.api.IChakraListener;
import prerna.ui.helpers.EntityFillerForSubClass;
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
			
			JComboBox exportDataSourceComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_SOURCE_COMBOBOX);
			populateLoadSheetExportComboBoxes(exportDataSourceComboBox.getSelectedItem().toString());
			
			JButton addExportButton = (JButton) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_ADD_EXPORT_BUTTON);
			JLabel maxLimitLabel = (JLabel)DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_MAX_LIMIT_MESSAGE);
			addExportButton.setVisible(true);
			maxLimitLabel.setVisible(false);
		}
	}
	
	public void populateLoadSheetExportComboBoxes(String repo) {
		ArrayList<JComboBox> boxes = new ArrayList<JComboBox>();
		for(int i = 1; i <= Constants.MAX_EXPORTS; i++) {
			JComboBox subjectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_SUBJECT_NODE_TYPE_COMBOBOX + i);
			boxes.add(subjectNodeTypeComboBox);
		}
		IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repo);
		EntityFillerForSubClass entityFillerSC = new EntityFillerForSubClass();
		entityFillerSC.boxes = boxes;
		entityFillerSC.engine = engine;
		entityFillerSC.parent = "Concept";
		Thread aThread = new Thread(entityFillerSC);
		aThread.run();
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(new String[0]);
		JComboBox objectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + "1");
		objectNodeTypeComboBox.setModel(model);
		objectNodeTypeComboBox.setEditable(false);
		JComboBox nodeRelationshipComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_NODE_RELATIONSHIP_COMBOBOX + "1");
		nodeRelationshipComboBox.setModel(model);
		nodeRelationshipComboBox.setEditable(false);
	}

	@Override
	public void setView(JComponent view) {
	}

}
