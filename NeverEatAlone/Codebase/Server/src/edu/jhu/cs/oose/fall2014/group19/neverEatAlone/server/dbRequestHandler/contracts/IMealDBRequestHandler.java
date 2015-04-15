package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * 
 * Interface for notification database query management services. Accessible to
 * the management request handler layers.
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface IMealDBRequestHandler {

	/**
	 * Method to create a meal notification.
	 * 
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> CreateMealNotification(
			Map<String, String[]> request);

	public List<Map<String, String>> fetchNotifications(
			Map<String, String[]> request);

	public List<Map<String, String>> fetchPosts(Map<String, String[]> request);

	public List<Map<String, String>> acceptMealNotification(
			Map<String, String[]> request);

	public List<Map<String, String>> rejectMealNotification(
			Map<String, String[]> request);

	public List<Map<String, String>> fetchAcceptedNotifications(
			Map<String, String[]> request);

	public List<Map<String, String>> getAttendingContacts(
			Map<String, String[]> request);

	public List<Map<String, String>> undoAccept(Map<String, String[]> request);

	public List<Map<String, String>> updateNotification(
			Map<String, String[]> request);

	public List<Map<String, String>> getRecipients(Map<String, String[]> request);

}
