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

public class ObjectNodeTypeSelectionListener extends AbstractListener {

	Logger logger = Logger.getLogger(getClass());
	String subjectNodeType = "";
	String objectNodeType = "";
	int exportNo = 1;
	private static final String NONE_SELECTED = "None";
	private static final String RELATION_URI = "http://health.mil/ontologies/dbcm/Relation/";
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0 != null) {
			JComboBox source = (JComboBox) arg0.getSource();
			int length = source.getName().length();
			this.exportNo = Integer.parseInt(source.getName().substring(length-1, length));
		}

		JComboBox subjectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_SUBJECT_NODE_TYPE_COMBOBOX + this.exportNo);
		JComboBox objectNodeTypeComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_OBJECT_NODE_TYPE_COMBOBOX + this.exportNo);
		if(subjectNodeTypeComboBox.getSelectedItem() != null && objectNodeTypeComboBox.getSelectedItem() != null) {
			this.subjectNodeType = subjectNodeTypeComboBox.getSelectedItem().toString();
			this.objectNodeType = objectNodeTypeComboBox.getSelectedItem().toString();
		}
		
		runQuery(this.subjectNodeType, this.objectNodeType);
	}
	
	public void updateFromSubjectNodeTypeComboBox(int exportNo) {
		this.exportNo = exportNo;
		this.actionPerformed(null);
	}

	@Override
	public void setView(JComponent view) {
	}
	
	private void populateRelationshipComboBox(Object[] values) {
		JComboBox nodeRelationshipComboBox = (JComboBox) DIHelper.getInstance().getLocalProp(Constants.EXPORT_LOAD_SHEET_NODE_RELATIONSHIP_COMBOBOX + this.exportNo);
		DefaultComboBoxModel model = new DefaultComboBoxModel(values);
		nodeRelationshipComboBox.setModel(model);
		nodeRelationshipComboBox.setEditable(false);
	}

	private void runQuery(String subjectNodeType, String objectNodeType) {
		String query = "SELECT DISTINCT ?verb WHERE {" + 
				"{?in <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
		query += subjectNodeType;
		query += "> ;} ";
		
		if(this.NONE_SELECTED.equals(subjectNodeType)) {
			String[] noneValueArray = {"None"};
			populateRelationshipComboBox(noneValueArray);
		} else {
			query += "{?out <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/";
			query += objectNodeType;
			query += "> ;} ";
		}
		
		query += "{?in ?relationship ?out ;} {?relationship <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> ?verb } }";
		
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
					Object var = sjss.getRawVar(names[colIndex]);
					if(var != null) {
						if(var.toString() != null && var.toString().contains(this.RELATION_URI)) {
							if(!var.toString().replace(this.RELATION_URI, "").contains("/")) {
								if(colIndex == 1) {
									properties.add((String) var.toString().replace(this.RELATION_URI, ""));
								}
								values[colIndex] = var.toString().replace(this.RELATION_URI, "");
							} else {
								filledData = false;
								break;
							}
						}
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
				if(array[0] != null) {
					objectNodeTypes.add(array[0].toString());
				}
			}
		} else {
			objectNodeTypes.add(0, "None");
		}
		
		populateRelationshipComboBox(objectNodeTypes.toArray());
	}
	
}
