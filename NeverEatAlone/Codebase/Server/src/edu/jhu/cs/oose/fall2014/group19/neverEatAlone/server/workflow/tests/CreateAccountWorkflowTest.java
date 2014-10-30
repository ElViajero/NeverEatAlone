package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class CreateAccountWorkflowTest {

	@Test
	public void CreateValidAccountTest() throws ClientProtocolException, IOException {
		
		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");
		
		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", "Tejas"));
		nvps.add(new BasicNameValuePair("Password", "T"));
		nvps.add(new BasicNameValuePair("Email", "Tea@tea.com"));
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		//execute the request.
		CloseableHttpResponse response2 = httpclient.execute(httpPost);		
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
		
		
	}

}
