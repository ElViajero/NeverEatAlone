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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealPostAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.InvitesView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * 
 * This class handles controller operations for the MyPosts page.
 * @author tejasvamsingh
 * @author Runze Tang
 */
public class MyPostsActivity extends ListActivity {

	private TextView titleNameObject;
	private ArrayAdapter<IActivityProperties> myPostsAdapter;
	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private InvitesView invitesView;

	IActivityProperties selectedPost;
	int listItemIndex;
	Button detailButton;
	Button closeButton;

	List<IActivityProperties> postList;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		MessageToasterHelper.toastMessage("Inside CREATE");

		initView(savedInstanceState);
		isCreated = false;

	}

	/**
	 * Method used to initialize InvitesView
	 * 
	 * @author tejasvamsingh
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
	 * @author Runze Tang
	 */
	private void initView(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);

		detailButton = new Button(getApplicationContext());
		closeButton = new Button(getApplicationContext());
		postList = new ArrayList<IActivityProperties>();
		myPostsAdapter = new MealPostAdapter(this, postList);

		setListAdapter(myPostsAdapter);

		NotificationAndPostCacheHelper.registerAdapterInstance(myPostsAdapter,
				"mealPost");

		fetchPosts();



		setTitleStyle();

		applyTheme();

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 */
	private void setTitleStyle() {
		initInvitesView();
		titleNameObject = (TextView) invitesView.getView("my_posts");
		ThemeManager.setHeaderFont(titleNameObject);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
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
	 * This method handles click events on the listview.
	 * 
	 * @param position
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		selectedPost = myPostsAdapter
				.getItem(position);
		listItemIndex = position;
		setButtonsVisible(v);
	}

	/**
	 * Toggles visibility of buttons for listview.
	 * 
	 * @param v
	 */
	private void setButtonsVisible(View v) {

		detailButton.setVisibility(View.INVISIBLE);
		closeButton.setVisibility(View.INVISIBLE);

		detailButton = (Button) v.findViewById(R.id.myPosts_button_detail);
		closeButton = (Button) v.findViewById(R.id.myPosts_button_close);
		detailButton.setVisibility(View.VISIBLE);
		closeButton.setVisibility(View.VISIBLE);
	}

	/**
	 * Handler for the detail button.
	 * 
	 * @param view
	 */
	public void onDetailMyPostsButtonClick(View view) {

		DataCacheHelper.setIActivityPropertiesObject(selectedPost);
		Intent intent = new Intent(this, MyPostsDetailActivity.class);
		startActivity(intent);
	}

	/**
	 * Handler for the close button.
	 * 
	 * @param view
	 */
	public void onCloseMyPostsButtonClick(View view) {
		// TODO Need to be revised here
		// requestType = "reject";
		// sendRequest();
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

		requestID = "Meal";
		requestType = "fetchPosts";

		NotificationAndPostCacheHelper.clearAdapterDataMap("mealPost");

		try {
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;

				NotificationProperties notification = new NotificationProperties(
						result);

				NotificationAndPostCacheHelper.addPost(
						PostProperties.notificationToPost(notification),
						"mealPost");
			}

		} catch (RequestAbortedException e) {
			System.out.println("Already Handled");
		}

	}

}
