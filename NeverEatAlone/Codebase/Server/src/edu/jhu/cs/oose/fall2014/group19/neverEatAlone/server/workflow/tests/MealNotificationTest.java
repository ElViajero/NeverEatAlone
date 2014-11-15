package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import static org.junit.Assert.*;

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
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

public class MealNotificationTest {

	@Test
	public void test() throws ClientProtocolException, IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();	    
		nvps2.add(new BasicNameValuePair("RequestID", "Notification"));	    
		nvps2.add(new BasicNameValuePair("RequestType", "Meal"));
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
			String response=in.readLine();		            	           
			in.close();
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}
	
		
		Gson gson =new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
/*
	    channel.queueDeclare("Test1", false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	 
	
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume("Test1", true, consumer);

	    
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      String message = new String(delivery.getBody());
	      List<Map<String,String>> res = gson.fromJson(message,stringStringMap);
	      System.out.println(" [x] Received '" + message + "'");
	      String resMessage = res.get(0).get("Message");
	      System.out.println(resMessage);
	*/    
	
		
	}

}
