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
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.InvitesView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

public class MyPostsActivity extends ListActivity {

	private ArrayAdapter<NotificationProperties> MyPostsAdapter;
	private TextView titleNameObject;

	List<NotificationProperties> NotificationList;

	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private InvitesView invitesview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		NotificationList = new ArrayList<NotificationProperties>();
		initView(savedInstanceState);
		isCreated = false;

		context = this;
		activity = this;
		invitesview = new InvitesView(context, activity);

		titleNameObject = (TextView) invitesview.getView("my_posts");

		setTitleStyle();
	}

	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);

		// Needs to be changed here!
		//MyPostsAdapter = new MealNotificationAdapter(this, NotificationList);
		setListAdapter(MyPostsAdapter);
		//DataCacheHelper.registerNotificationAdapter(MyPostsAdapter);

		applyTheme();

	}

	private void setTitleStyle() {

		ThemeManager.setHeaderFont(titleNameObject);
	}

	private void applyTheme() {

		View mainLayout = findViewById(R.id.layout_my_posts);
		View headerLayout = findViewById(R.id.header_my_posts);
		View buttonBar = findViewById(R.id.my_posts_buttons_layout);

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonTheme(buttonBar);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// Needs to be changed here! I will add functionalities for posters
		// after the list of invites from the poster is done.
		Intent intent = new Intent(this, MealDetailActivity.class);

		MealProperties mealProperties = (MealProperties) NotificationList.get(
				position).getNotificationData();

		Map<String, Object> mealPropertiesMap = mealProperties.toMap();

		intent.putExtra("mealProperties",
				GsonHelper.getGsoninstance().toJson(mealPropertiesMap));
		// intent.putExtra("mealProperties", mealProperties.toString());
		startActivity(intent);

	}

	public void onBackButtonClick(View view) {

		Intent intent = new Intent(MyPostsActivity.this, InvitesActivity.class);
		MyPostsActivity.this.startActivity(intent);
	}

}
