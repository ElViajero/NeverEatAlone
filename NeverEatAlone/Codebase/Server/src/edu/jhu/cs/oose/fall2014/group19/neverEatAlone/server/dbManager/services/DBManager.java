package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.api.exceptions.schema.AlreadyConstrainedException;

import scala.collection.Iterator;

/**
 * This class is a helper class for database operations.
 * It provides a database handle as well as formatting methods
 * for parameters(results) sent to (received from) the database.
 *  
 * @author tejasvamsingh
 *
 */

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
			}catch(AlreadyConstrainedException e){
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
	private static void SetDBSchema() throws AlreadyConstrainedException {

		
		try(Transaction tx = GraphDBInstance.beginTx()){
		
			//schema constraints go here
			
			GraphDBInstance.schema().
			constraintFor(DynamicLabel.label("User")).
			assertPropertyIsUnique("Username").create();			
			
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
		if(result ==null )
			return resultMapList;
		
		
		// retrieve the result as an iterator on nodes.
		Iterator<Node> column  = result.columnAs("n");
		for(Iterator<Node> node : IteratorUtil.asIterable(column)){
		
			// initialize map for this entry.
			Map<String,String> resultMap = new HashMap<String,String>();
			
			//check if there are no entries.
			if(node.isEmpty())
				break;
			
			//for each row in the column..
			int index=0;
			while(node.hasNext()){
				
				Node currentNode = node.next();
				index++; // 0 is for Status
				
				//check if this index exists in the list.
				if(index>=resultMapList.size())
					resultMapList.add(new HashMap<String,String>());
				
				// iterate over all the properties for the current node object.
				for(String property : currentNode.getPropertyKeys()){				
				//add the properties. Note the value is returned as string.
				resultMapList.get(index).
					put(property, currentNode.getProperty(property).toString());				
				
				}
				
									
				// add map to list.
				resultMapList.add(resultMap);
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
	