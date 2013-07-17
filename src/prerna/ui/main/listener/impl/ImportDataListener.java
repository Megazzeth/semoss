package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import prerna.poi.main.OntologyFileWriter;
import prerna.poi.main.POIReader;
import prerna.poi.main.PropFileWriter;
import prerna.ui.components.UpdateProcessor;
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
					ontologyWriter.runAugment(mapName, reader.importReader.createdURIsHash, 
							reader.importReader.baseObjRelations, reader.importReader.createdRelURIsHash, reader.importReader.baseRelRelations, 
							reader.importReader.dbPropURI, reader.importReader.dbPredicateURI);

					Utility.showMessage("Your database has been successfully updated!");
				}
				catch (Exception ex)
				{
					Utility.showError("Load has failed. Please make sure the loads sheets in the excel file are \nformatted correctly, and objects match the map file.");
				}
			}

		}
		else if(selection.equals("Modify/Replace data in existing engine")) {
			String fileName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_FIELD)).getText();
			//get the engine information
			JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			String repo = list.getSelectedValue()+"";
			String mapName = DIHelper.getInstance().getProperty(repo+"_"+Constants.ONTOLOGY);

			executeDeleteAndLoad(repo, fileName, customBaseURI, mapName);
		}
		else if (selection.equals("Create new database engine"))
		{
			//get necessary file information
			String mapFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.MAP_TEXT_FIELD)).getText();
			String dbPropFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_PROP_TEXT_FIELD)).getText();
			String questionFile = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.QUESTION_TEXT_FIELD)).getText();
			PropFileWriter propWriter = new PropFileWriter();
			String dbName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.DB_NAME_FIELD)).getText();

			propWriter.runWriter(dbName, mapFile, dbPropFile, questionFile);
			String fileName = ((JTextField)DIHelper.getInstance().getLocalProp(Constants.IMPORT_FILE_FIELD)).getText();

				try{
				reader.importFileWithOutConnection(propWriter.propFileName, fileName, customBaseURI, mapFile);
				File propFile = new File(propWriter.propFileName);
				File newProp = new File(propWriter.propFileName.replace("temp", "smss"));
				FileUtils.copyFile(propFile, newProp);
				propFile.delete();

				OntologyFileWriter ontologyWriter = new OntologyFileWriter();
				ontologyWriter.runAugment(propWriter.ontologyFileName, reader.importReader.createdURIsHash, 
						reader.importReader.baseObjRelations, reader.importReader.createdRelURIsHash, 
						reader.importReader.baseRelRelations, reader.importReader.dbPropURI, reader.importReader.dbPredicateURI);

				Hashtable propHash = propWriter.newProp;

				Enumeration<String> enumKey = propHash.keys();
				while(enumKey.hasMoreElements()) {
					String key = enumKey.nextElement();
					String value = (String) propHash.get(key);
					DIHelper.getInstance().setCoreProperty(key, value);
				}
				Utility.showMessage("Your database has been created successfully!");
			}
			catch(Exception ex)
			{
				try {
					reader.importReader.closeDB();
					System.out.println("SC IS OPEN:" + reader.importReader.sc.isOpen());
				} catch(Exception exe){
					
				}
				File propFile = new File(propWriter.propFileName);
				deleteFile(propFile);
				File propFile2 = propWriter.engineDirectory;
				deleteFile(propFile2);//need to use this function because must clear directory before i can delete it
				Utility.showError("Load has failed. Please make sure the loads sheets in the excel file are \nformatted correctly, and objects match the map file.");
			}
		}
	}
	
	public void executeDeleteAndLoad(String repo, String fileName, String customBaseURI, String mapName) {
		POIReader reader = new POIReader();

		try {
			//Delete all nodes/relationships of specified types
			XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(fileName.replace(";", "")));
			XSSFSheet lSheet = book.getSheet("Loader");
			int lastRow = lSheet.getLastRowNum();

			ArrayList<String> sheets = new ArrayList<String>();
			ArrayList<String> nodes = new ArrayList<String>();
			ArrayList<String[]> relationships = new ArrayList<String[]>();
			for (int rIndex = 1; rIndex <= lastRow; rIndex++) {
				XSSFRow sheetNameRow = lSheet.getRow(rIndex);
				XSSFCell cell = sheetNameRow.getCell(0);
				XSSFSheet sheet = book.getSheet(cell.getStringCellValue());

				XSSFRow row = sheet.getRow(0);
				String sheetType = "";
				if(row.getCell(0) != null) {
					sheetType = row.getCell(0).getStringCellValue();
				}
				if("Node".equalsIgnoreCase(sheetType)) {
					if(row.getCell(1) != null) {
						nodes.add(row.getCell(1).getStringCellValue());
					}
				}
				if("Relation".equalsIgnoreCase(sheetType)) {
					String subject = "";
					String object = "";
					String relationship = "";
					if(row.getCell(1) != null && row.getCell(2) != null) {
						subject = row.getCell(1).getStringCellValue();
						object = row.getCell(2).getStringCellValue();

						row = sheet.getRow(1);
						if(row.getCell(0) != null) {
							relationship = row.getCell(0).getStringCellValue();
						}

						relationships.add(new String[]{subject, relationship, object});
					}
				}
			}
			JFrame playPane = (JFrame) DIHelper.getInstance().getLocalProp(Constants.MAIN_FRAME);
			Object[] buttons = {"Cancel", "Continue"};
			String replacedString = "";
			for(String node: nodes) replacedString = replacedString + node + "\n";
			for(String[] rel : relationships) replacedString = replacedString + rel[0] +" " + rel[1] + " " + rel[2]+"\n";
			int response = JOptionPane.showOptionDialog(playPane, "This move cannot be undone.\nPlease make sure the excel file is formatted correctly and make a back up jnl file before continuing.\n\nThe following data will be replaced:\n\n" +
					replacedString +
					"\n" +
					"Would you still like to continue?", 
					"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[1]);
			if (response == 1)
			{
				String deleteQuery = "";
				UpdateProcessor proc = new UpdateProcessor();

				int numberNodes = nodes.size();
				if(numberNodes > 0) {
					deleteQuery = "DELETE {?s ?p ?prop. ?s ?x ?y} WHERE { {";
					deleteQuery += "SELECT ?s ?p ?prop ?x ?y WHERE { {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
					deleteQuery += nodes.get(0);
					deleteQuery += "> ;} {?s ?x ?y} MINUS {?x <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> <http://health.mil/ontologies/dbcm/Relation> ;} ";
					deleteQuery += "OPTIONAL{ {?p <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Relation/Contains> ;} {?s ?p ?prop ;} } } } ";

					if(numberNodes == 1) {
						for(int i = 1; i < numberNodes; i++) {
							deleteQuery += "UNION { ";
							deleteQuery += "SELECT ?s ?p ?prop ?x ?y WHERE { {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
							deleteQuery += nodes.get(i);
							deleteQuery += "> ;} {?s ?x ?y}";
							deleteQuery += "OPTIONAL{ {?p <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Relation/Contains> ;} {?s ?p ?prop ;} } } } ";
						}
					}
					deleteQuery += "}";

					proc.setQuery(deleteQuery);
					proc.processQuery();
				}

				int numberRelationships = relationships.size();
				if(numberRelationships > 0) {
					deleteQuery = "DELETE {?in ?relationship ?out. ?relationship ?contains ?prop} WHERE { {";
					deleteQuery += "SELECT ?in ?relationship ?out ?contains ?prop WHERE { "+ 
							"{?in <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
					deleteQuery += relationships.get(0)[0];
					deleteQuery += "> ;}";

					deleteQuery += "{?out <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
					deleteQuery += relationships.get(0)[2];
					deleteQuery += "> ;}";

					deleteQuery += "{?relationship <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> <http://health.mil/ontologies/dbcm/Relation/";
					deleteQuery += relationships.get(0)[1];
					deleteQuery += "> ;} {?in ?relationship ?out ;} ";
					deleteQuery += "OPTIONAL {{?relationship ?contains ?prop ;} } } }";

					if(numberRelationships > 1) {
						for(int i = 1; i < numberRelationships; i++) {
							deleteQuery += "UNION { ";
							deleteQuery += "SELECT ?in ?relationship ?out ?contains ?prop WHERE { "+ 
									"{?in <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
							deleteQuery += relationships.get(i)[0];
							deleteQuery += "> ;}";

							deleteQuery += "{?out <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
							deleteQuery += relationships.get(i)[2];
							deleteQuery += "> ;}";

							deleteQuery += "{?relationship <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> <http://health.mil/ontologies/dbcm/Relation/";
							deleteQuery += relationships.get(i)[1];
							deleteQuery += "> ;} {?in ?relationship ?out ;} ";
							deleteQuery += "OPTIONAL { {?relationship ?contains ?prop ;} } } }";
						}

					}
					deleteQuery += "} ";

					proc.setQuery(deleteQuery);
					proc.processQuery();
				}

				//run the reader
				reader.importFileWithConnection(repo, fileName, customBaseURI, mapName);

				//run the ontology augmentor

				OntologyFileWriter ontologyWriter = new OntologyFileWriter();
				ontologyWriter.runAugment(mapName, reader.importReader.createdURIsHash, 
						reader.importReader.baseObjRelations, reader.importReader.createdRelURIsHash, reader.importReader.baseRelRelations, 
						reader.importReader.dbPropURI, reader.importReader.dbPredicateURI);

				Utility.showMessage("Your database has been successfully updated!");
			}
		} catch (Exception ex)
		{
			Utility.showError("Load has failed. Please make sure the loads sheets in the excel file are \nformatted correctly, and objects match the map file.");
		}
	}

	public void deleteFile(File file){
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        	      
	        	      //this delete is only for two levels.  At this point, must be file, so just delete it
	        	     fileDelete.delete();
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		this.view = (JTextField)view;

	}


}
