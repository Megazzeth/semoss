package prerna.ui.components;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.components.api.IChakraListener;
import prerna.ui.main.listener.impl.RepoSelectionListener;
import prerna.util.Constants;
import prerna.util.DIHelper;

import aurelienribon.ui.components.AruiStyle;
import aurelienribon.ui.components.Button;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.swing.SwingStyle;

import com.ibm.icu.util.StringTokenizer;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

public class PlayPane extends JFrame {
	// field that imports a file
	public JTextField importFileNameField;
	
	// Panel for filtering data - to be created
	public JPanel filterPanel;
	
	// panel that shows all the properties etc. Need to make provision to plug this all into the lower portion of control panel at some point
	public JPanel outputPanel;
	
	public JPanel inputPanel;
	public JComboBox perspectiveSelector;
	public JComboBox questionSelector;
	
	// need to make this into a card layout so that I can add param panels into it
	public JPanel paramPanel;
	
	public JButton fillButton; // "Remove" button
	public JButton submitButton; // "Create" button
	public JToggleButton showSparql; // "Custom" button
	
	// the core right view
	public RightView rightView;
	
	
	public JPanel graphPanel;
	
	public JPanel imExPanel;
	public JButton importButton;
	public JPanel importPanel;
	public JPanel exportPanel;
	public JPanel settingsPanel;
	public JDesktopPane desktopPane;
	public JScrollPane scrollPane_1;
	public SparqlArea sparqlArea;
	public JList repoList;
	public JTable propertyTable2;
	public JButton refreshButton;
	private JScrollPane scrollPane_2;
	public JTable filterTable;
	public JToggleButton appendButton; //Overlay button
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	public JTable edgeTable;
	public JToggleButton extendButton;
	private JPanel debugPanel;
	private JScrollPane scrollPane_5;
	private JTextArea txtrAasdasdasd;
	
	//public JProgressBar calcBVprogressBar;
	Logger logger = Logger.getLogger(getClass());
	
