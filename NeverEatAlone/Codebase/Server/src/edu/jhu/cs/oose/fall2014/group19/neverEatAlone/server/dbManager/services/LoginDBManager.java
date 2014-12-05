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

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.ILoginDBManager;


/**
 * This class handles database interactions for login
 * related requests.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class LoginDBManager implements ILoginDBManager {

	GraphDatabaseService GraphDBInstance;
	public LoginDBManager(){
		// for now we get the instance in the constructor.
		// This may not turn out to be the best way. 
		GraphDBInstance = 
				DBManager.GetGraphDBInstance();
	}

	/**
	 * This method checks credentials for successful login.
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String, String>> CheckCredentials(
			Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached CheckCredentials in LoginManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("requestType");
		modifiableRequestMap.remove("requestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// set up paramters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;



		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("username",queryParamterMap.get("username"));
			parameters.put("password", queryParamterMap.get("password"));

			//create Cypher query to fetch
			//node with given credentials from the database.
			String query = "MATCH(n:User) WHERE n.username={username} "
					+ "AND n.password={password} RETURN n";

			//execute the query
			result = executionEngine.execute(query,parameters);

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);

			// Successful transaction.
			tx.success();

		}

		return resultMapList;

	}



}
