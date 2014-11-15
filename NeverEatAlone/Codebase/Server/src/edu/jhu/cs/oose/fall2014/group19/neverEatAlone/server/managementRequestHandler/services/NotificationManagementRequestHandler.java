package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;

@Stateless
public class NotificationManagementRequestHandler {

	@Inject INotificationManager INotificationManagerObject;
	
	public List<Map<String,String>> MealNotificationRequest(Map<String,String[]> request){
		
		System.out.println("reached MealNotificationRequest");
		System.out.println("Recpient 1 is " + request.get("Recipient")[0]);
		//System.out.println("Recpient 2 is " + request.get("Recipient")[1]);
				
		
		//for now just create stubs.
		Map<String, String> notificationMap = new HashMap<String,String>();
		notificationMap.put("Message", request.get("Message")[0]);		
		List<Map<String, String>> notificationList = new ArrayList<Map<String,String>>();
		notificationList.add(notificationMap);
		
		
		List<Map<String, String>> recipientList = new ArrayList<>();
		Map<String, String> recipientMap = new HashMap<String,String>();
		recipientMap.put("Username",request.get("Recipient")[0]);
		recipientList.add(recipientMap);
		
		INotificationManagerObject.PushNotification(notificationList , recipientList );
		//change this.
		return recipientList;
	}
	
	
}
