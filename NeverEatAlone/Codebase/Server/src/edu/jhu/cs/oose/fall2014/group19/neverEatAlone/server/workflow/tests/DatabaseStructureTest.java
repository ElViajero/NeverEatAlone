package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.INotificationDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.AccountDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.ContactDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.DBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.NotificationDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.TestRequestBuilder;

/**
 * This class create a test database for visualization 
 * It calls the DBManager functions directly (without using http request)
 * @author Xiaozhou Zhou
 *
 */
public class DatabaseStructureTest {

	/**
	 * This method creates a small test database 
	 */
	@Test
	public void createTestDatabase(){

		Map<String,String[]> request; 
		Map<String,String[]> parameters = new HashMap<String, String[]>(); 

		// Create user accounts 
		IAccountDBManager accountDBManager = new AccountDBManager(); 
		request = TestRequestBuilder.createAccountRequest("user1", "pw1", "email1"); 
		accountDBManager.CreateAccount(request); 
		request = TestRequestBuilder.createAccountRequest("user2", "pw2", "email2"); 
		accountDBManager.CreateAccount(request); 
		request = TestRequestBuilder.createAccountRequest("user3", "pw3", "email3"); 
		accountDBManager.CreateAccount(request); 
		request = TestRequestBuilder.createAccountRequest("user4", "pw4", "email4"); 
		accountDBManager.CreateAccount(request); 

		// Update account information 		
		parameters.clear(); 
		parameters.put("phone_number", new String[]{"443-111-2222"});
		parameters.put("last_name",  new String[]{"Messi"});
		parameters.put("first_name",  new String[]{"Leo"});
		parameters.put("work_place", new String[]{"Barcelona"});
		request = TestRequestBuilder.updateAccountRequest("user1", parameters); 
		accountDBManager.UpdateAccount(request); 

		parameters.clear(); 
		parameters.put("phone_number", new String[]{"888-666-1111"});
		parameters.put("last_name",  new String[]{"Mourinho"});
		parameters.put("first_name",  new String[]{"Jose"});
		parameters.put("work_place", new String[]{"Chelsea"});
		request = TestRequestBuilder.updateAccountRequest("user2", parameters); 
		accountDBManager.UpdateAccount(request);

		parameters.clear(); 
		parameters.put("first_name",  new String[]{"Pep"});
		parameters.put("work_place", new String[]{"Bayern"});
		request = TestRequestBuilder.updateAccountRequest("user3", parameters); 
		accountDBManager.UpdateAccount(request);

		// Add contacts		
		IContactDBManager contactDBManager = new ContactDBManager(); 
		request = TestRequestBuilder.addContactRequest("user1", "user3"); 
		contactDBManager.AddContact(request); 
		request = TestRequestBuilder.addContactRequest("user2", "user3"); 
		contactDBManager.AddContact(request); 
		
		// create meal notifications
		INotificationDBManager notificationDBManager = new NotificationDBManager(); 
		parameters.clear(); 
		parameters.put("poster", new String[]{"user3"});
		parameters.put("postID",  new String[]{"1"});
		String[] recipients = new String[] {"user1", "user2", "user4"}; 
		parameters.put("recipientList",  recipients);
		request = TestRequestBuilder.createMealNotificationRequest(parameters); 
		notificationDBManager.CreateMealNotification(request); 

	}
	
	/**
	 * This method deletes one relationship in the test database 
	 */
	// @Test
	public void deletionTest(){
		Map<String,String[]> request; 
		request = TestRequestBuilder.deleteContactRequest("user2", "user3"); 
		IContactDBManager contactDBManager = new ContactDBManager(); 
		contactDBManager.DeleteContact(request); 
	}

	/**
	 * WARNING!!! This method deletes everything in the database! Use with caution! 
	 */
	// @Test
	public void clearDatabase(){
		
		GraphDatabaseService GraphDBInstance = DBManager.GetGraphDBInstance();
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);	

		try ( Transaction tx = GraphDBInstance.beginTx() ){
			
			String query = "MATCH (n) "
					+ "OPTIONAL MATCH (n)-[r]-() "
					+ "DELETE n,r";

			try{
				executionEngine.execute(query);
				tx.success();
			}catch(Exception e){
				System.out.println(e.getMessage());
				tx.failure();
			}
		}
	}
}
