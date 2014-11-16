package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class MealNotificationAdapter extends ArrayAdapter {
	private LayoutInflater inflater;
	public MealNotificationAdapter(Activity activity, String[] items) {
		super(activity, R.layout.row_meal_notification_layout, items);
		inflater = activity.getWindow().getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return inflater.inflate(R.layout.row_meal_notification_layout, parent, false);
	}
	
}
