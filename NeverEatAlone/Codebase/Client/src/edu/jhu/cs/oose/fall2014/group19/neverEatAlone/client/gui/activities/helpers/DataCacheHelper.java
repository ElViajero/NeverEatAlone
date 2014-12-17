package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;

public class DataCacheHelper {

	private static IActivityProperties iActivityPropertiesObject;

	public static IActivityProperties getIActivityPropertiesObject() {
		return iActivityPropertiesObject;
	}

	public static void setIActivityPropertiesObject(
			IActivityProperties notificationPropertiesObject) {
		DataCacheHelper.iActivityPropertiesObject = notificationPropertiesObject;
	}




}
