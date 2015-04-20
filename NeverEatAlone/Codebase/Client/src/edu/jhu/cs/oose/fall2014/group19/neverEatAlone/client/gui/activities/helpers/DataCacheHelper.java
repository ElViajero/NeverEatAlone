package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.LocationProperties;

/**
 * 
 * @author tejasvamsingh
 *
 */
public class DataCacheHelper {

	private static IActivityProperties iActivityPropertiesObject;
	private static boolean isAccepted;
	private static Map<String, ArrayAdapter<?>> adapterCache;
	private static LocationProperties cachedLocation;

	private static Map<String, List<?>> cachedListDataMap;

	private static boolean genericFlag;

	public static IActivityProperties getIActivityPropertiesObject() {
		return iActivityPropertiesObject;
	}

	public static void setIActivityPropertiesObject(
			IActivityProperties notificationPropertiesObject) {
		DataCacheHelper.iActivityPropertiesObject = notificationPropertiesObject;
	}

	public static boolean isAccepted() {
		return isAccepted;
	}

	public static void setAccepted(boolean isAccepted) {
		DataCacheHelper.isAccepted = isAccepted;
	}

	public static void registerAdapter(ArrayAdapter<?> adapter, String key) {
		if (adapterCache == null)
			adapterCache = new HashMap<String, ArrayAdapter<?>>();
		adapterCache.put(key, adapter);
	}

	public static ArrayAdapter<?> getAdapter(String key) {
		if (adapterCache != null && adapterCache.containsKey(key))
			return adapterCache.get(key);
		return null;
	}

	public static void setCachedLocation(LocationProperties location) {
		cachedLocation = location;
	}

	public static LocationProperties getCachedLocation() {
		if (cachedLocation == null)
			cachedLocation = AccountProperties.getUserAccountInstance()
					.getLocationProperties();
		return cachedLocation;
	}

	public static boolean getGenericFlag() {
		return genericFlag;
	}

	public static void setGenericFlag(boolean genericFlag) {
		DataCacheHelper.genericFlag = genericFlag;
	}

	public static void cacheResults(String key, List<?> list) {
		if (cachedListDataMap == null)
			cachedListDataMap = new HashMap<String, List<?>>();
		cachedListDataMap.put(key, list);
	}

	public static List<?> getCachedResult(String key) {
		return cachedListDataMap.get(key);
	}

}
