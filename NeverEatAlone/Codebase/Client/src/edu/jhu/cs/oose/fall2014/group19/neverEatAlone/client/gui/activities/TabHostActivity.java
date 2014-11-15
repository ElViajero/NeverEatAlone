package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationManager.services.NotificationExecutor;
//Cannot extends Activity
/**
 * 
 * @author Hai Tang
 *
 */

public class TabHostActivity extends TabActivity {

	String Username ;
	AsyncTask<String, List<Map<String, String>>, List<Map<String, String>>> NotificationExecutorTask;
	Map<String,Map<String,String>> NotificationCache;

	/**
	 *  This method intializes the activity.
	 *  
	 *  @author tejasvamsingh 
	 *  */

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Initialize the view and cache.
		InitView();
		NotificationCache = new HashMap<String,Map<String,String>>();

		// Obtain the data required for the activity class
		Username = getIntent().getStringExtra("Username");


		// start the notification framework.
		NotificationExecutorTask= 
				new NotificationExecutor(this).
				executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Username);


		//default update the view.
		UpdateView();

	}



	private void InitView() {
		setContentView(R.layout.activity_tab_host);

		// create the TabHost that will contain the Tabs
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

		TabSpec tab_invites = tabHost.newTabSpec("Invites Tab");
		TabSpec tab_contacts = tabHost.newTabSpec("Contacts Tab");
		TabSpec tab_profile = tabHost.newTabSpec("Profile Tab");

		// Set the Tab name and Activity
		// that will be opened when particular Tab will be selected
		tab_invites.setIndicator("Invites");
		tab_invites.setContent(new Intent(this,InvitesActivity.class));

		tab_contacts.setIndicator("Contacts");
		tab_contacts.setContent(new Intent(this,ContactsActivity.class));

		tab_profile.setIndicator("Profile");
		tab_profile.setContent(new Intent(this,ProfileActivity.class));


		/** Add the tabs  to the TabHost to display. */

		tabHost.addTab(tab_invites);
		tabHost.addTab(tab_contacts);
		tabHost.addTab(tab_profile);

	}




	/**
	 * 
	 * Method to update notifications
	 * @author tejasvamsingh
	 */
	public void UpdateNotificationCache(List<Map<String,String>> notificationMapList){

		for(Map<String,String> notification : notificationMapList ){    		
			NotificationCache.put(notification.get("NotificationID"), notification);
		}

		System.out.println("in UpdateNotificationCache");
		System.out.println(NotificationCache.get("1"));
		UpdateView();

	}


	/**
	 * 
	 * Method to refresh the GUI whenever a notification arrives.
	 * @author tejasvamsingh
	 * 
	 */

	private void UpdateView() {

		for (Entry<String, Map<String, String>> entry : NotificationCache.entrySet()) {


			Map<String,String> notification = entry.getValue();

			System.out.println("In UpdateView");
			System.out.println(notification);

			//push the notifications to view.. Naive for now.
			Toast.makeText(getApplicationContext(), 
					notification.get("Message")+"", Toast.LENGTH_SHORT).show();

		}





	}

}
