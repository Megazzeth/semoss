package prerna.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StatusPanel extends JPanel {
	public JTextArea statusBox;

	/**
	 * Create the panel.
	 */
	public StatusPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUI(new NewScrollBarUI());
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		statusBox = new JTextArea();
		statusBox.setForeground(Color.GREEN);
		statusBox.setBackground(Color.BLACK);
		GridBagConstraints gbc_statusBox = new GridBagConstraints();
		gbc_statusBox.fill = GridBagConstraints.BOTH;
		gbc_statusBox.gridx = 0;
		gbc_statusBox.gridy = 0;
		panel.add(statusBox, gbc_statusBox);

	}

}
