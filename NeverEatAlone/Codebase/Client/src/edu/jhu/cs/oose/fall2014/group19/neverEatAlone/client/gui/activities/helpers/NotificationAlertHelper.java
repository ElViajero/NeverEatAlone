package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;

public class NotificationAlertHelper {

	static Map<String, View> viewMap;
	static Map<String, Boolean> isAlertEnabledMap;

	public static void registerNotificationAlertView(String key, View view) {
		if (viewMap == null)
			viewMap = new HashMap<String, View>();
		if (isAlertEnabledMap == null)
			isAlertEnabledMap = new HashMap<String, Boolean>();
		viewMap.put(key, view);
	}

	public static void enableNotificationAlert(String key) {
		View v = viewMap.get(key);
		TextView tv = (TextView) v.findViewById(R.id.tabsText);
		tv.setBackgroundColor(Color.RED);
		isAlertEnabledMap.put(key, true);
	}

	public static void disableNotificationAlert(String key) {
		View v = viewMap.get(key);
		TextView tv = (TextView) v.findViewById(R.id.tabsText);
		tv.setBackgroundColor(Color.TRANSPARENT);
		isAlertEnabledMap.put(key, false);
	}

	public static boolean isAlertEnabled(String key) {
		if (isAlertEnabledMap.containsKey(key))
			return isAlertEnabledMap.get(key);
		return false;
	}

}
