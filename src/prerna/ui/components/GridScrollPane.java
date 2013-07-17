package prerna.ui.components;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GridScrollPane extends JScrollPane{
	
	
	public GridScrollPane(String[] colNames, ArrayList <Object []> list)
	{
		GridFilterData gfd = new GridFilterData();
		gfd.setColumnNames(colNames);
		gfd.setDataList(list);
		JTable table = new JTable();
		table.setAutoCreateRowSorter(true);
		GridTableModel model = new GridTableModel(gfd);
		table.setModel(model);
		this.setViewportView(table);
		this.setAutoscrolls(true);
		this.getVerticalScrollBar().setUI(new NewScrollBarUI());
	}
	
	public void createTable(String[] colNames, ArrayList <Object []> list)
	{
		
	}

}
