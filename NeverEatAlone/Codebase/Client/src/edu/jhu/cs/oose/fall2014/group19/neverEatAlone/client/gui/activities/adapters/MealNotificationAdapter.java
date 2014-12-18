package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

/**
 * @author Hai Tang
 * @author tejasvamsingh
 * 
 * This class defines the MealNotificationAdapter which takes the List<Map<String,String>> data model
 * from InvitesActivity.java to set up the view for GUI.
 *
 */
public class MealNotificationAdapter extends ArrayAdapter<IActivityProperties> {

	private LayoutInflater inflater;
	private TextView poster;
	private TextView startTime;
	private TextView endTime;
	private TextView location;
	private TextView status;
	private Activity activity;

	private List<IActivityProperties> MealNotifications;

	/**
	 * Constructor to initialize the GUI meal Notifications
	 * handler.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param activity
	 * @param mealNotifications
	 */
	public MealNotificationAdapter(Activity activity, 
			List<IActivityProperties> mealNotifications) {

		super(activity, R.layout.row_meal_notification_layout, mealNotifications);

		this.activity = activity;
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
		status = (TextView) view.findViewById(R.id.textView_meal_notification_status);


		NotificationProperties notification = (NotificationProperties) 
				MealNotifications.get(position);

		MealProperties mealProperties =(MealProperties) 
				notification.getNotificationData();		

		poster.setText(notification.getPoster());
		startTime.setText(mealProperties.getStartDateAndTimeProperties().toString());
		location.setText(mealProperties.getlocation());
		status.setText(notification.getNotificationStatus());


		return view;
	}




}
