package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.List;

import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

public class DataCacheHelper {

	private static List<NotificationProperties> MealNotificationCache;
	private static ArrayAdapter<NotificationProperties> mealNotificationAdaapterInstance; 

	public static List<NotificationProperties> getMealNotificationCache() {
		return MealNotificationCache;
	}

	public static void setMealNotificationCache(
			List<NotificationProperties> notificationCache) {
		MealNotificationCache = notificationCache;
		mealNotificationAdaapterInstance.addAll(MealNotificationCache);
	}

	public static void registerMealNotificationAdapterInstance(
			ArrayAdapter<NotificationProperties> invitesAdapter){
		mealNotificationAdaapterInstance = invitesAdapter;
	}





}
