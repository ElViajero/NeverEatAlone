package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;


/**
 * 
 * This class handles test cases related to account deletion.
 * 
 * @author tejasvamsingh
 *
 */

public class DeleteAccountWorkflowTest {

	/**
	 *  This method tests the creation and deletion of an user 
	 *  account.
	 *  
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public void CreateAndDeleteAccountTest() throws ClientProtocolException, IOException {


		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

		//STEP 1 : Create a test user account.
		WorkflowTestHelper.CreateTestAccount();


		//STEP 2 : Delete a test user account.

		CloseableHttpResponse response2 = WorkflowTestHelper.DeleteTestAccount();		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity2.getContent() ) );		    
			String response=in.readLine();
			System.out.println(response);
			assertTrue(response.equals("[{\"Status\":\"Success\"}]"));	            	            
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();
	}
	
	@Test
	/**
	 * @author Xiaozhou Zhou
	 * Test deleting an account with or without any relationship by sending http request
	 */
	public void DeleteAccountTest() throws ClientProtocolException, IOException {
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

		// create 1 account
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");		
		WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");		
		
		//delete UserA
		System.out.println("deleting UserA...");
		List <NameValuePair> nvps1 = new ArrayList <NameValuePair>();	    
		nvps1.add(new BasicNameValuePair("requestID", "Account"));	    
		nvps1.add(new BasicNameValuePair("requestType", "delete"));
		nvps1.add(new BasicNameValuePair("username", "UserA"));

		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps1);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("deleting status: "+returnMap);
//		assertTrue(returnMap.toString().equals("[{Status=Success}]"));
		
		// assert that UserA is no longer in the database
		System.out.println("check validity of UserA...");
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("requestID", "Account"));	    
		nvps2.add(new BasicNameValuePair("requestType", "isValid"));
		nvps2.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps2);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("check validity of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// create relationships between A and B
		System.out.println("creating relationships...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");		
		List <NameValuePair> nvps3 = new ArrayList <NameValuePair>();	    
		nvps3.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps3.add(new BasicNameValuePair("requestType", "add"));
		nvps3.add(new BasicNameValuePair("username", "UserA"));
		nvps3.add(new BasicNameValuePair("contactusername", "UserB"));
		WorkflowTestHelper.ExecuteRequest(nvps3);
		
		// delete UserA again
		response = WorkflowTestHelper.ExecuteRequest(nvps1);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("deleting status of UserA: "+returnMap);
//		assertTrue(returnMap.toString().equals("[{Status=Success}]"));
		
		// assert that UserA is no longer in the database
		System.out.println("check validity of UserA...");
		response = WorkflowTestHelper.ExecuteRequest(nvps2);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("check validity of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// UserA creates post
		System.out.println("UserA posting a post...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");		
		nvps3.clear(); 
		nvps3.add(new BasicNameValuePair("requestID", "Meal"));	    
		nvps3.add(new BasicNameValuePair("requestType", "create"));
		nvps3.add(new BasicNameValuePair("poster", "UserA"));
		nvps3.add(new BasicNameValuePair("postID", "UserA_1"));
		nvps3.add(new BasicNameValuePair("recipientList", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps3);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("posting status of UserA: "+returnMap);

		// delete UserA
		response = WorkflowTestHelper.ExecuteRequest(nvps1);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("deleting status of UserA: "+returnMap);
//		assertTrue(returnMap.toString().equals("[{Status=Success}]"));
		
		// assert that UserA is no longer in the database
		response = WorkflowTestHelper.ExecuteRequest(nvps2);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("check validity of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// assert that UserB don't receive the post anymore 
		nvps3.clear();
		nvps3.add(new BasicNameValuePair("requestID", "Meal"));	    
		nvps3.add(new BasicNameValuePair("requestType", "fetchNotifications"));
		nvps3.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps3);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching notifications sent to UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {}]"));
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");
				
	}

}
