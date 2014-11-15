package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.AccountDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.DBManager;





/**
 * This class handles test cases for the AccountManager class.
 * @author tejasvamsingh
 *
 */

public class AccountManagerTests {

	/**
	 * Test case for account creation.
	 */
	@Test
	public void CreateAccountTest() {

		//popluate data		
		Map<String,String[]> requestMap = new HashMap<String,String[]>();

		requestMap.put("RequestID",new String[]{"Account"});
		requestMap.put("RequestType",new String[]{"Create"});
		requestMap.put("Username",new String[]{"Tejas"});
		requestMap.put("Password",new String[]{"T"});
		requestMap.put("Email",new String[]{"Tea@tea.com"});

		//create instances and execute account creation logic.
		IAccountDBManager iAccountManager = new AccountDBManager();
		iAccountManager.CreateAccount(requestMap);

		GraphDatabaseService graphDBInstance = DBManager.GetGraphDBInstance();
		ExecutionEngine executionEngine = new ExecutionEngine(graphDBInstance,
				StringLogger.SYSTEM);
		ExecutionResult result;
		List<Map<String, String>> resultMapList;


		//execute test query logic.
		try ( Transaction tx = graphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Username","Tejas");

			//create cypher query to fetch node from the dataase.
			String query = "MATCH (n:User) WHERE n.Username={Username} RETURN n";

			//execute the query
			result = executionEngine.execute(query,parameters);

			// retrieve a resultMapList.			
			resultMapList = DBManager.GetResultMapList(result);

			// Successful transaction.
			tx.success();		 		 
		}


		System.out.println("STATS");
		System.out.println(resultMapList);
		System.out.println(resultMapList.get(0));

		// test assertions.
		assertTrue(resultMapList!=null);
		assertTrue(!resultMapList.isEmpty());		
		assertTrue(resultMapList.get(0).containsKey("Status"));
		assertTrue(resultMapList.get(0).get("Status").equals("Success"));
		assertTrue(resultMapList.get(1).get("Password").equals("T"));				
	}



	/**
	 * Test case for account validity (and invalidity) check.
	 * 
	 */
	@Test
	public void IsValidAccountTest(){
		CreateAccountTest();		
		//CASE 1 : Account is valid.

		//popluate data		
		Map<String,String[]> requestMap = new HashMap<String,String[]>();

		requestMap.put("RequestID",new String[]{"Account"});
		requestMap.put("RequestType",new String[]{"IsValid"});
		requestMap.put("Username",new String[]{"Tejas"});		

		//create instances and execute account creation logic.
		IAccountDBManager iAccountManager = new AccountDBManager();
		List<Map<String, String>> resultMapList =
				iAccountManager.IsValidAccount(requestMap);


		System.out.println("STATS");
		System.out.println(resultMapList);
		System.out.println(resultMapList.get(0));

		// test assertions.
		assertTrue(resultMapList!=null);
		assertTrue(!resultMapList.isEmpty());		
		assertTrue(resultMapList.get(0).containsKey("Status"));
		assertTrue(resultMapList.get(0).get("Status").equals("Success"));
		assertTrue(resultMapList.get(1).get("Password").equals("T"));


		//CASE 1 : Account is invalid.
		requestMap.put("Username",new String[]{"T"});		

		//create instances and execute account creation logic.		
		resultMapList =
				iAccountManager.IsValidAccount(requestMap);


		System.out.println("STATS");
		System.out.println(resultMapList);
		System.out.println(resultMapList.get(0));

		// test assertions.
		assertTrue(resultMapList!=null);
		assertTrue(!resultMapList.isEmpty());		
		assertTrue(resultMapList.get(0).containsKey("Status"));
		assertTrue(resultMapList.get(0).get("Status").equals("Failed"));

	}


}
