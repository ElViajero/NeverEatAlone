package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealPostAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.InvitesView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * 
 * This class handles controller operations for the MyPosts page.
 * 
 */
public class MyPostsActivity extends ListActivity {

	private ArrayAdapter<IActivityProperties> myPostsAdapter;
	private TextView titleNameObject;

	List<IActivityProperties> notificationList;

	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private InvitesView invitesView;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * @author tejasvamsingh
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		MessageToasterHelper.toastMessage("Inside CREATE");

		notificationList = new ArrayList<IActivityProperties>();

		initView(savedInstanceState);
		isCreated = false;

		initInvitesView();

		fetchPosts();

		titleNameObject = (TextView) invitesView.getView("my_posts");
		setTitleStyle();
	}


	/**
	 * Method used to initialize InvitesView
	 * @author tejasvamsingh
	 */
	private void initInvitesView() {
		context = this;
		activity = this;
		invitesView = new InvitesView(context, activity);
	}

	/**
	 * This method updates the GUI.
	 * @author tejasvamsingh
	 */
	private void initView(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);


		myPostsAdapter = new MealPostAdapter(this, notificationList);
		setListAdapter(myPostsAdapter);

		NotificationAndPostCacheHelper.
		registerAdapterInstance(myPostsAdapter, "mealPost");

		applyTheme();

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 */
	private void setTitleStyle() {

		ThemeManager.setHeaderFont(titleNameObject);
	}

	/**
	 * This method applies the GUI's color theme.
	 * @author Hai Tang
	 * 
	 */
	private void applyTheme() {

		initInvitesView();
		View mainLayout = invitesView.getView("layout_my_posts");
		View headerLayout = invitesView.getView("header_my_posts");
		View buttonBar = invitesView.getView("my_posts_buttons_layout");
		View backButton = invitesView.getView("my_posts_button_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(backButton);


	}

	/**
	 * This method goes to the MealDetailActivity (needs to be changed) when
	 * clicking. It also passes the mealProperties to the MealDetailActivity
	 * (needs to be changed).
	 * 
	 * @param position
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

	}

	/**
	 * This method implements the back button.
	 * 
	 */
	public void onBackButtonClick(View view) {

		Intent intent = new Intent(MyPostsActivity.this, TabHostActivity.class);
		MyPostsActivity.this.startActivity(intent);
	}


	private void fetchPosts() {

		requestID="Meal";
		requestType="fetchPosts";

		NotificationAndPostCacheHelper.clearAdapterDataMap("mealPost");

		try {
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for( Map<String, String> result :resultMapList){

				if(result.isEmpty())
					continue;

				NotificationProperties notification = new NotificationProperties(result);

				NotificationAndPostCacheHelper.addPost(
						PostProperties.notificationToPost(notification), "mealPost");
			}


		} catch (RequestAbortedException e) {
			System.out.println("Already Handled");
		}


	}

}
