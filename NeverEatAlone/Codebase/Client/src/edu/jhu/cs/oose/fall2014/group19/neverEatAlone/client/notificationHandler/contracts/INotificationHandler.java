package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.contracts;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.TabHostActivity;

/**
 * Interface that exposes services offered by the notification handler.
 * @author tejasvamsingh
 *
 */
public interface INotificationHandler {
	/**
	 * Method to initialize the notification framework.
	 * @author tejasvamsingh
	 * @param tabHostactivity
	 * @param username
	 */
	public void init(TabHostActivity tabHostactivity,String username);
	/**
	 * Method to deinitialize, clean up resources and 
	 * perform graceful shutdown of the notifications framework.
	 * @author tejasvamsingh
	 */
	public void cleanUp();

}
