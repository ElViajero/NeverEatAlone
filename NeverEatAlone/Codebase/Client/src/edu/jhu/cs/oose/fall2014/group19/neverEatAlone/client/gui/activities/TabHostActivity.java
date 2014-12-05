package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
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

	String Username ;

	Map<String,Map<String,String>> NotificationCache;

	TabSpec TabContacts;
	TabSpec TabInvites;
	TabSpec TabProfile;
	TabHost TabHost; 
	String NotificationMapListJSON; 


	/**
	 *  This method intializes the activity.
	 *  @author tejasvamsingh 
	 *  */

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		NotificationMapListJSON="[{}]";
		super.onCreate(savedInstanceState);
		// Initialize the view and cache.
		InitView();
		NotificationCache = new HashMap<String,Map<String,String>>();

		// Obtain the data required for the activity class
		Username = getIntent().getStringExtra("Username");

		//start the notifcations framework.
		NotificationHelper.init(this, Username);



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
		intent.putExtra("NotificationMapListJSON", NotificationMapListJSON);
		TabInvites.setContent(intent);

		TabContacts.setIndicator("Contacts");
		TabContacts.setContent(new Intent(this,ContactsActivity.class));

		TabProfile.setIndicator("Profile");
		TabProfile.setContent(new Intent(this,ProfileActivity.class));


		/** Add the tabs  to the TabHost to display. */

		TabHost.addTab(TabInvites);
		TabHost.addTab(TabContacts);
		TabHost.addTab(TabProfile);


	}




	/**
	 * 
	 * Method to update UI whenever notifications arrive.
	 * @author tejasvamsingh
	 */
	public void UpdateNotificationCache(List<Map<String,String>> notificationMapList){

		System.out.println("in updateNotification");
		List<NotificationProperties> notificationList = 
				new ArrayList<NotificationProperties>();

		for(Map<String, String> notification : notificationMapList){
			notificationList.add(new NotificationProperties(notification));
		}

		DataCacheHelper.setMealNotificationCache(notificationList);

	}

}
