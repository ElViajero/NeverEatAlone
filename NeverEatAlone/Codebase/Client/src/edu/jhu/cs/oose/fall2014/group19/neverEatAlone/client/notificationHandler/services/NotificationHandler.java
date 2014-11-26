package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services;

import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.contracts.INotificationHandler;

public class NotificationHandler implements INotificationHandler {

	AsyncTask<String, List<Map<String, String>>, 
	List<Map<String, String>>> notificationExecutorTask;

	@Override
	public void init(TabHostActivity tabHostactivity,String username) {
		// start the notification framework.
		notificationExecutorTask= 
				new NotificationExecutor(tabHostactivity,username).
				executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);

	}

	@Override
	public void cleanUp() {
		NotificationExecutor.cleanUp();
		notificationExecutorTask.cancel(true);
	}

}
