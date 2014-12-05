package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;

public class NotificationProperties implements IActivityProperties {

	private String poster;
	private String notificationID;

	public String getPoster() {
		return poster;
	}



	public void setPoster(String poster) {
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

		poster = map.get("poster");
		notificationID = map.get("postID");
		notificationType = map.get("postType");
		if(notificationType.equals("MEAL"))
			notificationData= new MealProperties(map);
	}



	@Override
	public Map<String, Object> toMap() {
		//TODO
		return null;
	}

}
