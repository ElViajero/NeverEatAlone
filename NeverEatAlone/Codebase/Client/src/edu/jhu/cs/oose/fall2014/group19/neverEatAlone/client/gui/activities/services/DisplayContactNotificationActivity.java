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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests.GetContactNotificationExecutableRequest;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles the display of contact notification.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 */
public class DisplayContactNotificationActivity extends ListFragment {

	private TextView friendRequestTitleObject;
	private TextView friendRequestSwipeTitleObject;
	private ArrayAdapter<IActivityProperties> contactsNotificationAdapter;
	String requestType;
	String requestID;

	private Context context;
	private Activity activity;
	private FragmentView contactsView;

	NotificationProperties selectedNotification;
	int listItemIndex;
	Button acceptButton;
	Button rejectButton;

	View rootView;

	List<IActivityProperties> contactList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(
				R.layout.activity_display_contact_notification, container,
				false);
		initView(savedInstanceState);
		// initContactView();
		// ThemeManager.applyButtonBarTheme(rootView
		// .findViewById(R.id.buttons_contacts));

		return rootView;

	}

	/**
	 * 
	 * @author tejasvamsingh
	 * 
	 * @param savedInstanceState
	 * 
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		acceptButton = new Button(rootView.getContext());
		rejectButton = new Button(rootView.getContext());
		contactList = new ArrayList<IActivityProperties>();
		contactsNotificationAdapter = new ContactsNotificationAdapter(
				getActivity(), contactList);

		setListAdapter(contactsNotificationAdapter);

		NotificationAndPostCacheHelper.registerAdapterInstance(
				contactsNotificationAdapter, "contact");

		fetchContactRequests();

		setTitleStyle();
		requestID = "Contact";
		applyTheme();
	}

	/**
	 * Method used to initialize ProfileView
	 * 
	 * @author: Hai Tang
	 */
	private void initContactsView() {
		context = rootView.getContext();
		contactsView = new FragmentView(context, rootView);
	}

	private void applyTheme() {
		initContactsView();
		View mainLayout = contactsView.getView("main_friendsRequest");
		View headerLayout = contactsView.getView("header_friendsRequest");
		View buttonBar = contactsView.getView("buttons_layout_friendsRequest");
		// View backButton = contactsView.getView("friendsRequest_button_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		// ThemeManager.applyButtonColor(backButton);

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Yueling Loh
	 * @author tejasvamsingh
	 */
	private void setTitleStyle() {

		initContactsView();
		friendRequestTitleObject = (TextView) contactsView
				.getView("textView_friendsrequest_title");
		friendRequestSwipeTitleObject = (TextView) contactsView
				.getView("textView_friendsrequest_swipe_title");

		friendRequestSwipeTitleObject.setTextColor(Color.GRAY);
		friendRequestTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(friendRequestTitleObject);
	}

	/**
	 * Method used for clicking the back button
	 * 
	 * @author Yueling Loh
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(getActivity(), TabHostActivity.class);

		// Go to the specific tab.
		intent.putExtra("FirstTab", 1);
		DisplayContactNotificationActivity.this.startActivity(intent);
	}

	/**
	 * This method handles click events on the listview.
	 * 
	 * @author tejasvamsingh
	 * 
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		selectedNotification = (NotificationProperties) contactsNotificationAdapter
				.getItem(position);
		listItemIndex = position;
		setButtonsVisible(v);

	}

	/**
	 * Toggles visibility of buttons for listview.
	 * 
	 * @author tejasvamsingh
	 * @author Runze Tang
	 * @param v
	 */
	private void setButtonsVisible(View v) {

		acceptButton.setVisibility(View.INVISIBLE);
		rejectButton.setVisibility(View.INVISIBLE);

		View acceptButtonView = v
				.findViewById(R.id.contacts_notification_accept);
		View rejectButtonView = v
				.findViewById(R.id.contacts_notification_reject);

		acceptButton = (Button) acceptButtonView;
		rejectButton = (Button) rejectButtonView;

		setAcceptButtonClickListener();
		setRejectButtonClickListener();

		ThemeManager.applyButtonColor(acceptButtonView);
		ThemeManager.applyButtonColor(rejectButtonView);

		acceptButton.setVisibility(View.VISIBLE);
		rejectButton.setVisibility(View.VISIBLE);
	}

	private void setRejectButtonClickListener() {
		View.OnClickListener rejectButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				requestType = "reject";
				sendRequest();
			}
		};
		rejectButton.setOnClickListener(rejectButtonOnClickListener);
	}

	private void setAcceptButtonClickListener() {

		View.OnClickListener acceptedButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				requestType = "accept";
				selectedNotification.setAccepted(true);
				sendRequest();

			}
		};

		acceptButton.setOnClickListener(acceptedButtonOnClickListener);

	}

	private boolean sendRequest() {
		NotificationAndPostCacheHelper.setServerFetchRequired("contact", true);
		ContactProperties p = (ContactProperties) selectedNotification
				.getNotificationData();

		System.out.println("selectedNotification : " + selectedNotification);
		List<String> recipientList = new ArrayList<String>();
		recipientList.add(selectedNotification.getPoster());

		IActivityProperties postProperties = PostProperties
				.notificationToPost(selectedNotification);
		try {

			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(getActivity(),
							postProperties.toMap(), requestID, requestType);

			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					true);

			contactsNotificationAdapter.remove(selectedNotification);
			contactList = (List<IActivityProperties>) DataCacheHelper
					.getCachedResult("contactRequest");
			contactList.remove(selectedNotification);
			contactsNotificationAdapter.notifyDataSetChanged();
			return true;

		} catch (RequestAbortedException e) {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	private void fetchContactRequests() {

		contactList = (List<IActivityProperties>) DataCacheHelper
				.getCachedResult("contactRequest");

		if (!NotificationAndPostCacheHelper.isServerFetchRequired("contact")
				&& contactList != null) {
			contactsNotificationAdapter.clear();
			contactsNotificationAdapter.addAll(contactList);
			return;
		}

		new GetContactNotificationExecutableRequest()
				.executeRequest(getActivity());

		contactList = (List<IActivityProperties>) DataCacheHelper
				.getCachedResult("contactRequest");
		contactsNotificationAdapter.clear();
		contactsNotificationAdapter.addAll(contactList);

		/*
		 * requestID = "Contact"; requestType = "fetchRequests";
		 * 
		 * contactList.clear();
		 * 
		 * 
		 * try {
		 * 
		 * List<Map<String, String>> resultMapList = RequestHandlerHelper
		 * .getRequestHandlerInstance().handleRequest(getActivity(),
		 * AccountProperties.getUserAccountInstance().toMap(), requestID,
		 * requestType);
		 * 
		 * for (Map<String, String> result : resultMapList) {
		 * 
		 * if (result.isEmpty()) continue; System.out.println("RESULT IS :" +
		 * result); result.put("postType", "contact"); contactList.add(new
		 * NotificationProperties(result)); }
		 * 
		 * contactsNotificationAdapter.notifyDataSetChanged();
		 * 
		 * } catch (RequestAbortedException e) { return; }
		 * 
		 * }
		 */
	}

	public void onResume() {
		super.onResume();
		MessageToasterHelper.isMessageToastable = true;
		fetchContactRequests();
	}

	@Override
	public void onPause() {
		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}
}
