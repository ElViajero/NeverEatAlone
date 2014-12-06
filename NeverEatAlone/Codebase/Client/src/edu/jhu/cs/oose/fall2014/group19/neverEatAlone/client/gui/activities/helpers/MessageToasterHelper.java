package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import android.app.Activity;
import android.widget.Toast;

/**
 * This class is a helper class that
 * toasts messages to screen.
 * @author tejasvamsingh
 * @author Yueling Loh
 *
 */
public class MessageToasterHelper {

	/**
	 * This method toasts messages to the screen.
	 * @author tejasvamsingh
	 */
	public static void toastMessage(Activity activity, String message){
		Toast.makeText(activity.getApplicationContext(),
				message,Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * This method toasts messages to the screen, with alternate constructor
	 * @author Yueling Loh
	 */
	public static void toastMessage(Activity activity, int stringID){
		Toast.makeText(activity.getApplicationContext(),
				stringID,Toast.LENGTH_SHORT).show();
	}
}
