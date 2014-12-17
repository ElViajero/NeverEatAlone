package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

public class DataCacheHelper {

	private static NotificationProperties notificationPropertiesObject;

	public static NotificationProperties getNotificationPropertiesObject() {
		return notificationPropertiesObject;
	}

	public static void setNotificationPropertiesObject(
			NotificationProperties notificationPropertiesObject) {
		DataCacheHelper.notificationPropertiesObject = notificationPropertiesObject;
	}




}
