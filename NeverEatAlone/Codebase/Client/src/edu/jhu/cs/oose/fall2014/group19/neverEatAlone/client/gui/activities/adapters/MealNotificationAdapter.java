package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

/**
 * 
 * @author Hai Tang
 * This class defines the MealNotificationAdapter which takes the List<Map<String,String>> data model
 * from InvitesActivity.java to set up the view for GUI.
 *
 */
public class MealNotificationAdapter extends ArrayAdapter<NotificationProperties> {

	private LayoutInflater inflater;
	private TextView poster;
	private TextView startTime;
	private TextView endTime;
	private TextView location;
	private Activity activity;

	private List<NotificationProperties> MealNotifications;

	/**
	 * Constructor to initialize the GUI meal Notifications
	 * handler.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param activity
	 * @param mealNotifications
	 */
	public MealNotificationAdapter(Activity activity, List<NotificationProperties> mealNotifications) {

		super(activity, R.layout.row_meal_notification_layout, mealNotifications);

		activity = activity;
		inflater = activity.getWindow().getLayoutInflater();

		MealNotifications = mealNotifications;
		System.out.println("Reached contructor : "+ MealNotifications);
	}


	/**
	 * Udpdate the view based on the notifications List.
	 * @author tejasvamsingh
	 * @author Hai Tang
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		inflater = activity.getLayoutInflater();
		view = inflater.inflate(R.layout.row_meal_notification_layout, null);

		poster = (TextView) view.findViewById(R.id.textView_meal_notification_poster);
		startTime = (TextView) view.findViewById(R.id.textView_meal_notification_startTime);
		location = (TextView) view.findViewById(R.id.textView_meal_notification_location);

		MealProperties mealProperties = 
				(MealProperties) MealNotifications.get(position).getNotificationData();		

		poster.setText(MealNotifications.get(position).getposter());
		startTime.setText(mealProperties.getStartDateAndTimeProperties().toString());
		location.setText(mealProperties.getlocation());

		return view;
	}




}
