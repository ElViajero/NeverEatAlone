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
 * This class tests workflow related to fetching account information 
 * @author Xiaozhou Zhou
 *
 */
public class GetInfoAccountWorkflowTest {
	
	/**
	 * Test fetching the full information of an account
	 * @throws IOException
	 */
	@Test
	public void GetFullAccountInfoTest() throws IOException {
		
		// book keeping 
		System.out.println("deleting test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		
		// create test accounts
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");
		
		// add more properties to test account
		System.out.println("Adding properties to UserA...");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Update"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		nvps.add(new BasicNameValuePair("Telephone", "911"));
		nvps.add(new BasicNameValuePair("Workplace", "JHU"));
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> updatedAccount = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Updated Account: "+updatedAccount);
		
		// fetch account info and assert
		System.out.println("Fetching account info of UserA...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "GetInfo"));
		nvps.add(new BasicNameValuePair("Username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> fetchedAccount = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Fetched Account: "+fetchedAccount);
		assertTrue(fetchedAccount.toString().equals(updatedAccount.toString())); 
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		
		
	}
}
