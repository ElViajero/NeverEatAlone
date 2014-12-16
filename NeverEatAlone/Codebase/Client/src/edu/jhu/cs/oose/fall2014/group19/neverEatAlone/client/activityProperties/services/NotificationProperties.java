package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.helpers.ActivityPropertiesHelper;

public class NotificationProperties implements IActivityProperties {

	private String poster;
	private String notificationID;
	private boolean isAccepted;
	String notificationType;
	IActivityProperties notificationData;

	public String getPoster() {
		return poster;
	}


	public void setPoster(String poster) {
		this.poster = poster;
	}


	public boolean isAccepted() {
		return isAccepted;
	}


	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((notificationID == null) ? 0 : notificationID.hashCode());
		result = prime * result + ((poster == null) ? 0 : poster.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationProperties other = (NotificationProperties) obj;
		if (notificationID == null) {
			if (other.notificationID != null)
				return false;
		} else if (!notificationID.equals(other.notificationID))
			return false;
		if (poster == null) {
			if (other.poster != null)
				return false;
		} else if (!poster.equals(other.poster))
			return false;
		return true;
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
