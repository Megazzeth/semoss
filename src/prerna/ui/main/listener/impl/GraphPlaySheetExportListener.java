package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.JDesktopPane;

import org.apache.log4j.Logger;
import org.openrdf.query.TupleQueryResult;

import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.InMemorySesameEngine;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.GridPlaySheet;
import prerna.ui.components.ParamPanel;
import prerna.ui.components.api.IPlaySheet;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

public class GraphPlaySheetExportListener  extends AbstractPopupMenuListener{

	public static GraphPlaySheetExportListener listener = null;
	Logger logger = Logger.getLogger(getClass());
	GraphPlaySheet playSheet;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static GraphPlaySheetExportListener getInstance()
	{
		if(listener == null)
			listener = new GraphPlaySheetExportListener();
		return listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Export button has been pressed");
		GraphPlaySheet playSheet=  (GraphPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();
		
		GridPlaySheet NewplaySheet = null;
		try {
			NewplaySheet = (GridPlaySheet)Class.forName(Constants.GRID_VIEW).getConstructor(null).newInstance(null);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			logger.fatal(ex);
		}
		playSheet = (GraphPlaySheet) QuestionPlaySheetStore.getInstance().getActiveSheet();
		String query = playSheet.getQuery();
		String selectQuery = convertConstructToSelect(query);
		String questionID = playSheet.getQuestionID();
		String question = questionID + QuestionPlaySheetStore.getInstance().getCount();
		String title = "EXPORT: " + playSheet.getTitle();
		IEngine engine = playSheet.getRDFEngine();
		
		IEngine jenaEngine = new InMemorySesameEngine();
		((InMemorySesameEngine)jenaEngine).setRepositoryConnection(playSheet.rc);

		
		String sparql2 = "SELECT ?s ?p ?o WHERE {?s ?p ?o}";
		String sparql = "SELECT ?activity1 ?need ?data WHERE {{?activity1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/Activity>;} {?need <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> <http://health.mil/ontologies/dbcm/Relation/Needs> ;} {?data <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://health.mil/ontologies/dbcm/Concept/DataObject> ;} {?activity1 ?need ?data.}}";
		
		TupleQueryResult rs = (TupleQueryResult) jenaEngine.execSelectQuery(sparql2);
		//NewplaySheet.setResultSet(rs);
		
		NewplaySheet.setTitle(title);
		//NewplaySheet.setQuery(selectQuery);
		NewplaySheet.setParamPanel((ParamPanel)null);
		//NewplaySheet.setRDFEngine((IEngine)engine);
		NewplaySheet.setQuestionID(question);
		JDesktopPane pane = (JDesktopPane)DIHelper.getInstance().getLocalProp(Constants.DESKTOP_PANE);
		NewplaySheet.setJDesktopPane(pane);
	
		// put it into the store
		QuestionPlaySheetStore.getInstance().put(question, playSheet);
		
		// thread
		Thread playThread = new Thread(NewplaySheet);
		playThread.run();
			
		playSheet.jTab.add("Graph Export", NewplaySheet);
		playSheet.jTab.setSelectedComponent(NewplaySheet);
	}
		

	public String convertConstructToSelect(String ConstructQuery){
		System.out.println("Begining to convert query");
		String result = null;
		String newConstructQuery = null;
		
		//need to separate {{ so that tokenizer gets every piece of the construct query
		if(ConstructQuery.contains("{{")) newConstructQuery = ConstructQuery.replaceAll(Pattern.quote("{{"), "{ {");
		else newConstructQuery = ConstructQuery;
		StringTokenizer QueryBracketTokens = new StringTokenizer(newConstructQuery, "{");
		//Everything before first { becomes SELECT
		String firstToken = QueryBracketTokens.nextToken();
		String newFirstToken = null;
		if (firstToken.toUpperCase().contains("CONSTRUCT")){
			newFirstToken = firstToken.toUpperCase().replace("CONSTRUCT", "SELECT DISTINCT ");
		}
		else logger.info("Converting query that is not CONSTRUCT....");
		
		//the second token coming from between the first two brackets must have all period and semicolons removed
		String secondToken = QueryBracketTokens.nextToken();
		//String secondTokensansSemi = "";
		//String secondTokensansPer = "";
		String newsecondToken = null;
		if(secondToken.contains(";") && secondToken.contains(".")){
			String secondTokensansSemi = secondToken.replaceAll(";", " ");
			String secondTokensansPer = secondTokensansSemi.replaceAll(".", " ");
			newsecondToken = secondTokensansPer.replace("}", " ");
		}
		else if(secondToken.contains(".")&&!secondToken.contains(";")){
			String secondTokensansPer = secondToken.replaceAll("\\.", " ");
			newsecondToken = secondTokensansPer.replace("}", " ");
		}
		else if(secondToken.contains(";")&&!secondToken.contains(".")){
			String secondTokensansSemi = secondToken.replaceAll(";", " ");
			newsecondToken = secondTokensansSemi.replace("}", " ");
		}
		else if(!secondToken.contains(";")&&!secondToken.contains(".")){
			String secondTokensansSemi = secondToken.replaceAll(";", " ");
			newsecondToken = secondTokensansSemi.replace("}", " ");
		} 
		
		
		// Rest of the tokens go unchanged.  Must iterate through and replace {
		String restOfQuery = "";
		while (QueryBracketTokens.hasMoreTokens()){
			String token = QueryBracketTokens.nextToken();
			restOfQuery = restOfQuery+"{"+token;
		}
		result = newFirstToken + newsecondToken +restOfQuery;
		return result;
	}

	public void setPlaysheet(IPlaySheet ps) {
		playSheet = (GraphPlaySheet) ps;
		
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}
