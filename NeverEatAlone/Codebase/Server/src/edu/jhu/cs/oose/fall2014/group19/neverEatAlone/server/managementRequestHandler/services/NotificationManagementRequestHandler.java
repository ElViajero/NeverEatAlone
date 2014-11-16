package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.INotificationDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;


/**
 * 
 * This class manages all notification related requests.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class NotificationManagementRequestHandler {

	@Inject INotificationManager INotificationManagerObject;
	@Inject INotificationDBManager INotificationDBManagerObject;

	public List<Map<String,String>> MealNotificationRequest(Map<String,String[]> request){

		System.out.println("reached MealNotificationRequest");


		//commit to the DB.
		List<Map<String, String>> result = 
				INotificationDBManagerObject.CreateMealNotification(request);

		List<String> recipientList = Arrays.asList(request.get("Recipient"));


		INotificationManagerObject.PushNotification(result , recipientList );
		//change this.
		return result;
	}


}
