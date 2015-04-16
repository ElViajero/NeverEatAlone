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

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.ConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.TabHostActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * This class handles asynchronous notification fetches from a queue.
 * 
 * @author tejasvamsingh
 *
 */

public class NotificationExecutor extends
		AsyncTask<String, List<Map<String, String>>, List<Map<String, String>>> {

	TabHostActivity activityObject;

	// If it's being used on a thread separate from the UI thread,
	// make it static if it has to be global variable.

	static String IPAddress;
	static Channel ChannelObject;
	static String username;
	static String Tag;
	static Gson gsonObject;
	static QueueingConsumer consumerObject;
	static ConnectionFactory factoryObject;
	static Connection connectionObject;
	static boolean cleanBit;
	static int timeOutWait = 0;
	static boolean isFirstTime = true;

	/**
	 * 
	 * Set up method to be called the first time that the app registers to be
	 * notified.
	 * 
	 * @author tejasvamsingh
	 * @param tabHostActivity
	 */
	public NotificationExecutor(TabHostActivity tabHostActivity, String username) {
		activityObject = tabHostActivity;
		gsonObject = GsonHelper.getGsoninstance();
		this.username = AccountProperties.getUserAccountInstance()
				.getusername();
		cleanBit = false;

		// handling the try/catch stuff here makes sense for now.
		try {
			IPAddress = ConfigurationHelper.getConfigurationInstance()
					.getIPAddress();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The IP in NotificationExecutor is :" + IPAddress);

		System.out.flush();
	}

	public NotificationExecutor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Polling method that monitors queue for messages.
	 * 
	 * @author tejasvamsingh
	 * 
	 */
	@Override
	protected List<Map<String, String>> doInBackground(String... params) {

		// String username = params[0];
		// username=username;
		Type stringStringMap = new TypeToken<List<Map<String, String>>>() {
		}.getType();

		try {
			initConsumptionFramework();

			System.out
					.println(" [*] Waiting for messages. To exit press CTRL+C");
			List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
			while (true) {
				QueueingConsumer.Delivery delivery = consumerObject
						.nextDelivery(3000);
				if (delivery == null)
					break;
				String message = new String(delivery.getBody());
				List<Map<String, String>> currentResultMapList = gsonObject
						.fromJson(message, stringStringMap);
				resultMapList.addAll(currentResultMapList);
				System.out.println(" [x] Received '" + message + "'");
			}

			return resultMapList;

		} catch (IOException e) {
			System.out.println("IOException in NotificationExecutor."
					+ e.getMessage());
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
	 * 
	 * @author tejasvamsingh
	 * @param resultMapList
	 */

	@Override
	protected void onPostExecute(List<Map<String, String>> resultMapList) {
		boolean couldNotConnect = false;
		;
		timeOutWait++;
		// System.out.println("Entering onPostExecute");
		if (resultMapList == null) {
			// MessageToasterHelper.toastMessage("timeout : " + timeOutWait);
			if (timeOutWait == 1000 || isFirstTime) {

				MessageToasterHelper.toastMessage(activityObject,
						"Could not connect to notification server.");
				timeOutWait = 0;
				isFirstTime = false;
				couldNotConnect = true;
			}
			resetConnection();

		}

		else if (!resultMapList.isEmpty()) {
			System.out.println("NotificationMap is not empty.");
			activityObject.UpdateNotificationCache(resultMapList);
		}

		System.out.println("Reaching here regularly");
		if (!cleanBit) {
			// System.out.println("CleanBit is false");
			if (couldNotConnect)
				MessageToasterHelper.toastMessage("attempting reconnect");
			new NotificationExecutor(activityObject, username)
					.executeOnExecutor(THREAD_POOL_EXECUTOR, username);
		}
	}

	public static void cleanUp() {
		cleanBit = true;
		try {
			// ChannelObject.queueDelete(username);
			ChannelObject.basicCancel(username);

			ChannelObject.close();
			connectionObject.close();

		} catch (IOException e) {
			System.out.println("username is :" + username);
			System.out.println("IOException in cleanUp" + e.getMessage());

			ChannelObject = null;
			consumerObject = null;
			factoryObject = null;

			return;
		} catch (NullPointerException e) {
			System.out.println("NullPointer in onPostExecute");
			return;
		} finally {

			ChannelObject = null;
			consumerObject = null;
			factoryObject = null;
		}

	}

	private static void initConsumptionFramework() throws IOException {
		System.out
				.println("Inside initConsumptionFrameowork in NotificationExecutor.");
		if (ChannelObject == null || factoryObject == null
				|| connectionObject == null) {
			try {
				factoryObject = new ConnectionFactory();
				factoryObject.setHost(IPAddress);
				connectionObject = factoryObject.newConnection();
				ChannelObject = connectionObject.createChannel();
				System.out.println("username is : " + username);
				ChannelObject.queueDeclare(username, false, false, false, null);
				consumerObject = new QueueingConsumer(ChannelObject);
				ChannelObject.basicConsume(username, true, username, false,
						true, null, consumerObject);
			} catch (NullPointerException e) {
				return;
			}

		}
	}

	public static void setCleanUpBit(boolean clean) {
		cleanBit = clean;
	}

	private static void resetConnection() {

		ChannelObject = null;
		factoryObject = null;
		connectionObject = null;

	}

}
