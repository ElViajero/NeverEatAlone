package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListFragment;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;

/**
 * 
 * @author tejasvamsingh
 *
 */
public class NotificationAndPostCacheHelper {

	private static Map<String, ArrayAdapter<?>> adapterMap;
	private static Map<String, List<IActivityProperties>> adapterDataMap;
	private static Map<String, Boolean> isServerFetchRequiredMap;
	private static Map<String, IActivityProperties> myPostMap;
	private static Map<String, List<String>> attendingMap;
	private static Map<String, ListFragment> activityMap;
	public static boolean recievedNotification;

	public static void setNotificationCache(
			Collection<NotificationProperties> notificationCache) {
		System.out.println("Inside setNotificationCache");

		initMaps();
		clearAdapters();

		for (NotificationProperties notification : notificationCache) {

			if (isResponse(notification)) {
				handleResponse(notification);
				continue;
			}

			String key = notification.getNotificationType();

			if (!adapterDataMap.containsKey(key))
				adapterDataMap.put(key, new ArrayList<IActivityProperties>());

			String status = notification.getNotificationStatus();
			if (status.equalsIgnoreCase("CLOSED")
					|| status.equalsIgnoreCase("CANCELLED")) {

				for (IActivityProperties a : adapterDataMap.get(key)) {
					NotificationProperties n = (NotificationProperties) a;
					if (n.getNotificationID().equals(
							notification.getNotificationID()))
						adapterDataMap.get(key).remove(a);
				}
			} else
				adapterDataMap.get(key).add(notification);

			DataCacheHelper.setGenericFlag(true);
			refreshActivity("acceptedMeal");

			isServerFetchRequiredMap.put(key, true);
			NotificationAlertHelper.enableNotificationAlert(key);

		}

		System.out.println("adapter maps : " + adapterDataMap);
		populateAdapters();
		notificationCache.clear();
	}

	private static void populateAdapters() {

		for (Map.Entry<String, ArrayAdapter<?>> entry : adapterMap.entrySet()) {
			ArrayAdapter<IActivityProperties> a = (ArrayAdapter<IActivityProperties>) entry
					.getValue();
			a.addAll(adapterDataMap.get(entry.getKey()));
		}

	}

	private static void clearAdapters() {
		for (Map.Entry<String, ArrayAdapter<?>> entry : adapterMap.entrySet()) {
			ArrayAdapter<IActivityProperties> a = (ArrayAdapter<IActivityProperties>) entry
					.getValue();
			entry.getValue().clear();
			adapterDataMap.get(entry.getKey()).clear();
		}
	}

	private static void initMaps() {
		if (adapterMap == null) {
			adapterMap = new HashMap<String, ArrayAdapter<?>>();
			adapterDataMap = new HashMap<String, List<IActivityProperties>>();
			isServerFetchRequiredMap = new HashMap<String, Boolean>();
			myPostMap = new HashMap<String, IActivityProperties>();
			attendingMap = new HashMap<String, List<String>>();
		}
	}

	public static void registerAdapterInstance(ArrayAdapter<?> adapter,
			String adapterType) {
		initMaps();
		adapterMap.put(adapterType, adapter);
		if (!adapterDataMap.containsKey(adapterType))
			adapterDataMap.put(adapterType,
					new ArrayList<IActivityProperties>());
		populateAdapter(adapterType);

	}

	public static boolean isServerFetchRequired(String key) {
		initMaps();
		return isServerFetchRequiredMap.containsKey(key) ? isServerFetchRequiredMap
				.get(key) : true;
	}

	public static void setServerFetchRequired(String key, boolean value) {
		initMaps();
		isServerFetchRequiredMap.put(key, value);
	}

	public static void cleanUp() {
		adapterMap = null;
		adapterDataMap = null;
		isServerFetchRequiredMap = null;
	}

	public static void addPost(IActivityProperties post, String key) {

		if (!adapterDataMap.containsKey(key))
			adapterDataMap.put(key, new ArrayList<IActivityProperties>());
		if (!attendingMap.containsKey(key))
			attendingMap.put(key, new ArrayList<String>());

		adapterDataMap.get(key).add(post);
		PostProperties postPropertiesObject = (PostProperties) post;
		myPostMap.put(postPropertiesObject.getPostID(), post);

		populateAdapter(key);
	}

	public static void populateAdapter(String key) {
		if (adapterMap.containsKey(key)) {
			adapterMap.get(key).clear();
			ArrayAdapter<IActivityProperties> a = (ArrayAdapter<IActivityProperties>) adapterMap
					.get(key);
			a.addAll(adapterDataMap.get(key));
			a.notifyDataSetChanged();
		}
	}

	private static void handleResponse(NotificationProperties notification) {

		MessageToasterHelper.toastMessage(notification.getPoster()
				+ " has accepted your " + notification.getNotificationType()
				+ " invitation.");

		isServerFetchRequiredMap.put(notification.getNotificationID(), true);
		isServerFetchRequiredMap.put(notification.getNotificationType(), true);
		isServerFetchRequiredMap.put(notification.getNotificationType()
				+ "Post", true);
		refreshActivity(notification.getNotificationType());

	}

	private static void refreshActivity(String key) {

		if (activityMap != null && activityMap.containsKey(key))
			activityMap.get(key).onResume();

	}

	private static boolean isResponse(NotificationProperties notification) {
		return myPostMap.containsKey(notification.getNotificationID());
	}

	public static void clearAdapterDataMap(String key) {
		if (adapterDataMap != null && adapterDataMap.containsKey(key))
			adapterDataMap.get(key).clear();
	}

	public static void registerActivity(String key, ListFragment activity) {
		if (activityMap == null)
			activityMap = new HashMap<String, ListFragment>();
		activityMap.put(key, activity);
	}

}
