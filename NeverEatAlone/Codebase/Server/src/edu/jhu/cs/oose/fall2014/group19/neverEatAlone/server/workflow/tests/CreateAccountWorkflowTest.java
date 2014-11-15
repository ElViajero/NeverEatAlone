package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

/**
 * This class tests cases related to account creation.
 * 
 * @author tejasvamsingh
 *
 */
public class CreateAccountWorkflowTest {

	/**
	 * 
	 * This method tests the creations of a valid user account.
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 */

	@Test
	public void CreateValidAccountTest() throws ClientProtocolException, IOException {

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();

		//create the test account
		CloseableHttpResponse response2 = WorkflowTestHelper.CreateTestAccount();		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
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
	 * The method tests the uniqueness of the Username field.
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	@Test
	public void CheckUniquenessTest() throws ClientProtocolException, IOException {

		// book keeping
		WorkflowTestHelper.DeleteTestAccount();
		WorkflowTestHelper.CreateTestAccount();

		//try creating the same user account.
		CloseableHttpResponse response2 = WorkflowTestHelper.CreateTestAccount();
		try {		    
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
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
