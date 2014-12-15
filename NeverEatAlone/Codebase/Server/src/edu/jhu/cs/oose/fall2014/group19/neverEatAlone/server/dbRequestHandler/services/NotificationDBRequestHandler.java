package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.INotificationDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;

/**
 * This class handles notification management related database transactions.
 * 
 * @author tejasvamsingh
 *
 */


@Stateless
public class NotificationDBRequestHandler implements INotificationDBRequestHandler {

	@Inject IDBQueryExecutionManager iDBQueryExecutionManagerInstance; 


	/**
	 * This method is responsible for creating a meal notifcation in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String, String>> CreateMealNotification(
			Map<String, String[]> request) {


		// ******************** LOGGING ***************************
		System.out.println("Reached CreateMealNotification in NotificationDBManager");


		//get the recipients as List
		List<String> recipientList = Arrays.asList(request.get("recipientList"));

		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		String poster = paramterMap.get("poster");
		paramterMap.remove("recipientList");


		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("creationParameters",paramterMap);

		//create cypher query to create node in the dataase.
		String query = "CREATE(n:Post{creationParameters}) RETURN n";

		// This is the data returned.
		List<Map<String, String>> resultMapList = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);


		//step 2 : add an edge from poster to node.

		queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username",poster);
		queryParameterMap.put("postID",paramterMap.get("postID"));

		query = "MATCH (n:User),(a:Post) "
				+ "WHERE n.username={username} AND "
				+ "a.postID={postID} "
				+ "CREATE (n)-[:POSTER]->(a) "
				+ "SET n.currentPostID={postID}"
				+ "RETURN n";

		iDBQueryExecutionManagerInstance
		.executeQuery(query, queryParameterMap);


		for(String recipient : recipientList){

			//step 3 : add edges to all recipients.
			queryParameterMap = new HashMap<String,Object>();
			queryParameterMap.put("Recipient",	recipient);
			queryParameterMap.put("postID",paramterMap.get("postID"));

			query = "MATCH (n:Post),(a:User) "
					+ "WHERE a.username={Recipient} AND "
					+ "n.postID={postID} "
					+ "CREATE (n)-[:RECIPIENT]->(a) "
					+ "RETURN n";

			iDBQueryExecutionManagerInstance
			.executeQuery(query, queryParameterMap);
		}



		return resultMapList;

	}


	@Override
	public List<Map<String, String>> fetchNotifications(
			Map<String, String[]> request) {


		Map<String, String> parameterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		Map<String,Object> queryParameterMap = 
				new HashMap<String,Object>();

		queryParameterMap.put("username", parameterMap.get("username"));

		String query = "MATCH (a:User)"
				+ "WHERE a.username={username}"
				+ "OPTIONAL MATCH (n:Post)-[:RECIPIENT]->(a)"
				+ "RETURN n";

		return iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);


	}

}
