package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * @author tejasvamsingh
 * 
 *         This class defines the MealNotificationAdapter which takes the
 *         List<Map<String,String>> data model from InvitesActivity.java to set
 *         up the view for GUI.
 *
 */
public class MealPostAdapter extends ArrayAdapter<IActivityProperties> {

	private LayoutInflater inflater;
	private TextView startTime;
	private TextView location;
	private Activity activity;
	private TextView status;

	private List<IActivityProperties> MealNotifications;

	/**
	 * Constructor to initialize the GUI meal Post handler.
	 * 
	 * @author tejasvamsingh
	 * @param activity
	 * @param mealNotifications
	 */
	public MealPostAdapter(Activity activity,
			List<IActivityProperties> mealNotifications) {

		super(activity, R.layout.row_meal_notification_layout,
				mealNotifications);

		this.activity = activity;
		inflater = activity.getWindow().getLayoutInflater();

		MealNotifications = mealNotifications;
	}

	/**
	 * Udpdate the view based on the notifications List.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		inflater = activity.getLayoutInflater();
		view = inflater.inflate(R.layout.row_meal_post_layout, null);

		startTime = (TextView) view
				.findViewById(R.id.textView_meal_notification_startTime);
		location = (TextView) view
				.findViewById(R.id.textView_meal_notification_location);

		status = (TextView) view.findViewById(R.id.textView_meal_post_status);

		// Button closeButton = (Button) view
		// .findViewById(R.id.myPosts_button_close);
		Button detailsButton = (Button) view
				.findViewById(R.id.myPosts_button_detail);

		// closeButton.setVisibility(android.view.View.GONE);
		detailsButton.setVisibility(android.view.View.GONE);

		// closeButton.setVisibility(View.INVISIBLE);
		detailsButton.setVisibility(View.INVISIBLE);

		PostProperties post = (PostProperties) MealNotifications.get(position);

		String mealpostData = post.getPostData();

		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> mealPropertiesMap = GsonHelper.getGsoninstance()
				.fromJson(mealpostData, stringStringMap);

		MealProperties mealProperties = new MealProperties(mealPropertiesMap);

		startTime.setText(mealProperties.getStartDateAndTimeProperties()
				.toString());
		location.setText(mealProperties.getlocation());

		status.setText(post.getPostStatus());
		status.setBackgroundColor(Color.BLACK);
		if (post.getPostStatus().equalsIgnoreCase("OPEN"))
			status.setTextColor(Color.GREEN);
		else if (post.getPostStatus().equalsIgnoreCase("CLOSED"))
			status.setTextColor(Color.GRAY);
		else if (post.getPostStatus().equalsIgnoreCase("CANCELLED"))
			status.setTextColor(Color.RED);

		Spinner postStatusSpinner = (Spinner) view
				.findViewById(R.id.spinner_meal_post_status);
		postStatusSpinner.setVisibility(android.view.View.INVISIBLE);

		return view;
	}

}
