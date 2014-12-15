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

public class VisibilityWorkflowTest {
		
	/**
	 * This method creates 2 accounts UserA and UserB 
	 * A is visible to B, B is not visible to A
	 * @throws IOException 
	 */
	@Test
	public void GetContactsTest() throws IOException {
		
	
	// book keeping 
	System.out.println("deleting all test accounts...");
	WorkflowTestHelper.DeleteTestAccount("UserA");
	WorkflowTestHelper.DeleteTestAccount("UserB");

	// create 2 accounts
	System.out.println("creating test accounts...");
	WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");
	WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");
	
	// set UserA to be visible to UserB
	System.out.println("adding visibilities...");
	
	List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "add"));
	nvps.add(new BasicNameValuePair("username", "UserA"));
	nvps.add(new BasicNameValuePair("contactList", "UserB"));
	nvps.add(new BasicNameValuePair("available", "YES"));
	CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
	List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Success}, {available=YES}]"));
	
	// get all visible contacts of B should return A
	System.out.println("get all visible contacts of UserB...");
	nvps.clear();
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "getAll"));
	nvps.add(new BasicNameValuePair("username", "UserB"));
	response = WorkflowTestHelper.ExecuteRequest(nvps);
	returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Success}, {contactusername=UserA}]"));
	
	// get all visible contacts of A should fail
	System.out.println("get all visible contacts of UserA...");
	nvps.clear();
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "getAll"));
	nvps.add(new BasicNameValuePair("username", "UserA"));
	response = WorkflowTestHelper.ExecuteRequest(nvps);
	returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
	
	// update the visibility status
	System.out.println("update visibility status...");
	nvps.clear();
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "update"));
	nvps.add(new BasicNameValuePair("username", "UserA"));
	nvps.add(new BasicNameValuePair("available", "NO"));
	response = WorkflowTestHelper.ExecuteRequest(nvps);
	returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Success}, {available=NO}]"));
	
	// delete the visibility edge
	System.out.println("deleting visibility...");
	nvps.clear();
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "delete"));
	nvps.add(new BasicNameValuePair("username", "UserA"));
	nvps.add(new BasicNameValuePair("contactList", "UserB"));
	response = WorkflowTestHelper.ExecuteRequest(nvps);
	returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Success}, {password=pwA, email=emailA, username=UserA}]"));
	
	// get all visible contacts of B should fail
	System.out.println("get all visible contacts of UserB...");
	nvps.clear();
	nvps.add(new BasicNameValuePair("requestID", "Visibility"));	    
	nvps.add(new BasicNameValuePair("requestType", "getAll"));
	nvps.add(new BasicNameValuePair("username", "UserB"));
	response = WorkflowTestHelper.ExecuteRequest(nvps);
	returnMap = WorkflowTestHelper.GetReponseMap(response); 
	System.out.println(returnMap);
	assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
	
	// book keeping 
	System.out.println("deleting all test accounts...");
	WorkflowTestHelper.DeleteTestAccount("UserA");
	WorkflowTestHelper.DeleteTestAccount("UserB");

	
	}
	

}
