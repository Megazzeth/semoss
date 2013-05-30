package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

public class OWLRefreshListener implements IChakraListener {

	// quite simple
	// when refresh is pressed
	// get the active sheet and call the repaint

	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		System.out.println("Calling action performed - Redo");
		
		// set the values from here just in case the user has edited it
		JTextField field = (JTextField)DIHelper.getInstance().getLocalProp(Constants.OBJECT_PROP_STRING);
		DIHelper.getInstance().putProperty(Constants.PREDICATE_URI, field.getText());

		field = (JTextField)DIHelper.getInstance().getLocalProp(Constants.DATA_PROP_STRING);
		DIHelper.getInstance().putProperty(Constants.PROP_URI, field.getText());

		JToggleButton extend = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.EXTEND);
		JToggleButton overlay = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);

		// need to see if there is a neater way to do this
		if(extend.isSelected())
			QuestionPlaySheetStore.getInstance().getActiveSheet().extendView();
		if(overlay.isSelected())
			QuestionPlaySheetStore.getInstance().getActiveSheet().overlayView();			
		else
			QuestionPlaySheetStore.getInstance().getActiveSheet().redoView();
	}

	@Override
	public void setView(JComponent view) {

	}
}
