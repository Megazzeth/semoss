package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IChakraListener;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.QuestionPlaySheetStore;

public class ColorShapeClearRefreshListener implements IChakraListener {

	// quite simple
	// when refresh is pressed
	// get the active sheet and call the repaint

	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		System.out.println("Calling action performed - refine view");	
		GraphPlaySheet playSheet = (GraphPlaySheet)QuestionPlaySheetStore.getInstance().getActiveSheet();
		TypeColorShapeTable.getInstance().clearAll();
		playSheet.repaint();
		playSheet.genAllData();
		playSheet.showAll();
	}

	@Override
	public void setView(JComponent view) {

	}


}
