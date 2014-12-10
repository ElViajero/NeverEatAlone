package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.INotificationDBManager;

/**
 * This class handles notification management related database transactions.
 * 
 * @author tejasvamsingh
 *
 */


@Stateless
public class NotificationDBManager implements INotificationDBManager {



	GraphDatabaseService GraphDBInstance;

	/**
	 * Constructor gets a database handle.
	 * @author tejasvamsingh
	 * 
	 */
	public NotificationDBManager(){
		GraphDBInstance = DBManager.GetGraphDBInstance();

	}



	/**
	 * This method is responsible for creating a meal notifcation in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String, String>> CreateMealNotification(
			Map<String, String[]> request) {


		// ******************** LOGGING ***************************
		System.out.println("Reached CreateMealNotification in NotificationDBManager");



		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);



		//get the recipients as List
		List<String> recipientList = Arrays.asList(request.get("recipientList"));


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);

		String poster = queryParamterMap.get("poster");
		queryParamterMap.remove("requestType");
		queryParamterMap.remove("requestID");
		queryParamterMap.remove("recipientList");



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

			//create cypher query to create node in the dataase.
			String query = "CREATE(n:Post{creationParameters}) RETURN n";

			// Check for uniqueness constraint violation.
			try{
				//execute the query
				result = executionEngine.execute(query,parameters);				
			}catch(Exception e){
				System.out.println("Constraint violation in query 1. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);


			//step 2 : add an edge from poster to node.

			parameters = new HashMap<String,Object>();
			parameters.put("username",poster);
			parameters.put("postID",queryParamterMap.get("postID"));
			
			System.out.println("username: "+parameters.get("username")+" postID: "+parameters.get("postID"));

			query = "MATCH (n:User),(a:Post) "
					+ "WHERE n.username={username} AND "
					+ "a.postID={postID} "
					+ "CREATE (n)-[:POSTER]->(a) "
					+ "RETURN n";

			try{
				//execute the query
				result = executionEngine.execute(query,parameters);				
			}catch(Exception e){
				System.out.println("Constraint violation in query 2. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}


			for(String recipient : recipientList){
				//step 3 : add edges to all recipients.
				parameters = new HashMap<String,Object>();
				parameters.put("Recipient",	recipient);
				parameters.put("postID",queryParamterMap.get("postID"));

				query = "MATCH (n:Post),(a:User) "
						+ "WHERE a.username={Recipient} AND "
						+ "n.postID={postID} "
						+ "CREATE (n)-[:RECIPIENT]->(a) "
						+ "RETURN n";



				try{
					//execute the query
					result = executionEngine.execute(query,parameters);				
				}catch(Exception e){
					System.out.println("Constraint violation in query 3. :: ");
					System.out.println(e.getMessage());
					result = null;
					tx.failure();
				}
			}


			// Sucessful transaction.

			tx.success();


		}

		return resultMapList;

	}


}
