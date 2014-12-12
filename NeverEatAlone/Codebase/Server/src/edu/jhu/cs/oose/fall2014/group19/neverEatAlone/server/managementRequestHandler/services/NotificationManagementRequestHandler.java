package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.INotificationDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.ManagemenRequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;


/**
 * 
 * This class manages all notification related requests.
 * 
 * @author tejasvamsingh
 *
 */

public class NotificationManagementRequestHandler implements IManagementRequestHandler {

	@Inject INotificationManager INotificationManagerObject;
	@Inject INotificationDBManager INotificationDBManagerObject;

	/**
	 * Method that handles meal post and notification requests.
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> Meal(Map<String,String[]> request){

		System.out.println("reached MealNotificationRequest");


		//commit to the DB.
		List<Map<String, String>> result = 
				INotificationDBManagerObject.CreateMealNotification(request);

		List<String> recipientList = Arrays.asList(request.get("recipientList"));

		List<Map<String, String>> notificationMapList = 
				new ArrayList<Map<String,String>>(result);
		notificationMapList.remove(0);

		INotificationManagerObject.PushNotification(notificationMapList , recipientList );
		//change this.
		return result;
	}

	@Override
	public List<Map<String, String>> HandleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return ManagemenRequestHandlerHelper.invokeMethod(this,
				request.get("requestType")[0], request);

	}


}
