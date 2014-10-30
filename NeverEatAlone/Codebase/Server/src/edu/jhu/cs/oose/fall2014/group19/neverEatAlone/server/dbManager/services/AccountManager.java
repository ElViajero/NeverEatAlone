package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountManager;

public class AccountManager implements IAccountManager {

	GraphDatabaseService GraphDBInstance;
	
	public AccountManager(){
		GraphDBInstance = DBManager.GetGraphDBInstance();		
	}
	
	/**
	 * This method is responsible for creating an account in the database.
	 */
	@Override
	public List<Map<String,String>> CreateAccount(Map<String,String[]> request) {
		
		
		System.out.println("Reached CreateAccount in AccountManager");
		System.out.flush();
		
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
		
			//execute the query
			result = executionEngine.execute(query,parameters);
			
			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);
			
			// Sucessful transaction.
		    tx.success();
		    				    
		}
		
		return resultMapList;
		
	}

	@Override
	public List<Map<String,String>> UdateAccount(Map<String,String[]> request) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public List<Map<String,String>> DeleteAccount(Map<String,String[]> request) {
		// TODO Auto-generated method stub
		return null;		
	}

	/**
	 * This method checks if a given user account exists.
	 * 
	 */
	@Override
	public List<Map<String, String>> IsValidAccount(Map<String, String[]> request) {
		
		System.out.println("Reached IsValidAccount in AccountManager");
		System.out.flush();
		
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
