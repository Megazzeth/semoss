package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class AdvancedImportOptionsListener implements IChakraListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		JPanel advancedPanel = (JPanel) DIHelper.getInstance().getLocalProp(Constants.ADVANCED_IMPORT_OPTIONS_PANEL);
		
		//if button currently says "Show", show panel and set text of button to Hide
		if(button.getText().contains("Show")){
			button.setText(button.getText().replace("Show", "Hide"));
			advancedPanel.setVisible(true);
		}
		
		//if button currently says "Hide", hide panel and set text of button to Show
		else if(button.getText().contains("Hide")){
			button.setText(button.getText().replace("Hide", "Show"));
			advancedPanel.setVisible(false);
		}
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		
	}


}
