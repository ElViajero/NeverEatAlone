package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * This class handles controller operations for the invites tab.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 *
 */
public class InvitesActivity extends ListActivity {

	private ArrayAdapter<NotificationProperties> InvitesAdapter;
	List<NotificationProperties> NotificationList;
	String requestID;
	String requestType;
	boolean isCreated;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		NotificationList = new ArrayList<NotificationProperties>();
		initView(savedInstanceState);
		isCreated = false;

		setTitleStyle();

	}

	/**
	 * This method updates the GUI.
	 * 
	 * @author tejasvamsingh
	 * @param savedInstanceState
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invites);

		InvitesAdapter = new MealNotificationAdapter(this, NotificationList);
		setListAdapter(InvitesAdapter);
		DataCacheHelper.registerMealNotificationAdapterInstance(InvitesAdapter);

		applyTheme();

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		TextView tv = (TextView) findViewById(R.id.app_name);
		ThemeManager.setHeaderFont(tv);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author tejasvamsingh
	 * @author Yueling Loh
	 */
	private void applyTheme() {

		View mainLayout = findViewById(R.id.layout_invites);
		View headerLayout = findViewById(R.id.header_invites);
		View buttonBar = findViewById(R.id.buttons_invites);

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonTheme(buttonBar);

	}

	/**
	 * This method goes to the MealDetailActivity when clicking. It also passes
	 * the mealProperties to the MealDetailActivity.
	 * 
	 * @author Runze Tang
	 * @param position
	 * 
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, MealDetailActivity.class);

		MealProperties mealProperties = (MealProperties) NotificationList.get(
				position).getNotificationData();

		Map<String, Object> mealPropertiesMap = mealProperties.toMap();

		intent.putExtra("mealProperties",
				GsonHelper.getGsoninstance().toJson(mealPropertiesMap));
		// intent.putExtra("mealProperties", mealProperties.toString());
		startActivity(intent);

	}

	public void onCreateButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(InvitesActivity.this,
				CreateMealInformationActivity.class);
		InvitesActivity.this.startActivity(intent);
	}
}
