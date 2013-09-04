package prerna.ui.components;

import javax.swing.JList;

import org.apache.log4j.Logger;

import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.BigDataEngine;
import prerna.rdf.engine.impl.SesameJenaUpdateWrapper;
import prerna.util.Constants;
import prerna.util.DIHelper;

public class UpdateProcessor {

	Logger logger = Logger.getLogger(getClass());
	String query;
	IEngine engine;
	
	public UpdateProcessor(){
		
	}
	
	public void processQuery(){
		//if the engine has been set, it will run the query only on that engine
		//if the engine has not been set, it will run the query on all selected engines

		if(engine == null){
			//get the selected repositories
			JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			Object [] repos = (Object [])list.getSelectedValues();
			
			//for each selected repository, run the query
			for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
			{
				//get specific engine
				IEngine selectedEngine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
				logger.info("Selecting repository " + repos[repoIndex]);
				
				if(selectedEngine instanceof BigDataEngine) {
					BigDataEngine selectedEngineBigData = (BigDataEngine) selectedEngine;
					selectedEngineBigData.infer();
				}
				
				//create the update wrapper, set the variables, and let it run
				SesameJenaUpdateWrapper wrapper = new SesameJenaUpdateWrapper();
				wrapper.setEngine(selectedEngine);
				wrapper.setQuery(query);
				wrapper.execute();
				selectedEngine.commit();
				
			}
		}
		else {
			if(engine instanceof BigDataEngine) {
				BigDataEngine selectedEngineBigData = (BigDataEngine) engine;
				selectedEngineBigData.infer();
			}
			
			//create the update wrapper, set the variables, and let it run
			SesameJenaUpdateWrapper wrapper = new SesameJenaUpdateWrapper();
			wrapper.setEngine(engine);
			wrapper.setQuery(query);
			wrapper.execute();
			engine.commit();
		}
		
	}
	
	public void setQuery(String q){
		query = q;
	}
	
	public void setEngine(IEngine e){
		engine = e;
	}
	
}
