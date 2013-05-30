package prerna.ui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaSelectStatement;
import prerna.rdf.engine.impl.SesameJenaSelectWrapper;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.main.listener.impl.NeighborMenuListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

public class TFInstanceRelationPopup extends JMenu implements MouseListener{
	
	// need a way to cache this for later
	// sets the visualization viewer
	IPlaySheet ps = null;
	// sets the picked node list
	DBCMVertex [] pickedVertex = null;
	Logger logger = Logger.getLogger(getClass());

	String mainQuery = null;
	String altQuery = null;
	String neighborQuery = null;
	
	boolean instance = false;
	boolean populated = false;
	// core class for neighbor hoods etc.
	public TFInstanceRelationPopup(String name, IPlaySheet ps, DBCMVertex [] pickedVertex)
	{
		super(name);
		// need to get this to read from popup menu
		this.ps = ps;
		this.pickedVertex = pickedVertex;
		this.mainQuery = Constants.NEIGHBORHOOD_OBJECT_QUERY;
		this.altQuery = Constants.NEIGHBORHOOD_OBJECT_ALT2_QUERY;
		this.neighborQuery = Constants.TRAVERSE_FREELY_QUERY;
		addMouseListener(this);
	}
	
	public void addRelations(String prefix)
	{

		// execute the query
		// add all the relationships
		// the relationship needs to have the subject - selected vertex
		// need to add the relationship to the relationship URI
		// and the predicate selected
		// the listener should then trigger the graph play sheet possibly
		// and for each relationship add the listener
		String typeQuery =  DIHelper.getInstance().getProperty(this.neighborQuery + prefix);
		Hashtable<String, String> hash = new Hashtable<String, String>();
		String ignoreURI = DIHelper.getInstance().getProperty(Constants.IGNORE_URI);
		int count=0;
		for(int pi = 0;pi < pickedVertex.length;pi++)
		{
			DBCMVertex thisVert = pickedVertex[pi];
			String uri = thisVert.getURI();

			String query2 = DIHelper.getInstance().getProperty(this.mainQuery+ prefix);

			String typeName = Utility.getQualifiedClassName(uri);

			String type = Utility.getClassName(uri);
			
			Pattern pattern = Pattern.compile("\\s");
			Matcher matcher = pattern.matcher(uri);
			boolean found = matcher.find();

			hash.put("SUBJECT_TYPE", typeName);

			if(found)
			{
				String label = "\"" + Utility.getInstanceName(uri) + "\"";				
				hash.put(Constants.URI, label);			
				query2 = DIHelper.getInstance().getProperty(this.altQuery+ prefix);
			}
			else
			{
				uri = "<" + uri + ">";
				hash.put(Constants.URI, uri);			
			}
			String filledQuery = Utility.fillParam(query2, hash);
			
			// get the filter values
			String fileName = "";
			for(int vertIndex = 0;vertIndex < pickedVertex.length;vertIndex++)
			{
				if(vertIndex == 0)
					fileName = "\"" + Utility.getInstanceName(pickedVertex[vertIndex].getURI()) + "\"";
				else
					fileName = fileName + "," + "\"" + Utility.getInstanceName(pickedVertex[vertIndex].getURI()) + "\"";
			}
			hash.put("FILTER_VALUES", fileName);
			// get the repository and execute the query
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
			sjw.setQuery(filledQuery);
			sjw.executeQuery();
			
			logger.debug("Executed Query");
			
			String [] vars = sjw.getVariables();
			while(sjw.hasNext())
			{
				SesameJenaSelectStatement stmt = sjw.next();
				// only one variable
				
				String predName = stmt.getRawVar(vars[0]) + "";
								
				String predClassName = Utility.getQualifiedClassName(predName);

				
				hash.put("SUBJECT_TYPE", typeName);
				hash.put("OBJECT_TYPE", predClassName);
				//logger.debug("Predicate is " + predName + "<<>> "+ predClassName);
				
				String nFillQuery = Utility.fillParam(typeQuery, hash);
				//logger.debug("Filler Query is " + nFillQuery);
				// compose the query based on this class name
				// should we get type or not ?
				// that is the question
				logger.debug("Trying predicate class name for " + predClassName + " instance is " + instance);
				if(predClassName.length() > 0 && !Utility.checkPatternInString(ignoreURI, predClassName) && !hash.containsKey(predClassName) && (!instance && !predClassName.contains("\"")))
				{
					//add the to: and from: labels
					if(count == 0){
						if(this.getItemCount()>0)
							addSeparator();
						if(prefix.equals(""))
							addLabel("To:");
						else
							addLabel("From:");
					}
					count++;
					
					logger.debug("Adding Relation " + predClassName);
					String instance = Utility.getInstanceName(predClassName);
					NeighborMenuItem nItem = new NeighborMenuItem(instance, nFillQuery, engine);
					nItem.addActionListener(NeighborMenuListener.getInstance());
					add(nItem);
					hash.put(predClassName, predClassName);
				}
				// for each of these relationship add a relationitem
			
			}			
		}
		repaint();
		populated = true;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//addRelations("");
		//addRelations("_2");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!populated)
		{
			addRelations("");
			addRelations("_2");
		}
	//	addRelations();
		
	}
	
	public void addLabel(String label){
		add(new JLabel(label));
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
