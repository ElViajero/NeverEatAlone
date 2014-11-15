package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

/**
 * This class tests cases related 
 * checking the existence/validity of a given account.
 * @author tejasvamsingh
 *
 */
public class IsValidAccountWorkflowTest {

	/**
	 * This method creates an account and checks to see if the account created is 
	 * a valid account (i.e if it exists) in the system.
	 * @throws IOException 
	 */
	@Test
	public void CreateAccountAndTestValidity() throws IOException {



		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

		//Create accoutn
		WorkflowTestHelper.CreateTestAccount();

		// now test validity.



		//populate request headers and data		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps2.add(new BasicNameValuePair("RequestType", "IsValid"));
		nvps2.add(new BasicNameValuePair("Username", "TestUser"));						


		CloseableHttpResponse response2 = WorkflowTestHelper.ExecuteRequest(nvps2);		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity2.getContent() ) );		    
			String response=in.readLine();
			assertTrue(response.equals("Success"));	            	            
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

	}

	/**
	 * 
	 * This method creates an account 
	 * and then uses a faulty username to 
	 * test the invalidity of the username.  
	 * 
	 * 
	 * @throws IOException
	 */

	@Test
	public void CreateAccountAndTestInvalidity() throws IOException{

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();
		//create account
		WorkflowTestHelper.CreateTestAccount();

		// now test validity.


		//populate request headers and data		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps2.add(new BasicNameValuePair("RequestType", "IsValid"));
		nvps2.add(new BasicNameValuePair("Username", "SomeOtherUserID"));						

		//execute the request.
		CloseableHttpResponse response2 = WorkflowTestHelper.ExecuteRequest(nvps2);		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity2.getContent() ) );		    
			String response=in.readLine();
			assertTrue(response.equals("Failed"));	            	            
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();


	}

}
