package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

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

public class AddContactWorkflowTest {

	@Test
	public void AddContactTest() throws ClientProtocolException, IOException {

		//book keeping
		//WorkflowTestHelper.DeleteTestAccount();
		WorkflowTestHelper.CreateTestAccount();




		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Account"));	    
		nvps.add(new BasicNameValuePair("requestType", "Delete"));
		nvps.add(new BasicNameValuePair("username", "Test2"));
		nvps.add(new BasicNameValuePair("password", "TestPass2"));
		nvps.add(new BasicNameValuePair("email", "Test@test.com"));

		/*
		//execute the request.
		WorkflowTestHelper.ExecuteRequest(nvps);

		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Account"));	    
		nvps.add(new BasicNameValuePair("requestType", "Delete"));
		nvps.add(new BasicNameValuePair("username", "Test"));
		nvps.add(new BasicNameValuePair("password", "TestPass"));
		nvps.add(new BasicNameValuePair("email", "Test@test.com"));

		//execute the request.
		WorkflowTestHelper.ExecuteRequest(nvps);
		 */


		nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Account"));	    
		nvps.add(new BasicNameValuePair("requestType", "Create"));
		nvps.add(new BasicNameValuePair("username", "Test"));
		nvps.add(new BasicNameValuePair("password", "TestPass"));
		nvps.add(new BasicNameValuePair("email", "Test@test.com"));

		//execute the request.
		WorkflowTestHelper.ExecuteRequest(nvps);

		List <NameValuePair> nvps1 = new ArrayList <NameValuePair>();	    
		nvps1.add(new BasicNameValuePair("requestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("requestType", "Add"));
		nvps1.add(new BasicNameValuePair("username", "TestUser"));
		nvps1.add(new BasicNameValuePair("contactusername", "Test"));

		//execute the request.
		CloseableHttpResponse response1 = WorkflowTestHelper.ExecuteRequest(nvps1);

		List<Map<String,String>> returnMap = WorkflowTestHelper.GetReponseMap(response1); 
		System.out.println(returnMap);


	}
}
