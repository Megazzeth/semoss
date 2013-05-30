package prerna.ui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JMenu;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaSelectStatement;
import prerna.rdf.engine.impl.SesameJenaSelectWrapper;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.main.listener.impl.NeighborRelationMenuListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class RelationPredictPopup extends JMenu implements MouseListener{
	
	// given 2 queries it will visualize the relationships possible
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());

	// both of them bind
	String mainQuery = null;
	
	// subject bind but not object, object as label
	String mainQuery2 = null;
	
	//both subject and object as label
	String mainQuery3 = null;
	String neighborQuery = null;
	String altQuery = null;
	String altMainQuery = null;
	String altQuery2 = null;
	
	boolean instance = false;
	// core class for neighbor hoods etc.
	public RelationPredictPopup(String name, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(name);
		// need to get this to read from popup menu
		this.ps = ps;
		this.pickedVertex = pickedVertex;
		this.mainQuery = Constants.NEIGHBORHOOD_PREDICATE_FINDER_QUERY;
		this.mainQuery2 = Constants.NEIGHBORHOOD_PREDICATE_ALT2_FINDER_QUERY;
		this.mainQuery3 = Constants.NEIGHBORHOOD_PREDICATE_ALT3_FINDER_QUERY;
		addMouseListener(this);
	}
	
	public void addRelations()
	{

		// execute the query
		// add all the relationships
		// the relationship needs to have the subject - selected vertex
		// need to add the relationship to the relationship URI
		// and the predicate selected
		// the listener should then trigger the graph play sheet possibly
		// and for each relationship add the listener
		Hashtable<String, String> hash = new Hashtable<String, String>();
		String ignoreURI = DIHelper.getInstance().getProperty(Constants.IGNORE_URI);
		// there should exactly be 2 vertices
		// we try to find the predicate based on both subject and object
		DBCMVertex vert1 = pickedVertex[0];
		String uri = vert1.getURI();
		DBCMVertex vert2 = pickedVertex[1];
		String uri2 = vert2.getURI();
		
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(uri);
		boolean found1 = matcher.find();
		matcher = pattern.matcher(uri2);
		boolean found2 = matcher.find();
		
		String subject = uri;
		String object = uri2;
		String query = DIHelper.getInstance().getProperty(mainQuery);
		
		if(found1 && uri.contains("/"))
			subject = "\"" + Utility.getInstanceName(uri) + "\"";	
		else if(uri.contains("/"))
			subject = "<" + uri + ">";
		if(found2 && uri2.contains("/"))
			object = "\"" + Utility.getInstanceName(uri2) + "\"";	
		else if(uri2.contains("/"))
			object = "<" + uri2 + ">";
		
		hash.put(Constants.SUBJECT, subject);
		hash.put(Constants.OBJECT, object);
		
		if(found1 && uri.contains("/"))
		{
			query = DIHelper.getInstance().getProperty(mainQuery2);
			if(found2 && uri2.contains("/"))
				query = DIHelper.getInstance().getProperty(mainQuery3);
		}
		
		String query1 = Utility.fillParam(query, hash);

		if(found2 && uri.contains("/"))
		{
			query = DIHelper.getInstance().getProperty(mainQuery2);
			if(found1 && uri2.contains("/"))
				query = DIHelper.getInstance().getProperty(mainQuery3);
		}
			

		hash.put(Constants.SUBJECT, object);
		hash.put(Constants.OBJECT, subject);
		
		String query2 = Utility.fillParam(query, hash);
		
		
		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		// get the selected repository
		Object [] repos = (Object [])list.getSelectedValues();
		
		// I am only going to get one repository
		// hopefully they have selected one :)
		String repo = repos[0] +"";
		
		IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repo);
		logger.debug("Found the engine for repository   " + repo);

		// run the query
		SesameJenaSelectWrapper sjw = new SesameJenaSelectWrapper();
		sjw.setEngine(engine);
		sjw.setEngineType(engine.getEngineType());
		sjw.setQuery(query1);
		sjw.executeQuery();
		
		logger.debug("Executed Query");
		logger.info("Executing query " + query1);
		
		String [] vars = sjw.getVariables();
		while(sjw.hasNext())
		{
			SesameJenaSelectStatement stmt = sjw.next();
			// only one variable
			
			String predName = stmt.getRawVar(vars[0]) + "";
			
			if(predName.length() > 0 && !Utility.checkPatternInString(ignoreURI, predName))
			{
				NeighborRelationMenuItem nItem = new NeighborRelationMenuItem(predName, predName);
				nItem.addActionListener(NeighborRelationMenuListener.getInstance());
				add(nItem);
			}
		}			
		
		// do it the other way now
		sjw.setQuery(query2);
		sjw.executeQuery();
		logger.info("Executing query " + query2);
		
		logger.debug("Executed Query");
		while(sjw.hasNext())
		{
			SesameJenaSelectStatement stmt = sjw.next();
			// only one variable
			
			String predName = stmt.getRawVar(vars[0]) + "";
			
			if(predName.length() > 0 && !Utility.checkPatternInString(ignoreURI, predName))
			{
				NeighborRelationMenuItem nItem = new NeighborRelationMenuItem(predName, predName);
				nItem.addActionListener(NeighborRelationMenuListener.getInstance());
				add(nItem);
			}
		}			
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Entered and Clicked");
		addRelations();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Entered and Clicked");
	//	addRelations();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
