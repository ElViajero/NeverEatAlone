package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IAccountDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts.ISecurityManager;

/**
 * This class handles account management related database transactions.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */

@Stateless
public class AccountDBRequestHandler implements IAccountDBRequestHandler {

	@Inject
	IDBQueryExecutionManager iDBQueryExecutionManagerInstance;

	@Inject
	ISecurityManager iSecurtyManagerInstance;

	/**
	 * This method is responsible for creating an user account in the database.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String, String>> create(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("Reached CreateAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING *********

		// format the parameters for the query.
		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		// encrypt password.
		encryptPassword(parameterMap);

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("creationParameters", parameterMap);

		// create cypher query to create node in the database.
		String query = "CREATE(n:User{creationParameters}) RETURN n";

		List<Map<String, String>> resultMapList = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);

		// add the reason for failure.
		if (resultMapList.get(0).get("Status").equals("Failed")) {
			String reason = resultMapList.get(0).get("Reason");
			if (reason.contains("email"))
				reason = "Email is tied to an existing account.";
			else if (reason.contains("username"))
				reason = "Username is tied to an existing account.";
			else
				reason = "Failed";
			resultMapList.get(0).put("Reason", reason);

		}

		return resultMapList;

	}

	/**
	 * @author tejasvamsingh This method is responsible for account update
	 *         database transactions.
	 * 
	 */

	@Override
	public List<Map<String, String>> update(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("Reached UpdateAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING *********

		// format the parameters for the query.
		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		encryptPassword(parameterMap);

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("username", parameterMap.get("username"));
		queryParameterMap.put("updateParameters", parameterMap);

		// create cypher query to update the database.
		String query = "" + "MATCH (n:User) " + "WHERE n.username={username} "
				+ "SET n += {updateParameters} " + "RETURN n ";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

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

		// format the parameters for the query.
		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		// encrypt password
		encryptPassword(parameterMap);

		// ************************ LOGGING ************************
		System.out.println("username :" + parameterMap.get("username"));

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("username", parameterMap.get("username"));

		// Fetch all the properties of the user node

		String query = "MATCH (n:User)" + " WHERE " + "n.username = {username}"
				+ "RETURN n";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	/**
	 * This method deletes the specified account from the database.
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String, String>> delete(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("Reached DeleteAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING *********

		// format the parameters for the query.
		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		encryptPassword(parameterMap);

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("username", parameterMap.get("username"));

		System.out
				.println("deleting user " + queryParameterMap.get("username"));

		// create cypher query to delete node from the database.
		// first delete all the posts of the user

		String query = "MATCH (a:Post) " + "WHERE a.poster={username} "
				+ "OPTIONAL MATCH (a)-[r]-() " + "DELETE a,r ";

		iDBQueryExecutionManagerInstance.executeQuery(query, queryParameterMap);

		// next delete user along with relationships.
		query = "" + "MATCH (n:User) " + "WHERE n.username={username} "
				+ "OPTIONAL MATCH (n)-[r]-() " + "DELETE n,r "
				+ "RETURN {username} AS username";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	/**
	 * This method checks if a given user account exists in the database object.
	 * 
	 */
	@Override
	public List<Map<String, String>> isValid(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("Reached IsValidAccount in AccountDBManager");
		System.out.flush();
		// ********* LOGGING *********

		// format the parameters for the query.
		Map<String, String> parameterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		// encrypt password
		encryptPassword(parameterMap);

		// create a params map.
		Map<String, Object> queryParameterMap = new HashMap<String, Object>();
		queryParameterMap.put("username", parameterMap.get("username"));

		// create cypher query to create node in the dataase.
		String query = "MATCH(n:User) WHERE n.username={username} RETURN n";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameterMap);

	}

	private void encryptPassword(Map<String, String> parameterMap) {
		parameterMap.put("password", iSecurtyManagerInstance
				.getEncryptedString(parameterMap.get("password")));
	}

}
