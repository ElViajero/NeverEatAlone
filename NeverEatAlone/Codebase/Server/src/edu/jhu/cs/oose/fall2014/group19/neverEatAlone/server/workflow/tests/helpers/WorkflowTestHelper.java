package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class WorkflowTestHelper {

	/**
	 * 
	 * Helper method to create a test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static CloseableHttpResponse CreateTestAccount() throws ClientProtocolException, IOException{

		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.




		HttpPost httpPost = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");

		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", "TestUser"));
		nvps.add(new BasicNameValuePair("Password", "TestPass"));
		nvps.add(new BasicNameValuePair("Email", "Test@test.com"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		//execute the request.
		return httpclient.execute(httpPost);

	}

	/**
	 * Helper method to delete a test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static CloseableHttpResponse DeleteTestAccount() throws ClientProtocolException, IOException{

		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost = new HttpPost("http://localhost:8080/NeverEatAloneServer/RequestHandler");

		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Delete"));
		nvps.add(new BasicNameValuePair("Username", "TestUser"));
		nvps.add(new BasicNameValuePair("Password", "TestPass"));
		nvps.add(new BasicNameValuePair("Email", "Test@test.com"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		//execute the request.
		return httpclient.execute(httpPost);				
	}	


	/**
	 * 
	 * Helper method to execute a request.
	 * 
	 * @param nvps
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse ExecuteRequest(List<NameValuePair> nvps) throws ClientProtocolException, IOException{

		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost = new HttpPost("http://10.188.181.210:8080/NeverEatAloneServer/RequestHandler");

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		//execute the request.
		return httpclient.execute(httpPost);

	}





}
