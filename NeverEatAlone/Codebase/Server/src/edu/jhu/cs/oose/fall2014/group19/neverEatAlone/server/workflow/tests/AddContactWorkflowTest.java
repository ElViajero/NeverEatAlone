package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

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

public class AddContactWorkflowTest {

	@Test
	public void AddContactTest() throws ClientProtocolException, IOException {

		//book keeping
		WorkflowTestHelper.DeleteTestAccount();
		WorkflowTestHelper.CreateTestAccount();

		List <NameValuePair> nvps = new ArrayList <NameValuePair>();	    
		nvps.add(new BasicNameValuePair("RequestID", "Account"));	    
		nvps.add(new BasicNameValuePair("RequestType", "Create"));
		nvps.add(new BasicNameValuePair("Username", "Test2"));
		nvps.add(new BasicNameValuePair("Password", "TestPass2"));
		nvps.add(new BasicNameValuePair("Email", "Test@test.com"));


		//execute the request.
		WorkflowTestHelper.ExecuteRequest(nvps);

		List <NameValuePair> nvps1 = new ArrayList <NameValuePair>();	    
		nvps1.add(new BasicNameValuePair("RequestID", "Contact"));	    
		nvps1.add(new BasicNameValuePair("RequestType", "Add"));
		nvps1.add(new BasicNameValuePair("Username", "Test"));
		nvps1.add(new BasicNameValuePair("ContactUsername", "Test2"));

		//execute the request.
		CloseableHttpResponse response1 = WorkflowTestHelper.ExecuteRequest(nvps1);

		Gson gsonObject = new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();

		List<Map<String,String>> returnMap = null;
		try {		    
			HttpEntity entity1 = response1.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity1.getContent() ) );		    
			String responseString=in.readLine();
			returnMap = gsonObject.fromJson(responseString, stringStringMap);
			in.close();
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}	

		System.out.println(returnMap);

	}
}
