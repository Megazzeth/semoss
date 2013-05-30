package prerna.ui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.util.Constants;

public class NodeInfoPopup extends JInternalFrame{

	GraphPlaySheet ps = null;
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());
	GridFilterData gfd = new GridFilterData();
	JTable table = null;
	JDesktopPane pane = null;

	public NodeInfoPopup(GraphPlaySheet p, DBCMVertex[] picked){
		ps = p;
		pickedVertex = picked;
	}
	
	public void runTable(){
		ArrayList listData = setListData();
		setTableData(listData);
		createTable();
	}
	
	public ArrayList<Object[]> setListData(){
		ArrayList<Object[]> retList = new ArrayList();
		retList = addVertTypeCounts(retList);
		retList = addTotalVertCount(retList);
		return retList;
	}
	
	private ArrayList addVertTypeCounts(ArrayList list){
		//first process the data into a hashtable
		Hashtable<String, Integer> typeCounts = new Hashtable();
		for(DBCMVertex v : pickedVertex) {
			String vType = v.getProperty(Constants.VERTEX_TYPE) + "";
			//if the hashtable already contains the type, add to the count
			if(typeCounts.containsKey(vType)){
				int count = typeCounts.get(vType);
				count = count +1;
				typeCounts.put(vType, count);
			}
			//if the hashtable does not contain the type, put the type in with count 1
			else{
				int count = 1;
				typeCounts.put(vType, count);
			}
		}
		
		//use the hashtable to put it in table format
		Iterator hashIt = typeCounts.keySet().iterator();
		while (hashIt.hasNext()){
			String type = hashIt.next() + "";
			int count = typeCounts.get(type);
			Object[] row = new Object[2];
			row[0] = type;
			row[1] = count;
			list.add(row);
		}
		return list;
	}
	
	private ArrayList addTotalVertCount(ArrayList list){
		Object[] row = new Object[2];
		row[0] = "Total Vertex Count";
		row[1] = pickedVertex.length;
		list.add(row);
		return list;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createTable(){
		// create a panel and add the table to it
		try {
			JPanel mainPanel = new JPanel();

			logger.debug("Created the table");
			logger.debug("Added the internal frame listener ");
			table.setAutoCreateRowSorter(true);
			table.setPreferredScrollableViewportSize(new Dimension(250, 200));
			//this.add(new JButton("Yo"));
			GridBagLayout gbl_mainPanel = new GridBagLayout();
			gbl_mainPanel.columnWidths = new int[]{0, 0};
			gbl_mainPanel.rowHeights = new int[]{0, 0};
			gbl_mainPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_mainPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			mainPanel.setLayout(gbl_mainPanel);

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setAutoscrolls(true);
			
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			gbc_scrollPane.gridwidth = 100;
			gbc_scrollPane.gridheight = 100;
			mainPanel.add(scrollPane, gbc_scrollPane);

			mainPanel.setBounds(100, 100, 100, 75);
			this.setContentPane(mainPanel);
			
			this.setTitle("Selected Node Information");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setResizable(true);
			this.setClosable(true);
			this.setBounds(100, 100, 200, 250);
			this.setLocation(0, pane.getHeight()-this.getHeight());
			
			pane.add(this);
			
			this.pack();
			this.setVisible(true);
			this.setSelected(false);
			this.setSelected(true);
			logger.debug("Added the main pane");
			
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setJDesktopPane(JDesktopPane pane) {
		this.pane = pane;
	}

	public void setTableData(ArrayList<Object[]> list){
		table = new JTable();
		gfd.setColumnNames(new String[] {"Property Name", "Value"});
		gfd.setDataList(list);
		GridTableModel model = new GridTableModel(gfd);
		table.setModel(model);
	}
}
