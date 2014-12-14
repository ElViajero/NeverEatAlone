package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.List;

import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

public class DataCacheHelper {

	private static List<NotificationProperties> mealNotificationCache;
	private static ArrayAdapter<NotificationProperties> mealNotificationAdaapterInstance; 

	public static List<NotificationProperties> getmealNotificationCache() {
		return mealNotificationCache;
	}

	public static void setmealNotificationCache(
			List<NotificationProperties> notificationCache) {
		System.out.println("Inside this set");
		mealNotificationCache = notificationCache;
		mealNotificationAdaapterInstance.addAll(mealNotificationCache);
	}

	public static void registerMealNotificationAdapterInstance(
			ArrayAdapter<NotificationProperties> invitesAdapter){
		mealNotificationAdaapterInstance = invitesAdapter;
	}



}
