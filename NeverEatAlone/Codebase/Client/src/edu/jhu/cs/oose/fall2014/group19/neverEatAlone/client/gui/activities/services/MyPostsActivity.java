package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.comparators.services.PostDateTimeComparator;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services.changePostStatusConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
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
 * 
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
	// Button closeButton;
	Spinner postStatusSpinner;
	TextView postStatusTextView;

	List<IActivityProperties> postList;

	int lastPosition;

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
		// closeButton = new Button(getApplicationContext());
		postStatusSpinner = new Spinner(getApplicationContext());
		postStatusTextView = new TextView(getApplicationContext());
		lastPosition = -1;

		postList = new ArrayList<IActivityProperties>();
		myPostsAdapter = new MealPostAdapter(this, postList);
		setListAdapter(myPostsAdapter);
		NotificationAndPostCacheHelper.registerAdapterInstance(myPostsAdapter,
				"mealPost");

		fetchPosts();

		Collections.sort(postList, new PostDateTimeComparator());

		PostProperties p = (PostProperties) postList.get(0);
		MessageToasterHelper.toastMessage("postList : " + p.getPostData());
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
	 * @author tejasvamsingh
	 * @param position
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MessageToasterHelper.toastMessage("clicked : " + position + ": "
				+ lastPosition);
		selectedPost = myPostsAdapter.getItem(position);
		listItemIndex = position;
		setSpinnerVisible(v);
		setButtonsVisible(v);

	}

	/**
	 * @author tejasvamsingh
	 * @param v
	 * @param object
	 */
	private void setSpinnerVisible(View v) {

		postStatusSpinner.setVisibility(View.INVISIBLE);
		postStatusTextView.setVisibility(View.VISIBLE);

		postStatusSpinner = (Spinner) v
				.findViewById(R.id.spinner_meal_post_status);
		postStatusTextView = (TextView) v
				.findViewById(R.id.textView_meal_post_status);
		final PostProperties p = (PostProperties) selectedPost;

		int pos = 0;
		if (p.getPostStatus().equalsIgnoreCase("CLOSED"))
			pos = 1;
		else if (p.getPostStatus().equalsIgnoreCase("CANCELLED"))
			pos = 2;

		postStatusSpinner.setSelection(pos);

		ThemeManager.applyButtonColor(postStatusSpinner);
		postStatusTextView.setVisibility(View.GONE);
		postStatusSpinner.setVisibility(View.VISIBLE);
		final MyPostsActivity activityReference = this;
		postStatusSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						String selectedItem = postStatusSpinner
								.getSelectedItem().toString();
						String previousStatus = p.getPostStatus();

						Map<String, Object> constraintMap = new HashMap<String, Object>();
						constraintMap.put("previousStatus", previousStatus);
						constraintMap.put("newStatus", selectedItem);

						if (new changePostStatusConstraintChecker()
								.areConstraintsSatisfied(constraintMap)) {

							try {
								p.setPostStatus(selectedItem);

								List<Map<String, String>> result = RequestHandlerHelper
										.getRequestHandlerInstance()
										.handleRequest(activityReference,
												p.toMap(), "Meal",
												"changeStatus");

								// reaching here means successful query.
								myPostsAdapter.notifyDataSetChanged();

							} catch (RequestAbortedException e) {
								p.setPostStatus(previousStatus);
							}
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});

	}

	/**
	 * Toggles visibility of buttons for listview.
	 * 
	 * @author tejasvamsingh
	 * @param v
	 */
	private void setButtonsVisible(View v) {

		detailButton.setVisibility(View.INVISIBLE);
		// closeButton.setVisibility(View.INVISIBLE);

		View detailButtonView = v.findViewById(R.id.myPosts_button_detail);
		// View closeButtonView = v.findViewById(R.id.myPosts_button_close);

		detailButton = (Button) detailButtonView;
		// closeButton = (Button) closeButtonView;

		ThemeManager.applyButtonColor(detailButtonView);
		// ThemeManager.applyButtonColor(closeButtonView);

		detailButton.setVisibility(View.VISIBLE);
		// closeButton.setVisibility(View.VISIBLE);
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
