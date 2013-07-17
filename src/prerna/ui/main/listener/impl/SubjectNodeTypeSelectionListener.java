package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaSelectStatement;
import prerna.rdf.engine.impl.SesameJenaSelectWrapper;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class SubjectNodeTypeSelectionListener extends AbstractListener {
	
	Logger logger = Logger.getLogger(getClass());
	String subjectNodeType = "";
	int exportNo = 1;
	ActionEvent event;
	private static final String CONCEPT = "Concept";
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JComboBox subjectNodeTypeComboBox = (JComboBox) arg0.getSource();
		int length = subjectNodeTypeComboBox.getName().length();
		this.exportNo = Integer.parseInt(subjectNodeTypeComboBox.getName().substring(length-1, length));
		this.subjectNodeType = subjectNodeTypeComboBox.getSelectedItem().toString();
		
		if(this.subjectNodeType != null && !this.subjectNodeType.equals("")) {
			runQuery(this.subjectNodeType);
		}
	}
	
	private void populateObjectComboBox(Object[] values) {
		JComboBox objectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + this.exportNo);
		DefaultComboBoxModel model = new DefaultComboBoxModel(values);
		objectNodeTypeComboBox.setModel(model);
		objectNodeTypeComboBox.setEditable(false);
		
		updateRelationshipComboBox();
	}
	
	private void updateRelationshipComboBox() {
		JComboBox objectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + this.exportNo);
		if(objectNodeTypeComboBox.getSelectedItem() != null) {
			ObjectNodeTypeSelectionListener objectListener = new ObjectNodeTypeSelectionListener();
			objectListener.updateFromSubjectNodeTypeComboBox(this.exportNo);
		}
	}

	private void runQuery(String nodeType) {
		String query = "SELECT DISTINCT ?s WHERE { {?in <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
		query += nodeType;
		query += "> ;}";
		
		query += " {?s <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://health.mil/ontologies/dbcm/Concept> ;}" 
				+ "{?out <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?s ;} {?in ?p ?out ;} }";
		
		JList repoList = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		Object[] repo = (Object[])repoList.getSelectedValues();
		IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repo[0]+"");
		
		SesameJenaSelectWrapper wrapper = new SesameJenaSelectWrapper();
		wrapper.setQuery(query);
		wrapper.setEngine(engine);
		wrapper.executeQuery();
		
		int count = 0;
		String[] names = wrapper.getVariables();
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		HashSet<String> properties = new HashSet<String>();
		
		try {
			while(wrapper.hasNext()) {
				SesameJenaSelectStatement sjss = wrapper.next();
				Object [] values = new Object[names.length];
				boolean filledData = true;
				
				for(int colIndex = 0;colIndex < names.length;colIndex++) {
					if(sjss.getVar(names[colIndex]) != null) {
						if(colIndex == 1) {
							properties.add((String) sjss.getVar(names[colIndex]));
						}
						values[colIndex] = sjss.getVar(names[colIndex]);
					}
					else {
						filledData = false;
						break;
					}
				}
				if(filledData) {
					list.add(count, values);
					count++;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<String> objectNodeTypes = new ArrayList<String>();
		if(list.size() > 0) {
			for(Object[] array : list) {
				if(array[0] != null && !CONCEPT.equals(array[0])) {
					objectNodeTypes.add((String) array[0]);
				}
			}
		} else {
			objectNodeTypes.add(0, "None");
		}
		
		populateObjectComboBox(objectNodeTypes.toArray());
	}
	

	@Override
	public void setView(JComponent view) {
		
	}
}
