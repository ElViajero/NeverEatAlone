package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.helpers.ActivityPropertiesHelper;

public class NotificationProperties implements IActivityProperties {

	private String poster;
	private String notificationID;
	private boolean isAccepted;

	public String getposter() {
		return poster;
	}


	public void setposter(String poster) {
		this.poster = poster;
	}



	public String getNotificationID() {
		return notificationID;
	}



	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}



	public IActivityProperties getNotificationData() {
		return notificationData;
	}



	public void setNotificationData(IActivityProperties notificationData) {
		this.notificationData = notificationData;
	}



	String notificationType;
	IActivityProperties notificationData;


	public NotificationProperties(Map<String,String> map){

		System.out.println("the map is " + map);

		isAccepted=false;
		poster = map.get("poster");
		notificationID = map.get("postID");
		notificationType = map.get("postType");
		try {
			notificationData = (IActivityProperties) 
					ActivityPropertiesHelper.getPropertyTypes(notificationType).newInstance();
			System.out.println("THE CRAP IS "+ notificationData);
			notificationData.fromMap(map);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	@Override
	public Map<String, Object> toMap() {
		//TODO
		return null;
	}



	@Override
	public void fromMap(Map<String, String> map) {
		// TODO Auto-generated method stub

	}

}
