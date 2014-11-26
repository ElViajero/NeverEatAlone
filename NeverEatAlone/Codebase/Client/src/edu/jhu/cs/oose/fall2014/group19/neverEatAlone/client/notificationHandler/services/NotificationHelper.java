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


	/**
	 * This method is used to start the notifications framework.
	 * It is called via activity classes.
	 * @author tejasvamsingh
	 * @param tabHostactivity
	 * @param username
	 */
	public static void init(
			TabHostActivity tabHostactivity,
			String username){

		if(iNotificationHandlerInstance==null){
			iNotificationHandlerInstance = new NotificationHandler();
		}
		iNotificationHandlerInstance.init(tabHostactivity, username);
	}

	/**
	 * This method is used to clean up resources related to the notifcations
	 * framework and is responsible for graceful shutdown.
	 * It is called directly by the activity classes.
	 * @author tejasvamsingh
	 * 
	 */
	public static void cleanUp(){
		iNotificationHandlerInstance.cleanUp();
	}

}
