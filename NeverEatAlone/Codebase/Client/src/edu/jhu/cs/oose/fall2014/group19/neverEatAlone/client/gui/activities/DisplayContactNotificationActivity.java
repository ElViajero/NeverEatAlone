package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles the display of contact notification.
 * 
 * @author tejasvamsingh
 */
public class DisplayContactNotificationActivity extends ListActivity {

	private ArrayAdapter<IActivityProperties> contactsNotificationAdapter;
	String requestType;
	String requestID;
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
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact_notification);

		acceptButton =new Button(getApplicationContext());
		rejectButton = new Button(getApplicationContext());
		contactList = new ArrayList<IActivityProperties>();
		contactsNotificationAdapter = new ContactsNotificationAdapter(this,
				contactList);

		setListAdapter(contactsNotificationAdapter);

		DataCacheHelper.registerNotificationAdapterInstance(contactsNotificationAdapter, "contact");		
		setTitleStyle();
		requestID = "Contact";
		applyTheme();
	}

	private void applyTheme() {
		ThemeManager.applyTheme(findViewById(android.R.id.content));
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.textView_friendsrequest_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);
	}



	/**
	 * This method handles click events on the listview.
	 * @author tejasvamsingh
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		selectedNotification = (NotificationProperties) 
				contactsNotificationAdapter.getItem(position);
		listItemIndex=position;
		setButtonsVisible(v);


	}


	/**
	 * Toggles visibility of buttons for listview.
	 * @author tejasvamsingh
	 * @param v
	 */
	private void setButtonsVisible(View v) {

		acceptButton.setVisibility(View.INVISIBLE);
		rejectButton.setVisibility(View.INVISIBLE);

		acceptButton = (Button) v.findViewById(R.id.contacts_notification_accept);
		rejectButton = (Button) v.findViewById(R.id.contacts_notification_reject);
		acceptButton.setVisibility(View.VISIBLE);
		rejectButton.setVisibility(View.VISIBLE);
	}

	/**
	 * Handler for the accept button.
	 * @author tejasvamsingh
	 * @param view
	 */
	public void onAcceptContactRequestButtonClick(View view){
		requestType="accept";
		selectedNotification.setAccepted(true);
		sendRequest();		
	}

	/**
	 * @author tejasvamsingh
	 * @param view
	 */
	public void onRejectContactRequestButtonClick(View view){
		requestType="reject";
		sendRequest();		
	}

	/**
	 * sends the request.
	 * @author tejasvamsingh
	 */
	private boolean sendRequest() {

		ContactProperties p = (ContactProperties) selectedNotification.getNotificationData();
		MessageToasterHelper.toastMessage(this,"YES : "+p.toMap());
		System.out.println("selectedNotification : "+ selectedNotification);
		List<String> recipientList = new ArrayList<String>();
		recipientList.add(selectedNotification.getPoster());

		IActivityProperties postProperties = 
				PostProperties.notificationToPost(selectedNotification);
		try{

			List<Map<String, String>> result =
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,postProperties.toMap(),requestID,requestType);

			DataCacheHelper.setServerFetchRequired("contact", true);
			System.out.println("FETCH STATUS :"+
					DataCacheHelper.isServerFetchRequired("contact"));
			contactsNotificationAdapter.remove(selectedNotification);
			return true;

		}catch(RequestAbortedException e){
			return false;
		}

	}



}
