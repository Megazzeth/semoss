package prerna.ui.components;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IPlaySheet;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

// abstract base class for performing all of the work around
// getting the specific queries and painting it etc. 

public class NeighborRelationMenuItem extends JMenuItem{

	String predicateURI = null;
	String name = null;
	
	Logger logger = Logger.getLogger(getClass());

	public NeighborRelationMenuItem(String name, String predicateURI)
	{
		super(name);
		this.name = name;
		this.predicateURI = predicateURI;
	}
	
	public void paintNeighborhood()
	{
		// compose the query and paint the neighborhood
		IPlaySheet ps = QuestionPlaySheetStore.getInstance().getActiveSheet();
		// need to find a way to add the relationship here
		String predURI = DIHelper.getInstance().getProperty(Constants.PREDICATE_URI);
		predURI += ";" + name;
		DIHelper.getInstance().putProperty(Constants.PREDICATE_URI, predURI);		
		ps.refineView();
	}
	
	
}
