package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

//Cannot extends Activity
/**
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 * 
 *         TabHostActivity is used to create and switch between three main
 *         pages: Invites page, Contacts page and Profile page. Which are
 *         corresponding to InvitesActivity, ContactsActivity and
 *         ProfileActivity
 */

public class TabHostActivity extends TabActivity {

	String username;
	String requestID;
	String requestType;

	TabSpec TabContacts;
	TabSpec TabInvites;
	TabSpec TabProfile;
	TabHost TabHost;
	String notificationMapListJSon;

	Set<NotificationProperties> notificationCache;

	/**
	 * This method intializes the activity.
	 * 
	 * @author tejasvamsingh
	 * @author Runze Tang
	 * */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		notificationMapListJSon = "[{}]";
		super.onCreate(savedInstanceState);

		Intent i = getIntent();
		int tabToOpen = i.getIntExtra("FirstTab", 0);

		notificationCache = new HashSet<NotificationProperties>();
		fetchNotifications();

		// Initialize the view and cache.
		InitView(tabToOpen);
		// Obtain the data required for the activity class
		username = getIntent().getStringExtra("username");
		// start the notifcations framework.
		NotificationHelper.init(this, username);

	}

	/**
	 * This method is used to initialize the three main Tabs, i.e., Invites,
	 * Contacts and Profile
	 * 
	 * @author: Hai Tang
	 * @author tejasvamsingh
	 * @author Yueling Loh
	 * @author Runze Tang
	 */
	private void InitView(int tabToOpen) {

		setContentView(R.layout.activity_tab_host);

		// create the TabHost that will contain the Tabs
		TabHost = (TabHost) findViewById(android.R.id.tabhost);

		TabInvites = TabHost.newTabSpec("Invites Tab");
		TabContacts = TabHost.newTabSpec("Contacts Tab");
		TabProfile = TabHost.newTabSpec("Profile Tab");

		// Set the Tab name and Activity
		// that will be opened when particular Tab will be selected

		// Set Invite tab
		View invitesTabview = createTabView(TabHost.getContext(), "Invites");
		TabInvites.setIndicator(invitesTabview);
		// TabInvites.setIndicator("Invites");
		Intent intent = new Intent(this, InvitesActivity.class);
		intent.putExtra("notificationMapListJSon", notificationMapListJSon);
		TabInvites.setContent(intent);

		// Set Contacts tabs
		View contactsTabview = createTabView(TabHost.getContext(), "Contacts");
		TabContacts.setIndicator(contactsTabview);
		// TabContacts.setIndicator("Contacts");
		TabContacts.setContent(new Intent(this, ContactTabbedActivity.class));

		// Set Profile tabs
		View profileTabview = createTabView(TabHost.getContext(), "Profile");
		TabProfile.setIndicator(profileTabview);
		// TabProfile.setIndicator("Profile");
		TabProfile.setContent(new Intent(this, ProfileActivity.class));

		// Add the tabs to the TabHost to display.
		TabHost.addTab(TabInvites);
		TabHost.addTab(TabContacts);
		TabHost.addTab(TabProfile);

		// Change to the target tab
		if (tabToOpen != 0) {
			TabHost.setCurrentTab(tabToOpen);
		}
		// applyTheme();
	}

	/**
	 * This method is used to set the Tab theme and titles
	 * 
	 * @author Yueling Loh
	 */
	private static View createTabView(final Context context, final String string) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_layout,
				null);
		ThemeManager.applyTabTheme(view);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(string);
		ThemeManager.setTabFont(tv);
		return view;
	}

	/**
	 * 
	 * Method to update UI whenever notifications arrive.
	 * 
	 * @author tejasvamsingh
	 */
	public void UpdateNotificationCache(
			List<Map<String, String>> notificationMapList) {

		System.out.println("in updateNotification");
		for (Map<String, String> notification : notificationMapList) {
			if (notification.isEmpty())
				continue;
			notificationCache.add(new NotificationProperties(notification));
		}
		NotificationAndPostCacheHelper.setNotificationCache(notificationCache);
	}

	private void fetchNotifications() {
		MessageToasterHelper.toastMessage("INside FEtCH");
		notificationCache.clear();
		try {
			requestID = "Meal";
			requestType = "fetchNotifications";

			// send the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			System.out.println("The notifications are : "
					+ resultMapList.size());

			UpdateNotificationCache(resultMapList);
		} catch (RequestAbortedException e) {
			return;
		}
	}

}
