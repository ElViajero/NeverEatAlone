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
public class ContactWorkflowTest {

	@Test
	public void ContactTest() throws ClientProtocolException, IOException {

		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

		// create accounts
		System.out.println("creating test accounts...");
		WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");		
		WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");		

		// A sends friend request to B
		System.out.println("A sending friend request to B...");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "add"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		nvps.add(new BasicNameValuePair("recipientList", "UserB"));
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Sent request: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, "
				+ "{recipientList=UserB, contactusername=UserB, username=UserA}]"));
		
		// fetch request of B
		System.out.println("fetch requests of UserB...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "fetchRequests"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching requests of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {poster=UserA, username=UserA}]"));
		
		// assert that the relationship is not created
		System.out.println("fetch contact of UserA...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));

		// B rejects friend request from A
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "reject"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		nvps.add(new BasicNameValuePair("contactusername", "UserA"));
		nvps.add(new BasicNameValuePair("recipientList", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Rejected request: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, "
				+ "{recipientList=UserA, contactusername=UserA, username=UserB}]"));
		
		// fetch request of B
		System.out.println("fetch requests of UserB...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "fetchRequests"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching requests of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// assert that the relationship is not created
		System.out.println("fetch contact of UserA...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// A sends friend request to B again
		nvps.clear();
		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "add"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		nvps.add(new BasicNameValuePair("recipientList", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Sent request: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, "
				+ "{recipientList=UserB, contactusername=UserB, username=UserA}]"));				
		
		// B accepts friend request from A
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "accept"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		nvps.add(new BasicNameValuePair("contactusername", "UserA"));
		nvps.add(new BasicNameValuePair("recipientList", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("Accepted request: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, "
				+ "{recipientList=UserA, contactusername=UserA, username=UserB}]"));
		
		// fetch request of B
		System.out.println("fetch requests of UserB...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "fetchRequests"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching requests of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		// assert that the relationship is created
		System.out.println("fetch contact of UserA...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {username=UserB}]"));
		
		System.out.println("fetch contact of UserB...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {username=UserA}]"));
		
		// delete relationship
		System.out.println("deleting relationship...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "delete"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		nvps.add(new BasicNameValuePair("recipientList", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("deleted relationship: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, "
				+ "{recipientList=UserB, contactusername=UserB, username=UserA}]"));

		// assert that the relationship is no longer in the database
		System.out.println("fetch contact of UserA...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));
		
		System.out.println("fetch contact of UserB...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "getAll"));
		nvps.add(new BasicNameValuePair("username", "UserB"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserB: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Failed}]"));

		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

	}

}
