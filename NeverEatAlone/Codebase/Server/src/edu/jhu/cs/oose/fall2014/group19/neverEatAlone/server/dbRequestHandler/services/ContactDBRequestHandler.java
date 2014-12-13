package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IContactDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;


/**
 * This class handles contact management related database transactions.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */
@Stateless
public class ContactDBRequestHandler implements IContactDBRequestHandler {

	@Inject IDBQueryExecutionManager iDBQueryExecutionManagerInstance;

	/**
	 * This method is responsible for adding a contact in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String,String>> add(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached AddContact in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		// ************************ LOGGING ************************

		System.out.println("username :"+paramterMap.get("username"));
		System.out.println("contact :"+paramterMap.get("contactusername"));



		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",paramterMap.get("username"));
		queryParameterMap.put("contactusername",paramterMap.get("contactusername"));
		queryParameterMap.put("alias", ""); 

		//create cypher query to add a relation in the database.
		// contact is always added as two ways
		String query = "MATCH (a:User),(b:User)"
				+ " WHERE "
				+ "a.username = {username} AND "
				+ "b.username = {contactusername}"
				+ "CREATE UNIQUE (a)-[r1:KNOWS]->(b), (a)<-[r2:KNOWS]-(b) "
				+ "SET r1.alias = {alias}, r2.alias = {alias} "
				+ "RETURN r1";

		iDBQueryExecutionManagerInstance
		.executeQuery(query, queryParameterMap);

		// Fetch the contact via query

		query = "MATCH (a:User)-[r]->(n:User)"
				+ " WHERE "
				+ "a.username = {username} AND "
				+ "n.username = {contactusername}"
				+ "RETURN n ";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

	}

	/**
	 * method to fetch all contacts of a user
	 * @author Xiaozhou Zhou
	 */
	@Override
	public List<Map<String, String>> getAll(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached GetAllContacts in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		System.out.println("query parameters: "+parameterMap);

		// ************************ LOGGING ************************
		System.out.println("username :"+parameterMap.get("username"));

		// set up parameters to execute and store the result of query

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",parameterMap.get("username"));


		// Fetch the contact via query

		String query = "MATCH (a:User)-[r:KNOWS]->(n:User)"
				+ " WHERE "
				+ "a.username = {username}"
				+ "RETURN n.username AS username, r.alias AS alias";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);
	}

	/**
	 * method to delete a contact
	 * @author Xiaozhou Zhou
	 */
	@Override
	public List<Map<String, String>> delete(
			Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached DeleteContact in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//format the parameters for the query.		
		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);


		// ************************ LOGGING ************************

		System.out.println("username :"+parameterMap.get("username"));
		System.out.println("contact :"+parameterMap.get("contactusername"));


		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",parameterMap.get("username"));
		queryParameterMap.put("contactusername",parameterMap.get("contactusername"));

		//create cypher query to add a relation in the database.
		// contact is always added as two ways
		String query = "MATCH (n:User)-[r1:KNOWS]->(a:User), (n:User)<-[r2:KNOWS]-(a:User) "
				+ " WHERE n.username = {username} AND a.username = {contactusername} "
				+ "DELETE r1,r2 ";
		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

	}


	/**
	 * method to update a contact's information
	 * @author Xiaozhou Zhou
	 */
	@Override
	public List<Map<String, String>> update(Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached UpdateContact in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 
		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);
		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",paramterMap.get("username"));
		queryParameterMap.put("contactusername",paramterMap.get("contactusername"));
		queryParameterMap.put("updateParameters",paramterMap);

		// update the property of the relationship in database
		String query = "MATCH (n:User)-[r:KNOWS]->(a:User) "
				+ " WHERE n.username = {username} AND a.username = {contactusername} "
				+ "SET r += {updateParameters} "
				+ "RETURN r ";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);
	}





}
