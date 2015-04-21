package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.comparators.services.PostDateTimeComparator;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services.changePostStatusConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealPostAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests.FetchPostRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * 
 * This class handles controller operations for the MyPosts page.
 * 
 * @author tejasvamsingh
 * @author Runze Tang
 */
public class MyPostsActivity extends ListFragment {

	private TextView myPostsTitle;
	private TextView myPostsSwipeTitle;

	private ArrayAdapter<IActivityProperties> myPostsAdapter;
	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private FragmentView invitesView;

	IActivityProperties selectedPost;
	int listItemIndex;
	Button detailButton;
	// Button closeButton;
	Spinner postStatusSpinner;
	TextView postStatusTextView;

	Button createInviteButton;

	private View rootView;

	List<IActivityProperties> postList;

	int lastPosition;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * 
	 * @author tejasvamsingh
	 */

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_my_posts, container,
				false);
		initView(savedInstanceState);
		isCreated = false;
		setCreateInviteButtonHandler();
		setDetailButtonHandler();
		return rootView;

	}

	/**
	 * 
	 * This method handles details button click event.
	 * 
	 * @author tejasvamsingh
	 */

	private void setDetailButtonHandler() {

		View.OnClickListener detailsButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DataCacheHelper.setIActivityPropertiesObject(selectedPost);
				Intent intent = new Intent(getActivity(),
						MyPostsDetailActivity.class);
				startActivity(intent);

			}
		};

		detailButton.setOnClickListener(detailsButtonOnClickListener);

	}

	/**
	 * This method sets the onclick handler for the create invites button.
	 * 
	 * @author tejasvamsingh
	 */
	private void setCreateInviteButtonHandler() {

		View.OnClickListener createInviteButtonClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						CreateMealInformationActivity.class);
				getActivity().startActivity(intent);
			}
		};

		createInviteButton.setOnClickListener(createInviteButtonClickListener);

	}

	/**
	 * Method used to initialize InvitesView
	 * 
	 * @author tejasvamsingh
	 */
	private void initInvitesView() {
		context = rootView.getContext();
		invitesView = new FragmentView(context, rootView);
	}

	/**
	 * This method updates the GUI.
	 * 
	 * @author tejasvamsingh
	 * @author Runze Tang
	 */
	private void initView(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		detailButton = new Button(rootView.getContext());
		// closeButton = new Button(getApplicationContext());
		postStatusSpinner = new Spinner(rootView.getContext());
		postStatusTextView = new TextView(rootView.getContext());
		lastPosition = -1;

		postList = new ArrayList<IActivityProperties>();
		myPostsAdapter = new MealPostAdapter(getActivity(), postList);
		setListAdapter(myPostsAdapter);
		NotificationAndPostCacheHelper.registerAdapterInstance(myPostsAdapter,
				"mealPost");

		fetchPosts();

		Collections.sort(postList, new PostDateTimeComparator());

		setTitleStyle();

		applyTheme();

	}

	/**
	 * 
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author tejasvamsingh
	 * 
	 */
	private void setTitleStyle() {
		initInvitesView();
		myPostsTitle = (TextView) invitesView.getView("my_posts_title");
		myPostsSwipeTitle = (TextView) invitesView.getView("my_posts_swipe");
		myPostsSwipeTitle.setGravity(android.view.Gravity.RIGHT);
		myPostsTitle.setGravity(android.view.Gravity.CENTER);

		myPostsSwipeTitle.setText(">>");
		myPostsSwipeTitle.setTextColor(Color.GRAY);

		ThemeManager.setHeaderFont(myPostsTitle);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * 
	 */
	private void applyTheme() {

		initInvitesView();
		View mainLayout = invitesView.getView("layout_my_posts");
		View headerLayout = invitesView.getView("header_my_posts");
		View buttonBar = invitesView.getView("my_posts_buttons_layout");
		// View backButton = invitesView.getView("my_posts_button_back");

		createInviteButton = (Button) invitesView
				.getView("my_posts_button_create");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(createInviteButton);

	}

	/**
	 * This method handles click events on the listview.
	 * 
	 * @author tejasvamsingh
	 * @param position
	 * 
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

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
		// final MyPostsActivity activityReference = getActivity();
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
										.handleRequest(getActivity(),
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

		setDetailButtonHandler();

		ThemeManager.applyButtonColor(detailButtonView);
		// ThemeManager.applyButtonColor(closeButtonView);

		detailButton.setVisibility(View.VISIBLE);
		// closeButton.setVisibility(View.VISIBLE);
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

		Intent intent = new Intent(getActivity(), TabHostActivity.class);
		MyPostsActivity.this.startActivity(intent);
	}

	private void fetchPosts() {

		postList = (List<IActivityProperties>) DataCacheHelper
				.getCachedResult("mealPost");

		if (postList == null
				|| NotificationAndPostCacheHelper
						.isServerFetchRequired("mealPost")) {

			FetchPostRequestExecutor fetchPostRequestExecutor = new FetchPostRequestExecutor();
			fetchPostRequestExecutor.executeRequest(getActivity());
			postList = (List<IActivityProperties>) DataCacheHelper
					.getCachedResult("mealPost");
			myPostsAdapter.clear();
			myPostsAdapter.addAll(postList);
			myPostsAdapter.notifyDataSetChanged();

		}

		if (postList.size() != myPostsAdapter.getCount()) {

			myPostsAdapter.clear();
			myPostsAdapter.addAll(postList);
			myPostsAdapter.notifyDataSetChanged();
		}

		// myPostsAdapter.notifyDataSetChanged();

	}

	@Override
	public void onResume() {
		super.onResume();
		MessageToasterHelper.isMessageToastable = true;
		fetchPosts();
	}

	@Override
	public void onPause() {
		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}

}
