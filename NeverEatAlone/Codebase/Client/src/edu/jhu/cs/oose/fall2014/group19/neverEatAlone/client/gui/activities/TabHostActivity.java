package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
//Cannot extends Activity
/**
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * TabHostActivity is used to create and switch between three main pages: 
 * Invites page, Contacts page and Profile page.
 * Which are corresponding to InvitesActivity, ContactsActivity and ProfileActivity
 */

public class TabHostActivity extends TabActivity {

	String username ;
	String requestID;
	String requestType;


	TabSpec TabContacts;
	TabSpec TabInvites;
	TabSpec TabProfile;
	TabHost TabHost; 
	String notificationMapListJSon;

	Set<NotificationProperties> notificationCache; 



	/**
	 *  This method intializes the activity.
	 *  @author tejasvamsingh 
	 *  */

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		notificationMapListJSon="[{}]";
		super.onCreate(savedInstanceState);

		notificationCache=new HashSet<NotificationProperties>();
		//		fetchNotifications();

		// Initialize the view and cache.
		InitView();
		// Obtain the data required for the activity class
		username = getIntent().getStringExtra("username");
		//start the notifcations framework.
		NotificationHelper.init(this, username);




	}


	/**
	 * This method is used to initialize the three main Tabs, i.e., Invites, Contacts and Profile
	 * @author: Hai Tang
	 * @author tejasvamsingh
	 */
	private void InitView() {


		setContentView(R.layout.activity_tab_host);

		// create the TabHost that will contain the Tabs
		TabHost = (TabHost)findViewById(android.R.id.tabhost);

		TabInvites = TabHost.newTabSpec("Invites Tab");
		TabContacts = TabHost.newTabSpec("Contacts Tab");
		TabProfile = TabHost.newTabSpec("Profile Tab");

		// Set the Tab name and Activity
		// that will be opened when particular Tab will be selected

		TabInvites.setIndicator("Invites");
		Intent intent = new Intent(this,InvitesActivity.class);
		intent.putExtra("notificationMapListJSon", notificationMapListJSon);
		TabInvites.setContent(intent);

		TabContacts.setIndicator("Contacts");
		TabContacts.setContent(new Intent(this,ContactsActivity.class));

		TabProfile.setIndicator("Profile");
		TabProfile.setContent(new Intent(this,ProfileActivity.class));


		/** Add the tabs  to the TabHost to display. */

		TabHost.addTab(TabInvites);
		TabHost.addTab(TabContacts);
		TabHost.addTab(TabProfile);

		//apply the theme
		applyTheme();


	}




	private void applyTheme() {
		System.out.println("Inside apply theme");
		ThemeManager.applyTheme(findViewById(android.R.id.content));
		//ThemeManager.applyTheme(findViewById(R.id.layout_tab_host));
		//ThemeManager.applyTheme(findViewById(android.R.id.tabs));
	}


	/**
	 * 
	 * Method to update UI whenever notifications arrive.
	 * @author tejasvamsingh
	 */
	public void UpdateNotificationCache(List<Map<String,String>> notificationMapList){

		System.out.println("in updateNotification");

		for(Map<String, String> notification : notificationMapList){
			if(notification.isEmpty())
				continue;
			notificationCache.add(new NotificationProperties(notification));
		}
		DataCacheHelper.setNotificationCache(notificationCache);
	}


	private void fetchNotifications() {

		try{
			requestID = "Notification";
			requestType = "fetch";

			//send the request.
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID,requestType) ;

			System.out.println("The notifications are : "  + resultMapList.size());

			UpdateNotificationCache(resultMapList);
		}catch(RequestAbortedException e){
			return;
		}
	}


}
