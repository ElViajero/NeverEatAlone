package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;


/** 
 * This class handles test cases related to contact deletion.
 * To test correctly, AddContact functionality must be working 
 * 
 * @author Xiaozhou Zhou
 *
 */
public class DeleteContactWorkflowTest {

	@Test
	public void DeleteAccountTest() throws ClientProtocolException, IOException {

		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

		// create accounts
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");		
		WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");		

		// create relationships between A and B
		System.out.println("creating relationship...");
		List <NameValuePair> nvps1 = new ArrayList <NameValuePair>();	    
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "Add"));
		nvps1.add(new BasicNameValuePair("username", "UserA"));
		nvps1.add(new BasicNameValuePair("contactusername", "UserB"));
		WorkflowTestHelper.ExecuteRequest(nvps1);

		// delete relationship
		System.out.println("deleting relationship...");
		nvps1.clear();
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "Delete"));
		nvps1.add(new BasicNameValuePair("username", "UserA"));
		nvps1.add(new BasicNameValuePair("contactusername", "UserB"));
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps1);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("deleting status: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}]"));

		// assert that the relationship is no longer in the database
		System.out.println("fetch contact of UserA...");
		nvps1.clear();
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps1.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps1);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		System.out.println("fetch contact of UserB...");
		nvps1.clear();
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps1.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps1);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));

		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

	}

}
