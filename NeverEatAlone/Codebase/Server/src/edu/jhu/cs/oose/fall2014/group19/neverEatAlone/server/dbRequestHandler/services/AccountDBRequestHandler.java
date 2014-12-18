package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IAccountDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;

/**
 * This class handles account management related database transactions.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */

@Stateless
public class AccountDBRequestHandler implements IAccountDBRequestHandler {

	@Inject IDBQueryExecutionManager iDBQueryExecutionManagerInstance;

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


		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("creationParameters",parameterMap);

		//create cypher query to create node in the database.
		String query = "CREATE(n:User{creationParameters}) RETURN n";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);


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

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username", parameterMap.get("username")); 
		queryParameterMap.put("updateParameters",parameterMap);

		//create cypher query to update the database.
		String query =""
				+ "MATCH (n:User) "
				+ "WHERE n.username={username} "
				+ "SET n += {updateParameters} "
				+ "RETURN n ";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

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

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);


		// ************************ LOGGING ************************
		System.out.println("username :"+parameterMap.get("username"));



		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",parameterMap.get("username"));

		// Fetch all the properties of the user node 

		String query = "MATCH (n:User)"
				+ " WHERE "
				+ "n.username = {username}"
				+ "RETURN n";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

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

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);


		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",parameterMap.get("username"));

		System.out.println("deleting user "+queryParameterMap.get("username"));

		//create cypher query to delete node from the database.
		// first delete all the posts of the user

		String query = "MATCH (a:Post) "
				+ "WHERE a.poster={username} "
				+ "OPTIONAL MATCH (a)-[r]-() "
				+ "DELETE a,r ";

		iDBQueryExecutionManagerInstance
		.executeQuery(query, queryParameterMap);

		query =""
				+ "MATCH (n:User) "
				+ "WHERE n.username={username} "
				+ "OPTIONAL MATCH (n)-[r]-() "
				+ "DELETE n,r "
				+ "RETURN {username} AS username";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

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

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);


		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",parameterMap.get("username"));

		//create cypher query to create node in the dataase.
		String query = "MATCH(n:User) WHERE n.username={username} RETURN n";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

	}







}
