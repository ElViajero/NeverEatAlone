package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.ILocationDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts.ISecurityManager;

/**
 * 
 * This class handles location persistence operations.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class LocationDBRequestHandler implements ILocationDBRequestHandler {

	@Inject
	IDBQueryExecutionManager iDBQueryExecutionManagerInstance;

	@Inject
	ISecurityManager iSecurtyManagerInstance;

	/**
	 * 
	 * This method updates user location.
	 * 
	 */
	@Override
	public List<Map<String, String>> updateUserLocation(
			Map<String, String[]> request) {

		// first remove all locations that the user was previously tied to.
		Map<String, Object> queryParameters = new HashMap<String, Object>();
		queryParameters.put("username", request.get("username")[0]);

		String query = "OPTIONAL MATCH (a:User)-[r:LOCATED_IN]->(:Location) "
				+ "WHERE a.username={username} " + "DELETE r";

		iDBQueryExecutionManagerInstance.executeQuery(query, queryParameters);

		// next for each new location, create a new node if the node for that
		// location
		// is not already present.
		// Finally add a link between the user and location.
		List<Map<String, String>> resultMapList = null;
		for (String locationName : request.get("locationName")) {

			System.out.println("locationName inside UpdateUserLocation is : "
					+ locationName);

			Map<String, String> locationParameters = new HashMap<String, String>();
			String encryptedLocationName = iSecurtyManagerInstance
					.getEncryptedString(locationName);

			locationParameters.put("locationName", encryptedLocationName);
			queryParameters.put("locationParameters", locationParameters);
			queryParameters.put("locationName", encryptedLocationName);

			query = "CREATE (n:Location{locationParameters}) " + "RETURN n";
			iDBQueryExecutionManagerInstance.executeQuery(query,
					queryParameters);

			query = "MATCH (n:User),(a:Location) "
					+ "WHERE n.username={username} "
					+ "AND a.locationName={locationName} "
					+ "CREATE UNIQUE (n)-[:LOCATED_IN]->(a) " + "RETURN a";
			resultMapList = iDBQueryExecutionManagerInstance.executeQuery(
					query, queryParameters);

		}

		query = "MATCH (n:User)-[:LOCATED_IN]->(a:Location) "
				+ "WHERE n.username={username} " + "RETURN a";

		List<Map<String, String>> res = iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameters);

		System.out.println("RESULTS ARE DD : " + res);

		return resultMapList;
	}

	@Override
	public List<Map<String, String>> deleteUserLocation(
			Map<String, String[]> request) {

		System.out
				.println("Reached deleteUserLocation in LocationDBRequestHandler");

		Map<String, Object> queryParameters = new HashMap<String, Object>();
		queryParameters.put("username", request.get("username")[0]);

		String query = "MATCH (a:User) "
				+ "OPTIONAL MATCH (a:User)-[r:LOCATED_IN]->(:Location) "
				+ "WHERE a.username={username} " + "DELETE r";

		return iDBQueryExecutionManagerInstance.executeQuery(query,
				queryParameters);

	}
}
