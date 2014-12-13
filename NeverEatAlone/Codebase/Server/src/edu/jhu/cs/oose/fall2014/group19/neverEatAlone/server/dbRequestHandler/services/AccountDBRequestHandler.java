package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IAccountDBRequestHandler;

/**
 * This class handles account management related database transactions.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */

@Stateless
public class AccountDBRequestHandler implements IAccountDBRequestHandler {

	GraphDatabaseService GraphDBInstance;

	/**
	 * Constructor gets a database handle.
	 * @author tejasvamsingh
	 * 
	 */
	public AccountDBRequestHandler(){
		GraphDBInstance = DBManager.GetGraphDBInstance();

	}

	/**
	 * This method is responsible for creating an user account in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String,String>> create(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached CreateAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");

		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;


		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("creationParameters",queryParamterMap);

			//create cypher query to create node in the database.
			String query = "CREATE(n:User{creationParameters}) RETURN n";

			// Check for uniqueness constraint violation.
			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in create account. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);

			// Successful transaction.


		}

		return resultMapList;

	}

	/**
	 * This method is responsible for account update database transactions.
	 * 
	 */

	@Override
	public List<Map<String,String>> update(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached UpdateAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");

		// user name can never be changed 
		String username = modifiableRequestMap.get("username")[0]; 
		modifiableRequestMap.remove("username"); 

		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;


		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("username", username); 
			parameters.put("updateParameters",queryParamterMap);

			//create cypher query to update the database.
			String query =""
					+ "MATCH (n:User) "
					+ "WHERE n.username={username} "
					+ "SET n += {updateParameters} "
					+ "RETURN n ";

			// Check for uniqueness constraint violation.
			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in updating account. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);

			// Successful transaction.


		}

		return resultMapList;
	}

	/**
	 * Method to get full account information
	 */
	@Override
	public List<Map<String, String>> getInfo(Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached GetInfo in AccountDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// ************************ LOGGING ************************
		System.out.println("username :"+queryParamterMap.get("username"));

		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;

		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("username",queryParamterMap.get("username"));

			// Fetch all the properties of the user node 

			String query = "MATCH (n:User)"
					+ " WHERE "
					+ "n.username = {username}"
					+ "RETURN n";

			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in fetching account information :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}
			//			System.out.println("query result: \n"+result.dumpToString());
			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);
			// Sucessful transaction.
		}

		System.out.println("Account Info Fetched: "+resultMapList);
		return resultMapList;		

	}


	/**
	 * This method deletes the specified account from the database.
	 * //TODO need to delete the posts posted by this user too.  
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String,String>> delete(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached DeleteAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// set up parameters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
		resultMapList.add(new HashMap<String,String>()); 


		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("username",queryParamterMap.get("username"));

			System.out.println("deleting user "+parameters.get("username"));

			//create cypher query to delete node from the database.
			// first delete the relationships
			String query =""
					+ "MATCH (n:User) "
					+ "WHERE n.username={username} "
					+ "OPTIONAL MATCH (n)-[r]-() "
					+ "DELETE n,r ";

			// Check for constraint violation.
			try{
				//execute the query
				executionEngine.execute(query,parameters);
				tx.success();
				resultMapList.get(0).put("Status", "Success");
			}catch(Exception e){
				System.out.println("Constraint violation in deleting account. :: ");
				System.out.println(e.getMessage());
				tx.failure();
				resultMapList.get(0).put("Status", "Failed");
			}
		}
		System.out.println("deleting result: "+resultMapList);

		return resultMapList;

	}

	/**
	 * This method checks if a given user 
	 * account exists in the database object.
	 * 
	 */
	@Override
	public List<Map<String, String>> isValid(Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached IsValidAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// set up paramters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;


		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("username",queryParamterMap.get("username"));

			//create cypher query to create node in the dataase.
			String query = "MATCH(n:User) WHERE n.username={username} RETURN n";

			//execute the query
			result = executionEngine.execute(query,parameters);

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);

			// Sucessful transaction.
			tx.success();

		}

		return resultMapList;

	}



}
