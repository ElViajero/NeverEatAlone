package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles the display of contact notification.
 * 
 * 
 */
public class DisplayContactNotificationActivity extends ListActivity {
	private ArrayAdapter<ContactProperties> contactsNotificationAdapter;

	String requestType;
	String requestID;

	List<ContactProperties> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * 
	 * 
	 * 
	 * @param savedInstanceState
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact_notification);

		fetchContacts();
		contactsNotificationAdapter = new ContactsNotificationAdapter(this,
				contactList);
		setListAdapter(contactsNotificationAdapter);

		// Button acceptButton = (Button)
		// findViewById(R.id.contacts_notification_accept);
		// acceptButton.setVisibility(View.GonE);

		// acceptButton.setVisibility(View.VISIBLE);
	}

	// I am using contact instead of contact notification here.
	// Need to revise it into contact notification.
	// So instead we need a method called fetchContactsNotification.

	/**
	 * This method fetches contacts from the server if the cache is empty.
	 * 
	 * @author tejasvamsingh
	 * @return
	 */
	private void fetchContacts() {

		requestID = "Contact";
		requestType = "GetAll";
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());
		contactList = new ArrayList<ContactProperties>();
		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							requestMap, requestID, requestType);

			for (Map<String, String> result : resultMapList) {
				if (result.isEmpty())
					continue;
				contactList.add(new ContactProperties(result));
			}

		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method implements the accept button.
	 * 
	 * 
	 * 
	 */
	public void onAcceptButtonClick(View view) {
		// TODO
		// Need revise here
		Toast.makeText(this, "Accepted!", Toast.LENGTH_SHORT).show();
	}

	/**
	 * This method implements the accept button.
	 * 
	 * 
	 * 
	 */
	public void onRejectButtonClick(View view) {
		// TODO
		// Need revise here
		Toast.makeText(this, "Rejected!", Toast.LENGTH_SHORT).show();
	}

}
