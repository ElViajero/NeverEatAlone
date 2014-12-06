package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

/**
 * This class tests cases related to login requests.
 * 
 * @author tejasvamsingh
 *
 */
public class LoginWorkflow {

	/**
	 * This method tests successful login of a valid user.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	@Test
	public void CredentialCheckTest() throws ClientProtocolException, IOException {

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

		//PART 1 : Create a test account.

		WorkflowTestHelper.CreateTestAccount();


		//create Gson object

		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
		// Part 2 : Check Credentials



		//populate request headers and data		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("requestID", "Login"));	    
		nvps2.add(new BasicNameValuePair("requestType", "CheckCredentials"));
		nvps2.add(new BasicNameValuePair("username", "TestUser"));
		nvps2.add(new BasicNameValuePair("password", "TestPass"));		

		//execute the request.
		CloseableHttpResponse response2 = WorkflowTestHelper.ExecuteRequest(nvps2);		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity2.getContent() ) );		    
			String response=in.readLine();

			List<Map<String,String>> result= gson.fromJson(response, stringStringMap);

			assertTrue(result.get(0).equals("Success"));	            	            
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

	}

	/**
	 * This method tests rejection of credentials for an
	 * invalid user.
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	@Test
	public void InvalidCredentialCheckTest() throws ClientProtocolException, IOException {


		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("requestID", "Login"));	    
		nvps.add(new BasicNameValuePair("requestType", "CheckCredentials"));
		nvps.add(new BasicNameValuePair("username", "testInvalidUser"));
		nvps.add(new BasicNameValuePair("password", "testInvalidPass"));
		nvps.add(new BasicNameValuePair("email", "testInvalid@testInvalid.com"));


		//execute the request.
		CloseableHttpResponse response1 = WorkflowTestHelper.ExecuteRequest(nvps);	

		try {		    
			HttpEntity entity1 = response1.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity1.getContent() ) );		    
			String response=in.readLine();
			assertTrue(response.equals("Failed"));	            	            
			in.close();
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}	
	}

}
