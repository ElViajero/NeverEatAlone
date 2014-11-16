package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.ConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;

/**
 * This class tests whether notifications can 
 * be pushed successfully to the client.
 * 
 * @author tejasvamsingh
 *
 */
public class NotificationPushTest extends ActivityInstrumentationTestCase2<TabHostActivity> {

	
	private Solo solo;
	
	public NotificationPushTest() {
		super(TabHostActivity.class);
	}
	
	public void setUP() throws Exception{
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	
	
	  public void tearDown() throws Exception {
	    solo.finishOpenedActivities();
	  }

	/**
	 * This method tests whether the client can consume 
	 * notifications properly.
	 * For now, this is a manual test
	 * for which one has to sign in 
	 * and check whether the gui recognizes
	 *  the notification sent form this method.
	 */
	
	public void GUIUpdateTest(){
		
		List<Map<String,String>> notificationList= 
				new ArrayList<Map<String,String>>();
		
		HashMap<String, String> notification =
				new HashMap<String,String>();
		
		Gson gson = new Gson();

		
		notification.put("NotificationID", "1");
		notification.put("Message", "Hi there. This is a test");
		
		notificationList.add(notification);
		
	    try{
			
				
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setHost(ConfigurationHelper.GetConfigurationInstance().GetIPAddress());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		
		
		
		
		channel.queueDeclare("Tejas", false, false, false, null);
		String message = gson.toJson(notificationList); 
		channel.basicPublish("", "Tejas", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
		}catch(IOException e){
			System.out.println("An IO Exception occured in PushNotification." + e.getMessage());
		}
		
		
	}
	
}
