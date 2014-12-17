package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IMealDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.logger.helper.LoggerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.INotificationManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;


/**
 * 
 * This class manages all notification related requests.
 * 
 * @author tejasvamsingh
 *
 */

public class MealManagementRequestHandler implements 
IManagementRequestHandler,INotificationManagementRequestHandler{

	@Inject INotificationManager iNotificationManagerObject;
	@Inject IMealDBRequestHandler iMealDBRequestHandlerObject;
	@Inject IReflectionManager iReflectionManagerObject;

	/**
	 * Method that handles meal post and notification requests.
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> create(Map<String,String[]> request){

		System.out.println("reached MealNotificationRequest");


		//commit to the DB.
		List<Map<String, String>> result = 
				iMealDBRequestHandlerObject.CreateMealNotification(request);

		List<String> recipientList = Arrays.asList(request.get("recipientList"));

		List<Map<String, String>> notificationMapList = 
				new ArrayList<Map<String,String>>(result);
		notificationMapList.remove(0);

		iNotificationManagerObject.pushNotification(notificationMapList , recipientList );

		//change this.
		return result;
	}


	private List<Map<String,String>> fetch(Map<String,String[]> request){

		System.out.println("Reached fetch in NotificationManagementRequestHandler.");
		return iMealDBRequestHandlerObject.fetchNotifications(request);

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
		System.out.println("reached accept in MealMRH");
		LoggerHelper.printrequestMap(request);

		List<Map<String, String>> result=
				iMealDBRequestHandlerObject.acceptMealNotifications(request);

		List<Map<String, String>> notificationMapList =
				new ArrayList<Map<String,String>>(result);
		LoggerHelper.printresultMap(result);

		if(result.get(0).get("Status").equals("Success")){
			System.out.println("Successful operation.");
			notificationMapList.remove(0);
			iNotificationManagerObject.pushNotification(
					notificationMapList, Arrays.asList(request.get("recipientList")));
		}
		return result;
	}


	@Override
	public List<Map<String, String>> reject(Map<String, String[]> request) {
		// TODO Auto-generated method stub
		return null;
	}


}
