package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.InvitesView;
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
	private TextView appNameObject;
	private Button createAnInviteObject;
	private Button viewMyInvitesObject;
	private Switch availabilitySwitchObject;
	List<NotificationProperties> NotificationList;

	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private InvitesView invitesView;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @author Yueling Loh
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		NotificationList = new ArrayList<NotificationProperties>();
		initView(savedInstanceState);
		isCreated = false;

		initInvitesView();

		appNameObject = (TextView) invitesView.getView("app_name");
		createAnInviteObject = (Button) invitesView.getView("invites_button_create");
		viewMyInvitesObject = (Button) invitesView.getView("invites_button_my_posts");
		availabilitySwitchObject = (Switch) invitesView.getView("switch_availability_status");
		
//		mainLayout = invitesview.getView("layout_invites");
//		headerLayout = invitesview.getView("header_invites");
//		buttonBar = invitesview.getView("buttons_invites");
		
		setTitleStyle();
	}

	/**
	 * Method used to initialize the InvitesView
	 * @author: Hai Tang
	 */
	private void initInvitesView() {
		context = this;
		activity = this;
		invitesView = new InvitesView(context, activity);
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

		ThemeManager.setHeaderFont(appNameObject);
	}


	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author tejasvamsingh
	 * @author Yueling Loh
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initInvitesView();
		View mainLayout = invitesView.getView("layout_invites");
		View headerLayout = invitesView.getView("header_invites");
		View buttonBar = invitesView.getView("buttons_invites");
		
		View createInviteButton = invitesView.getView("invites_button_create");
		View myInvitesButton = invitesView.getView("invites_button_my_posts");
		Switch availabilitySwitch = (Switch) invitesView.getView("switch_availability_status");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(createInviteButton);
		ThemeManager.applyButtonColor(myInvitesButton);
		ThemeManager.applyAvailabilityColor(availabilitySwitch);
		
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
	
	public void onMyPostsButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(InvitesActivity.this,
				MyPostsActivity.class);
		InvitesActivity.this.startActivity(intent);
	}
}
