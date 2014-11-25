package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationManager.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.ConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;

/**
 * 
 * This class handles asynchronous notification fetches
 * from a queue.
 * @author tejasvamsingh
 *
 */

public class NotificationExecutor extends AsyncTask<String, List<Map<String,String>>,List<Map<String,String>>> {




	TabHostActivity ActivityObject;

	// If it's being used on a thread separate from the UI thread,
	//make it static if it has to be global variable.

	static String IPAddress;
	static Channel ChannelObject;
	static String Username;
	static String Tag;
	static Gson GsonObject;


	/**
	 * 
	 * Set up method to be called the first time that the app registers
	 * to be notified.
	 * 
	 * @author tejasvamsingh
	 * @param tabHostActivity
	 */
	public NotificationExecutor(TabHostActivity tabHostActivity){
		ActivityObject = tabHostActivity;
		GsonObject = new Gson();
		//handling the try/catch stuff here makes sense for now.
		try {
			IPAddress = ConfigurationHelper.GetConfigurationInstance().GetIPAddress();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("The IP in NotificationExecutor is :" + IPAddress );
		System.out.flush();
	}

	public NotificationExecutor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Polling method that monitors queue for messages.
	 * @author tejasvamsingh
	 * 
	 */
	@Override
	protected List<Map<String, String>> doInBackground(String... params) {


		String username = params[0];
		Username=username;
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();

		try{

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(IPAddress);
			Connection connection = factory.newConnection();
			ChannelObject = connection.createChannel();
			System.out.println("username is" + username);
			ChannelObject.queueDeclare(username, false, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


			QueueingConsumer consumer = new QueueingConsumer(ChannelObject);
			Tag = ChannelObject.basicConsume(username, true, consumer);


			QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
			String message = new String(delivery.getBody());
			List<Map<String,String>> resultMapList = GsonObject.fromJson(message,stringStringMap);
			System.out.println(" [x] Received '" + message + "'");




			return resultMapList;


		}catch(IOException e){
			System.out.println("IOException in NotificationExecutor." + e.getMessage());
			return null;
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
	 * @author tejasvamsingh
	 * @param resultMapList
	 */

	@Override
	protected void onPostExecute(List<Map<String,String>> resultMapList){

		try {
			//ChannelObject.queueDelete(Username);

			ChannelObject.basicCancel(Tag);
			ChannelObject.close();
		} catch (IOException e) {
			System.out.println("IOException in onPostExecute");
		}
		catch (NullPointerException e){
			System.out.println("NullPointer in onPostExecute");
			Toast.makeText(ActivityObject.getApplicationContext(), 
					"Could not connect to notification server", Toast.LENGTH_SHORT).show();			
			new NotificationExecutor(ActivityObject).executeOnExecutor(THREAD_POOL_EXECUTOR, Username);
			return;
		}

		ActivityObject.UpdateNotificationCache(resultMapList);
		System.out.println("Reaching here regularly");
		new NotificationExecutor(ActivityObject).executeOnExecutor(THREAD_POOL_EXECUTOR, Username);
	}
}

