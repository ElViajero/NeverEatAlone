package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IVisibilityDBRequestHandler;

/**
 * This class set the visibility of a contact
 * @author Xiaozhou Zhou
 *
 */
public class VisibilityDBRequestHandler implements IVisibilityDBRequestHandler {

	GraphDatabaseService GraphDBInstance;

	/**
	 * Constructor gets a database handler
	 * 
	 */
	public VisibilityDBRequestHandler(){
		GraphDBInstance = DBManager.GetGraphDBInstance();
	}

	/**
	 * Add VisibleTo edges to the contacts that are chosen to be visible to 
	 */
	@Override
	public List<Map<String, String>> setVisibility(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached SetVisibility in VisibilityDBManager");		

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);

		//get the contacts as List and remove it from the map
		List<String> contactList = Arrays.asList(request.get("contactList"));
		modifiableRequestMap.remove("contactList"); 

		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);

		queryParamterMap.remove("requestType");
		queryParamterMap.remove("requestID");

		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result = null;
		List<Map<String,String>> resultMapList;

		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("creationParameters",queryParamterMap);

			// add visibility edges for each contact
			for(String contact : contactList){
				parameters = new HashMap<String,Object>();
				parameters.put("contact", contact);

				String query = "MATCH (n:User),(a:User) "
						+ "WHERE n.username={username} AND "
						+ "a.username={contact} "
						+ "CREATE (n)-[:VISIBLETO]->(a) "
						+ "RETURN n";

				try{
					//execute the query
					result = executionEngine.execute(query,parameters);				
				}catch(Exception e){
					System.out.println("Constraint violation in setting visibility. :: ");
					System.out.println(e.getMessage());
					result = null;
					tx.failure();
				}
			}


			// Sucessful transaction.
			resultMapList = DBManager.GetResultMapList(result);
			tx.success();			

		}

		return resultMapList;

	}

	@Override
	public List<Map<String, String>> unsetVisibility(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached UnsetVisibility in VisibilityDBManager");		

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);

		//get the contacts as List and remove it from the map
		List<String> contactList = Arrays.asList(request.get("contactList"));
		modifiableRequestMap.remove("contactList"); 

		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);

		queryParamterMap.remove("requestType");
		queryParamterMap.remove("requestID");

		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result = null;
		List<Map<String,String>> resultMapList;

		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("creationParameters",queryParamterMap);

			// delete visibility edges for each contact
			for(String contact : contactList){
				parameters = new HashMap<String,Object>();
				parameters.put("contact", contact);

				String query = "MATCH (n)-[r:VISIBLETO]->(a) "
						+ "WHERE n.username={username} AND "
						+ "a.username={contact} "
						+ "DELETE r  "
						+ "RETURN n";

				try{
					//execute the query
					result = executionEngine.execute(query,parameters);				
				}catch(Exception e){
					System.out.println("Constraint violation in unsetting visibility. :: ");
					System.out.println(e.getMessage());
					result = null;
					tx.failure();
				}
			}


			// Sucessful transaction.
			resultMapList = DBManager.GetResultMapList(result);
			tx.success();			

		}

		return resultMapList;

	}

}
