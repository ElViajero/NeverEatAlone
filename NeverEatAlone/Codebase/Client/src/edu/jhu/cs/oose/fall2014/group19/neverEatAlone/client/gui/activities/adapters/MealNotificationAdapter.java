package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.MealNotificationModel;
/**
 * 
 * @author Hai Tang
 *
 */
public class MealNotificationAdapter extends ArrayAdapter<MealNotificationModel> {
	private LayoutInflater inflater;
	private TextView postername;
	private TextView starttime;
	private TextView endtime;
	private TextView resturant;
	private Activity activity;
	private List<MealNotificationModel> mealnotifications;
	
	public MealNotificationAdapter(Activity activity, List<MealNotificationModel> mealnotifications) {
		super(activity, R.layout.row_meal_notification_layout, mealnotifications);
		inflater = activity.getWindow().getLayoutInflater();
		this.activity = activity;
		this.mealnotifications = mealnotifications;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	View view = null;
    	inflater = activity.getLayoutInflater();
    	view = inflater.inflate(R.layout.row_meal_notification_layout, null);
    	
    	postername = (TextView) view.findViewById(R.id.textView_meal_notification_poster);
    	starttime = (TextView) view.findViewById(R.id.textView_meal_notification_starttime);
    	resturant = (TextView) view.findViewById(R.id.textView_meal_notification_location);
    	postername.setText(mealnotifications.get(position).getPosterName());
    	starttime.setText(mealnotifications.get(position).getStartTime());
    	resturant.setText(mealnotifications.get(position).getResturant());
    	
		return view;
	}
	
}
