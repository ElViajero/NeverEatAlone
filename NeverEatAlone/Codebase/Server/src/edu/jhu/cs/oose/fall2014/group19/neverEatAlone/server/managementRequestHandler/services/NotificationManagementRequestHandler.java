package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.INotificationDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;


/**
 * 
 * This class manages all notification related requests.
 * 
 * @author tejasvamsingh
 *
 */

public class NotificationManagementRequestHandler implements IManagementRequestHandler {

	@Inject INotificationManager iNotificationManagerObject;
	@Inject INotificationDBManager iNotificationDBManagerObject;
	@Inject IReflectionManager iReflectionManagerObject;

	/**
	 * Method that handles meal post and notification requests.
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> meal(Map<String,String[]> request){

		System.out.println("reached MealNotificationRequest");


		//commit to the DB.
		List<Map<String, String>> result = 
				iNotificationDBManagerObject.CreateMealNotification(request);

		List<String> recipientList = Arrays.asList(request.get("recipientList"));

		List<Map<String, String>> notificationMapList = 
				new ArrayList<Map<String,String>>(result);
		notificationMapList.remove(0);

		iNotificationManagerObject.pushNotification(notificationMapList , recipientList );
		//change this.
		return result;
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);
	}


}
