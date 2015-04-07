package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.ILoginDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts.ISecurityManager;

/**
 * This class handles database interactions for login related requests.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class LoginDBRequestHandler implements ILoginDBRequestHandler {

	@Inject
	IDBQueryExecutionManager iDBManagerInstance;

	@Inject
	ISecurityManager iSecurityManagerInstance;

	/**
	 * This method checks credentials for successful login.
	 * 
	 * @param request
	 * @return
	 */

	@Override
	public List<Map<String, String>> checkCredentials(
			Map<String, String[]> request) {

		if (iSecurityManagerInstance == null)
			System.out.println("security is null");

		// ********* LOGGING *********
		System.out.println("Reached CheckCredentials in LoginSOmething");
		System.out.flush();
		// ********* LOGGING *********

		// format the parameters for the query.
		Map<String, String> paramterMap = DBRequestHandlerHelper
				.GetQueryParameterMap(request);

		String pass = iSecurityManagerInstance.getEncryptedString("xyz");

		System.out.println("pass is : " + pass);
		System.out.flush();

		// get the encrypted password.
		paramterMap.put("password", iSecurityManagerInstance
				.getEncryptedString(paramterMap.get("password")));

		Map<String, Object> QueryparameterMap = new HashMap<String, Object>();
		QueryparameterMap.put("username", paramterMap.get("username"));
		QueryparameterMap.put("password", paramterMap.get("password"));

		String query = "MATCH(n:User) WHERE n.username={username} "
				+ "AND n.password={password} RETURN n";

		return iDBManagerInstance.executeQuery(query, QueryparameterMap);

	}

}
