package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationManager.services;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.NotificationTestActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class NotificationExecutor extends AsyncTask<String, List<Map<String,String>>,List<Map<String,String>>> {

	NotificationTestActivity ActivityObject;
	String Username;
	String Tag;
	Channel ChannelObject;
	
	public NotificationExecutor(NotificationTestActivity notificationTestAtivity){
		ActivityObject = notificationTestAtivity;
	}
	
	public NotificationExecutor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Map<String, String>> doInBackground(String... params) {
	
		
		String username = params[0];
		Username=username;
		
		
		
		try{
		Gson gson =new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("10.188.181.210");
	    Connection connection = factory.newConnection();
	    ChannelObject = connection.createChannel();
System.out.println("username is"+ username);
	    ChannelObject.queueDeclare(username, false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	 
	
	    QueueingConsumer consumer = new QueueingConsumer(ChannelObject);
	    Tag = ChannelObject.basicConsume(username, false, consumer);
	    
	    
	    
	    	
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
	      String message = new String(delivery.getBody());
	      List<Map<String,String>> resultMapList = gson.fromJson(message,stringStringMap);
	      System.out.println(" [x] Received '" + message + "'");
	      String resultMessage = 	resultMapList.get(0).get("Message");	      
	      System.out.println(resultMessage);	      
	      	      

	      
	      return resultMapList;
	      
	      
	    
		
		}catch(IOException e){
			System.out.println("IOException in NotificationExecutor." + e.getMessage());
		} catch (ShutdownSignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	
	}
	
	
	/**
	 * Publish partial results to the UI thread.
	 * @param resultMapList
	 */
	
	protected void onPostExecute(List<Map<String,String>> resultMapList){
		
		try {
			ChannelObject.queueDelete(Username);
			ChannelObject.basicCancel(Tag);
			ChannelObject.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultMapList!=null)
		{
		System.out.println("In post execute");
		for(Map<String,String> notification : resultMapList){
			ActivityObject.UpdateNotificationList(notification);
		}
		}
		
		//new NotificationExecutor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Username);
		
	}

	
	
}