	public JTable labelTable;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private JScrollPane filterSliders;
	public JTable propertyTable;
	private JScrollPane scrollPane_6;
	public JTable tooltipTable;
	private JLabel lblDisplayTooltip;
	private JLabel lblLabelDisplay;
	public JButton undoBtn; // Undo button
	public JButton fileBrowseBtn;
	public JTextField dbSelectorField;
	private JPanel owlPanel;
	private JLabel lblDataProperties;
	private JLabel lblObjectProperties;
	public JTable objectPropertiesTable;
	public JTable dataPropertiesTable;
	public JTextField dataPropertiesString;
	public JTextField objectPropertiesString;
	public JButton btnRepaintGraph;
	private JScrollPane scrollPane_8;
	private JScrollPane scrollPane_7;
	private JPanel cosmeticPanel;
	private JScrollPane scrollPane_9;
	private JLabel lblColorAndShape;
	private JScrollPane scrollPane_10;
	private JLabel lblSize;
	private JScrollPane scrollPane_11;
	public JTable colorShapeTable;
	public JTable sizeTable;
	public JButton btnColorShape;
	private JPanel panel_6;
	public JButton btnResetDefaults;
	private JLabel lblSectionADefine;
	private JLabel lblSelectAvailable;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private JLabel lblSectionBSupplement;
	private JLabel lblAddsAdditionalGraph;
	private JLabel lblRemovesNodesAnd;
	private JSeparator separator_1;
	private JLabel lblSectionCCustomize;
	private JLabel lblSelectA;
	private JLabel lblSectionBAugment;
	private JSeparator separator_2;
	public JLabel sparqlLbl;
	public JRadioButton rdbtnGraph, rdbtnGrid, rdbtnRaw;
	private JLabel lblAbout;
	private JLabel lblLearningMaterials;
	private JLabel lblHeidiMaterials;
	private JLabel lblNewLabel_2;
	private JPanel helpPanel;
	private JLabel lblRemovesNodesAnd_1;
	private JTextArea aboutArea;
	private JLabel lblReleaseNotes;
	private JTextPane releaseNoteArea;
	private JLabel lblNewLabel_1;
	private JLabel lblInsertData;
	private JLabel lblCustomDelete;
	public JButton btnCustomUpdate;
	private JScrollPane customUpdateScrollPane;
	public JTextPane customUpdateTextPane;
	public JComboBox dbImportTypeComboBox;
	public JPanel dbImportPanel;
	public JLabel dbNameLbl;
	public JLabel selectionFileLbl;
	private JLabel lblDatabaseImportOptions;
	public JButton htmlTrainingBtn;
	public JButton pptTrainingBtn;
	private JLabel lblDesignateBaseUri;
	public JTextField customBaseURItextField;
	public JLabel lblselectCustomMap;
	public JButton mapBrowseBtn;
	public JTextField importMapFileNameField;
	public JPanel advancedImportOptionsPanel;
	private JLabel lblselectCustomProp;
	public JButton dbPropBrowseButton;
	public JTextField dbPropFileNameField;
	private JLabel lblselectCustomQuestionssheet;
	public JButton questionBrowseButton;
	public JTextField questionFileNameField;
	public JButton btnShowAdvancedImportFeatures;
	/**
	 * Launch the application.
	 */
	public void start() throws Exception{

		// load all the listeners
		// cast it to IChakraListener
		// for each listener specify what is the view field - Listener_VIEW
		// for each listener specify the right panel field - Listener_RIGHT_PANEL
		// utilize reflection to get all the fields
		// for each field go into the properties file and find any of the listeners
        scrollPane_1.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_2.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_3.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_4.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_5.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_6.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_7.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_8.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_9.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_10.getVerticalScrollBar().setUI(new NewScrollBarUI());
        scrollPane_11.getVerticalScrollBar().setUI(new NewScrollBarUI());
        filterSliders.getVerticalScrollBar().setUI(new NewScrollBarUI());
        customUpdateScrollPane.getVerticalScrollBar().setUI(new NewScrollBarUI());
        
        
        //Drop down scrollbars
        Object popup = questionSelector.getUI().getAccessibleChild(questionSelector, 0);
        Component c = ((Container) popup).getComponent(0);
        if(c instanceof JScrollPane) {
        	((JScrollPane) c).getVerticalScrollBar().setUI(new NewScrollBarUI());
        }
        
        popup = dbImportTypeComboBox.getUI().getAccessibleChild(dbImportTypeComboBox, 0);
        c = ((Container) popup).getComponent(0);
        if(c instanceof JScrollPane) {
        	((JScrollPane) c).getVerticalScrollBar().setUI(new NewScrollBarUI());
        }
        
		SwingStyle.init(); // for swing rules and functions
		AruiStyle.init();  // for custom components rules and functions
		
		//Components to style
		Style.registerTargetClassName(lblLearningMaterials, ".label");
		Style.registerTargetClassName(submitButton, ".createBtn");
		//Style.registerTargetClassName(undoBtn, ".undoBtn");
		//Style.registerTargetClassName(fillButton, ".fillButton");
		
		
		Style.apply(getContentPane(), new Style(getClass().getResource("styles.css")));
		
		// start with self reference
		DIHelper.getInstance().setLocalProperty(Constants.MAIN_FRAME, this);
	
		
		java.lang.reflect.Field[] fields = PlayPane.class.getFields();

		// run through the view components
		for(int fieldIndex = 0; fieldIndex < fields.length;fieldIndex++)
		{
			//System.out.println(fields[fieldIndex].getName());
			Object obj = fields[fieldIndex].get(this);
			logger.debug("Object set to " + obj);
			String fieldName = fields[fieldIndex].getName();
			if(obj instanceof JComboBox || obj instanceof JButton || obj instanceof JToggleButton 
					|| obj instanceof JSlider || obj instanceof JInternalFrame || obj instanceof JRadioButton)
			{
				// load the controllers
				// find the view
				// right view and listener
					String ctrlNames = DIHelper.getInstance().getProperty(fieldName + "_" + Constants.CONTROL);
					if(ctrlNames != null && ctrlNames.length()!=0)
					{
						logger.debug("Listeners >>>>  "+ ctrlNames + "   for field " + fieldName);
						StringTokenizer listenerTokens = new StringTokenizer(ctrlNames, ";");
						while(listenerTokens.hasMoreTokens())
						{
							String ctrlName = listenerTokens.nextToken();
							logger.debug("Processing widget " + ctrlName);
							String className = DIHelper.getInstance().getProperty(ctrlName);
							IChakraListener listener = (IChakraListener)Class.forName(className).getConstructor(null).newInstance(null);
							// in the future this could be a list
							// add it to this object
							logger.debug("Listener " + ctrlName + "<>" + listener);
							// check to if this is a combobox or button
							if(obj instanceof JComboBox)
								((JComboBox)obj).addActionListener(listener);
							else if(obj instanceof JButton)
								((JButton)obj).addActionListener(listener);
							else if(obj instanceof JRadioButton)
								((JRadioButton)obj).addActionListener( listener);
							else if(obj instanceof JToggleButton)
								((JToggleButton)obj).addActionListener(listener);
							else if(obj instanceof JSlider)
								((JSlider)obj).addChangeListener((ChangeListener) listener);
							else
								((JInternalFrame)obj).addInternalFrameListener((InternalFrameListener) listener);
							DIHelper.getInstance().setLocalProperty(ctrlName, listener);
						}
					}
			}
			logger.debug("Loading <" + fieldName + "> <> " + obj);
			DIHelper.getInstance().setLocalProperty(fieldName, obj);
		}
		
		// need to also add the listeners respective views
		// Go through the listeners and add the model
		String listeners = DIHelper.getInstance().getProperty(Constants.LISTENERS);
		StringTokenizer lTokens = new StringTokenizer(listeners,";");
		while(lTokens.hasMoreElements())
		{
			String lToken = lTokens.nextToken();
		
			// set the views
			String viewName = DIHelper.getInstance().getProperty(lToken + "_" + Constants.VIEW);
			Object listener = DIHelper.getInstance().getLocalProp(lToken);
			if(viewName != null && listener != null)
			{
				// get the listener object and set it
				Method method = listener.getClass().getMethod("setView", JComponent.class);
				Object param = DIHelper.getInstance().getLocalProp(viewName);
				logger.debug("Param is <"+viewName+"><" + param + ">");
				method.invoke(listener, param);
			}

			// set the parent views
			viewName = DIHelper.getInstance().getProperty(lToken + "_" + Constants.PARENT_VIEW);
			/*
			if(viewName != null && listener != null)
			{
				// get the listener object and set it
				Method method = listener.getClass().getMethod("setParentView", JComponent.class);
				Object param = DIHelper.getInstance().getLocalProp(viewName);
				logger.debug("Param is <"+viewName+"><" + param + ">");
				method.invoke(listener, param);
			}
		

			// set the parent views
			viewName = DIHelper.getInstance().getProperty(lToken + "_" + Constants.RIGHT_VIEW);
			if(viewName != null && listener != null)
			{
				// get the listener object and set it
				Method method = listener.getClass().getMethod("setRightPanel", JComponent.class);
				Object param = DIHelper.getInstance().getLocalProp(viewName);
				logger.debug("Param is <"+viewName+"><" + param + ">");
				method.invoke(listener, param);
			}
			*/
		}		
		// set the repository
				String engines = (String)DIHelper.getInstance().getLocalProp(Constants.ENGINES);
				
				StringTokenizer tokens = new StringTokenizer(engines,";");
				DefaultListModel listModel = new DefaultListModel();
				while(tokens.hasMoreTokens())
				{		
					String engineName = tokens.nextToken();
					IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(engineName);
					if(engine.isConnected())
						listModel.addElement(engineName);
				}
				repoList.setModel(listModel);
				repoList.setSelectedIndex(0);
				
				// set the models now
				// set the perspectives information
				/*Hashtable perspectiveHash = (Hashtable) DIHelper.getInstance().getLocalProp(Constants.PERSPECTIVE);
				Vector<String> perspectives = Utility.convertEnumToArray(perspectiveHash.keys(), perspectiveHash.size());
				Collections.sort(perspectives);
				System.out.println("Perspectives " + perspectiveHash);
				for(int itemIndex = 0;itemIndex < perspectives.size();this.perspectiveSelector.addItem(perspectives.get(itemIndex)), itemIndex++);
				*/
			}


	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public PlayPane() throws IOException {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set the icons
		List<Image> images = new Vector();
		String workingDir = System.getProperty("user.dir");
		String imgFile16 = workingDir + "/pictures/finalWhiteLogo16.png";
		ImageIcon img16 = new ImageIcon(imgFile16);
		images.add(img16.getImage());
		String imgFile32 = workingDir + "/pictures/finalWhiteLogo32.png";
		ImageIcon img32 = new ImageIcon(imgFile32);
		images.add(img32.getImage());
		String imgFile64 = workingDir + "/pictures/finalWhiteLogo64.png";
		ImageIcon img64 = new ImageIcon(imgFile64);
		images.add(img64.getImage());
		String imgFile128 = workingDir + "/pictures/finalWhiteLogo128.png";
		ImageIcon img128 = new ImageIcon(imgFile128);
		images.add(img128.getImage());
		setIconImages(images);
		setTitle("SEMOSS - Analytics Environment");
		
		setSize(new Dimension(1371, 744));
		
		new JScrollPane();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1164, 0};
		gridBagLayout.rowHeights = new int[]{540, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		//setBounds(100, 100, 450, 300);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		
		rightView = new RightView(JTabbedPane.TOP);
		splitPane.setRightComponent(rightView);
		
		graphPanel = new JPanel();
		rightView.addTab("Display Pane", null, graphPanel, null);
		graphPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		graphPanel.add(desktopPane);
		
		imExPanel = new JPanel();
		JScrollPane imExPanelScroll = new JScrollPane(imExPanel);
		rightView.addTab("DB Modification", null, imExPanelScroll, null);
		GridBagLayout gbl_imExPanel = new GridBagLayout();
		gbl_imExPanel.columnWidths = new int[]{1026, 0};
		gbl_imExPanel.rowHeights = new int[]{0, 0};
		gbl_imExPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_imExPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		imExPanel.setLayout(gbl_imExPanel);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerLocation(300);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		
		
		importPanel = new JPanel();
		importPanel.setBackground(SystemColor.control);
		importPanel.setMinimumSize(new Dimension(0, 0));
		splitPane_1.setLeftComponent(importPanel);
		GridBagLayout gbl_importPanel = new GridBagLayout();
		gbl_importPanel.columnWidths = new int[]{43, 0, 117, 300, 0};
		gbl_importPanel.rowHeights = new int[]{0, 0, 0, 28, 0};
		gbl_importPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_importPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		importPanel.setLayout(gbl_importPanel);
		
		lblInsertData = new JLabel("Import Data");
		lblInsertData.setFont(new Font("SansSerif", Font.BOLD, 13));
		GridBagConstraints gbc_lblInsertData = new GridBagConstraints();
		gbc_lblInsertData.gridwidth = 4;
		gbc_lblInsertData.insets = new Insets(10, 0, 10, 0);
		gbc_lblInsertData.gridx = 0;
		gbc_lblInsertData.gridy = 0;
		importPanel.add(lblInsertData, gbc_lblInsertData);
		
		lblDatabaseImportOptions = new JLabel("Database Import Options");
		GridBagConstraints gbc_lblDatabaseImportOptions = new GridBagConstraints();
		gbc_lblDatabaseImportOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabaseImportOptions.anchor = GridBagConstraints.EAST;
		gbc_lblDatabaseImportOptions.gridx = 1;
		gbc_lblDatabaseImportOptions.gridy = 1;
		importPanel.add(lblDatabaseImportOptions, gbc_lblDatabaseImportOptions);
		
		dbImportTypeComboBox = new JComboBox();
		dbImportTypeComboBox.setPreferredSize(new Dimension(400,25));
		dbImportTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Select a database import method", "Add to existing database engine", "Create new database engine"}));
		GridBagConstraints gbc_dbImportTypeComboBox = new GridBagConstraints();
		gbc_dbImportTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbImportTypeComboBox.anchor = GridBagConstraints.NORTH;
		gbc_dbImportTypeComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_dbImportTypeComboBox.gridx = 2;
		gbc_dbImportTypeComboBox.gridy = 1;
		importPanel.add(dbImportTypeComboBox, gbc_dbImportTypeComboBox);
		
		dbImportPanel = new JPanel();
		dbImportPanel.setBackground(SystemColor.control);
		dbImportPanel.setVisible(false);
		GridBagConstraints gbc_dbImportPanel = new GridBagConstraints();
		gbc_dbImportPanel.gridwidth = 2;
		gbc_dbImportPanel.insets = new Insets(0, 0, 0, 5);
		gbc_dbImportPanel.fill = GridBagConstraints.BOTH;
		gbc_dbImportPanel.gridx = 1;
		gbc_dbImportPanel.gridy = 3;
		importPanel.add(dbImportPanel, gbc_dbImportPanel);
		GridBagLayout gbl_dbImportPanel = new GridBagLayout();
		gbl_dbImportPanel.columnWidths = new int[]{160, 0, 0, 0};
		gbl_dbImportPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_dbImportPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		gbl_dbImportPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		dbImportPanel.setLayout(gbl_dbImportPanel);
		
		dbNameLbl = new JLabel("Create Database Name");
		GridBagConstraints gbc_dbNameLbl = new GridBagConstraints();
		gbc_dbNameLbl.anchor = GridBagConstraints.WEST;
		gbc_dbNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_dbNameLbl.gridx = 0;
		gbc_dbNameLbl.gridy = 0;
		dbImportPanel.add(dbNameLbl, gbc_dbNameLbl);
		dbNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		dbSelectorField = new JTextField();
		GridBagConstraints gbc_dbSelectorField = new GridBagConstraints();
		gbc_dbSelectorField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbSelectorField.gridwidth = 3;
		gbc_dbSelectorField.insets = new Insets(0, 0, 5, 0);
		gbc_dbSelectorField.gridx = 1;
		gbc_dbSelectorField.gridy = 0;
		dbImportPanel.add(dbSelectorField, gbc_dbSelectorField);
		dbSelectorField.setColumns(10);
		
		selectionFileLbl = new JLabel("Select File(s) to Import");
		GridBagConstraints gbc_selectionFileLbl = new GridBagConstraints();
		gbc_selectionFileLbl.anchor = GridBagConstraints.WEST;
		gbc_selectionFileLbl.insets = new Insets(0, 0, 5, 5);
		gbc_selectionFileLbl.gridx = 0;
		gbc_selectionFileLbl.gridy = 1;
		dbImportPanel.add(selectionFileLbl, gbc_selectionFileLbl);
		selectionFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		fileBrowseBtn = new JButton("Browse");
		GridBagConstraints gbc_fileBrowseBtn = new GridBagConstraints();
		gbc_fileBrowseBtn.anchor = GridBagConstraints.EAST;
		gbc_fileBrowseBtn.insets = new Insets(0, 0, 5, 5);
		gbc_fileBrowseBtn.gridx = 1;
		gbc_fileBrowseBtn.gridy = 1;
		dbImportPanel.add(fileBrowseBtn, gbc_fileBrowseBtn);
		
		importFileNameField = new JTextField();
		GridBagConstraints gbc_importFileNameField = new GridBagConstraints();
		gbc_importFileNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_importFileNameField.gridwidth = 2;
		gbc_importFileNameField.insets = new Insets(0, 0, 5, 0);
		gbc_importFileNameField.gridx = 2;
		gbc_importFileNameField.gridy = 1;
		dbImportPanel.add(importFileNameField, gbc_importFileNameField);
		importFileNameField.setColumns(10);
		
		lblDesignateBaseUri = new JLabel("<HTML>Designate Base URI<br>(Optional)</HTML>");
		lblDesignateBaseUri.setMinimumSize(new Dimension(155, 32));
		lblDesignateBaseUri.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblDesignateBaseUri = new GridBagConstraints();
		gbc_lblDesignateBaseUri.anchor = GridBagConstraints.WEST;
		gbc_lblDesignateBaseUri.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesignateBaseUri.gridx = 0;
		gbc_lblDesignateBaseUri.gridy = 2;
		dbImportPanel.add(lblDesignateBaseUri, gbc_lblDesignateBaseUri);
		
		customBaseURItextField = new JTextField();
		customBaseURItextField.setText("http://health.mil/ontologies/dbcm");
		customBaseURItextField.setColumns(10);
		GridBagConstraints gbc_customBaseURItextField = new GridBagConstraints();
		gbc_customBaseURItextField.gridwidth = 3;
		gbc_customBaseURItextField.insets = new Insets(0, 0, 5, 0);
		gbc_customBaseURItextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_customBaseURItextField.gridx = 1;
		gbc_customBaseURItextField.gridy = 2;
		dbImportPanel.add(customBaseURItextField, gbc_customBaseURItextField);
		
		btnShowAdvancedImportFeatures = new JButton("Show Advanced Features");
		GridBagConstraints gbc_btnShowAdvancedImportFeatures = new GridBagConstraints();
		gbc_btnShowAdvancedImportFeatures.anchor = GridBagConstraints.WEST;
		gbc_btnShowAdvancedImportFeatures.gridwidth = 2;
		gbc_btnShowAdvancedImportFeatures.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowAdvancedImportFeatures.gridx = 0;
		gbc_btnShowAdvancedImportFeatures.gridy = 3;
		dbImportPanel.add(btnShowAdvancedImportFeatures, gbc_btnShowAdvancedImportFeatures);
		
		advancedImportOptionsPanel = new JPanel();
		advancedImportOptionsPanel.setBackground(SystemColor.control);
		GridBagConstraints gbc_advancedImportOptionsPanel = new GridBagConstraints();
		gbc_advancedImportOptionsPanel.gridwidth = 4;
		gbc_advancedImportOptionsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_advancedImportOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_advancedImportOptionsPanel.gridx = 0;
		gbc_advancedImportOptionsPanel.gridy = 4;
		dbImportPanel.add(advancedImportOptionsPanel, gbc_advancedImportOptionsPanel);
		GridBagLayout gbl_advancedImportOptionsPanel = new GridBagLayout();
		gbl_advancedImportOptionsPanel.columnWidths = new int[]{210, 0, 0, 0};
		gbl_advancedImportOptionsPanel.rowHeights = new int[]{0, 30, 0, 0};
		gbl_advancedImportOptionsPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_advancedImportOptionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		advancedImportOptionsPanel.setLayout(gbl_advancedImportOptionsPanel);
		
		lblselectCustomMap = new JLabel("<HTML>Select Custom or Default Map File</HTML>");
		lblselectCustomMap.setMinimumSize(new Dimension(186, 16));
		lblselectCustomMap.setSize(new Dimension(44, 0));
		GridBagConstraints gbc_lblselectCustomMap = new GridBagConstraints();
		gbc_lblselectCustomMap.anchor = GridBagConstraints.WEST;
		gbc_lblselectCustomMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblselectCustomMap.gridx = 0;
		gbc_lblselectCustomMap.gridy = 0;
		advancedImportOptionsPanel.add(lblselectCustomMap, gbc_lblselectCustomMap);
		lblselectCustomMap.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		mapBrowseBtn = new JButton("Browse");
		mapBrowseBtn.setName("mapBrowseBtn");
		GridBagConstraints gbc_mapBrowseBtn = new GridBagConstraints();
		gbc_mapBrowseBtn.fill = GridBagConstraints.VERTICAL;
		gbc_mapBrowseBtn.insets = new Insets(0, 0, 5, 5);
		gbc_mapBrowseBtn.gridx = 1;
		gbc_mapBrowseBtn.gridy = 0;
		advancedImportOptionsPanel.add(mapBrowseBtn, gbc_mapBrowseBtn);
		mapBrowseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		importMapFileNameField = new JTextField();
		GridBagConstraints gbc_importMapFileNameField = new GridBagConstraints();
		gbc_importMapFileNameField.insets = new Insets(0, 0, 5, 0);
		gbc_importMapFileNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_importMapFileNameField.gridx = 2;
		gbc_importMapFileNameField.gridy = 0;
		advancedImportOptionsPanel.add(importMapFileNameField, gbc_importMapFileNameField);
		importMapFileNameField.setColumns(10);
		
		lblselectCustomProp = new JLabel("<HTML>Select Custom Prop File</HTML>");
		lblselectCustomProp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblselectCustomProp = new GridBagConstraints();
		gbc_lblselectCustomProp.anchor = GridBagConstraints.WEST;
		gbc_lblselectCustomProp.insets = new Insets(0, 0, 5, 5);
		gbc_lblselectCustomProp.gridx = 0;
		gbc_lblselectCustomProp.gridy = 1;
		advancedImportOptionsPanel.add(lblselectCustomProp, gbc_lblselectCustomProp);
		
		dbPropBrowseButton = new JButton("Browse");
		dbPropBrowseButton.setName("dbPropBrowseButton");
		GridBagConstraints gbc_dbPropBrowseButton = new GridBagConstraints();
		gbc_dbPropBrowseButton.insets = new Insets(0, 0, 5, 5);
		gbc_dbPropBrowseButton.gridx = 1;
		gbc_dbPropBrowseButton.gridy = 1;
		advancedImportOptionsPanel.add(dbPropBrowseButton, gbc_dbPropBrowseButton);
		
		dbPropFileNameField = new JTextField();
		dbPropFileNameField.setColumns(10);
		GridBagConstraints gbc_dbPropFileNameField = new GridBagConstraints();
		gbc_dbPropFileNameField.insets = new Insets(0, 0, 5, 0);
		gbc_dbPropFileNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbPropFileNameField.gridx = 2;
		gbc_dbPropFileNameField.gridy = 1;
		advancedImportOptionsPanel.add(dbPropFileNameField, gbc_dbPropFileNameField);
		
		lblselectCustomQuestionssheet = new JLabel("<HTML>Select Custom Questions Sheet</HTML>");
		lblselectCustomQuestionssheet.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblselectCustomQuestionssheet = new GridBagConstraints();
		gbc_lblselectCustomQuestionssheet.anchor = GridBagConstraints.WEST;
		gbc_lblselectCustomQuestionssheet.insets = new Insets(0, 0, 0, 5);
		gbc_lblselectCustomQuestionssheet.gridx = 0;
		gbc_lblselectCustomQuestionssheet.gridy = 2;
		advancedImportOptionsPanel.add(lblselectCustomQuestionssheet, gbc_lblselectCustomQuestionssheet);
		
		questionBrowseButton = new JButton("Browse");
		questionBrowseButton.setName("questionBrowseButton");
		GridBagConstraints gbc_questionBrowseButton = new GridBagConstraints();
		gbc_questionBrowseButton.insets = new Insets(0, 0, 0, 5);
		gbc_questionBrowseButton.gridx = 1;
		gbc_questionBrowseButton.gridy = 2;
		advancedImportOptionsPanel.add(questionBrowseButton, gbc_questionBrowseButton);
		
		questionFileNameField = new JTextField();
		questionFileNameField.setColumns(10);
		GridBagConstraints gbc_questionFileNameField = new GridBagConstraints();
		gbc_questionFileNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_questionFileNameField.gridx = 2;
		gbc_questionFileNameField.gridy = 2;
		advancedImportOptionsPanel.add(questionFileNameField, gbc_questionFileNameField);
		advancedImportOptionsPanel.setVisible(false);
		
		importButton = new JButton("Import");
		GridBagConstraints gbc_importButton = new GridBagConstraints();
		gbc_importButton.anchor = GridBagConstraints.EAST;
		gbc_importButton.gridx = 3;
		gbc_importButton.gridy = 5;
		dbImportPanel.add(importButton, gbc_importButton);
		
		exportPanel = new JPanel();
		exportPanel.setBackground(SystemColor.control);
		exportPanel.setPreferredSize(new Dimension(200, 200));
		exportPanel.setMinimumSize(new Dimension(0, 0));
		splitPane_1.setRightComponent(exportPanel);
		GridBagLayout gbl_exportPanel = new GridBagLayout();
		gbl_exportPanel.columnWidths = new int[]{10, 0, 0, 150, 150, 150, 150, 0, 0};
		gbl_exportPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_exportPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_exportPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		exportPanel.setLayout(gbl_exportPanel);
		
		lblNewLabel_1 = new JLabel("Delete/Insert Data");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 6;
		gbc_lblNewLabel_1.insets = new Insets(10, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 0;
		exportPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lblCustomDelete = new JLabel("Custom Insert/Delete Query");
		lblCustomDelete.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomDelete = new GridBagConstraints();
		gbc_lblCustomDelete.gridwidth = 3;
		gbc_lblCustomDelete.anchor = GridBagConstraints.WEST;
		gbc_lblCustomDelete.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomDelete.gridx = 1;
		gbc_lblCustomDelete.gridy = 1;
		exportPanel.add(lblCustomDelete, gbc_lblCustomDelete);
		
		customUpdateScrollPane = new JScrollPane();
		customUpdateScrollPane.setMaximumSize(new Dimension(32767, 75));
		customUpdateScrollPane.setPreferredSize(new Dimension(6, 75));
		customUpdateScrollPane.setMinimumSize(new Dimension(0, 75));
		GridBagConstraints gbc_customUpdateScrollPane = new GridBagConstraints();
		gbc_customUpdateScrollPane.gridwidth = 6;
		gbc_customUpdateScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_customUpdateScrollPane.fill = GridBagConstraints.BOTH;
		gbc_customUpdateScrollPane.gridx = 1;
		gbc_customUpdateScrollPane.gridy = 2;
		exportPanel.add(customUpdateScrollPane, gbc_customUpdateScrollPane);
		
		customUpdateTextPane = new JTextPane();
		customUpdateScrollPane.setViewportView(customUpdateTextPane);
		
		btnCustomUpdate = new JButton("Update");
		GridBagConstraints gbc_btnCustomUpdate = new GridBagConstraints();
		gbc_btnCustomUpdate.insets = new Insets(0, 0, 20, 0);
		gbc_btnCustomUpdate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCustomUpdate.gridx = 7;
		gbc_btnCustomUpdate.gridy = 2;
		exportPanel.add(btnCustomUpdate, gbc_btnCustomUpdate);
		
	
		
		
		
		GridBagConstraints gbc_splitPane_1 = new GridBagConstraints();
		gbc_splitPane_1.fill = GridBagConstraints.BOTH;
		gbc_splitPane_1.gridx = 0;
		gbc_splitPane_1.gridy = 0;
		imExPanel.add(splitPane_1, gbc_splitPane_1);
		
		debugPanel = new JPanel();
		//rightView.addTab("Debug ", null, debugPanel, null);
		GridBagLayout gbl_debugPanel = new GridBagLayout();
		gbl_debugPanel.columnWidths = new int[]{0, 0};
		gbl_debugPanel.rowHeights = new int[] {40, 0, 40, 40, 40, 40};
		gbl_debugPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_debugPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		debugPanel.setLayout(gbl_debugPanel);
		
		scrollPane_5 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
		gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_5.gridx = 0;
		gbc_scrollPane_5.gridy = 1;
		debugPanel.add(scrollPane_5, gbc_scrollPane_5);
		
		txtrAasdasdasd = new JTextArea();
		txtrAasdasdasd.setText("AasdasdASD\r\n");
		txtrAasdasdasd.setPreferredSize(new Dimension(400, 22));
		scrollPane_5.setViewportView(txtrAasdasdasd);

		//String workingDir = System.getProperty("user.dir");
		FileReader fr = null;
		
		//Here we read the release notes text file
		String  releaseNotesTextFile= workingDir + "/help/info.txt";
		fr = new FileReader(releaseNotesTextFile);
		BufferedReader releaseNotesTextReader = new BufferedReader(fr);
		
		String releaseNotesData = "<html><body bgcolor=\"#f0f0f0\"> ";	
		String line = null;
		while ((line = releaseNotesTextReader.readLine()) != null)
		{
			releaseNotesData = releaseNotesData + line +"<br>";
		}
		releaseNotesData = releaseNotesData + "";
		
		
		
		
		settingsPanel = new JPanel();
		settingsPanel.setBackground(SystemColor.control);
		settingsPanel.setBorder(null);
		JScrollPane settingPanelScroll = new JScrollPane(settingsPanel);
		rightView.addTab("Help", null, settingPanelScroll, null);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{0, 0};
		gbl_settingsPanel.rowHeights = new int[]{337, 0};
		gbl_settingsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		helpPanel = new JPanel();
		helpPanel.setBackground(SystemColor.control);
		//panel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		//settingsPanel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		GridBagConstraints gbc_helpPanel = new GridBagConstraints();
		gbc_helpPanel.anchor = GridBagConstraints.NORTH;
		gbc_helpPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_helpPanel.gridx = 0;
		gbc_helpPanel.gridy = 0;
		settingsPanel.add(helpPanel, gbc_helpPanel);
		GridBagLayout gbl_helpPanel = new GridBagLayout();
		gbl_helpPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_helpPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_helpPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_helpPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		helpPanel.setLayout(gbl_helpPanel);
		
		lblAbout = new JLabel("      About");
		lblAbout.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblAbout = new GridBagConstraints();
		gbc_lblAbout.gridwidth = 2;
		gbc_lblAbout.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAbout.insets = new Insets(20, 0, 10, 0);
		gbc_lblAbout.gridx = 1;
		gbc_lblAbout.gridy = 0;
		helpPanel.add(lblAbout, gbc_lblAbout);
		lblAbout.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		aboutArea = new JTextArea();
		aboutArea.setEditable(false);
		aboutArea.setBorder(null);
		aboutArea.setBackground(SystemColor.control);
		aboutArea.setForeground(new Color(0, 0, 0));
		aboutArea.setWrapStyleWord(true);
		aboutArea.setLineWrap(true);
		aboutArea.setText("The Graph Tool is an innovative and data-driven application that allows users to explore and uncover connections among existing data from multiple repositories in an effort to make more informed decisions.\r\n\r\nThe tool displays data from one or more databases in an interactive format that users can customize, overlay, and extend based on their individual needs. This can help establish connections between a specific piece of data, that a healthcare provider needs to perform a certain procedure, with the system(s) that provide it.\r\n");
		GridBagConstraints gbc_aboutArea = new GridBagConstraints();
		gbc_aboutArea.gridwidth = 2;
		gbc_aboutArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_aboutArea.insets = new Insets(0, 20, 5, 20);
		gbc_aboutArea.gridx = 1;
		gbc_aboutArea.gridy = 1;
		helpPanel.add(aboutArea, gbc_aboutArea);
		
		lblReleaseNotes = new JLabel("      Release Notes");
		lblReleaseNotes.setHorizontalAlignment(SwingConstants.LEFT);
		lblReleaseNotes.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblReleaseNotes.setBackground(Color.WHITE);
		GridBagConstraints gbc_lblReleaseNotes = new GridBagConstraints();
		gbc_lblReleaseNotes.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblReleaseNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblReleaseNotes.gridx = 1;
		gbc_lblReleaseNotes.gridy = 2;
		helpPanel.add(lblReleaseNotes, gbc_lblReleaseNotes);
		
				releaseNoteArea = new JTextPane();
				releaseNoteArea.setText("<html><body bgcolor=\"#ddd\"> </body></html>");
				GridBagConstraints gbc_releaseNoteArea = new GridBagConstraints();
				gbc_releaseNoteArea.anchor = GridBagConstraints.WEST;
				gbc_releaseNoteArea.insets = new Insets(0, 20, 5, 20);
				gbc_releaseNoteArea.gridx = 2;
				gbc_releaseNoteArea.gridy = 4;
				helpPanel.add(releaseNoteArea, gbc_releaseNoteArea);
				releaseNoteArea.setMinimumSize(new Dimension(12, 20));
				//releaseNoteArea.setWrapStyleWord(true);
				//releaseNoteArea.setLineWrap(true);
				releaseNoteArea.setContentType("text/html");
				releaseNoteArea.setBorder(null);
				releaseNoteArea.setText(releaseNotesData);
				releaseNoteArea.setForeground(Color.DARK_GRAY);
				releaseNoteArea.setBackground(SystemColor.control);
				releaseNoteArea.setEditable(false);
				
				lblLearningMaterials = new JLabel("      Learning Materials");
				lblLearningMaterials.setHorizontalAlignment(SwingConstants.LEFT);
				lblLearningMaterials.setFont(new Font("SansSerif", Font.BOLD, 13));
				GridBagConstraints gbc_lblLearningMaterials = new GridBagConstraints();
				gbc_lblLearningMaterials.anchor = GridBagConstraints.WEST;
				gbc_lblLearningMaterials.insets = new Insets(0, 0, 5, 5);
				gbc_lblLearningMaterials.gridx = 1;
				gbc_lblLearningMaterials.gridy = 5;
				helpPanel.add(lblLearningMaterials, gbc_lblLearningMaterials);
				
				lblHeidiMaterials = new JLabel("Interactive Powerpoint");
				GridBagConstraints gbc_lblHeidiMaterials = new GridBagConstraints();
				gbc_lblHeidiMaterials.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblHeidiMaterials.insets = new Insets(0, 20, 5, 5);
				gbc_lblHeidiMaterials.gridx = 1;
				gbc_lblHeidiMaterials.gridy = 6;
				helpPanel.add(lblHeidiMaterials, gbc_lblHeidiMaterials);
				
				pptTrainingBtn = new JButton("Starting Interactive Powerpoint Training");
				GridBagConstraints gbc_pptTrainingBtn = new GridBagConstraints();
				gbc_pptTrainingBtn.anchor = GridBagConstraints.NORTHWEST;
				gbc_pptTrainingBtn.insets = new Insets(0, 20, 5, 5);
				gbc_pptTrainingBtn.gridx = 1;
				gbc_pptTrainingBtn.gridy = 7;
				helpPanel.add(pptTrainingBtn, gbc_pptTrainingBtn);
				
				lblNewLabel_2 = new JLabel("How to Videos");
				GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
				gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblNewLabel_2.insets = new Insets(0, 20, 5, 5);
				gbc_lblNewLabel_2.gridx = 1;
				gbc_lblNewLabel_2.gridy = 8;
				helpPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
				
				htmlTrainingBtn = new JButton("Start Simulation Training");
				GridBagConstraints gbc_htmlTrainingBtn = new GridBagConstraints();
				gbc_htmlTrainingBtn.anchor = GridBagConstraints.NORTHWEST;
				gbc_htmlTrainingBtn.insets = new Insets(0, 20, 5, 5);
				gbc_htmlTrainingBtn.gridx = 1;
				gbc_htmlTrainingBtn.gridy = 9;
				helpPanel.add(htmlTrainingBtn, gbc_htmlTrainingBtn);
		
		JTabbedPane leftView = new JTabbedPane(JTabbedPane.TOP);
		//JScrollPane leftScrollView = new JScrollPane(leftView);
		splitPane.setLeftComponent(leftView);
		
		inputPanel = new JPanel();
		inputPanel.setBackground(SystemColor.control);
		//JScrollPane inputPanelScroll = new JScrollPane(inputPanel);
		leftView.addTab("Main", null, inputPanel, null);
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] {5, 81, 81, 81, 5};
		gbl_inputPanel.rowHeights = new int[] {30, 0, 67, 19, 0, 15, 0, 0, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 30};
		gbl_inputPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_inputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		inputPanel.setLayout(gbl_inputPanel);
		
		lblSectionADefine = new JLabel("Define Graph");
		lblSectionADefine.setForeground(Color.DARK_GRAY);
		lblSectionADefine.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblSectionADefine = new GridBagConstraints();
		gbc_lblSectionADefine.gridwidth = 2;
		gbc_lblSectionADefine.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblSectionADefine.insets = new Insets(0, 5, 5, 5);
		gbc_lblSectionADefine.gridx = 1;
		gbc_lblSectionADefine.gridy = 0;
		inputPanel.add(lblSectionADefine, gbc_lblSectionADefine);
		
		lblSelectA = new JLabel("1. Select a database to pull data from");
		lblSelectA.setForeground(Color.DARK_GRAY);
		lblSelectA.setFont(new Font("SansSerif", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSelectA = new GridBagConstraints();
		gbc_lblSelectA.anchor = GridBagConstraints.WEST;
		gbc_lblSelectA.gridwidth = 4;
		gbc_lblSelectA.insets = new Insets(0, 5, 5, 0);
		gbc_lblSelectA.gridx = 1;
		gbc_lblSelectA.gridy = 1;
		inputPanel.add(lblSelectA, gbc_lblSelectA);
		
		repoList = new JList();
		repoList.setBorder(new LineBorder(Color.LIGHT_GRAY));
		repoList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		repoList.setLayoutOrientation(JList.VERTICAL_WRAP);
		repoList.setVisibleRowCount(4);
		GridBagConstraints gbc_repoList = new GridBagConstraints();
		gbc_repoList.gridwidth = 4;
		gbc_repoList.fill = GridBagConstraints.BOTH;
		gbc_repoList.insets = new Insets(0, 10, 5, 10);
		gbc_repoList.gridx = 1;
		gbc_repoList.gridy = 2;
		inputPanel.add(repoList, gbc_repoList);
		// set the listener
		repoList.addListSelectionListener(RepoSelectionListener.getInstance());
		
		
		JLabel lblPerspective = new JLabel("2. Select the category you'd like to address");
		lblPerspective.setForeground(Color.DARK_GRAY);
		lblPerspective.setFont(new Font("SansSerif", Font.PLAIN, 10));
		GridBagConstraints gbc_lblPerspective = new GridBagConstraints();
		gbc_lblPerspective.gridwidth = 4;
		gbc_lblPerspective.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPerspective.insets = new Insets(0, 5, 5, 0);
		gbc_lblPerspective.gridx = 1;
		gbc_lblPerspective.gridy = 3;
		inputPanel.add(lblPerspective, gbc_lblPerspective);
		
		perspectiveSelector = new JComboBox();
		perspectiveSelector.setBackground(Color.WHITE);
		perspectiveSelector.setPreferredSize(new Dimension(150, 25));
		perspectiveSelector.setMinimumSize(new Dimension(150, 25));
		perspectiveSelector.setMaximumSize(new Dimension(200, 32767));
		GridBagConstraints gbc_perspectiveSelector = new GridBagConstraints();
		gbc_perspectiveSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_perspectiveSelector.gridwidth = 4;
		gbc_perspectiveSelector.anchor = GridBagConstraints.NORTH;
		gbc_perspectiveSelector.insets = new Insets(0, 5, 5, 5);
		gbc_perspectiveSelector.gridx = 1;
		gbc_perspectiveSelector.gridy = 4;
		inputPanel.add(perspectiveSelector, gbc_perspectiveSelector);
		
		
		JLabel lblQuery = new JLabel("3. Select a specific question");
		lblQuery.setForeground(Color.DARK_GRAY);
		lblQuery.setFont(new Font("SansSerif", Font.PLAIN, 10));
		GridBagConstraints gbc_lblQuery = new GridBagConstraints();
		gbc_lblQuery.gridwidth = 4;
		gbc_lblQuery.anchor = GridBagConstraints.WEST;
		gbc_lblQuery.insets = new Insets(0, 5, 5, 0);
		gbc_lblQuery.gridx = 1;
		gbc_lblQuery.gridy = 5;
		inputPanel.add(lblQuery, gbc_lblQuery);
		
		questionSelector = new JComboBox();
		questionSelector.setBackground(Color.WHITE);
		questionSelector.setMinimumSize(new Dimension(60, 25));
		questionSelector.setPreferredSize(new Dimension(150, 25));
		questionSelector.setMaximumSize(new Dimension(200, 32767));
		GridBagConstraints gbc_questionSelector = new GridBagConstraints();
		gbc_questionSelector.anchor = GridBagConstraints.NORTH;
		gbc_questionSelector.gridwidth = 4;
		gbc_questionSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_questionSelector.insets = new Insets(0, 5, 5, 5);
		gbc_questionSelector.gridx = 1;
		gbc_questionSelector.gridy = 6;
		inputPanel.add(questionSelector, gbc_questionSelector);
		
		lblSelectAvailable = new JLabel("4. Select available parameters");
		lblSelectAvailable.setForeground(Color.DARK_GRAY);
		lblSelectAvailable.setFont(new Font("SansSerif", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSelectAvailable = new GridBagConstraints();
		gbc_lblSelectAvailable.anchor = GridBagConstraints.WEST;
		gbc_lblSelectAvailable.gridwidth = 4;
		gbc_lblSelectAvailable.insets = new Insets(0, 5, 5, 0);
		gbc_lblSelectAvailable.gridx = 1;
		gbc_lblSelectAvailable.gridy = 7;
		inputPanel.add(lblSelectAvailable, gbc_lblSelectAvailable);
		
		paramPanel = new JPanel();
		paramPanel.setForeground(Color.DARK_GRAY);
		paramPanel.setBackground(SystemColor.control);
		GridBagConstraints gbc_paramPanel = new GridBagConstraints();
		gbc_paramPanel.fill = GridBagConstraints.BOTH;
		gbc_paramPanel.insets = new Insets(0, 0, 5, 0);
		gbc_paramPanel.gridwidth = 4;
		gbc_paramPanel.gridx = 1;
		gbc_paramPanel.gridy = 8;
		inputPanel.add(paramPanel, gbc_paramPanel);
		paramPanel.setLayout(new CardLayout(0, 0));
		
		submitButton = new Button();
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		submitButton.setText("Create");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_submitButton.insets = new Insets(0, 5, 5, 5);
		gbc_submitButton.gridx = 1;
		gbc_submitButton.gridy = 9;
		inputPanel.add(submitButton, gbc_submitButton);
		
		lblNewLabel = new JLabel("<HTML> Opens a new window with<br>query results displayed</HTML>");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 9;
		inputPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 128));
		separator.setBackground(new Color(0, 0, 128));
		UIManager.put(
                "Separator.foreground", Color.RED);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 4;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 10;
		inputPanel.add(separator, gbc_separator);
		
		lblSectionBSupplement = new JLabel("Undo/Remove Graph Options");
		lblSectionBSupplement.setForeground(Color.DARK_GRAY);
		lblSectionBSupplement.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblSectionBSupplement = new GridBagConstraints();
		gbc_lblSectionBSupplement.anchor = GridBagConstraints.WEST;
		gbc_lblSectionBSupplement.gridwidth = 4;
		gbc_lblSectionBSupplement.insets = new Insets(0, 5, 5, 0);
		gbc_lblSectionBSupplement.gridx = 1;
		gbc_lblSectionBSupplement.gridy = 11;
		inputPanel.add(lblSectionBSupplement, gbc_lblSectionBSupplement);
				
				undoBtn = new JButton();
				undoBtn.setText("Undo");
				undoBtn.setForeground(Color.DARK_GRAY);
				undoBtn.setBackground(SystemColor.menu);
				//undoBtn = new Button();
				undoBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
				undoBtn.setText("Undo");
				GridBagConstraints gbc_undoBtn = new GridBagConstraints();
				gbc_undoBtn.fill = GridBagConstraints.HORIZONTAL;
				gbc_undoBtn.insets = new Insets(0, 5, 5, 5);
				gbc_undoBtn.gridx = 1;
				gbc_undoBtn.gridy = 12;
				inputPanel.add(undoBtn, gbc_undoBtn);
				undoBtn.setToolTipText("Remove the last graph you overlayed");
				
				lblRemovesNodesAnd_1 = new JLabel("<HTML> Removes nodes and edges from<br> graph based off last action</HTML>");
				lblRemovesNodesAnd_1.setForeground(Color.GRAY);
				lblRemovesNodesAnd_1.setFont(new Font("SansSerif", Font.ITALIC, 10));
				GridBagConstraints gbc_lblRemovesNodesAnd_1 = new GridBagConstraints();
				gbc_lblRemovesNodesAnd_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblRemovesNodesAnd_1.gridwidth = 3;
				gbc_lblRemovesNodesAnd_1.insets = new Insets(0, 0, 5, 0);
				gbc_lblRemovesNodesAnd_1.gridx = 2;
				gbc_lblRemovesNodesAnd_1.gridy = 12;
				inputPanel.add(lblRemovesNodesAnd_1, gbc_lblRemovesNodesAnd_1);
				
				fillButton = new JButton("Remove");
				fillButton.setForeground(Color.DARK_GRAY);
				fillButton.setBackground(SystemColor.menu);
				fillButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				//fillButton.setText("Remove");
				fillButton.setToolTipText("Click on this to arbitrarily remove certain nodes from the graph based on the query selected");
				GridBagConstraints gbc_fillButton = new GridBagConstraints();
				gbc_fillButton.anchor = GridBagConstraints.WEST;
				gbc_fillButton.insets = new Insets(0, 5, 5, 5);
				gbc_fillButton.gridx = 1;
				gbc_fillButton.gridy = 13;
				inputPanel.add(fillButton, gbc_fillButton);
				
				lblRemovesNodesAnd = new JLabel("<HTML> Removes nodes and edges from<br> graph based off selected query</HTML>");
				lblRemovesNodesAnd.setForeground(Color.GRAY);
				lblRemovesNodesAnd.setFont(new Font("SansSerif", Font.ITALIC, 10));
				GridBagConstraints gbc_lblRemovesNodesAnd = new GridBagConstraints();
				gbc_lblRemovesNodesAnd.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblRemovesNodesAnd.gridwidth = 3;
				gbc_lblRemovesNodesAnd.insets = new Insets(0, 0, 5, 0);
				gbc_lblRemovesNodesAnd.gridx = 2;
				gbc_lblRemovesNodesAnd.gridy = 13;
				inputPanel.add(lblRemovesNodesAnd, gbc_lblRemovesNodesAnd);
				
				separator_2 = new JSeparator();
				separator_2.setForeground(new Color(0, 0, 128));
				separator_2.setBackground(new Color(0, 0, 128));
				GridBagConstraints gbc_separator_2 = new GridBagConstraints();
				gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_separator_2.gridwidth = 4;
				gbc_separator_2.insets = new Insets(0, 0, 5, 0);
				gbc_separator_2.gridx = 1;
				gbc_separator_2.gridy = 14;
				inputPanel.add(separator_2, gbc_separator_2);
				
				lblSectionBAugment = new JLabel("Augment Graph Options");
				lblSectionBAugment.setForeground(Color.DARK_GRAY);
				lblSectionBAugment.setFont(new Font("SansSerif", Font.BOLD, 12));
				GridBagConstraints gbc_lblSectionBAugment = new GridBagConstraints();
				gbc_lblSectionBAugment.anchor = GridBagConstraints.WEST;
				gbc_lblSectionBAugment.gridwidth = 4;
				gbc_lblSectionBAugment.insets = new Insets(0, 5, 5, 0);
				gbc_lblSectionBAugment.gridx = 1;
				gbc_lblSectionBAugment.gridy = 15;
				inputPanel.add(lblSectionBAugment, gbc_lblSectionBAugment);
		
				extendButton = new JToggleButton("Extend");
				GridBagConstraints gbc_extendButton = new GridBagConstraints();
				gbc_extendButton.anchor = GridBagConstraints.WEST;
				gbc_extendButton.insets = new Insets(0, 5, 5, 5);
				gbc_extendButton.gridx = 0;
				gbc_extendButton.gridy = 15;
				//inputPanel.add(extendButton, gbc_extendButton);
				//extendButton.setEnabled(false);
		
		appendButton = new JToggleButton("Overlay");
		appendButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		appendButton.setBackground(SystemColor.menu);
		GridBagConstraints gbc_appendButton = new GridBagConstraints();
		gbc_appendButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_appendButton.insets = new Insets(0, 5, 5, 5);
		gbc_appendButton.gridx = 1;
		gbc_appendButton.gridy = 16;
		inputPanel.add(appendButton, gbc_appendButton);
		appendButton.setToolTipText("This will overlay the existing SPARQL query into the currently active graph. To overlay, just depress this button. Make sure you disable it when you want new graphs");
		appendButton.setEnabled(false);
		
		lblAddsAdditionalGraph = new JLabel("<HTML> Adds additional graph to window<br>based off another selected query</HTML>");
		lblAddsAdditionalGraph.setForeground(Color.GRAY);
		lblAddsAdditionalGraph.setFont(new Font("SansSerif", Font.ITALIC, 10));
		GridBagConstraints gbc_lblAddsAdditionalGraph = new GridBagConstraints();
		gbc_lblAddsAdditionalGraph.gridwidth = 3;
		gbc_lblAddsAdditionalGraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAddsAdditionalGraph.insets = new Insets(0, 0, 5, 0);
		gbc_lblAddsAdditionalGraph.gridx = 2;
		gbc_lblAddsAdditionalGraph.gridy = 16;
		inputPanel.add(lblAddsAdditionalGraph, gbc_lblAddsAdditionalGraph);
				
				separator_1 = new JSeparator();
				GridBagConstraints gbc_separator_1 = new GridBagConstraints();
				gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_separator_1.gridwidth = 4;
				gbc_separator_1.insets = new Insets(0, 0, 5, 0);
				gbc_separator_1.gridx = 1;
				gbc_separator_1.gridy = 17;
				inputPanel.add(separator_1, gbc_separator_1);
				
				lblSectionCCustomize = new JLabel("Customize Sparql Query");
				lblSectionCCustomize.setForeground(Color.DARK_GRAY);
				lblSectionCCustomize.setFont(new Font("SansSerif", Font.BOLD, 12));
				GridBagConstraints gbc_lblSectionCCustomize = new GridBagConstraints();
				gbc_lblSectionCCustomize.anchor = GridBagConstraints.WEST;
				gbc_lblSectionCCustomize.gridwidth = 4;
				gbc_lblSectionCCustomize.insets = new Insets(0, 5, 5, 0);
				gbc_lblSectionCCustomize.gridx = 1;
				gbc_lblSectionCCustomize.gridy = 18;
				inputPanel.add(lblSectionCCustomize, gbc_lblSectionCCustomize);
				
				showSparql = new JToggleButton("Custom");
				showSparql.setForeground(Color.DARK_GRAY);
				showSparql.setBackground(SystemColor.menu);
				showSparql.setFont(new Font("Tahoma", Font.BOLD, 11));
				GridBagConstraints gbc_showSparql = new GridBagConstraints();
				gbc_showSparql.fill = GridBagConstraints.HORIZONTAL;
				gbc_showSparql.insets = new Insets(0, 5, 5, 5);
				gbc_showSparql.gridx = 1;
				gbc_showSparql.gridy = 19;
				inputPanel.add(showSparql, gbc_showSparql);
				
				sparqlLbl = new JLabel("<HTML> Turn toggle button on to submit<br>your own customized query</HTML>");
				sparqlLbl.setForeground(Color.GRAY);
				sparqlLbl.setFont(new Font("SansSerif", Font.ITALIC, 10));
				GridBagConstraints gbc_sparqlLbl = new GridBagConstraints();
				gbc_sparqlLbl.gridwidth = 3;
				gbc_sparqlLbl.fill = GridBagConstraints.HORIZONTAL;
				gbc_sparqlLbl.insets = new Insets(0, 0, 5, 0);
				gbc_sparqlLbl.gridx = 2;
				gbc_sparqlLbl.gridy = 19;
				inputPanel.add(sparqlLbl, gbc_sparqlLbl);
				
				rdbtnGraph = new JRadioButton("Graph");
				rdbtnGraph.setForeground(Color.DARK_GRAY);
				GridBagConstraints gbc_rdbtnGraph = new GridBagConstraints();
				gbc_rdbtnGraph.anchor = GridBagConstraints.WEST;
				gbc_rdbtnGraph.insets = new Insets(0, 5, 5, 5);
				gbc_rdbtnGraph.gridx = 1;
				gbc_rdbtnGraph.gridy = 20;
				inputPanel.add(rdbtnGraph, gbc_rdbtnGraph);
				rdbtnGraph.setEnabled(false);
				
				rdbtnGrid = new JRadioButton("Grid");
				rdbtnGrid.setForeground(Color.DARK_GRAY);
				GridBagConstraints gbc_rdbtnGrid = new GridBagConstraints();
				gbc_rdbtnGrid.anchor = GridBagConstraints.WEST;
				gbc_rdbtnGrid.insets = new Insets(0, 0, 5, 5);
				gbc_rdbtnGrid.gridx = 2;
				gbc_rdbtnGrid.gridy = 20;
				inputPanel.add(rdbtnGrid, gbc_rdbtnGrid);
				rdbtnGrid.setEnabled(false);
				
				rdbtnRaw = new JRadioButton("Raw");
				rdbtnRaw.setForeground(Color.DARK_GRAY);
				GridBagConstraints gbc_rdbtnRaw = new GridBagConstraints();
				gbc_rdbtnRaw.anchor = GridBagConstraints.WEST;
				gbc_rdbtnRaw.insets = new Insets(0, 0, 5, 5);
				gbc_rdbtnRaw.gridx = 3;
				gbc_rdbtnRaw.gridy = 20;
				inputPanel.add(rdbtnRaw, gbc_rdbtnRaw);
				rdbtnRaw.setEnabled(false);
				
				sparqlArea = new SparqlArea();
				
				scrollPane_1 = new JScrollPane(sparqlArea);
				GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
				gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
				gbc_scrollPane_1.gridwidth = 4;
				gbc_scrollPane_1.gridx = 1;
				gbc_scrollPane_1.gridy = 21;
				inputPanel.add(scrollPane_1, gbc_scrollPane_1);
				
				sparqlArea.setColumns(12);
				sparqlArea.setLineWrap(true);
				sparqlArea.setWrapStyleWord(true);
				sparqlArea.setEnabled(false);
				//sparqlArea.addComponentListener(showSparql);
				scrollPane_1.setPreferredSize(new Dimension(80,40));
				
				owlPanel = new JPanel();
				owlPanel.setBackground(SystemColor.control);
				leftView.addTab("SUDOWL", null, owlPanel, null);
				GridBagLayout gbl_owlPanel = new GridBagLayout();
				gbl_owlPanel.columnWidths = new int[]{228, 0};
				gbl_owlPanel.rowHeights = new int[]{29, 0, 0, 0, 0, 0, 0, 0};
				gbl_owlPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_owlPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
				owlPanel.setLayout(gbl_owlPanel);
				
				lblDataProperties = new JLabel("Data Properties");
				lblDataProperties.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblDataProperties.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblDataProperties = new GridBagConstraints();
				gbc_lblDataProperties.anchor = GridBagConstraints.WEST;
				gbc_lblDataProperties.insets = new Insets(0, 0, 5, 0);
				gbc_lblDataProperties.gridx = 0;
				gbc_lblDataProperties.gridy = 0;
				owlPanel.add(lblDataProperties, gbc_lblDataProperties);
				
				scrollPane_8 = new JScrollPane();
				scrollPane_8.setPreferredSize(new Dimension(150, 350));
				scrollPane_8.setMinimumSize(new Dimension(150, 350));
				scrollPane_8.setMaximumSize(new Dimension(150, 350));
				GridBagConstraints gbc_scrollPane_8 = new GridBagConstraints();
				gbc_scrollPane_8.fill = GridBagConstraints.HORIZONTAL;
				gbc_scrollPane_8.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane_8.gridx = 0;
				gbc_scrollPane_8.gridy = 1;
				owlPanel.add(scrollPane_8, gbc_scrollPane_8);
				
				dataPropertiesTable = new JTable();
				dataPropertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
				scrollPane_8.setViewportView(dataPropertiesTable);
				dataPropertiesTable.setFillsViewportHeight(true);
				dataPropertiesTable.setShowGrid(true);
				dataPropertiesTable.setShowHorizontalLines(true);
				dataPropertiesTable.setShowVerticalLines(true);
				
				dataPropertiesString = new JTextField();
				dataPropertiesString.setText(DIHelper.getInstance().getProperty(Constants.PROP_URI));
				GridBagConstraints gbc_dataPropertiesString = new GridBagConstraints();
				gbc_dataPropertiesString.insets = new Insets(0, 0, 5, 0);
				gbc_dataPropertiesString.fill = GridBagConstraints.HORIZONTAL;
				gbc_dataPropertiesString.gridx = 0;
				gbc_dataPropertiesString.gridy = 2;
				owlPanel.add(dataPropertiesString, gbc_dataPropertiesString);
				dataPropertiesString.setColumns(10);
				// add the routine to do the predicate and properties

				
				lblObjectProperties = new JLabel("Object Properties");
				lblObjectProperties.setFont(new Font("Tahoma", Font.BOLD, 11));
				GridBagConstraints gbc_lblObjectProperties = new GridBagConstraints();
				gbc_lblObjectProperties.anchor = GridBagConstraints.WEST;
				gbc_lblObjectProperties.insets = new Insets(0, 0, 5, 0);
				gbc_lblObjectProperties.gridx = 0;
				gbc_lblObjectProperties.gridy = 3;
				owlPanel.add(lblObjectProperties, gbc_lblObjectProperties);
				
				scrollPane_7 = new JScrollPane();
				scrollPane_7.setPreferredSize(new Dimension(150, 350));
				scrollPane_7.setMinimumSize(new Dimension(150, 350));
				scrollPane_7.setMaximumSize(new Dimension(150, 350));
				GridBagConstraints gbc_scrollPane_7 = new GridBagConstraints();
				gbc_scrollPane_7.fill = GridBagConstraints.BOTH;
				gbc_scrollPane_7.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane_7.gridx = 0;
				gbc_scrollPane_7.gridy = 4;
				owlPanel.add(scrollPane_7, gbc_scrollPane_7);
				
				objectPropertiesTable = new JTable();
				scrollPane_7.setViewportView(objectPropertiesTable);
				objectPropertiesTable.setShowGrid(true);
				objectPropertiesTable.setShowHorizontalLines(true);
				objectPropertiesTable.setShowVerticalLines(true);
				
				objectPropertiesString = new JTextField();
				objectPropertiesString.setText(DIHelper.getInstance().getProperty(Constants.PREDICATE_URI));

				GridBagConstraints gbc_objectPropertiesString = new GridBagConstraints();
				gbc_objectPropertiesString.anchor = GridBagConstraints.BELOW_BASELINE;
				gbc_objectPropertiesString.insets = new Insets(0, 0, 5, 0);
				gbc_objectPropertiesString.fill = GridBagConstraints.HORIZONTAL;
				gbc_objectPropertiesString.gridx = 0;
				gbc_objectPropertiesString.gridy = 5;
				owlPanel.add(objectPropertiesString, gbc_objectPropertiesString);
				objectPropertiesString.setColumns(10);
				
				btnRepaintGraph = new JButton("Refresh");
				GridBagConstraints gbc_btnRepaintGraph = new GridBagConstraints();
				gbc_btnRepaintGraph.gridx = 0;
				gbc_btnRepaintGraph.gridy = 6;
				owlPanel.add(btnRepaintGraph, gbc_btnRepaintGraph);

				outputPanel = new JPanel();
				outputPanel.setBackground(SystemColor.control);
				leftView.addTab("Graph Labels", null, outputPanel, null);
				GridBagLayout gbl_outputPanel = new GridBagLayout();
				gbl_outputPanel.columnWidths = new int[] {231};
				gbl_outputPanel.rowHeights = new int[] {0, 0, 350, 0, 350, 150};
				gbl_outputPanel.columnWeights = new double[]{1.0};
				gbl_outputPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
				outputPanel.setLayout(gbl_outputPanel);
				
				propertyTable2 = new JTable();
				//propertyTable.setGridColor(Color.LIGHT_GRAY);
				propertyTable2.setShowGrid(true);
				propertyTable2.setShowHorizontalLines(true);
				propertyTable2.setShowVerticalLines(true);
				
						//propertyTable.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
						//propertyTable.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
						//propertyTable.setBackground(Color.WHITE);
						//propertyTable.setForeground(new Color(0, 0, 0));
						//propertyTable.setFillsViewportHeight(true);
						//propertyTable.setCellSelectionEnabled(true);
						//propertyTable.setColumnSelectionAllowed(true);
						//propertyTable.setFillsViewportHeight(true);
						GridBagConstraints gbc_table_21 = new GridBagConstraints();
						gbc_table_21.insets = new Insets(0, 0, 5, 0);
						gbc_table_21.fill = GridBagConstraints.BOTH;
						gbc_table_21.gridx = 0;
						gbc_table_21.gridy = 0;
						JScrollPane pane = new JScrollPane(propertyTable2);
						pane.setMinimumSize(new Dimension(0, 0));
						pane.setMaximumSize(new Dimension(0, 0));
						pane.setPreferredSize(new Dimension(150, 100));
						//pane.setForeground(Color.GRAY);
						outputPanel.add(pane, gbc_table_21);
						
						lblLabelDisplay = new JLabel("Label Display");
						lblLabelDisplay.setFont(new Font("Tahoma", Font.BOLD, 12));
						lblLabelDisplay.setForeground(Color.BLACK);
						lblLabelDisplay.setBackground(Color.BLACK);
						GridBagConstraints gbc_lblLabelDisplay = new GridBagConstraints();
						gbc_lblLabelDisplay.insets = new Insets(0, 0, 5, 0);
						gbc_lblLabelDisplay.gridx = 0;
						gbc_lblLabelDisplay.gridy = 1;
						outputPanel.add(lblLabelDisplay, gbc_lblLabelDisplay);
						
						scrollPane_3 = new JScrollPane((Component) null);
						scrollPane_3.setPreferredSize(new Dimension(150, 350));
						scrollPane_3.setMinimumSize(new Dimension(150, 350));
						scrollPane_3.setMaximumSize(new Dimension(150, 350));
						scrollPane_3.setForeground(Color.GRAY);
						scrollPane_3.setBorder(null);
						scrollPane_3.setBackground(Color.WHITE);
						GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
						gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
						gbc_scrollPane_3.gridx = 0;
						gbc_scrollPane_3.gridy = 2;
						outputPanel.add(scrollPane_3, gbc_scrollPane_3);
						
						labelTable = new JTable();
						labelTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
						scrollPane_3.setViewportView(labelTable);
						labelTable.setShowGrid(true);
						labelTable.setShowHorizontalLines(true);
						labelTable.setShowVerticalLines(true);
						
						lblDisplayTooltip = new JLabel("Tooltip Display");
						lblDisplayTooltip.setFont(new Font("Tahoma", Font.BOLD, 12));
						lblDisplayTooltip.setBackground(Color.BLACK);
						lblDisplayTooltip.setForeground(Color.BLACK);
						GridBagConstraints gbc_lblDisplayTooltip = new GridBagConstraints();
						gbc_lblDisplayTooltip.insets = new Insets(0, 0, 5, 0);
						gbc_lblDisplayTooltip.gridx = 0;
						gbc_lblDisplayTooltip.gridy = 3;
						outputPanel.add(lblDisplayTooltip, gbc_lblDisplayTooltip);
						
						scrollPane_6 = new JScrollPane();
						scrollPane_6.setMaximumSize(new Dimension(150, 350));
						scrollPane_6.setPreferredSize(new Dimension(150, 350));
						scrollPane_6.setMinimumSize(new Dimension(150, 350));
						GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
						gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_6.gridx = 0;
						gbc_scrollPane_6.gridy = 4;
						outputPanel.add(scrollPane_6, gbc_scrollPane_6);
						
						tooltipTable = new JTable();
						scrollPane_6.setViewportView(tooltipTable);
						tooltipTable.setShowGrid(true);
						tooltipTable.setShowHorizontalLines(true);
						tooltipTable.setShowVerticalLines(true);
						
						filterPanel = new JPanel();
						filterPanel.setBackground(SystemColor.control);
						leftView.addTab("Graph Filter", null, filterPanel, null);
						GridBagLayout gbl_filterPanel = new GridBagLayout();
						gbl_filterPanel.columnWidths = new int[]{239, 0};
						gbl_filterPanel.rowHeights = new int[]{44, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						gbl_filterPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
						gbl_filterPanel.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						filterPanel.setLayout(gbl_filterPanel);
						
								filterTable = new JTable();
								filterTable.setShowGrid(true);
								filterTable.setShowHorizontalLines(true);
								filterTable.setShowVerticalLines(true);
								
										scrollPane_2 = new JScrollPane(filterTable);
										GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
										gbc_scrollPane_2.gridheight = 2;
										gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
										gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
										gbc_scrollPane_2.gridx = 0;
										gbc_scrollPane_2.gridy = 0;
										filterPanel.add(scrollPane_2, gbc_scrollPane_2);
										
										filterSliders = new JScrollPane();
										GridBagConstraints gbc_filterSliders = new GridBagConstraints();
										gbc_filterSliders.insets = new Insets(0, 0, 5, 0);
										gbc_filterSliders.fill = GridBagConstraints.BOTH;
										gbc_filterSliders.gridx = 0;
										gbc_filterSliders.gridy = 2;
										filterPanel.add(filterSliders, gbc_filterSliders);
										
										propertyTable = new JTable();
										filterSliders.setViewportView(propertyTable);
										propertyTable.setShowGrid(true);
										propertyTable.setShowHorizontalLines(true);
										propertyTable.setShowVerticalLines(true);
										
										scrollPane_4 = new JScrollPane();
										GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
										gbc_scrollPane_4.insets = new Insets(0, 0, 5, 0);
										gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
										gbc_scrollPane_4.gridx = 0;
										gbc_scrollPane_4.gridy = 3;
										filterPanel.add(scrollPane_4, gbc_scrollPane_4);
										
										edgeTable = new JTable();
										scrollPane_4.setViewportView(edgeTable);
										edgeTable.setShowGrid(true);
										edgeTable.setShowHorizontalLines(true);
										edgeTable.setShowVerticalLines(true);
										
										//scrollPane_2.setColumnHeaderView(filterTable);
										
										refreshButton = new JButton("Refresh Graph");
										GridBagConstraints gbc_refreshGraph = new GridBagConstraints();
										gbc_refreshGraph.insets = new Insets(0, 0, 5, 0);
										gbc_refreshGraph.gridx = 0;
										gbc_refreshGraph.gridy = 6;
										filterPanel.add(refreshButton, gbc_refreshGraph);
						
						cosmeticPanel = new JPanel();
						cosmeticPanel.setBackground(SystemColor.control);
						leftView.addTab("Graph Cosmetics", null, cosmeticPanel, null);
						GridBagLayout gbl_cosmeticPanel = new GridBagLayout();
						gbl_cosmeticPanel.columnWidths = new int[] {231};
						gbl_cosmeticPanel.rowHeights = new int[] {0, 0, 350, 0, 350, 0, 0, 0, 150};
						gbl_cosmeticPanel.columnWeights = new double[]{1.0};
						gbl_cosmeticPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
						cosmeticPanel.setLayout(gbl_cosmeticPanel);
						
						scrollPane_9 = new JScrollPane((Component) null);
						scrollPane_9.setPreferredSize(new Dimension(150, 100));
						scrollPane_9.setMinimumSize(new Dimension(0, 0));
						scrollPane_9.setMaximumSize(new Dimension(0, 0));
						GridBagConstraints gbc_scrollPane_9 = new GridBagConstraints();
						gbc_scrollPane_9.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_9.insets = new Insets(0, 0, 5, 0);
						gbc_scrollPane_9.gridx = 0;
						gbc_scrollPane_9.gridy = 0;
						cosmeticPanel.add(scrollPane_9, gbc_scrollPane_9);
						
						lblColorAndShape = new JLabel("Color and Shape");
						lblColorAndShape.setForeground(Color.DARK_GRAY);
						lblColorAndShape.setFont(new Font("Tahoma", Font.BOLD, 12));
						lblColorAndShape.setBackground(Color.BLACK);
						GridBagConstraints gbc_lblColorAndShape = new GridBagConstraints();
						gbc_lblColorAndShape.insets = new Insets(0, 0, 5, 0);
						gbc_lblColorAndShape.gridx = 0;
						gbc_lblColorAndShape.gridy = 1;
						cosmeticPanel.add(lblColorAndShape, gbc_lblColorAndShape);
						
						scrollPane_10 = new JScrollPane((Component) null);
						scrollPane_10.setPreferredSize(new Dimension(150, 350));
						scrollPane_10.setMinimumSize(new Dimension(150, 350));
						scrollPane_10.setMaximumSize(new Dimension(150, 350));
						scrollPane_10.setForeground(Color.GRAY);
						scrollPane_10.setBorder(null);
						scrollPane_10.setBackground(Color.WHITE);
						GridBagConstraints gbc_scrollPane_10 = new GridBagConstraints();
						gbc_scrollPane_10.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_10.insets = new Insets(0, 0, 5, 0);
						gbc_scrollPane_10.gridx = 0;
						gbc_scrollPane_10.gridy = 2;
						cosmeticPanel.add(scrollPane_10, gbc_scrollPane_10);
						
						colorShapeTable = new JTable();
						scrollPane_10.setViewportView(colorShapeTable);
						colorShapeTable.setShowGrid(true);
						colorShapeTable.setShowHorizontalLines(true);
						colorShapeTable.setShowVerticalLines(true);

						lblSize = new JLabel("Size");
						lblSize.setForeground(Color.BLACK);
						lblSize.setFont(new Font("Tahoma", Font.BOLD, 12));
						lblSize.setBackground(Color.BLACK);
						GridBagConstraints gbc_lblSize = new GridBagConstraints();
						gbc_lblSize.insets = new Insets(0, 0, 5, 0);
						gbc_lblSize.gridx = 0;
						gbc_lblSize.gridy = 3;
						cosmeticPanel.add(lblSize, gbc_lblSize);
						
						scrollPane_11 = new JScrollPane();
						scrollPane_11.setPreferredSize(new Dimension(150, 350));
						scrollPane_11.setMinimumSize(new Dimension(150, 350));
						scrollPane_11.setMaximumSize(new Dimension(150, 350));
						GridBagConstraints gbc_scrollPane_11 = new GridBagConstraints();
						gbc_scrollPane_11.insets = new Insets(0, 0, 5, 0);
						gbc_scrollPane_11.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_11.gridx = 0;
						gbc_scrollPane_11.gridy = 4;
						cosmeticPanel.add(scrollPane_11, gbc_scrollPane_11);
						
						sizeTable = new JTable();
						scrollPane_11.setViewportView(sizeTable);
						
						btnColorShape = new JButton("Refresh Graph");
						GridBagConstraints gbc_btnColorShape = new GridBagConstraints();
						gbc_btnColorShape.insets = new Insets(0, 0, 5, 0);
						gbc_btnColorShape.gridx = 0;
						gbc_btnColorShape.gridy = 5;
						cosmeticPanel.add(btnColorShape, gbc_btnColorShape);
						
						panel_6 = new JPanel();
						GridBagConstraints gbc_panel_6 = new GridBagConstraints();
						gbc_panel_6.insets = new Insets(0, 0, 5, 0);
						gbc_panel_6.fill = GridBagConstraints.BOTH;
						gbc_panel_6.gridx = 0;
						gbc_panel_6.gridy = 6;
						cosmeticPanel.add(panel_6, gbc_panel_6);
						
						btnResetDefaults = new JButton("Reset Defaults");
						GridBagConstraints gbc_btnResetDefaults = new GridBagConstraints();
						gbc_btnResetDefaults.gridx = 0;
						gbc_btnResetDefaults.gridy = 7;
						cosmeticPanel.add(btnResetDefaults, gbc_btnResetDefaults);
							splitPane.setDividerLocation(300);
							GridBagConstraints gbc_splitPane = new GridBagConstraints();
							gbc_splitPane.fill = GridBagConstraints.BOTH;
							gbc_splitPane.gridx = 0;
							gbc_splitPane.gridy = 0;
							getContentPane().add(splitPane, gbc_splitPane);
			//UIManager.put("nimbusBase", Color.BLUE);
			//UIManager.put("nimbusBlueGrey", new Color(102,0,0));
			//UIManager.put("control", Color.WHITE);
	

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
