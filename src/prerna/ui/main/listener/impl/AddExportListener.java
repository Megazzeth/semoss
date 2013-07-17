package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class AddExportListener implements IChakraListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String genericSubjectNodeTypeComboBox = "subjectNodeTypeComboBox";
		String genericObjectNodeTypeComboBox = "objectNodeTypeComboBox";
		String genericNodeRelationshipComboBox = "nodeRelationshipComboBox";
		
		for(int i = 2; i <= Constants.MAX_EXPORTS; i++) {
			JComboBox subject = (JComboBox)DIHelper.getInstance().getLocalProp(genericSubjectNodeTypeComboBox + i);
			JComboBox object = (JComboBox)DIHelper.getInstance().getLocalProp(genericObjectNodeTypeComboBox + i);
			JComboBox relationship = (JComboBox)DIHelper.getInstance().getLocalProp(genericNodeRelationshipComboBox + i);
			
			if(subject.isVisible() == false) {
				subject.setVisible(true);
				object.setVisible(true);
				relationship.setVisible(true);
				
				if(i == Constants.MAX_EXPORTS) {
					JButton button = (JButton) arg0.getSource();
					JLabel maxLimitLabel = (JLabel)DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_MAX_LIMIT_MESSAGE);
					button.setVisible(false);
					maxLimitLabel.setVisible(true);
				}
				
				break;
			}
		}
	}

	@Override
	public void setView(JComponent view) {
	}

}
