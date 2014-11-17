package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * This class handles controller operations for the invites tab.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 *
 */
public class InvitesActivity extends ListActivity {
	private ArrayAdapter<Map<String,String>> MealNotificationArrayAdapter;
	private TextView tv;
	List<Map<String,String>> NotificationList;

	/**
	 * This constructor is responsible for obtaining 
	 * notifications and updating the GUT.
	 * @author tejasvamsingh
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
		String x= getIntent().getStringExtra("NotificationMapListJSON");
		System.out.println("JSON is  : "+x);
		NotificationList =GsonHelper.GetGsonInstance().
				fromJson(x,stringStringMap);
		initView(savedInstanceState);

	}

	/**
	 * This method updates the GUI.
	 * @author tejasvamsingh
	 * @param savedInstanceState
	 */

	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invites);

		MealNotificationArrayAdapter = 
				new MealNotificationAdapter(this,NotificationList);
		setListAdapter(MealNotificationArrayAdapter);



	}



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent  = new  Intent(this, MealDetailActivity.class);
		startActivity(intent);
	}

	public void OnCreateButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(InvitesActivity.this,
				CreateMealInformationActivity.class);
		InvitesActivity.this.startActivity(intent);
	}
}
