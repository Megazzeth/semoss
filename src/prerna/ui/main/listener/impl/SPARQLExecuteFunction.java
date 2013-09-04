package prerna.ui.main.listener.impl;

import javax.swing.JList;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaSelectWrapper;
import prerna.rdf.engine.impl.SesameJenaUpdateWrapper;
import prerna.util.Constants;
import prerna.util.DIHelper;

import com.teamdev.jxbrowser.BrowserFunction;

public class SPARQLExecuteFunction implements BrowserFunction {

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public Object invoke(Object... arg0) {

		System.out.println("Arguments are " + arg0);
		
		//get the query from the args
		String query = ((String) arg0[0]).trim(); 
		
		//get the engine from what is selected

		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		Object [] repos = (Object [])list.getSelectedValues();
		//for each selected repository, run the query
		for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
		{
			//get specific engine
			IEngine selectedEngine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
			logger.info("Selecting repository " + repos[repoIndex]);
			
			if(query.startsWith("INSERT") || query.startsWith("DELETE")){
				System.out.println("running update");
				processUpdate(query, selectedEngine);
			}

			else if(query.startsWith("SELECT") ){
				System.out.println("running select");
				processSelect(query, selectedEngine);
			}
			
			else { 
				System.err.println("UNKNOWN QUERY TYPE SENT TO JAVA FOR PROCESSING");
			}
		}
		
		return "SPARQLExecute Function query: " + query;
	}
	
	private void processUpdate(String query, IEngine selectedEngine){
		//create the update wrapper, set the variables, and let it run
		SesameJenaUpdateWrapper wrapper = new SesameJenaUpdateWrapper();
		wrapper.setEngine(selectedEngine);
		wrapper.setQuery(query);
		wrapper.execute();
	}
	private void processSelect(String query, IEngine selectedEngine){
		//create the update wrapper, set the variables, and let it run
		SesameJenaSelectWrapper wrapper = new SesameJenaSelectWrapper();
		wrapper.setEngine(selectedEngine);
		wrapper.setQuery(query);
		wrapper.executeQuery();
	}
}
