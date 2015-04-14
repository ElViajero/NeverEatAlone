package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * This class is a helper class that toasts messages to screen.
 * 
 * @author tejasvamsingh
 * @author Yueling Loh
 *
 */
public class MessageToasterHelper {

	public static Context contextObject;

	/**
	 * This method toasts messages to the screen.
	 * 
	 * @author tejasvamsingh
	 */
	public static void toastMessage(Activity activity, String message) {
		Toast.makeText(contextObject, message, Toast.LENGTH_SHORT).show();
	}

	public static void toastMessage(String message) {
		if (contextObject == null)
			System.out.println("context object is NULL :  :  : "
					+ contextObject);

		Toast.makeText(contextObject, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * This method toasts messages to the screen, with alternate constructor
	 * 
	 * @author Yueling Loh
	 */
	public static void toastMessage(Activity activity, int stringID) {
		Toast.makeText(contextObject, stringID, Toast.LENGTH_SHORT).show();
	}
}
