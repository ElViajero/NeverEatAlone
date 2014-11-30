package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

/**
 * This class tests workflows related to updating account, including setting availability 
 * @author Xiaozhou Zhou
 *
 */
public class UpdateAccountWorkflowTest {

	/**
	 * This test create two accounts, change password and email, 
	 * and check the uniqueness constraint on email
	 * @throws IOException
	 */
	@Test
	public void UpdateAccountTest() throws IOException {
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");
		
		// create test accounts
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");
		WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");
		
		// change the password of A to pwB, it should succeed 
		System.out.println("changing the password of UserA...");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Update"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		nvps.add(new BasicNameValuePair("Password", "pwB"));
		
		// check the response
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals(
				"[{Status=Success}, {Email=emailA, Username=UserA, Available=YES, Password=pwB}]"));
		
		// change both the email and password of A
		System.out.println("changing the email and password of UserA...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Update"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		nvps.add(new BasicNameValuePair("Email", "emailA2"));
		nvps.add(new BasicNameValuePair("Password", "pwA2"));
		
		// check the response
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals(
				"[{Status=Success}, {Email=emailA2, Username=UserA, Available=YES, Password=pwA2}]"));
		
		// change the availability status
		System.out.println("changing the availability of UserA...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Update"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		nvps.add(new BasicNameValuePair("Available", "NO"));
		
		// check the response
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals(
				"[{Status=Success}, {Email=emailA2, Username=UserA, Available=NO, Password=pwA2}]"));
		
		// change the email of A to emailB, it violate the uniqueness constraint and should fail
		System.out.println("changing the email of UserA...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Update"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		nvps.add(new BasicNameValuePair("Email", "emailB"));
		
		// check the response
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");
	}

	@Test
	public void SetAvailabilityTest() throws IOException {

	}
}
