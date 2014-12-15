package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * 
 * Interface for notification management services.
 * Accessible to the management request handler layers. 
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface INotificationDBRequestHandler {

	/**
	 * Method to create a meal notification.
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> CreateMealNotification(Map<String,String[]> request);

	public List<Map<String,String>> fetchNotifications(Map<String,String[]> request);

}
