//package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//// import org.junit.Test;
//import org.neo4j.cypher.javacompat.ExecutionEngine;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.kernel.impl.util.StringLogger;
//
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IAccountDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IContactDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.INotificationDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services.AccountDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services.ContactDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services.DBManager;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.services.NotificationDBRequestHandler;
//import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.TestRequestBuilder;
//
///**
// * This class create a test database for visualization 
// * It calls the DBManager functions directly (without using http request)
// * @author Xiaozhou Zhou
// *
// */
//public class DatabaseStructureTest {
//
//	/**
//	 * This method creates a small test database 
//	 */
//	// @Test
//	public void createTestDatabase(){
//
//		Map<String,String[]> request; 
//		Map<String,String[]> parameters = new HashMap<String, String[]>(); 
//
//		// Create user accounts 
//		IAccountDBRequestHandler accountDBManager = new AccountDBRequestHandler(); 
//		request = TestRequestBuilder.createAccountRequest("user1", "pw1", "email1"); 
//		accountDBManager.create(request); 
//		request = TestRequestBuilder.createAccountRequest("user2", "pw2", "email2"); 
//		accountDBManager.create(request); 
//		request = TestRequestBuilder.createAccountRequest("user3", "pw3", "email3"); 
//		accountDBManager.create(request); 
//		request = TestRequestBuilder.createAccountRequest("user4", "pw4", "email4"); 
//		accountDBManager.create(request); 
//
//		// Update account information 		
//		parameters.clear(); 
//		parameters.put("phone_number", new String[]{"443-111-2222"});
//		parameters.put("last_name",  new String[]{"Messi"});
//		parameters.put("first_name",  new String[]{"Leo"});
//		parameters.put("work_place", new String[]{"Barcelona"});
//		request = TestRequestBuilder.updateAccountRequest("user1", parameters); 
//		accountDBManager.update(request); 
//
//		parameters.clear(); 
//		parameters.put("phone_number", new String[]{"888-666-1111"});
//		parameters.put("last_name",  new String[]{"Mourinho"});
//		parameters.put("first_name",  new String[]{"Jose"});
//		parameters.put("work_place", new String[]{"Chelsea"});
//		request = TestRequestBuilder.updateAccountRequest("user2", parameters); 
//		accountDBManager.update(request);
//
//		parameters.clear(); 
//		parameters.put("first_name",  new String[]{"Pep"});
//		parameters.put("work_place", new String[]{"Bayern"});
//		request = TestRequestBuilder.updateAccountRequest("user3", parameters); 
//		accountDBManager.update(request);
//
//		// Add contacts		
//		IContactDBRequestHandler contactDBManager = new ContactDBRequestHandler(); 
//		request = TestRequestBuilder.addContactRequest("user1", "user3"); 
//		contactDBManager.add(request); 
//		request = TestRequestBuilder.addContactRequest("user2", "user3"); 
//		contactDBManager.add(request); 
//
//		// create meal notifications
//		INotificationDBRequestHandler notificationDBManager = new NotificationDBRequestHandler(); 
//		parameters.clear(); 
//		parameters.put("poster", new String[]{"user3"});
//		parameters.put("postID",  new String[]{"1"});
//		String[] recipients = new String[] {"user1", "user2", "user4"}; 
//		parameters.put("recipientList",  recipients);
//		request = TestRequestBuilder.createMealNotificationRequest(parameters); 
//		notificationDBManager.CreateMealNotification(request); 
//
//	}
//
//	/**
//	 * This method deletes one relationship in the test database 
//	 */
//	// @Test
//	public void deletionTest(){
//		Map<String,String[]> request; 
//		request = TestRequestBuilder.deleteContactRequest("user2", "user3"); 
//		IContactDBRequestHandler contactDBManager = new ContactDBRequestHandler(); 
//		contactDBManager.delete(request); 
//	}
//
//	/**
//	 * WARNING!!! This method deletes everything in the database! Use with caution! 
//	 */
//	// @Test
//	public void clearDatabase(){
//
//		GraphDatabaseService GraphDBInstance = DBManager.GetGraphDBInstance();
//		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
//				StringLogger.SYSTEM);	
//
//		try ( Transaction tx = GraphDBInstance.beginTx() ){
//
//			String query = "MATCH (n) "
//					+ "OPTIONAL MATCH (n)-[r]-() "
//					+ "DELETE n,r";
//
//			try{
//				executionEngine.execute(query);
//				tx.success();
//			}catch(Exception e){
//				System.out.println(e.getMessage());
//				tx.failure();
//			}
//		}
//	}
//}
