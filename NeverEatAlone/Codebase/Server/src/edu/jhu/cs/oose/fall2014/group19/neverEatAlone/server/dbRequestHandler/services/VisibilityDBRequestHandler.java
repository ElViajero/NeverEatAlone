package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IVisibilityDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers.DBRequestHandlerHelper;

/**
 * This class set the visibility of a contact
 * @author Xiaozhou Zhou
 *
 */
public class VisibilityDBRequestHandler implements IVisibilityDBRequestHandler {

	@Inject IDBQueryExecutionManager iDBQueryExecutionManagerInstance;  

	/**
	 * Add VisibleTo edges to the contacts that are chosen to be visible to 
	 */
	@Override
	public List<Map<String, String>> add(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached addVisibility in VisibilityDBManager");		


		//get the contacts as List and remove it from the map
		List<String> contactList = Arrays.asList(request.get("contactList"));
		String username = request.get("username")[0]; 

		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);
		paramterMap.remove("contactList");
		paramterMap.remove("username");

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("creationParameters",paramterMap);
		queryParameterMap.put("username", username);

		List<Map<String,String>> resultMapList=null;

		// add visibility edges for each contact
		for(String contact : contactList){

			queryParameterMap.put("contact", contact);

			String query = "MATCH (n:User),(a:User) "
					+ "WHERE n.username={username} AND "
					+ "a.username={contact} "
					+ "CREATE UNIQUE (n)-[r:VISIBLE{creationParameters}]->(a) "
					+ "RETURN r";

			resultMapList=iDBQueryExecutionManagerInstance
					.executeQuery(query, queryParameterMap);
		}
		return resultMapList;

	}

	@Override
	public List<Map<String, String>> delete(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached deleteVisibility in VisibilityDBManager");		


		//get the contacts as List and remove it from the map
		List<String> contactList = Arrays.asList(request.get("contactList"));


		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		paramterMap.remove("contactList");


		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username", paramterMap.get("username"));
		List<Map<String,String>> resultMapList=null;

		// delete visibility edges for each contact
		for(String contact : contactList){
			queryParameterMap.put("contact", contact);

			String query = "MATCH (n)-[r:VISIBLE]->(a) "
					+ "WHERE n.username={username} AND "
					+ "a.username={contact} "
					+ "DELETE r  "
					+ "RETURN n";	
			resultMapList=iDBQueryExecutionManagerInstance
					.executeQuery(query, queryParameterMap);

		}
		return resultMapList;
	}


	@Override
	public List<Map<String, String>> update(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached update in VisibilityDBManager");		


		//get the contacts as List and remove it from the map
		String username = request.get("username")[0]; 

		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);
		paramterMap.remove("username");

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("updateParameters",paramterMap);
		queryParameterMap.put("username", username);

		List<Map<String,String>> resultMapList=null;

		// update visibility status for each contact
		String query = "MATCH (n)-[r:VISIBLE]->() "
				+ "WHERE n.username={username} "
				+ "SET r += {updateParameters} "
				+ "RETURN r";	

		resultMapList=iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);


		return resultMapList;
	}

	
	@Override
	public List<Map<String, String>> getAll(Map<String, String[]> request) {

		// ******************** LOGGING ***************************
		System.out.println("Reached getAll in VisibilityDBManager");		

		//format the parameters for the query.		
		Map<String, String> paramterMap = 
				DBRequestHandlerHelper.GetQueryParameterMap(request);

		//create a params map.
		Map<String,Object> queryParameterMap = new HashMap<String,Object>();
		queryParameterMap.put("username", paramterMap.get("username"));

		List<Map<String,String>> resultMapList=null;

		// get all visible contacts of the user
		String query = "MATCH (n)<-[r:VISIBLE]-(a) "
				+ "WHERE n.username={username} "
				+ "RETURN a.username AS contactusername";	

		resultMapList=iDBQueryExecutionManagerInstance
				.executeQuery(query, queryParameterMap);


		return resultMapList;
	}


}
