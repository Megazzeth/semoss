package prerna.algorithm.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JList;

import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.rdf.engine.api.IEngine;
import prerna.rdf.engine.impl.SesameJenaConstructStatement;
import prerna.rdf.engine.impl.SesameJenaConstructWrapper;
import prerna.ui.components.PropertySpecData;
import prerna.ui.components.UpdateProcessor;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;
import edu.uci.ics.jung.graph.DelegateForest;

public class DistanceDownstreamInserter {

	Hashtable masterHash = new Hashtable();
	Logger logger = Logger.getLogger(getClass());
	String RELATION_URI = null;
	String PROP_URI = null;
	IEngine engine;
	double depreciationRate;
	double appreciationRate;
	// references to main vertstore
	Hashtable<String, DBCMVertex> vertStore = new Hashtable();
	// references to the main edgeStore
	Hashtable<String, DBCMEdge> edgeStore = new Hashtable();
	PropertySpecData predData = new PropertySpecData();
	Hashtable networkValueHash = new Hashtable();
	Hashtable soaValueHash = new Hashtable();

	//This method will use the engine to get all data objects in the selected database
	//For each data object, it will create the forest (system network query) and pass it to DistanceDownstreamProcessor
	//It will get back the masterHash from DistanceDownstreamProcessor so it knows all of the distances for the systems
	//It will use all of these returned masterHashes to create insert query to put it in the database
	//Finally it will run the insert query.
	public void insertAllDataDownstream(){
		Vector<String> dataObjectsArray = getObjects("DataObject");
		//dataObjectsArray.add("Patient_Procedures");

		Hashtable distanceDownstreamHash = new Hashtable();
		
		//this will add distance downstream for all systems connected to creators
		for(String dataObjectString: dataObjectsArray){
			double maxCreditValue = 0.0;
			//Create the forest for this data object
			String unfilledQuery = (String) DIHelper.getInstance().getProperty(Constants.DISTANCE_DOWNSTREAM_QUERY);
			Hashtable<String, String> paramHash = new Hashtable<String, String>();
			paramHash.put("Data-Data", dataObjectString);
			String query = Utility.fillParam(unfilledQuery, paramHash);
			DelegateForest<DBCMVertex, DBCMEdge> dataForest = new DelegateForest<DBCMVertex, DBCMEdge>();
			try {
				dataForest = createForest(query);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DistanceDownstreamProcessor processor = new DistanceDownstreamProcessor();
			//now set everything in DistanceDownstreamProcessor and let that buddy run
			processor.setForest(dataForest);
			processor.setRootNodesAsSelected();
			processor.addSelectedNode(dataObjectString, 0);//need to make sure that creators first have the chance to go from data object directly
			processor.execute();
		
			//now add everything but the data object to fullSystemHash
			//format of fullSystemHash will be key:data object, object: sysHash with key: system and object: distance and system_network: network weight
			Hashtable sysHash = new Hashtable();
			Iterator keyIt = processor.masterHash.keySet().iterator();
			while (keyIt.hasNext()){
				
				DBCMVertex sysVertex = (DBCMVertex) keyIt.next();
				String system = (String) sysVertex.getProperty(Constants.VERTEX_NAME);
				
				//the processor masterHash will have the dataObject as a key.  Need to make sure I don't take that
				if (system.equals(dataObjectString)){
					//do nothing
				}
				else {
					Hashtable vertHash = (Hashtable) processor.masterHash.get(sysVertex);
					ArrayList<DBCMVertex> path = (ArrayList<DBCMVertex>) vertHash.get(processor.pathString);
					//if the path starts with the data object, need to subtract 1 from distance
					int distance = (Integer) vertHash.get(processor.distanceString);
					if((path.get(0)).getProperty(Constants.VERTEX_NAME).equals(dataObjectString))
						distance = distance - 1;
					sysHash.put(system, distance);
					
					//this will fill networkValueHash and soaValueHash with raw numbers
					//normalizeNetworkWeights will then update the values in networkValueHash by normalizing it
					maxCreditValue = calculateWeights(vertHash, system, dataObjectString, distance, processor.pathString, maxCreditValue);
				}
				
			}
			distanceDownstreamHash.put(dataObjectString, sysHash);
			normalizeNetworkWeights(maxCreditValue, dataObjectString);
		}
				
		//now all of the information should be contained within fullSystemHash.
		//Just need to prepare insert query and run insert
		
		String insertQuery = prepareInsert(distanceDownstreamHash, "DistanceDownstream", "integer");
		UpdateProcessor updatePro = new UpdateProcessor();
		updatePro.setQuery(insertQuery);
		updatePro.processQuery();
		
		String insertSOAweightQuery = prepareInsert(soaValueHash, "weight", "double");
		updatePro.setQuery(insertSOAweightQuery);
		updatePro.processQuery();

		String insertNetworkWeightQuery = prepareInsert(networkValueHash, "NetworkWeight", "double");
		updatePro.setQuery(insertNetworkWeightQuery);
		updatePro.processQuery();
	}
	
	public void normalizeNetworkWeights(Double maxCreditValue, String dataObjectString){
		Hashtable dataHash = (Hashtable) networkValueHash.get(dataObjectString);
		if(dataHash!=null){
			Iterator dataIt = dataHash.keySet().iterator();
			while(dataIt.hasNext()){
				String sysName = (String) dataIt.next();
				Double rawNetworkValue = (Double) dataHash.get(sysName);
				Double networkValue = rawNetworkValue/maxCreditValue;
				dataHash.put(sysName, networkValue);
			}
			networkValueHash.put(dataObjectString, dataHash);
		}
	}
	
	public void setAppAndDep(Double appreciation, Double depreciation){
		appreciationRate = appreciation;
		depreciationRate = depreciation;
	}
	
	//This uses a system's vert hash to calculate its SOA weight and network weight
	private double calculateWeights(Hashtable vertHash, String systemName, String dataName, int distance, String pathString, double maxCreditValue){
		double newMax = maxCreditValue;
		//first the SOA part.  just need to put the calculation on the distance
		double soaWeight = Math.pow(depreciationRate, distance);
		Hashtable dataSOAHash = new Hashtable();
		if(soaValueHash.containsKey(dataName)) dataSOAHash = (Hashtable) soaValueHash.get(dataName);
		dataSOAHash.put(systemName, soaWeight);
		soaValueHash.put(dataName, dataSOAHash);
		
		//if its not a root node, take the edge above its weight and add its network value everywhere
		ArrayList<DBCMVertex> pathArray = (ArrayList<DBCMVertex>) vertHash.get(pathString);
		if(distance>0){
			Hashtable dataNetHash = new Hashtable();
			if(networkValueHash.containsKey(dataName)) dataNetHash = (Hashtable) networkValueHash.get(dataName);
			int pathLength = pathArray.size();
			double difference = Math.pow(depreciationRate, distance-1)- Math.pow(depreciationRate, distance);
			double prevValue = 0.0;
			for(int i= 0; i<distance; i++){
				int thisNodeIdx = pathLength-1-i;
				DBCMVertex thisVert = pathArray.get(thisNodeIdx);
				DBCMVertex vertAbove = pathArray.get(thisNodeIdx-1);

				double addedValue;
				if(i == 0) addedValue = difference*appreciationRate;
				else addedValue= prevValue*(1-appreciationRate);
				double oldValue =0.0;
				if(dataNetHash.containsKey(vertAbove.getProperty(Constants.VERTEX_NAME))){
					oldValue = (Double) dataNetHash.get(vertAbove.getProperty(Constants.VERTEX_NAME));
				}
				double newValue = oldValue+addedValue;
				//if newValue > max, max becomes new
				if(newValue>newMax) newMax = newValue;
				dataNetHash.put(vertAbove.getProperty(Constants.VERTEX_NAME), newValue);
				networkValueHash.put(dataName, dataNetHash);
				prevValue = addedValue;
			}
		}
		return newMax;
	}
	//this will prepare the insert query.  hash must contain object level, then subject level, then value
	//prop Name is the name of the property to be inserted and type must be "integer" or "double"
	private String prepareInsert(Hashtable hash, String propName, String type){
		String predUri = "<http://health.mil/ontologies/dbcm/Relation/Contains/"+propName+">";
		
		//add start with type triple
		String insertQuery = "INSERT DATA { " +predUri + " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
				"<http://health.mil/ontologies/dbcm/Relation/Contains>. ";
		
		//add other type triple
		insertQuery = insertQuery + predUri +" <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
				"<http://www.w3.org/1999/02/22-rdf-syntax-ns#Property>. ";
		
		//add sub property triple -->>>>>>> should probably look into this.... very strange how other properties are set up
		insertQuery = insertQuery + predUri +" <http://www.w3.org/2000/01/rdf-schema#subPropertyOf> " +
				predUri + ". ";
		
		Iterator dataIt = hash.keySet().iterator();
		while(dataIt.hasNext()){
			String dataName = (String) dataIt.next();
			Hashtable sysHash = (Hashtable) hash.get(dataName);
			Iterator sysIt = sysHash.keySet().iterator();
			while(sysIt.hasNext()){
				String sysName = (String) sysIt.next();
				String relationUri = "<http://health.mil/ontologies/dbcm/Relation/Provide/"+sysName +
						Constants.RELATION_URI_CONCATENATOR+dataName+">";
				
				
				Object value = sysHash.get(sysName);
				
				String objUri = "\"" + value + "\"" + "^^<http://www.w3.org/2001/XMLSchema#"+type+">";
				
				insertQuery = insertQuery + relationUri + " " + predUri + " " + objUri + ". ";
				
			}
		}
		insertQuery = insertQuery + "}";
		
		return insertQuery;
	}
	
	
	public DelegateForest<DBCMVertex, DBCMEdge> createForest(String query) throws Exception {
		//run query
		SesameJenaConstructWrapper sjw = new SesameJenaConstructWrapper();
		sjw.setQuery(query);
		sjw.setEngine(engine);
		sjw.execute();
		
		
		//this is pretty much directly from GraphPlaySheet CreateForest().  I removed Jena Model and Control Data though
		logger.info("Creating Forest >>>>>");

		DelegateForest<DBCMVertex, DBCMEdge> forest = new DelegateForest<DBCMVertex, DBCMEdge>();
		Properties rdfMap = DIHelper.getInstance().getRdfMap();

		createBaseURIs();
		// iterate through the graph query result and set everything up
		// this is also the place where the vertex filter data needs to be created
		
		logger.debug(" Adding graph to forest " );
		while (sjw.hasNext()) {
			logger.debug("Iterating ...");
			SesameJenaConstructStatement st = sjw.next();

			String predicate = st.getPredicate();
			predData.addPredicate(predicate);
			// need to work on this logic
			logger.debug(st.getSubject() + "<>" + st.getPredicate() + "<>" + st.getObject());
			if (Utility.checkPatternInString(RELATION_URI, predicate)
					&& !rdfMap.contains(predicate)) // need to change this to starts with relation
			{
				if (Utility.checkPatternInString(PROP_URI, predicate)) {
					logger.debug("Add Property Routine");
				} else {
					logger.debug("Create edge routine");
					DBCMVertex vert1 = vertStore.get(st.getSubject());
					
					// there is a good possibility that the 
					logger.debug("Adding Edge " + st.getPredicate());
					if (vertStore.get(st.getSubject()) == null) {
						vert1 = new DBCMVertex(st.getSubject());
						vertStore.put(st.getSubject(), vert1);
					}
					DBCMVertex vert2 = vertStore.get(st.getObject()+"");
					if (vertStore.get(st.getObject()+"") == null) {
						vert2 = new DBCMVertex(st.getObject()+"");
						vertStore.put(st.getObject() +"", vert2);
					}
					try {
						DBCMEdge edge = edgeStore.get(st.getPredicate());
						if (edge == null) {
							edge = new DBCMEdge(vert1, vert2, st.getPredicate());
							edgeStore.put(st.getPredicate(), edge);
						}
						else
						{
							DBCMVertex inVert = edge.inVertex;
							DBCMVertex outVert = edge.outVertex;
							
							String inURI = inVert.getURI();
							String outURI = outVert.getURI();
							
							if( !(st.getSubject().equalsIgnoreCase(inURI) && (st.getObject()+"").equalsIgnoreCase(outURI)) &&  !(st.getSubject().equalsIgnoreCase(outURI) && (st.getObject()+"").equalsIgnoreCase(inURI)))
							{
								// this is a random edge that needs to be taken care of
								// so add this with a different name
								String predicateName = st.getPredicate()+"/" + vert1.getProperty(Constants.VERTEX_NAME) + "-" + vert2.getProperty(Constants.VERTEX_NAME);
								edge = new DBCMEdge(vert1, vert2, predicateName);
								edgeStore.put(predicateName, edge);
							}
							
						}
						
						// need to revisit this IF statement, ideally I will not need this if I take the lowest level
						// need to see why this shit goes on
						if (!vertStore.contains(st.getSubject() + "_"
								+ st.getObject())
								&& !vertStore.contains(st.getPredicate()))
						{
							forest.addEdge(edge,
									vertStore.get(st.getSubject()),
									vertStore.get(st.getObject()+""));
						}
					} catch (Exception ignored) {
						//logger.warn("Exception " + ignored);
						//ignored.printStackTrace();
					}
				}
			}
		}
		logger.info("Creating Forest Complete >>>>>> ");
		return forest;
	}
	
	private void createBaseURIs(){
		RELATION_URI = DIHelper.getInstance().getProperty(
				Constants.PREDICATE_URI);
		PROP_URI = DIHelper.getInstance()
				.getProperty(Constants.PROP_URI);
	}
	
	//this is for getting all of the objects of a certain type.  I am most likely getting DataObject so that I can populate my forest queries
	public Vector<String> getObjects(String type){
		Vector<String> dataObjectArray = new Vector<String>();
		
		//get the query for data objects
		String entityNS = DIHelper.getInstance().getProperty(type);
		String sparqlQuery = DIHelper.getInstance().getProperty(
				"TYPE" + "_" + Constants.QUERY);
		// System.out.println()
		Hashtable paramTable = new Hashtable();
		paramTable.put(Constants.ENTITY, entityNS);
		sparqlQuery = Utility.fillParam(sparqlQuery, paramTable);	
		
		
		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		// get the selected repository
		Object [] repos = (Object [])list.getSelectedValues();

		for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
		{
			engine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
			Vector<String> newArray = engine.getEntityOfType(sparqlQuery);
			dataObjectArray.addAll(newArray);
		}
		return dataObjectArray;
		
	}

}
