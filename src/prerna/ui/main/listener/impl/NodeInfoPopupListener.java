package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;

import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.NodeInfoPopup;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class NodeInfoPopupListener implements ActionListener{

	GraphPlaySheet ps = null;
	DBCMVertex[] selectedNodes = null;
	

	public NodeInfoPopupListener(GraphPlaySheet p, DBCMVertex[] pickedV){
		ps = p;
		selectedNodes = pickedV;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NodeInfoPopup pop = new NodeInfoPopup(ps, selectedNodes);
		JDesktopPane pane = (JDesktopPane) DIHelper.getInstance().getLocalProp(Constants.DESKTOP_PANE);
		pop.setJDesktopPane(pane);
		pop.runTable();
	}

}
