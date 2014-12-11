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
 * This class handles test cases related to updating contact information.
 * To test correctly, AddContact functionality must be working 
 * 
 * @author Xiaozhou Zhou
 *
 */
public class UpdateContactWorkflowTest {
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
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "Add"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		WorkflowTestHelper.ExecuteRequest(nvps);

		// UserA updates alias of UserB for nonexisting alias
		System.out.println("Updating UserB in UserA's contact list...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "Update"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		nvps.add(new BasicNameValuePair("alias", "Leo"));
		CloseableHttpResponse response = WorkflowTestHelper.ExecuteRequest(nvps);
		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("updating status: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=Leo}]"));
		
		// UserA updates alias of UserB for existing alias
		System.out.println("Updating UserB in UserA's contact list...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "Update"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		nvps.add(new BasicNameValuePair("contactusername", "UserB"));
		nvps.add(new BasicNameValuePair("alias", "Messi"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("updating status: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=Messi}]"));

		// fetching contacts of UserA should get the correct updated information of UserB
		System.out.println("fetch contact of UserA...");
		nvps.clear();
		nvps.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps.add(new BasicNameValuePair("requestType", "GetAll"));
		nvps.add(new BasicNameValuePair("username", "UserA"));
		response = WorkflowTestHelper.ExecuteRequest(nvps);
		returnMap = WorkflowTestHelper.GetReponseMap(response); 
		System.out.println("fetching contacts of UserA: "+returnMap);
		assertTrue(returnMap.toString().equals("[{Status=Success}, {alias=Messi, username=UserB}]"));

		// book keeping 
		System.out.println("deleting all test accounts...");
		WorkflowTestHelper.DeleteTestAccount("UserA");
		WorkflowTestHelper.DeleteTestAccount("UserB");

	}

}
