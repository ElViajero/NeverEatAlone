package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.contracts.INotificationHandler;

/**
 * This class is responsible for the initialization and clean up
 * of the notifcations framework and related resources.
 * @author tejasvamsingh
 *
 */
public class NotificationHandler implements INotificationHandler {

	AsyncTask<String, List<Map<String, String>>, 
	List<Map<String, String>>> notificationExecutorTask;

	/**
	 * This method is used to initialize the notifications framework.
	 * @author tejasvamsingh
	 */
	@Override
	public void init(TabHostActivity tabHostactivity,String username) {
		// start the notification framework.
		notificationExecutorTask= 
				new NotificationExecutor(tabHostactivity,username).
				executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);

	}

	/**
	 * This method is used to clean up resources related to the notifications framework.
	 * @author tejasvamsingh
	 */
	@Override
	public void cleanUp() {
		NotificationExecutor.cleanUp();
		notificationExecutorTask.cancel(true);
	}

}
