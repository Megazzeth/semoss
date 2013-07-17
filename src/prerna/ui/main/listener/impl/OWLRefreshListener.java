package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.log4j.Logger;
import org.openrdf.model.vocabulary.RDF;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.PropertySpecData;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

import com.hp.hpl.jena.vocabulary.RDFS;

public class OWLRefreshListener implements IChakraListener {

	// quite simple
	// when refresh is pressed
	// get the active sheet and call the repaint

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		
		// puts the parent and the child predicate
		// it can be like Parent - BusinessProcess and Child - BusinessProcess/Anaesthesia
		// When the user selects this to be a property
		// I will remove the type-of relationship from the jenamodel
		// and insert child subproperty-of to contains - not the parent
		// because if I did to parent everything else would become a property too
		// however this is not attached to anything
		// 

		GraphPlaySheet ps = (GraphPlaySheet)QuestionPlaySheetStore.getInstance().getActiveSheet();

		// Step 1 : Establish Concept Model
		// converting something into a concept is fairly simple and straight forward
		// get all of the conceptHash
		// run through each one of them
		// if you dont see it in the prop Hash - remove it from the jenaModel
		// aggregate all the new concepts
		PropertySpecData data = ps.getPredicateData();
				
		// finish the subjects first
		logger.warn("Removing Subjects " + data.subject2bRemoved);
		ps.removeExistingConcepts(createTriples2Remove(data.subject2bRemoved, "<http://health.mil/ontologies/dbcm/Concept>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>"));

		logger.warn("Adding Subjects " + data.subject2bAdded);
		ps.addNewConcepts(data.subject2bAdded, "http://health.mil/ontologies/dbcm/Concept", RDF.TYPE + "");
		
		// finish the predicates next
		// this wont work since I am not giving it the hierarchy
		logger.warn("Removing Predicates " + data.pred2bRemoved);
		ps.removeExistingConcepts(createTriples2Remove(data.pred2bRemoved, "<http://health.mil/ontologies/dbcm/Relation>", "<http://www.w3.org/2000/01/rdf-schema#subPropertyOf>"));
		
		logger.warn("Adding Predicates " + data.pred2bAdded);
		ps.addNewConcepts(data.pred2bAdded, "http://health.mil/ontologies/dbcm/Relation", RDFS.subPropertyOf+"");
		
		// finish the properties
		// this is interesting because the properties has to be contains relation
		logger.warn("Removing Properties " + data.prop2bRemoved);
		ps.removeExistingConcepts(createTriples2Remove(data.prop2bRemoved, "<http://health.mil/ontologies/dbcm/Relation/Contains>", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>"));
		
		logger.warn("Adding Properties " + data.prop2bAdded);
		ps.addNewConcepts(data.prop2bAdded, "http://health.mil/ontologies/dbcm/Relation/Contains", RDF.TYPE+"");
		
		
		
		// Step 2 - Converting a Relation to a Property
		// to Convert a relationship to a property
		// 1. query everything associate with the child element
		// 2. query for the base types
		// for each
		// 3. blow take it away
		// 4. insert with contains relationship
		// done for
		// 4. For each relations from step 1
		// 5. Remove it 
		// 6. Convert it into the appropriate contains relationship // ah just make it contains
		// 7. The base graph would now be all set
		
		// To Convert Property to a relationship
		// Seems like exact opposite of the previous

		// 
		
		// set the values from here just in case the user has edited it
		
		
		
		JTextField field = (JTextField)DIHelper.getInstance().getLocalProp(Constants.OBJECT_PROP_STRING);
		DIHelper.getInstance().putProperty(Constants.PREDICATE_URI, field.getText());

		field = (JTextField)DIHelper.getInstance().getLocalProp(Constants.DATA_PROP_STRING);
		DIHelper.getInstance().putProperty(Constants.PROP_URI, field.getText());

		JToggleButton overlay = (JToggleButton)DIHelper.getInstance().getLocalProp(Constants.APPEND);

		// need to see if there is a neater way to do this
		if(overlay.isSelected())
			QuestionPlaySheetStore.getInstance().getActiveSheet().overlayView();			
		else
			QuestionPlaySheetStore.getInstance().getActiveSheet().refineView();
	}
	
	private Vector<String> createTriples2Remove(String subjects, String parentObj, String predicate)
	{
		Vector <String> subVector = new Vector<String>();
		
		String deleteQuery = "DELETE  WHERE {" +
			"<child> " + predicate + " <parent>; " +
		"}";

		String deleteQuery2 = "DELETE  WHERE {" +
		"<child> " + predicate + "  " + parentObj + "; " +
	"}";
		
		StringTokenizer tokenz = new StringTokenizer(subjects, ";");
		
		while(tokenz.hasMoreTokens())
		{
			String deleter = tokenz.nextToken();
			
			String parent = deleter.substring(0,deleter.indexOf("@@"));
			String child = deleter.substring(deleter.indexOf("@@") + 2);
			
			String q = deleteQuery.replace("child", child);
			q = q.replace("parent", parent);
		
			subVector.addElement(q);
			System.out.println(" Query....  "+ q);
			q = deleteQuery2.replace("child", child);
			subVector.add(q);
		}
		return subVector;
	}	
	
	@Override
	public void setView(JComponent view) {

	}
}
