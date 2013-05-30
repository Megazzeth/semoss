package prerna.ui.helpers;

import java.util.Collections;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import prerna.rdf.engine.api.IEngine;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class EntityFiller implements Runnable {

	public JComboBox box;
	public String type;
	public IEngine engine;
	public String engineName;

	@Override
	public void run() {
		// Get access to engine
		// get the type query based on the type
		// fill the param on the query
		// run the query
		// create a new combobox model
		// plug it in
		System.out.println(" Engine Name is  " + engineName);
		engine = (IEngine)DIHelper.getInstance().getLocalProp(engineName);
		
		Vector <String> names = new Vector<String>();

		if (box != null && type != null) { 
			// only get the first one
			// IEngine engine = DIHelper.getInstance().getRdfEngine();
			String entityNS = DIHelper.getInstance().getProperty(type);

			if (entityNS != null) {
				if (DIHelper.getInstance().getLocalProp(type) == null) {
					String sparqlQuery = DIHelper.getInstance().getProperty(
							"TYPE" + "_" + Constants.QUERY);
					// System.out.println()
					Hashtable paramTable = new Hashtable();
					paramTable.put(Constants.ENTITY, entityNS);
					sparqlQuery = Utility.fillParam(sparqlQuery, paramTable);	
				
					names = engine.getEntityOfType(sparqlQuery);
					Collections.sort(names);
					//DIHelper.getInstance().setLocalProperty(type, names);
				}
			} else if(DIHelper.getInstance().getProperty(
						type + "_" + Constants.OPTION) != null){
				// try the other route // assumes the names is never empty
				// if it is.. try to see if it is a custom field
				// and read from property
				// try to pick this from DBCM Properties table
				// this will typically be of the format
				String options = DIHelper.getInstance().getProperty(
						type + "_" + Constants.OPTION);
				// this is a string with ; delimited values
				StringTokenizer tokens = new StringTokenizer(options, ";");
				//String[] names = new String[tokens.countTokens()];
				// sorry for the cryptic crap below
				int tknIndex = 0;
				for (; tokens.hasMoreTokens(); names.addElement(tokens
						.nextToken()), tknIndex++)
					;
				//DIHelper.getInstance().setLocalProperty(type, names);
			}
			else
			{
				//String[] names = {"Unknown"};
				names.addElement("Unknown");
				//DIHelper.getInstance().setLocalProperty(type, names);				
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(names);
					//(Vector<String>) DIHelper.getInstance().getLocalProp(type));
			// box = new JComboBox((String [])DIHelper.getInstance().getLocalProp(type));
			box.setModel(model);
			box.setEditable(false);
		}
	}
}
