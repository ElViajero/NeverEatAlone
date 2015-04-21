package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAlertHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * 
 * This class handles controller operations for the invites tab.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 *
 */
public class InvitesActivity extends ListFragment {

	private ArrayAdapter<IActivityProperties> invitesAdapter;

	private TextView invitesTitleObject;
	private TextView invitesSwipeLeftObject;
	private TextView invitesSwipeRightObject;

	Button createInviteButton;

	List<IActivityProperties> notificationList;

	String requestID;
	String requestType;

	boolean isCreated;
	private Context context;
	private Activity activity;
	private FragmentView invitesView;

	private View rootView;

	/**
	 * This constructor is responsible for obtaining notifications and updating
	 * the GUI.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @author Yueling Loh
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.activity_invites, container, false);

		notificationList = new ArrayList<IActivityProperties>();
		initView(savedInstanceState);
		isCreated = false;
		initInvitesView();
		invitesTitleObject = (TextView) invitesView.getView("invites_title");
		invitesSwipeLeftObject = (TextView) invitesView
				.getView("invites_swipe_left");
		invitesSwipeRightObject = (TextView) invitesView
				.getView("invites_swipe_right");

		setTitleStyle();
		setCreateInviteButtonHandler();

		return rootView;

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
	 * Method used to initialize the InvitesView
	 * 
	 * @author: Hai Tang
	 */
	private void initInvitesView() {
		context = rootView.getContext();
		// activity = this;
		invitesView = new FragmentView(context, rootView);
	}

	/**
	 * This method updates the GUI.
	 * 
	 * @author tejasvamsingh
	 * @param savedInstanceState
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		invitesAdapter = new MealNotificationAdapter(getActivity(),
				notificationList);
		setListAdapter(invitesAdapter);
		NotificationAndPostCacheHelper.registerAdapterInstance(invitesAdapter,
				"meal");

		applyTheme();

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		invitesSwipeLeftObject.setText("<<");
		invitesSwipeLeftObject.setTextColor(Color.GRAY);
		invitesSwipeLeftObject.setGravity(android.view.Gravity.LEFT);
		invitesSwipeRightObject.setText(">>");
		invitesSwipeRightObject.setTextColor(Color.GRAY);
		invitesSwipeRightObject.setGravity(android.view.Gravity.RIGHT);

		invitesTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(invitesTitleObject);
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

		createInviteButton = (Button) invitesView
				.getView("invites_button_create");
		// View myInvitesButton =
		// invitesView.getView("invites_button_my_posts");
		// View acceptedInvitesButton = invitesView
		// .getView("invites_button_accepted_invites");
		// Switch availabilitySwitch = (Switch)
		// invitesView.getView("switch_availability_status");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(createInviteButton);
		// ThemeManager.applyButtonColor(myInvitesButton);
		// ThemeManager.applyButtonColor(acceptedInvitesButton);
		// ThemeManager.applyAvailabilityColor(availabilitySwitch);

	}

	/**
	 * This method goes to the MealDetailActivity when clicking. It also passes
	 * the mealProperties to the MealDetailActivity.
	 * 
	 * @author tejasvamsingh
	 * @param position
	 * 
	 * 
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), MealDetailActivity.class);

		NotificationProperties notification = (NotificationProperties) notificationList
				.get(position);

		DataCacheHelper.setAccepted(false);
		DataCacheHelper.setIActivityPropertiesObject(notification);

		startActivity(intent);

	}

	@Override
	public void onResume() {
		super.onResume();
		MessageToasterHelper.isMessageToastable = true;

		if (NotificationAndPostCacheHelper.isServerFetchRequired("meal")) {
			fetchNotifications();
			NotificationAndPostCacheHelper
					.setServerFetchRequired("meal", false);
		}

		NotificationAlertHelper.disableNotificationAlert("meal");

	}

	private void fetchNotifications() {
		notificationList.clear();
		try {
			requestID = "Meal";
			requestType = "fetchNotifications";

			// send the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(getActivity(),
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {
				if (result.isEmpty())
					continue;
				notificationList.add(new NotificationProperties(result));
			}
			invitesAdapter.notifyDataSetChanged();
		} catch (RequestAbortedException e) {
			return;
		}
	}

	@Override
	public void onPause() {

		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}

}
