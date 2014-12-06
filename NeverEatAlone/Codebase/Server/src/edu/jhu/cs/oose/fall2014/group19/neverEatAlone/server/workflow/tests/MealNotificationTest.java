package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

public class MealNotificationTest {

	@Test
	public void test() throws ClientProtocolException, IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {

		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("requestID", "Notification"));	    
		nvps2.add(new BasicNameValuePair("requestType", "Meal"));
		nvps2.add(new BasicNameValuePair("Recipient", "Test1"));
		nvps2.add(new BasicNameValuePair("Message", "TestUser says Hi !"));

		//execute the request.
		CloseableHttpResponse response2 = WorkflowTestHelper.ExecuteRequest(nvps2);		
		try {		    
			HttpEntity entity2 = response2.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity2.getContent() ) );		    
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}


		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");    


	}

}
