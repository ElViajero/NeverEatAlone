package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

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
		
		
		
		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost1 = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");
				
		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", "Maverick"));
		nvps.add(new BasicNameValuePair("Password", "Mav"));
		nvps.add(new BasicNameValuePair("Email", "T@t.com"));
				
		httpPost1.setEntity(new UrlEncodedFormEntity(nvps));
		//execute the request.
		CloseableHttpResponse response1 = httpclient.execute(httpPost1);		
		try {		    
			HttpEntity entity1 = response1.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity1.getContent() ) );		    
			String response=in.readLine();
			assertTrue(response.equals("Success"));	            	            
			in.close();
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}
		
		
		// now test validity.
		
		HttpPost httpPost2 = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");
		
		//populate request headers and data		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps2.add(new BasicNameValuePair("RequestType", "IsValid"));
		nvps2.add(new BasicNameValuePair("Username", "Maverick"));						
		httpPost2.setEntity(new UrlEncodedFormEntity(nvps2));
		//execute the request.
		CloseableHttpResponse response2 = httpclient.execute(httpPost2);		
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
		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost1 = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");
				
		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", "Maverick"));
		nvps.add(new BasicNameValuePair("Password", "Mav"));
		nvps.add(new BasicNameValuePair("Email", "T@t.com"));
				
		httpPost1.setEntity(new UrlEncodedFormEntity(nvps));
		//execute the request.
		CloseableHttpResponse response1 = httpclient.execute(httpPost1);		
		try {		    
			HttpEntity entity1 = response1.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity1.getContent() ) );		    
			String response=in.readLine();
			assertTrue(response.equals("Success"));	            	            
			in.close();
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}
		
		
		// now test validity.
		
		HttpPost httpPost2 = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");
		
		//populate request headers and data		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps2.add(new BasicNameValuePair("RequestType", "IsValid"));
		nvps2.add(new BasicNameValuePair("Username", "SomeOtherUserID"));						
		httpPost2.setEntity(new UrlEncodedFormEntity(nvps2));
		//execute the request.
		CloseableHttpResponse response2 = httpclient.execute(httpPost2);		
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
		
	}
	
}
