package prerna.ui.helpers;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GridFilterData;
import prerna.ui.components.GridTableModel;
import prerna.ui.components.NewScrollBarUI;
import prerna.ui.components.api.IPlaySheet;

public class PlaysheetRemoveRunner implements Runnable{

	IPlaySheet playSheet = null;
	
	public PlaysheetRemoveRunner(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}

	@Override
	public void run() {
		playSheet.removeView();
		GraphPlaySheet gPlaySheet = (GraphPlaySheet) playSheet;
		createRemoveGrid(gPlaySheet);
	}
	
	public void createRemoveGrid(GraphPlaySheet gPlaySheet) {
		
		GridFilterData gfd = new GridFilterData();
		JInternalFrame techMatSheet = new JInternalFrame();
		String[] colNames = new String[1];
		colNames[0] = "Edges Removed";
		gfd.setColumnNames(colNames);
		ArrayList <Object []> list = new ArrayList();
		Vector edgeV = gPlaySheet.edgeVector;
		for(int i = 0; i < edgeV.size(); i++){
			String [] strArray = new String[1];
			strArray[0]=(String) edgeV.get(i);
			list.add(i, strArray);
		}
		gfd.setDataList(list);
		JTable table = new JTable();
		GridTableModel model = new GridTableModel(gfd);
		table.setModel(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getVerticalScrollBar().setUI(new NewScrollBarUI());
		scrollPane.setAutoscrolls(true);
		techMatSheet.setContentPane(scrollPane);
		
		gPlaySheet.jTab.add("Edges Removed", techMatSheet);
		techMatSheet.setClosable(true);
		techMatSheet.setMaximizable(true);
		techMatSheet.setIconifiable(true);
		techMatSheet.setTitle("Edges Removal");
		techMatSheet.pack();
		techMatSheet.setVisible(true);

	}
	

		
	
	
	public void setPlaySheet(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}

}
