package prerna.ui.components;

import javax.swing.JList;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaBooleanWrapper;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class BooleanProcessor {

	Logger logger = Logger.getLogger(getClass());
	String query;
	IEngine engine;
	
	public BooleanProcessor(){
		
	}
	
	//if an engine has been set, it will run the query on that engine
	//if an engine has not been set, it will run it on all selected engines
	
	public boolean processQuery(){
		boolean ret = false;

		if(engine==null){
			//get the selected repositories
			JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			Object [] repos = (Object [])list.getSelectedValues();
			
			//for each selected repository, run the query
			for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
			{
				//get specific engine
				IEngine selectedEngine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
				logger.info("Selecting repository " + repos[repoIndex]);
				
				//create the update wrapper, set the variables, and let it run
				SesameJenaBooleanWrapper wrapper = new SesameJenaBooleanWrapper();
				wrapper.setEngine(selectedEngine);
				wrapper.setQuery(query);
				ret = wrapper.execute();
				
			}
		}
		else {
			//create the update wrapper, set the variables, and let it run
			SesameJenaBooleanWrapper wrapper = new SesameJenaBooleanWrapper();
			wrapper.setEngine(engine);
			wrapper.setQuery(query);
			ret = wrapper.execute();
		}
		
		return ret;
	}
	
	public void setEngine(IEngine e){
		engine = e;
	}
	
	public void setQuery(String q){
		query = q;
	}
	
}
