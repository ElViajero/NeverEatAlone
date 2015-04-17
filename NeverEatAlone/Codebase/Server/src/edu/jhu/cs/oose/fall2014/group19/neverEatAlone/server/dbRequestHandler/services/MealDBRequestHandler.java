package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IMealDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.logger.helper.LoggerHelper;

/**
 * This class handles notification management related database transactions.
 * 
 * @author tejasvamsingh
 *
 */

@Stateless
public class MealDBRequestHandler implements IMealDBRequestHandler {

	@Inject
	IDBQueryExecutionManager iDBQueryExecutionManagerInstance;

	/**
	 * This method is responsible for creating a meal notifcation in the
	 * database.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String, String>> CreateMealNotification(
			Map<String, String[]> request) {

		LoggerHelper.printrequestMap(request);

		// ******************** LOGGING ***************************
		System.out
				.println("Reached CreateMealNotification in NotificationDBManager");

		// get the recipients as List
		List<String> recipientList = Arrays
				.asList(request.get("recipientList"));

		// format the parameters for the query.
		Map<String, String> paramterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		String poster = paramterMap.get("poster");
		paramterMap.remove("recipientList");

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("creationParameters", paramterMap);

		// create cypher query to create node in the dataase.
		String query = "CREATE(n:Post{creationParameters}) RETURN n";

		// This is the data returned.
		List<Map<String, String>> resultMapList = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		// step 2 : add an edge from poster to node.

		queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("username", poster);
		queryParameterMap.put("postID", paramterMap.get("postID"));

		query = "MATCH (n:User),(a:Post) " + "WHERE n.username={username} AND "
				+ "a.postID={postID} " + "CREATE (n)-[:POSTER]->(a) "
				+ "SET n.currentPostID={postID}" + "RETURN n";

		iDBQueryExecutionManagerInstance.executeQuery(query, queryParameterMap);

		for (String recipient : recipientList) {

			// step 3 : add edges to all recipients.
			queryParameterMap = new HashMap<String, Object>();
			queryParameterMap.put("Recipient", recipient);
			queryParameterMap.put("postID", paramterMap.get("postID"));

			query = "MATCH (n:Post),(a:User) "
					+ "WHERE a.username={Recipient} AND "
					+ "n.postID={postID} " + "CREATE (n)-[:RECIPIENT]->(a) "
					+ "RETURN n";

			iDBQueryExecutionManagerInstance.executeQuery(query,
					queryParameterMap);
		}

		return resultMapList;

	}

	@Override
	public List<Map<String, String>> fetchNotifications(
			Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		queryParameterMap.put("username", parameterMap.get("username"));
		queryParameterMap.put("postStatus", "OPEN");

		String query = "MATCH (a:User) " + "WHERE a.username={username} "
				+ "OPTIONAL MATCH (n:Post)-[:RECIPIENT]->(a) "
				+ "WHERE NOT (a)-[:ATTENDING]->(n) "
				+ "AND n.postStatus={postStatus}" + "RETURN n";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	@Override
	public List<Map<String, String>> acceptMealNotification(
			Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		queryParameterMap.put("username", parameterMap.get("poster"));
		queryParameterMap.put("postID", parameterMap.get("postID"));

		String query = "MATCH (a:User),(n:Post) "
				+ "WHERE a.username={username} " + "AND n.postID={postID} "
				+ "CREATE UNIQUE (a)-[:ATTENDING]->(n) " + "RETURN n";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		Map<String, String> statusMap = null;
		if (resultMap.get(0).get("Status").equals("Success")) {
			System.out.println("Successful query.");
			statusMap = resultMap.get(0);
			resultMap.clear();
		}
		resultMap.add(0, statusMap);
		resultMap.add(parameterMap);
		return resultMap;

	}

	@Override
	public List<Map<String, String>> fetchPosts(Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		System.out.println("USERNAME : " + parameterMap.get("username"));

		queryParameterMap.put("poster", parameterMap.get("username"));

		String query = "MATCH (n:Post) " + "WHERE n.poster={poster} "
				+ "RETURN n";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		return resultMap;
	}

	@Override
	public List<Map<String, String>> fetchAcceptedNotifications(
			Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		System.out.println("USERNAME : " + parameterMap.get("username"));

		queryParameterMap.put("username", parameterMap.get("username"));

		String query = "MATCH (n:Post),(a:User) "
				+ "WHERE (a)-[:ATTENDING]->(n) " + "AND a.username={username} "
				+ "RETURN n";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		return resultMap;

	}

	@Override
	public List<Map<String, String>> getAttendingContacts(
			Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		System.out.println("USERNAME : " + parameterMap.get("poster"));
		System.out.println("POSTID : " + parameterMap.get("postID"));

		queryParameterMap.put("username", parameterMap.get("poster"));
		queryParameterMap.put("postID", parameterMap.get("postID"));

		String query = "MATCH (n:Post),(a:User),(b:User) "
				+ "WHERE ((n)-[:RECIPIENT]->(a) " + "OR (a)-[:POSTER]->(n)) "
				+ "AND a.username={username} " + "AND n.postID={postID} "
				+ "AND (b)-[:ATTENDING]->(n) " + "AND (a)-[:KNOWS]->(b) "
				+ "AND (b)-[:KNOWS]->(a) " + "AND NOT b.username={username}"
				+ "RETURN b";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		System.out.println("RESULT MAP IS :" + resultMap);
		return resultMap;

	}

	@Override
	public List<Map<String, String>> undoAccept(Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		System.out.println("USERNAME : " + parameterMap.get("poster"));

		queryParameterMap.put("username", parameterMap.get("poster"));
		queryParameterMap.put("postID", parameterMap.get("postID"));

		String query = "MATCH (a:User)-[r:ATTENDING]->(n:Post) "
				+ "WHERE a.username={username} " + "AND n.postID={postID} "
				+ "DELETE r " + "RETURN a";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		return resultMap;

	}

	@Override
	public List<Map<String, String>> rejectMealNotification(
			Map<String, String[]> request) {

		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();

		System.out.println("USERNAME : " + parameterMap.get("poster"));
		System.out.println("POSTID : " + parameterMap.get("postID"));

		queryParameterMap.put("username", parameterMap.get("poster"));
		queryParameterMap.put("postID", parameterMap.get("postID"));

		String query = "MATCH (n:Post)-[r:RECIPIENT]->(a:User) "
				+ "WHERE a.username={username} " + "AND n.postID={postID} "
				+ "OPTIONAL MATCH (a)-[q]->(n)" + "DELETE r,q " + "RETURN a";

		List<Map<String, String>> resultMap = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		LoggerHelper.printresultMap(resultMap);
		return resultMap;

	}

	@Override
	public List<Map<String, String>> updateNotification(
			Map<String, String[]> request) {

		System.out.println("inside updateNotification in MealDBRequestHandler");

		Map<String, String> paramterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		String poster = paramterMap.get("poster");
		paramterMap.remove("recipientList");

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("postID", paramterMap.get("postID"));
		queryParameterMap.put("creationParameters", paramterMap);

		String query = "MATCH (n:Post) " + "WHERE n.postID={postID} "
				+ "OPTIONAL MATCH (n)-[r]-(a:User) " + "DELETE n,r";

		iDBQueryExecutionManagerInstance.executeQuery(query, queryParameterMap);

		return CreateMealNotification(request);

	}

	@Override
	public List<Map<String, String>> getRecipients(Map<String, String[]> request) {

		Map<String, String> paramterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		String poster = paramterMap.get("poster");
		paramterMap.remove("recipientList");

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("postID", paramterMap.get("postID"));

		String query = "MATCH (n:Post), (n)-[:RECIPIENT]->(a:User) "
				+ "WHERE n.postID={postID} "
				+ "RETURN a.username AS recipientUsername";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	@Override
	public List<Map<String, String>> changeStatus(Map<String, String[]> request) {

		Map<String, String> paramterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("postID", paramterMap.get("postID"));
		queryParameterMap.put("postStatus", paramterMap.get("postStatus"));

		String query = "MATCH (n:Post) " + "WHERE n.postID={postID} "
				+ "SET n.postStatus={postStatus} " + "RETURN n";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	@Override
	public List<Map<String, String>> deleteOldPosts(
			Map<String, String[]> request) {

		System.out.println("Inside deleteOldPosts in MealDBRequestHandler");

		List<Map<String, String>> postMapList = fetchPosts(request);
		String currentDateAndTimeString = request
				.get("currentDateAndTimeString")[0];

		List<String> postsToBeDeletedList = new ArrayList<String>();
		List<Map<String, String>> result = null;
		postMapList.remove(0);

		for (Map<String, String> postMap : postMapList) {

			String month = postMap.get("endmonth");
			String day = postMap.get("endday");
			String year = postMap.get("endyear");
			String hour = postMap.get("endhour");
			String minute = postMap.get("endminute");

			String postDateAndTimeString = month + "/" + day + "/" + year
					+ "   " + hour + ":" + minute;

			System.out.println("post date and time string is : "
					+ postDateAndTimeString);
			System.out.println("current date and time string is : "
					+ currentDateAndTimeString);

			int compVal = postDateAndTimeString
					.compareTo(currentDateAndTimeString);

			System.out.println("comp val : " + compVal);
			if (compVal < 0) {
				System.out.println("LESS POST IN HERE");
				postsToBeDeletedList.add(postMap.get("postID"));
			}
		}

		for (String postToBeDeleted : postsToBeDeletedList) {

			System.out.println("DELETING POST WITH ID : " + postToBeDeleted);

			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("postID", postToBeDeleted);

			String query = "MATCH (n:Post) " + "WHERE n.postID={postID} "
					+ "OPTIONAL MATCH (n)-[r]-() " + "DELETE r,n";

			result = iDBQueryExecutionManagerInstance.executeQuery(query,
					queryParameters);
		}

		if (result == null) {
			result = new ArrayList<Map<String, String>>();
			Map<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("Status", "Success");
			result.add(statusMap);
		}
		return result;

	}
}
