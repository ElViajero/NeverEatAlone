package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IContactDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.logger.helper.LoggerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.INotificationManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * 
 * This class manages all contact related requests. The operations handled by
 * this class include adding/removing contacts etc.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */

public class ContactManagementRequestHandler implements
		IManagementRequestHandler, INotificationManagementRequestHandler {

	@Inject
	IContactDBRequestHandler iContactDBManagerObject;
	@Inject
	IReflectionManager iReflectionManagerObject;
	@Inject
	INotificationManager iNotificationManagerObject;

	/**
	 * This method handles requests to add a contact.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> add(Map<String, String[]> request) {

		System.out.println("Reaching AddContactRequest");
		List<Map<String, String>> result = iContactDBManagerObject.add(request);
		List<Map<String, String>> notificationMapList = new ArrayList<Map<String, String>>(
				result);
		System.out.println("FINISHED ADD EXECUTION.");
		LoggerHelper.printresultMap(result);

		if (result.get(0).get("Status").equals("Success")) {
			System.out.println("Successful operation.");
			notificationMapList.remove(0);
			iNotificationManagerObject.pushNotification(notificationMapList,
					Arrays.asList(request.get("recipientList")));
		}

		return result;

	}

	/**
	 * This method handles requests to fetch all contacts
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> getAll(Map<String, String[]> request) {

		System.out.println("Reaching GetAllContactRequest");
		return iContactDBManagerObject.getAll(request);
	}

	/**
	 * This method fetches nearby contacts.
	 * 
	 * @author tejasvamsingh
	 */

	public List<Map<String, String>> getNearby(Map<String, String[]> request) {
		return iContactDBManagerObject.getNearby(request);
	}

	/**
	 * This method handles requests to delete a contact.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> delete(Map<String, String[]> request) {

		System.out.println("Reaching DeleteContactRequest");

		List<Map<String, String>> result = iContactDBManagerObject
				.delete(request);
		List<Map<String, String>> notificationMapList = new ArrayList<Map<String, String>>(
				result);
		System.out.println("FINISHED DELETE EXECUTION.");
		LoggerHelper.printresultMap(result);

		// NOTIFY CONTACTS HERE.
		/*
		 * if (result.get(0).get("Status").equals("Success")) {
		 * System.out.println("Successful operation.");
		 * notificationMapList.remove(0);
		 * iNotificationManagerObject.pushNotification(notificationMapList,
		 * Arrays.asList(request.get("recipientList"))); }
		 */

		return result;

	}

	/**
	 * This method handles requests to update a contact's information.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> update(Map<String, String[]> request) {

		System.out.println("Reaching UpdateContactRequest");
		return iContactDBManagerObject.update(request);

	}

	private List<Map<String, String>> fetchRequests(
			Map<String, String[]> request) {
		System.out.println("Reaching fetchContact in ContactMRH");
		return iContactDBManagerObject.fetchRequests(request);
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {
		System.out.println("Inside HandleManagementRequest");
		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);

	}

	@Override
	public List<Map<String, String>> accept(Map<String, String[]> request) {
		System.out.println("inside accept in ContactMRH");
		LoggerHelper.printrequestMap(request);
		List<Map<String, String>> result = add(request);
		/*
		 * List<Map<String, String>> notificationMapList = new
		 * ArrayList<Map<String,String>>(result);
		 * 
		 * if(result.get(0).get("Status").equals("Success")){
		 * System.out.println("Successful operation.");
		 * notificationMapList.remove(0);
		 * iNotificationManagerObject.pushNotification( notificationMapList,
		 * Arrays.asList(request.get("recipientList"))); }
		 */
		return result;
	}

	@Override
	public List<Map<String, String>> reject(Map<String, String[]> request) {
		System.out.println("inside reject in ContactMRH");
		LoggerHelper.printrequestMap(request);
		List<Map<String, String>> result = delete(request);
		return result;
	}

}
