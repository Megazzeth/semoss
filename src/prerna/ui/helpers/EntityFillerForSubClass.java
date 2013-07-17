package prerna.ui.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import prerna.rdf.engine.api.IEngine;
import prerna.util.DIHelper;

public class EntityFillerForSubClass implements Runnable {
	public ArrayList<JComboBox> boxes;
	public String parent;
	public IEngine engine;
	public String engineName;
	String sparqlQuery = "SELECT ?entity WHERE {?entity <http://www.w3.org/2000/01/rdf-schema#subClassOf> <"; 
	
	@Override
	public void run() {
		// Get access to engine
		// get the type query based on the type
		// fill the param on the query
		// run the query
		// create a new combobox model
		// plug it in

		Vector<String> names = new Vector<String>();
		
		String entityNS = DIHelper.getInstance().getProperty(parent);
		if (entityNS != null) {
			sparqlQuery = sparqlQuery + entityNS + "> ;}";
			names = engine.getEntityOfType(sparqlQuery);
			Collections.sort(names);
			names.add(0, "");
		}
		
		for(JComboBox box : boxes) {
			if (box != null) {
				DefaultComboBoxModel model = new DefaultComboBoxModel(names);
				box.setModel(model);
				box.setEditable(false);
			}
		}
	}
}
