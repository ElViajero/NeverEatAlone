package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestHandler.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

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

/**
 * 
 * This class is just to test the sending and receiving of
 * HTTP request/response to/from the servlet.
 *  
 * @author tejasvamsingh
 *
 */

public class RequestHandlerTests {

	
	/**
	 * This method tests the sending of a POST request to the servlet and retreiving the 
	 * appropriate response.
	 * 
	 * As it stands this method should not be used.
	 * 
	 * To perform actual tests, look at the workflow tests.
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	@Test
	public void HttpPostRequestTest() throws ClientProtocolException, IOException {
	
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://10.0.0.3:8080/NeverEatAloneServer/RequestHandler");	    
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "IsValid"));	    
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));	    
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		try {		    
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    BufferedReader in = 
	                new BufferedReader( new InputStreamReader( entity2.getContent() ) );
	            
	            in.close();
		    EntityUtils.consume(entity2);
		} finally {
		    response2.close();
		}
		
	}

	
	
}
