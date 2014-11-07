package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;


import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.api.exceptions.schema.UniqueConstraintViolationKernelException;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountManager;

/**
 * This class handles account management related database transactions.
 * 
 * @author tejasvamsingh
 *
 */

@Stateless
public class AccountManager implements IAccountManager {

	GraphDatabaseService GraphDBInstance;
	
	/**
	 * Constructor gets a database handle.
	 * 
	 */
	public AccountManager(){
		GraphDBInstance = DBManager.GetGraphDBInstance();
		
	}
	
	/**
	 * This method is responsible for creating an user account in the database.
	 */
	@Override
	public List<Map<String,String>> CreateAccount(Map<String,String[]> request) {
		
		// ********* LOGGING ********* 
		System.out.println("Reached CreateAccount in AccountManager");
		System.out.flush();
		// ********* LOGGING ********* 
		
		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("RequestType");
		modifiableRequestMap.remove("RequestID");
		
			
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
			parameters.put("creationParameters",queryParamterMap);
			
			//create cypher query to create node in the dataase.
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
			
			// Sucessful transaction.
		    
		    				    
		}
		
		return resultMapList;
		
	}

	/**
	 * This method is responsible for account update database transactions.
	 * 
	 */
	
	@Override
	public List<Map<String,String>> UpdateAccount(Map<String,String[]> request) {
		
		// ********* LOGGING ********* 
		System.out.println("Reached UpdateAccount in AccountManager");
		// ********* LOGGING *********
		//TODO
		
		return null;
		
	}

	/**
	 * This method deletes the specified account from the database.
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String,String>> DeleteAccount(Map<String,String[]> request) {
		
		// ********* LOGGING ********* 
		System.out.println("Reached DeleteAccount in AccountManager");
		System.out.flush();
		// ********* LOGGING ********* 
		
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("RequestType");
		modifiableRequestMap.remove("RequestID");
		
			
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
			parameters.put("Username",queryParamterMap.get("Username"));
			
			//create cypher query to delete node from the dataase.
			String query = "MATCH(n:User) WHERE n.Username={Username} DELETE n ";
		
			
			//execute the query
			result = executionEngine.execute(query,parameters);
			
			System.out.println("result : "+result);
			System.out.flush();
			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);
			
			// Sucessful transaction.
		    tx.success();
		    
		    //if we reached here then transaction was successful.
		    resultMapList.get(0).put("Status", "Success");
		}
		
		return resultMapList;
		
	}

	/**
	 * This method checks if a given user 
	 * account exists in the database object.
	 * 
	 */
	@Override
	public List<Map<String, String>> IsValidAccount(Map<String, String[]> request) {
		
		// ********* LOGGING ********* 
		System.out.println("Reached IsValidAccount in AccountManager");
		System.out.flush();
		// ********* LOGGING ********* 
		
		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("RequestType");
		modifiableRequestMap.remove("RequestID");
		
			
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
			parameters.put("Username",queryParamterMap.get("Username"));
			
			//create cypher query to create node in the dataase.
			String query = "MATCH(n:User) WHERE n.Username={Username} RETURN n";
		
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
