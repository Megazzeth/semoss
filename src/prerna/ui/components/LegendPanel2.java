package prerna.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import prerna.om.DBCMVertex;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;

public class LegendPanel2 extends JPanel {
	
	public PaintLabel icon1;
	public PaintLabel icon2;
	public PaintLabel icon3;
	public PaintLabel icon4;
	public PaintLabel icon5;
	public PaintLabel icon6;
	public PaintLabel icon7;
	public PaintLabel icon8;

	
	public JLabel label1;
	public JLabel label2;
	public JLabel label3;
	public JLabel label4;
	public JLabel label5;
	public JLabel label6;
	public JLabel label7;
	public JLabel label8;
	
	public VertexFilterData data = null;

	/**
	 * Create the panel.
	 */
	public LegendPanel2() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUI(new NewScrollBarUI());
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setAutoscrolls(true);
		//scrollPane.setMinimumSize(new Dimension(800,50));
		JPanel panel = new JPanel();
		panel.setToolTipText("You can adjust the shape and color by going to the cosmetics tab on the navigation panel");
		panel.setPreferredSize(new Dimension(500, 40));
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 80, 0, 80, 0, 80, 0, 80, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0};
		panel.setLayout(gbl_panel);
		
		icon1 = new PaintLabel("");
		icon1.setPreferredSize(new Dimension(20, 30));
		icon1.setMinimumSize(new Dimension(20, 30));
		icon1.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon1 = new GridBagConstraints();
		gbc_icon1.insets = new Insets(4, 4, 5, 5);
		gbc_icon1.gridx = 0;
		gbc_icon1.gridy = 0;
		panel.add(icon1, gbc_icon1);
		
		label1 = new JLabel("");
		GridBagConstraints gbc_label1 = new GridBagConstraints();
		label1.setPreferredSize(new Dimension(80, 30));
		label1.setMinimumSize(new Dimension(80, 30));
		label1.setMaximumSize(new Dimension(80, 30));
		gbc_label1.insets = new Insets(4, 0, 5, 5);
		gbc_label1.gridx = 1;
		gbc_label1.gridy = 0;
		panel.add(label1, gbc_label1);
		
		icon2 = new PaintLabel("");
		GridBagConstraints gbc_icon2 = new GridBagConstraints();
		icon2.setPreferredSize(new Dimension(20, 30));
		icon2.setMinimumSize(new Dimension(20, 30));
		icon2.setMaximumSize(new Dimension(20, 30));
		gbc_icon2.insets = new Insets(4, 0, 5, 5);
		gbc_icon2.gridx = 2;
		gbc_icon2.gridy = 0;
		panel.add(icon2, gbc_icon2);
		
		label2 = new JLabel("");
		label2.setPreferredSize(new Dimension(80, 30));
		label2.setMinimumSize(new Dimension(80, 30));
		label2.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label2 = new GridBagConstraints();
		gbc_label2.insets = new Insets(4, 0, 5, 5);
		gbc_label2.gridx = 3;
		gbc_label2.gridy = 0;
		panel.add(label2, gbc_label2);
		
		icon3 = new PaintLabel("");
		icon3.setPreferredSize(new Dimension(20, 30));
		icon3.setMinimumSize(new Dimension(20, 30));
		icon3.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon3 = new GridBagConstraints();
		gbc_icon3.insets = new Insets(4, 0, 5, 5);
		gbc_icon3.gridx = 4;
		gbc_icon3.gridy = 0;
		panel.add(icon3, gbc_icon3);
		
		label3 = new JLabel("");
		label3.setPreferredSize(new Dimension(80, 30));
		label3.setMinimumSize(new Dimension(80, 30));
		label3.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label3 = new GridBagConstraints();
		gbc_label3.insets = new Insets(4, 0, 5, 5);
		gbc_label3.gridx = 5;
		gbc_label3.gridy = 0;
		panel.add(label3, gbc_label3);
		
		icon4 = new PaintLabel("");
		icon4.setPreferredSize(new Dimension(20, 30));
		icon4.setMinimumSize(new Dimension(20, 30));
		icon4.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon4 = new GridBagConstraints();
		gbc_icon4.insets = new Insets(4, 0, 5, 5);
		gbc_icon4.gridx = 6;
		gbc_icon4.gridy = 0;
		panel.add(icon4, gbc_icon4);
		
		label4 = new JLabel("");
		label4.setPreferredSize(new Dimension(80, 30));
		label4.setMinimumSize(new Dimension(80, 30));
		label4.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label4 = new GridBagConstraints();
		gbc_label4.insets = new Insets(4, 0, 5, 0);
		gbc_label4.gridx = 7;
		gbc_label4.gridy = 0;
		panel.add(label4, gbc_label4);
		
		icon5 = new PaintLabel("");
		icon5.setPreferredSize(new Dimension(20, 30));
		icon5.setMinimumSize(new Dimension(20, 30));
		icon5.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon5 = new GridBagConstraints();
		gbc_icon5.insets = new Insets(0, 4, 0, 5);
		gbc_icon5.gridx = 0;
		gbc_icon5.gridy = 1;
		panel.add(icon5, gbc_icon5);
		
		label5 = new JLabel("");
		label5.setPreferredSize(new Dimension(80, 30));
		label5.setMinimumSize(new Dimension(80, 30));
		label5.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label5 = new GridBagConstraints();
		gbc_label5.insets = new Insets(0, 0, 0, 5);
		gbc_label5.gridx = 1;
		gbc_label5.gridy = 1;
		panel.add(label5, gbc_label5);
		
		icon6 = new PaintLabel("");
		icon6.setPreferredSize(new Dimension(20, 30));
		icon6.setMinimumSize(new Dimension(20, 30));
		icon6.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon6 = new GridBagConstraints();
		gbc_icon6.insets = new Insets(0, 0, 0, 5);
		gbc_icon6.gridx = 2;
		gbc_icon6.gridy = 1;
		panel.add(icon6, gbc_icon6);
		
		label6 = new JLabel("");
		label6.setPreferredSize(new Dimension(80, 30));
		label6.setMinimumSize(new Dimension(80, 30));
		label6.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label6 = new GridBagConstraints();
		gbc_label6.insets = new Insets(0, 0, 0, 5);
		gbc_label6.gridx = 3;
		gbc_label6.gridy = 1;
		panel.add(label6, gbc_label6);
		
		icon7 = new PaintLabel("");
		icon7.setPreferredSize(new Dimension(20, 30));
		icon7.setMinimumSize(new Dimension(20, 30));
		icon7.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon7 = new GridBagConstraints();
		gbc_icon7.insets = new Insets(0, 0, 0, 5);
		gbc_icon7.gridx = 4;
		gbc_icon7.gridy = 1;
		panel.add(icon7, gbc_icon7);
		
		label7 = new JLabel("");
		label7.setPreferredSize(new Dimension(80, 30));
		label7.setMinimumSize(new Dimension(80, 30));
		label7.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label7 = new GridBagConstraints();
		gbc_label7.insets = new Insets(0, 0, 0, 5);
		gbc_label7.gridx = 5;
		gbc_label7.gridy = 1;
		panel.add(label7, gbc_label7);
		
		icon8 = new PaintLabel("");
		icon8.setPreferredSize(new Dimension(20, 30));
		icon8.setMinimumSize(new Dimension(20, 30));
		icon8.setMaximumSize(new Dimension(20, 30));
		GridBagConstraints gbc_icon8 = new GridBagConstraints();
		gbc_icon8.insets = new Insets(0, 0, 0, 5);
		gbc_icon8.gridx = 6;
		gbc_icon8.gridy = 1;
		panel.add(icon8, gbc_icon8);
		
		label8 = new JLabel("");
		label8.setPreferredSize(new Dimension(80, 30));
		label8.setMinimumSize(new Dimension(80, 30));
		label8.setMaximumSize(new Dimension(80, 30));
		GridBagConstraints gbc_label8 = new GridBagConstraints();
		gbc_label8.gridx = 7;
		gbc_label8.gridy = 1;
		panel.add(label8, gbc_label8);

	}
	
	public void setFilterData(VertexFilterData data)
	{
		this.data = data;
	}
	
	public void drawLegend()
	{
		// this will draw the legend
		// get the type hash
		// for each node type - find what is the color and shape
		// paint it
		// specify the node type
		Hashtable <String, Vector> nodeHash = data.typeHash;
		String icon = "icon";
		String label = "label";


		try {
			Enumeration <String> nodeTypes = nodeHash.keys();
			for(int idx = 1;nodeTypes.hasMoreElements();idx++)
			{
				String nodeType = nodeTypes.nextElement();
				java.lang.reflect.Field iconField = LegendPanel2.class.getField(icon + idx);				
				java.lang.reflect.Field labelField = LegendPanel2.class.getField(label + idx);				
								
				//LegendPanel2.class.
				PaintLabel pl = (PaintLabel)iconField.get(this);
				Vector <DBCMVertex> vector = nodeHash.get(nodeType);
				DBCMVertex vert = vector.elementAt(0);
				int typeSize = vector.size();
				
				Method method = PaintLabel.class.getMethod("setShape", Shape.class);
				Method method2 = PaintLabel.class.getMethod("setColor", Color.class);
				Method method3 = JLabel.class.getMethod("setToolTipText", String.class);
				Shape shape = TypeColorShapeTable.getInstance().getShapeL(vert.getProperty(Constants.VERTEX_TYPE)+"", vert.getProperty(Constants.VERTEX_NAME) +"");
				Color color = TypeColorShapeTable.getInstance().getColor(vert.getProperty(Constants.VERTEX_TYPE)+"", vert.getProperty(Constants.VERTEX_NAME) +"");
				method.invoke(pl, shape);
				method2.invoke(pl, color);
				method3.invoke(pl, typeSize + "-" + nodeType);
				
				JLabel tl = (JLabel)labelField.get(this);
				Method method4 = JLabel.class.getMethod("setText", String.class);
				Method method5 = JLabel.class.getMethod("setToolTipText", String.class);
				System.out.println("Setting the text to " + nodeType);
				method4.invoke(tl, typeSize + "-" + nodeType);
				method5.invoke(tl, typeSize + "-" + nodeType);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// iterate through the nodes and make modifications
		
		// for the remaining ones, get rid of it labels
		
		
	}

	

}
