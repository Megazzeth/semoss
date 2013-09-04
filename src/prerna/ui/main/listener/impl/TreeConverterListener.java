package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GraphToTreeConverter;
import prerna.ui.transformer.VertexLabelFontTransformer;
import prerna.util.Constants;
import prerna.util.DIHelper;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class TreeConverterListener implements ActionListener{

	GraphPlaySheet playSheet;
	GraphToTreeConverter converter;
	public DelegateForest networkForest;
	
	public TreeConverterListener(){
		converter = new GraphToTreeConverter();
	}
	
	public void setPlaySheet(GraphPlaySheet ps){
		this.playSheet = ps;
		networkForest = ps.forest;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		converter.setPlaySheet(playSheet);
		JToggleButton button = (JToggleButton) e.getSource();
		
		//if the button is selected run converter
		if(button.isSelected()){
			converter.execute();
		}
		//if the button is unselected, revert to old forest
		else{
			playSheet.setForest(networkForest);
		}
		boolean success = playSheet.createLayout();
		if(!success){
			int response = showOptionPopup();
			if(response == 1){
				playSheet.setLayout(Constants.FR);
				playSheet.createLayout();
			}
		}
		playSheet.refreshView();
		
	}

	private int showOptionPopup(){
		JFrame playPane = (JFrame) DIHelper.getInstance().getLocalProp(Constants.MAIN_FRAME);
		Object[] buttons = {"Cancel Graph Modification", "Continue With "+Constants.FR};
		int response = JOptionPane.showOptionDialog(playPane, "This layout requires the graph to be in the format of a tree.\nWould you like to revert the layout to " + Constants.FR+ "?", 
				"Convert to Tree", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
		return response;
	}
}
