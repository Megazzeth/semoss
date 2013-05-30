package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import prerna.poi.main.OntologyFileWriter;
import prerna.poi.main.POIReader;
import prerna.poi.main.PropFileWriter;
import prerna.rdf.engine.api.IEngine;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class ImportDataListener implements IChakraListener {

	JTextField view = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	
	public void setModel(JComponent model)
	{
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the import data
		// trigger the import
		POIReader reader = new POIReader();
		JComboBox comboBox = (JComboBox)DIHelper.getInstance().getLocalProp(Constants.IMPORT_COMBOBOX);
		String selection = comboBox.getSelectedItem() + "";
		
		String customBaseURI = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.BASE_URI_TEXT_FIELD)).getText();
		
		if(selection.equals("Add to existing database engine"))
			{
				JFrame playPane = (JFrame) DIHelper.getInstance().getLocalProp(Constants.MAIN_FRAME);
				Object[] buttons = {"Cancel Loading", "Continue With Loading"};
				int response = JOptionPane.showOptionDialog(playPane, "This move cannot be undone. Please make sure the excel file is formatted correctly \nand make a back up jnl file before continuing. Would you still like to continue?", 
						"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[1]);
				if (response ==1)
				{
					try {
						String fileName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_FIELD)).getText();
						//get the engine information
						JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
						String repo = list.getSelectedValue()+"";
						String mapName = DIHelper.getInstance().getProperty(repo+"_"+Constants.ONTOLOGY);
						
						//run the reader
						reader.importFileWithConnection(repo, fileName, customBaseURI, mapName);
						
						//run the ontology augmentor

						OntologyFileWriter ontologyWriter = new OntologyFileWriter();
						ontologyWriter.runAugment(mapName, reader.createdURIsHash, 
								reader.baseObjRelations, reader.createdRelURIsHash, reader.baseRelRelations, reader.dbPropURI, reader.dbPredicateURI);
						
						Utility.showMessage("Your database has been successfully updated!");
					}
					catch (Exception ex)
					{
						Utility.showError("Load has failed. Please make sure the loads sheets in the excel file are \nformatted correctly, and objects match the map file.");
					}
				}

			}
			else if (selection.equals("Create new database engine"))
			{
				try{
					//get necessary file information
					String mapFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.MAP_TEXT_FIELD)).getText();
					String dbPropFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_PROP_TEXT_FIELD)).getText();
					String questionFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.QUESTION_TEXT_FIELD)).getText();
					PropFileWriter propWriter = new PropFileWriter();
					String dbName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_NAME_FIELD)).getText();
					
					propWriter.runWriter(dbName, mapFile, dbPropFile, questionFile);
					String fileName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_FIELD)).getText();
					 
					reader.importFileWithOutConnection(propWriter.propFileName, fileName, customBaseURI, mapFile);

					OntologyFileWriter ontologyWriter = new OntologyFileWriter();
					ontologyWriter.runAugment(propWriter.ontologyFileName, reader.createdURIsHash, 
							reader.baseObjRelations, reader.createdRelURIsHash, reader.baseRelRelations, reader.dbPropURI, reader.dbPredicateURI);
					
					Hashtable propHash = propWriter.newProp;
					
					Enumeration<String> enumKey = propHash.keys();
					while(enumKey.hasMoreElements()) {
					    String key = enumKey.nextElement();
						String value = (String) propHash.get(key);
						DIHelper.getInstance().setCoreProperty(key, value);
					}
					
					String engineName = propWriter.engineName;
					String engineClass = DIHelper.getInstance().getProperty(engineName);
					logger.info("Trying to start engine " + engineName + "<<>>" + engineClass);
					IEngine engine = null;

					engine = (IEngine)Class.forName(engineClass).newInstance();				
					// startup the engine etc. - need to change it later to only what you need
					String enginePropFile = DIHelper.getInstance().getProperty(engineName + "_PROP");
					engine.openDB(enginePropFile);
					
					// set the reference
					DIHelper.getInstance().setLocalProperty(engineName, engine);
					JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
					DefaultListModel listModel = (DefaultListModel) list.getModel();
					listModel.addElement(engineName);
					list.setModel(listModel);
					list.setSelectedIndex(0);
					list.repaint();
					JFrame frame2 = (JFrame) DIHelper.getInstance().getLocalProp(
							Constants.MAIN_FRAME);
					frame2.repaint();
					Utility.showMessage("Your database has been created successfully!");
					}
					catch(Exception ex)
					{
						Utility.showError("Load has failed. Please make sure the loads sheets in the excel file are \nformatted correctly, and objects match the map file.");
					}
			}



		
	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.view = (JTextField)view;
		
	}


}
