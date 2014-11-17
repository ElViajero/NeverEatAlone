package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * 
 * @author Hai Tang
 *
 */
public class MealNotificationAdapter extends ArrayAdapter<Map<String,String>> {

	private LayoutInflater Inflater;
	private TextView Poster;
	private TextView StartTime;
	private TextView EndTime;
	private TextView Location;
	private Activity Activity;

	private List<Map<String,String>> MealNotifications;

	/**
	 * Constructor to initialize the GUI meal Notifications
	 * handler.
	 * 
	 * @author tejasvamsingh
	 * @param activity
	 * @param mealNotifications
	 */
	public MealNotificationAdapter(Activity activity, List<Map<String,String>> mealNotifications) {

		super(activity, R.layout.row_meal_notification_layout, mealNotifications);

		Activity = activity;
		Inflater = activity.getWindow().getLayoutInflater();
		Activity = activity;
		MealNotifications = mealNotifications;
		System.out.println("Reached contructor : "+ MealNotifications);
	}


	/**
	 * Udpdate the view based on the notifications List.
	 * @author tejasvamsingh
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		Inflater = Activity.getLayoutInflater();
		view = Inflater.inflate(R.layout.row_meal_notification_layout, null);

		Poster = (TextView) view.findViewById(R.id.textView_meal_notification_poster);
		StartTime = (TextView) view.findViewById(R.id.textView_meal_notification_starttime);
		Location = (TextView) view.findViewById(R.id.textView_meal_notification_location);
		Poster.setText(MealNotifications.get(position).get("Poster"));
		StartTime.setText(MealNotifications.get(position).get("StartHour"));
		Location.setText(MealNotifications.get(position).get("Location"));

		return view;
	}




}
