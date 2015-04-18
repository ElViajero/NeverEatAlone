package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ContactsView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles the display of contact notification.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 */
public class DisplayContactNotificationActivity extends ListActivity {

	private TextView friendRequestTitleObject;
	private ArrayAdapter<IActivityProperties> contactsNotificationAdapter;
	String requestType;
	String requestID;

	private Context context;
	private Activity activity;
	private ContactsView contactsView;

	NotificationProperties selectedNotification;
	int listItemIndex;
	Button acceptButton;
	Button rejectButton;

	List<IActivityProperties> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
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
		setContentView(R.layout.activity_display_contact_notification);
		acceptButton = new Button(getApplicationContext());
		rejectButton = new Button(getApplicationContext());
		contactList = new ArrayList<IActivityProperties>();
		contactsNotificationAdapter = new ContactsNotificationAdapter(this,
				contactList);

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
		context = this;
		activity = this;
		contactsView = new ContactsView(context, activity);
	}

	private void applyTheme() {
		initContactsView();
		View mainLayout = contactsView.getView("main_friendsRequest");
		View headerLayout = contactsView.getView("header_friendsRequest");
		View buttonBar = contactsView.getView("buttons_layout_friendsRequest");
		View backButton = contactsView.getView("friendsRequest_button_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(backButton);

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		initContactsView();
		friendRequestTitleObject = (TextView) contactsView
				.getView("textView_friendsrequest_title");

		friendRequestTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(friendRequestTitleObject);
	}

	/**
	 * Method used for clicking the back button
	 * 
	 * @author Yueling Loh
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(DisplayContactNotificationActivity.this,
				TabHostActivity.class);
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
	protected void onListItemClick(ListView l, View v, int position, long id) {

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

		ThemeManager.applyButtonColor(acceptButtonView);
		ThemeManager.applyButtonColor(rejectButtonView);

		acceptButton.setVisibility(View.VISIBLE);
		rejectButton.setVisibility(View.VISIBLE);
	}

	/**
	 * Handler for the accept button.
	 * 
	 * @author tejasvamsingh
	 * @param view
	 */
	public void onAcceptContactRequestButtonClick(View view) {
		requestType = "accept";
		selectedNotification.setAccepted(true);
		sendRequest();
	}

	/**
	 * @author tejasvamsingh
	 * @param view
	 */
	public void onRejectContactRequestButtonClick(View view) {
		requestType = "reject";
		sendRequest();
	}

	/**
	 * sends the request.
	 * 
	 * @author tejasvamsingh
	 */
	private boolean sendRequest() {

		ContactProperties p = (ContactProperties) selectedNotification
				.getNotificationData();

		System.out.println("selectedNotification : " + selectedNotification);
		List<String> recipientList = new ArrayList<String>();
		recipientList.add(selectedNotification.getPoster());

		IActivityProperties postProperties = PostProperties
				.notificationToPost(selectedNotification);
		try {

			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postProperties.toMap(), requestID, requestType);

			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					true);
			System.out.println("FETCH STATUS :"
					+ NotificationAndPostCacheHelper
							.isServerFetchRequired("contact"));
			contactsNotificationAdapter.remove(selectedNotification);
			return true;

		} catch (RequestAbortedException e) {
			return false;
		}

	}

	private void fetchContactRequests() {

		requestID = "Contact";
		requestType = "fetchRequests";

		contactList.clear();

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;
				System.out.println("RESULT IS :" + result);
				result.put("postType", "contact");
				contactList.add(new NotificationProperties(result));
			}

			contactsNotificationAdapter.notifyDataSetChanged();

		} catch (RequestAbortedException e) {
			return;
		}

	}

}
