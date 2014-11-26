package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;

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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

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
	static QueueingConsumer consumerObject;
	static ConnectionFactory factoryObject;
	static Connection connectionObject;
	static boolean cleanBit;



	/**
	 * 
	 * Set up method to be called the first time that the app registers
	 * to be notified.
	 * 
	 * @author tejasvamsingh
	 * @param tabHostActivity
	 */
	public NotificationExecutor(TabHostActivity tabHostActivity,String username){
		ActivityObject = tabHostActivity;
		GsonObject = new Gson();	
		Username = username;
		cleanBit=false;

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


		//String username = params[0];
		//Username=username;
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();

		try{
			initConsumptionFramework();

			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
			while(true){
				QueueingConsumer.Delivery delivery = consumerObject.nextDelivery(3000);
				if(delivery==null)						
					break;
				String message = new String(delivery.getBody());
				List<Map<String,String>> currentResultMapList= GsonObject.fromJson(message,stringStringMap);
				resultMapList.addAll(currentResultMapList);
				System.out.println(" [x] Received '" + message + "'");
			}



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

		if(resultMapList==null)
			MessageToasterHelper.toastMessage(ActivityObject,
					"Could not connect to notification server.");

		if(!resultMapList.isEmpty())
			ActivityObject.UpdateNotificationCache(resultMapList);
		System.out.println("Reaching here regularly");
		if(!cleanBit)
			new NotificationExecutor(ActivityObject,Username).executeOnExecutor(THREAD_POOL_EXECUTOR, Username);
	}

	public static void cleanUp(){		
		cleanBit=true;
		try {
			//ChannelObject.queueDelete(Username);			
			ChannelObject.basicCancel(Username);
			ChannelObject.close();
			connectionObject.close();

		} catch (IOException e) {
			System.out.println("Username is :" + Username);
			System.out.println("IOException in cleanUp" + e.getMessage());
			return;
		}
		catch (NullPointerException e){
			System.out.println("NullPointer in onPostExecute");
			return;
		}

		ChannelObject=null;
		consumerObject=null;
		factoryObject=null;


	}


	private static void initConsumptionFramework() throws IOException{
		if(ChannelObject==null){
			factoryObject = new ConnectionFactory();
			factoryObject.setHost(IPAddress);
			connectionObject = factoryObject.newConnection();
			ChannelObject = connectionObject.createChannel();
			System.out.println("username is"+Username);
			ChannelObject.queueDeclare(Username, false, false, false, null);
			consumerObject =  new QueueingConsumer(ChannelObject);
			ChannelObject.basicConsume(Username, true,Username,true,true,null, consumerObject);

		}
	}

}

