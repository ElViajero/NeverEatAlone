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

import com.google.gson.Gson;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationManager.services.NotificationExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;
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

	TabSpec TabContacts;
	TabSpec TabInvites;
	TabSpec TabProfile;
	TabHost TabHost; 
	String NotificationMapListJSON; 


	/**
	 *  This method intializes the activity.
	 *  
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


		TabHost.addTab(TabContacts);
		TabHost.addTab(TabProfile);
		TabHost.addTab(TabInvites);
	}




	/**
	 * 
	 * Method to update notifications
	 * @author tejasvamsingh
	 */
	public void UpdateNotificationCache(List<Map<String,String>> notificationMapList){

		notificationMapList.remove(0);
		for(Map<String,String> notification : notificationMapList ){
			if(notification.isEmpty())
				continue;
			NotificationCache.put(notification.get("PostID"), notification);
		}

		System.out.println("in UpdateNotificationCache");
		System.out.println(NotificationCache.get("2"));
		UpdateView();

	}


	/**
	 * 
	 * Method to refresh the GUI whenever a notification arrives.
	 * @author tejasvamsingh
	 * 
	 */

	private void UpdateView() {

		NotificationMapListJSON = "[";
		Gson gson = GsonHelper.GetGsonInstance();

		for (Entry<String, Map<String, String>> entry : NotificationCache.entrySet()) {

			Map<String,String> notification = entry.getValue();
			System.out.println("In UpdateView");
			System.out.println(notification);

			//push the notifications to view.. Naive for now.
			Toast.makeText(getApplicationContext(), 
					notification+"", Toast.LENGTH_SHORT).show();

			String notificationJSON = gson.toJson(notification);
			NotificationMapListJSON=NotificationMapListJSON+notificationJSON+",";

		}

		NotificationMapListJSON=NotificationMapListJSON.substring(0, NotificationMapListJSON.length()-1);
		NotificationMapListJSON+="]";

		System.out.println("THE MAP JSON IS : "+NotificationMapListJSON);

		if(NotificationMapListJSON.equals("]"))
			return;

		Intent intent = new Intent(this,InvitesActivity.class);
		intent.putExtra("NotificationMapListJSON", NotificationMapListJSON);
		TabInvites.setContent(intent);
		//TabHost.addTab(TabInvites);

	}

}
