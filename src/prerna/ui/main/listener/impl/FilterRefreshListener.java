package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import prerna.ui.components.api.IChakraListener;
import prerna.util.QuestionPlaySheetStore;

public class FilterRefreshListener implements IChakraListener {

	// quite simple
	// when refresh is pressed
	// get the active sheet and call the repaint

	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		System.out.println("Calling action performed - Refine");
		QuestionPlaySheetStore.getInstance().getActiveSheet().refineView();
	}

	@Override
	public void setView(JComponent view) {

	}

}
