package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;


/**
 * This class handles contact management related database transactions.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */
@Stateless
public class ContactDBManager implements IContactDBManager {


	GraphDatabaseService GraphDBInstance;


	/**
	 * Constructor gets a database handle.
	 * @author tejasvamsingh
	 */
	public ContactDBManager(){
		GraphDBInstance = DBManager.GetGraphDBInstance();
	}


	/**
	 * This method is responsible for adding a contact in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String,String>> AddContact(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached AddContact in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// ************************ LOGGING ************************

		System.out.println("username :"+queryParamterMap.get("username"));
		System.out.println("contact :"+queryParamterMap.get("contactusername"));


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
			parameters.put("contactusername",queryParamterMap.get("contactusername"));

			//create cypher query to add a relation in the dataase.
			String query = "MATCH (a:User),(b:User)"
					+ " WHERE "
					+ "a.username = {username} AND "
					+ "b.username = {contactusername}"
					+ "CREATE UNIQUE (a)-[n:KNOWS]->(b)"
					+ "RETURN n";

			// Check for uniqueness constraint violation.
			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
			}catch(Exception e){
				System.out.println("Constraint violation in add contact. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}


			// Fetch the contact via query

			query = "MATCH (a:User)-[r]->(n:User)"
					+ " WHERE "
					+ "a.username = {username} AND "
					+ "n.username = {contactusername}"
					+ "RETURN n ";

			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in add contact. :: ");
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
	 * method to fetch all contacts of a user
	 * @author Xiaozhou Zhou
	 */
	@Override
	public List<Map<String, String>> GetAllContacts(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached GetAllContacts in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);
		System.out.println("query parameters: "+queryParamterMap);

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


			// Fetch the contact via query

			String query = "MATCH (a:User)-[:KNOWS]->(n:User)"
					+ " WHERE "
					+ "a.username = {username}"
					+ "RETURN n.username AS username, n.Available AS Available";

			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in fetching contacts. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}
//			System.out.println("query result: \n"+result.dumpToString());
			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);
			// Sucessful transaction.
		}

		System.out.println("Contacts Fetched: "+resultMapList);
		return resultMapList;

	}






}
