package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers;

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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WorkflowTestHelper {

	/**
	 * 
	 * Helper method to create a default test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static CloseableHttpResponse CreateTestAccount() throws ClientProtocolException, IOException{

		return CreateTestAccount("TestUser", "TestPass", "Test@test.com");
	}
	
	/**
	 * 
	 * Helper method to create a test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static CloseableHttpResponse CreateTestAccount(String user, String pw, String email) 
			throws ClientProtocolException, IOException{

		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.

		HttpPost httpPost = new HttpPost("http://localhost:8080/NeverEatAloneServer/RequestHandler");

		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", user));
		nvps.add(new BasicNameValuePair("Password", pw));
		nvps.add(new BasicNameValuePair("Email", email));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		//execute the request.
		return httpclient.execute(httpPost);

	}

	/**
	 * Helper method to delete the default test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public static CloseableHttpResponse DeleteTestAccount() throws ClientProtocolException, IOException{
		return DeleteTestAccount("TestUser"); 
	}

	/**
	 * Helper method to delete a test account.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse DeleteTestAccount(String user) throws ClientProtocolException, IOException{

		//get a client handle.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//set up post request.
		HttpPost httpPost = new HttpPost("http://localhost:8080/NeverEatAloneServer/RequestHandler");

		//populate request headers and data		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Delete"));
		nvps.add(new BasicNameValuePair("Username", user));
//		nvps.add(new BasicNameValuePair("Password", "TestPass"));
//		nvps.add(new BasicNameValuePair("Email", "Test@test.com"));

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
		HttpPost httpPost = new HttpPost("http://localhost:8080/NeverEatAloneServer/RequestHandler");

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		//execute the request.
		return httpclient.execute(httpPost);

	}

	/**
	 * This method builds the http response message map from a response
	 * @param response
	 * @return List<Map<String,String>>
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static List<Map<String,String>> GetReponseMap(CloseableHttpResponse response) 
			throws IllegalStateException, IOException{

		// get response message
		Gson gsonObject = new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
		List<Map<String,String>> returnMap = null;
		try {		    
			HttpEntity entity1 = response.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity1.getContent() ) );		    
			String responseString=in.readLine();
			returnMap = gsonObject.fromJson(responseString, stringStringMap);

			in.close();
			EntityUtils.consume(entity1);
		} finally {
			response.close();
		}	
		
		return returnMap; 
	}




}
