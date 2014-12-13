package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * This class is a helper class for database operations.
 * It provides a database handle as well as formatting methods
 * for parameters(results) sent to (received from) the database.
 *  
 * @author tejasvamsingh
 *
 */
@Stateless
public class DBManager {

	private static GraphDatabaseService GraphDBInstance;

	/**
	 * This method returns a graph database
	 * instance that is shared by all classes
	 * that wish to access the database.
	 * It also registers the instance for clean
	 * shutdown and defines schema constraints for the first time 
	 * instantiation. 
	 * 
	 * @return
	 */

	public static GraphDatabaseService GetGraphDBInstance(){
		if(GraphDBInstance==null){
			//initialize the DB instance in embedded mode.
			GraphDBInstance = new GraphDatabaseFactory().newEmbeddedDatabase( "./DBData" );
			// Register the instance for clean shutdown.
			RegisterShutdownHook();
			// define schema constraints if not already defined.
			try{
				SetDBSchema();
			}catch(Exception e){
				System.out.println("No problem. Schema is already defined.");
			}			
		}
		return GraphDBInstance;
	}


	/**
	 * 
	 * This method sets constraints on the
	 * different types of possible nodes in the graph.
	 * 
	 * @param graphDBInstance2
	 */
	private static void SetDBSchema() throws Exception {


		try(Transaction tx = GraphDBInstance.beginTx()){

			//schema constraints go here

			GraphDBInstance.schema().
			constraintFor(DynamicLabel.label("User")).
			assertPropertyIsUnique("username").create();			
			GraphDBInstance.schema().
			constraintFor(DynamicLabel.label("User")).
			assertPropertyIsUnique("email").create();		



			tx.success();
		}
	}

	/**
	 * 
	 * This method returns a list of Maps for 
	 * each entry that is part of a Cypher query result. 
	 * 
	 * @param result
	 * @return
	 */		
	public static List<Map<String,String>> GetResultMapList(ExecutionResult result){


		// ************** LOGGING ************************
		System.out.println("Inside DBManager: getting result maplist");
		// ************** LOGGING ************************


		// initialize the map to return.
		List<Map<String,String>> resultMapList = 
				new ArrayList<Map<String,String>>();


		//map to determine status of result ( success or failure).
		Map<String, String> successMap =
				new HashMap<String,String>();
		// default status of query is failed.
		successMap.put("Status","Failed");

		resultMapList.add(0, successMap);

		// Check if result is null. 
		// This happens in the case of violated
		// constraints.		
		if(result == null ){
			System.out.println("Result is NULL");
			return resultMapList;
		}

		// Iterate over the returned rows
		// The keys of the map are the column names and the values are the entries
		ResourceIterator<Map<String,Object>> rows = result.iterator();

		//index of element in the maplist 
		int index=0;

		while(rows.hasNext()){

			index ++; // 0 is for Status
			Map<String,Object> currentRow = rows.next();
			System.out.println("reached row "+index+" : "+currentRow);
			//check if this index exists in the list.
			if(index>=resultMapList.size())
				resultMapList.add(new HashMap<String,String>());

			for(String col : currentRow.keySet()){
				Object entry = currentRow.get(col); 

				//another null check
				if(entry==null)
					continue;

				if (entry instanceof org.neo4j.graphdb.PropertyContainer){

					// ********* LOGGING *********
					System.out.println("IT IS A NODE OR RELATIONSHIP");
					// ********* LOGGING *********

					PropertyContainer currentEntry = (PropertyContainer) entry; 
					// iterate over all the properties for the current node object.
					for(String property : currentEntry.getPropertyKeys()){				
						//add the properties. Note the value is returned as string.
						resultMapList.get(index).
						put(property, currentEntry.getProperty(property).toString());				

					}
				}

				else{
					System.out.println("DATA : " + entry.toString());
					resultMapList.get(index).put(col,entry.toString());
				}
			}
		}

		// check if we have some result entries.
		// If so, update the query result status.
		if(resultMapList.size()>1)			
			resultMapList.get(0).put("Status", "Success");

		// return the query result.
		return resultMapList;

	} 


	/**
	 * This method is used for formatting query parameters.
	 * Note, this is a generic method that assumes every String[] has 
	 * one element.
	 * 
	 * In case of multiple query parameters, 
	 * the calling method must take care of this detail.
	 * If not, the information may be lost.
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String,String> GetQueryParameterMap(Map<String,String[]> request){		 

		Map<String,String> queryParameterMap = new HashMap<String,String>();

		for (Map.Entry<String, String[]> entry : request.entrySet()) {

			//obtain the key and value for the current entry.
			String key = entry.getKey();
			String[] value = entry.getValue();

			// Put the first element of String[] into our new map.
			// Read method documentation for more details.		    		    
			queryParameterMap.put(key, value[0]);		   		   
		}		
		return queryParameterMap;
	}















	/**
	 * This method ensures that the database shuts down gracefully
	 * when the JVM exits.
	 * @param graphDb
	 */
	private static void RegisterShutdownHook()
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				GraphDBInstance.shutdown();
			}
		} );
	}





}
