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
 * This class tests workflows related to fetching contacts
 * @author Xiaozhou Zhou
 *
 */
public class GetAllContactsWorkflowTest {
	/**
	 * This method creates 3 accounts UserA, UserB, UserC. 
	 * A adds both B and C, B adds A but not C, C adds no one.
	 * Fetching contacts for A, B and C should return the correct response.  
	 * @throws IOException 
	 */
	@Test
	public void GetContactsTest() throws IOException {
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");
		WorkflowTestHelper.DeleteTestAccount("UserC");

		// create 3 accounts
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");
		WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");
		WorkflowTestHelper.CreateTestAccount("UserC", "pwC", "emailC");
		
		// create relationships (add contacts)
		System.out.println("creating relationships...");
		List <NameValuePair> nvps1 = new ArrayList <NameValuePair>();	    
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "Add"));
		nvps1.add(new BasicNameValuePair("username", "UserA"));
		nvps1.add(new BasicNameValuePair("contactusername", "UserB"));
		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps2.add(new BasicNameValuePair("requestType", "Add"));
		nvps2.add(new BasicNameValuePair("username", "UserA"));
		nvps2.add(new BasicNameValuePair("contactusername", "UserC"));

		List <NameValuePair> nvps3 = new ArrayList <NameValuePair>();	    
		nvps3.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps3.add(new BasicNameValuePair("requestType", "Add"));
		nvps3.add(new BasicNameValuePair("username", "UserB"));
		nvps3.add(new BasicNameValuePair("contactusername", "UserA"));
		
		//execute the add contacts request.
		WorkflowTestHelper.ExecuteRequest(nvps1);
		WorkflowTestHelper.ExecuteRequest(nvps2);
		WorkflowTestHelper.ExecuteRequest(nvps3);
		
		//get all contacts of A
		System.out.println("getting all contacts of UserA...");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		
		// check the response
		System.out.println("checking response for getting all contacts of UserA...");
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=, username=UserB}, {alias=, username=UserC}]"));
		
		//get all contacts of B
		System.out.println("getting all contacts of UserB...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		
		// check the response
		System.out.println("checking response for getting all contacts of UserB...");
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=, username=UserA}]"));
		
		//get all contacts of C
		System.out.println("getting all contacts of UserC...");
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps.add(new BasicNameValuePair("username", "UserC"));
		
		// check the response
		System.out.println("checking response for getting all contacts of UserC...");
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println(returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=, username=UserA}]"));
		
		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");
		WorkflowTestHelper.DeleteTestAccount("UserC");
	}
}
