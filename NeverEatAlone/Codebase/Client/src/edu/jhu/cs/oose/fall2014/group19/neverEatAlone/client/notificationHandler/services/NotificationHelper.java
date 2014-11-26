package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.contracts.INotificationHandler;

/**
 * Helper class for notifications.
 * Provides methods to inititalize and cleanup the notification framework.
 * @author tejasvamsingh
 *
 */
public class NotificationHelper {

	static INotificationHandler iNotificationHandlerInstance;


	public static void init(
			TabHostActivity tabHostactivity,
			String username){

		if(iNotificationHandlerInstance==null){
			iNotificationHandlerInstance = new NotificationHandler();
		}
		iNotificationHandlerInstance.init(tabHostactivity, username);
	}

	public static void cleanUp(){
		iNotificationHandlerInstance.cleanUp();
	}

}
