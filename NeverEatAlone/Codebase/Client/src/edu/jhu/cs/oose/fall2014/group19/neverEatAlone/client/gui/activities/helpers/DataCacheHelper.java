package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.List;
import java.util.Map;

import android.widget.ArrayAdapter;

public class DataCacheHelper {

	private static List<Map<String,String>> MealNotificationCache;
	private static ArrayAdapter<Map<String, String>> mealNotificationAdaapterInstance; 

	public static List<Map<String, String>> getMealNotificationCache() {
		return MealNotificationCache;
	}

	public static void setMealNotificationCache(
			List<Map<String, String>> notificationCache) {
		MealNotificationCache = notificationCache;
		mealNotificationAdaapterInstance.addAll(MealNotificationCache);
	}

	public static void registerMealNotificationAdapterInstance(ArrayAdapter<Map<String, String>> invitesAdapter){
		mealNotificationAdaapterInstance = invitesAdapter;
	}





}
