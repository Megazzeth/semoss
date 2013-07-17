package prerna.ui.main.listener.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.helpers.EntityFillerForSubClass;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class RepoSelectionListener implements ListSelectionListener {

	static RepoSelectionListener instance = null;

	public static RepoSelectionListener getInstance()
	{
		if(instance == null)
			instance = new RepoSelectionListener();
		return instance;
	}

	protected RepoSelectionListener()
	{
	}

	Logger logger = Logger.getLogger(getClass());
	// when the repo is selected, load the specific properties file
	// along with it load the database and the questions

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		logger.info("Repository Changed");
		JList list = (JList)e.getSource(); //DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		if (!list.isSelectionEmpty())
		{
			logger.info("Engine selected " + list.getSelectedValue()); //e.getFirstIndex();
			// now get the prop file
			// need to change this to local prop
			String qPropName = (String)DIHelper.getInstance().getCoreProp().get(list.getSelectedValue() + "_" + Constants.DREAMER);
			String ontologyPropName = (String)DIHelper.getInstance().getCoreProp().get(list.getSelectedValue() + "_" + Constants.ONTOLOGY);
			
			logger.info("Question file to load " + qPropName);
			logger.info("Ontology file to load " + qPropName);
			// and use the prop file to load the engine and perspectives
			DIHelper.getInstance().loadEngineProp(list.getSelectedValue()+"", qPropName, ontologyPropName);
			// do this after
			// need a method in the DIHelper which loads a properties file first
			// and then loads perspectives etc.
			// once this is done.. keep the core properties pointed to it / need to modify the calls on process query listener etc. 
			Hashtable perspectiveHash = (Hashtable) DIHelper.getInstance().getLocalProp(Constants.PERSPECTIVE);
			Vector<String> perspectives = Utility.convertEnumToArray(perspectiveHash.keys(), perspectiveHash.size());
			Collections.sort(perspectives);
			System.out.println("Perspectives " + perspectiveHash);
			JComboBox box = (JComboBox)DIHelper.getInstance().getLocalProp(Constants.PERSPECTIVE_SELECTOR);
			//JComboBoxModel model = new JComboBoxModel();
			box.removeAllItems();
			for(int itemIndex = 0;itemIndex < perspectives.size();((JComboBox)DIHelper.getInstance().getLocalProp(Constants.PERSPECTIVE_SELECTOR)).addItem(perspectives.get(itemIndex)), itemIndex++);

			//Clear then populate Node Type drop-down in Export Load Sheet Panel
			try {
				LoadSheetExportClearAllListener clear = new LoadSheetExportClearAllListener();
				clear.actionPerformed(null);
				populateLoadSheetExportComboBoxes(list);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}


	}
	
	public void populateLoadSheetExportComboBoxes(JList list) {
		ArrayList<JComboBox> boxes = new ArrayList<JComboBox>();
		for(int i = 1; i <= Constants.MAX_EXPORTS; i++) {
			JComboBox subjectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_SUBJECT_NODE_TYPE_COMBOBOX + i);
			boxes.add(subjectNodeTypeComboBox);
		}
		IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(list.getSelectedValue()+"");
		EntityFillerForSubClass entityFillerSC = new EntityFillerForSubClass();
		entityFillerSC.boxes = boxes;
		entityFillerSC.engine = engine;
		entityFillerSC.parent = "Concept";
		Thread aThread = new Thread(entityFillerSC);
		aThread.run();
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(new String[0]);
		JComboBox objectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + "1");
		objectNodeTypeComboBox.setModel(model);
		objectNodeTypeComboBox.setEditable(false);
		JComboBox nodeRelationshipComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_NODE_RELATIONSHIP_COMBOBOX + "1");
		nodeRelationshipComboBox.setModel(model);
		nodeRelationshipComboBox.setEditable(false);
	}

}
