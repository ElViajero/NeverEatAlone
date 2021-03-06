package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles controller operations for the contacts tab
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 */
public class NearbyContactsActivity extends ListFragment {

	private ContactsInformationAdapter contactsInformationAdapter;
	private TextView contactTitleObject;
	private TextView swipeTitleObject;
	// private Button friendRequestButtonObejct;
	private Button addFriendButtonObject;
	private Context context;
	private Activity activity;
	private FragmentView fragmentView;

	String requestType = "getNearby";
	String requestID;
	View rootView;

	List<ContactProperties> contactList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_nearby_contacts,
				container, false);
		initView(savedInstanceState);
		// initContactView();
		// ThemeManager.applyButtonBarTheme(rootView
		// .findViewById(R.id.buttons_contacts));

		return rootView;

	}

	/**
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @param savedInstanceState
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {

		initContactView();

		contactTitleObject = (TextView) fragmentView
				.getView("textView_nearby_contacts_title");

		swipeTitleObject = (TextView) fragmentView
				.getView("textView_nearby_swipe_title");
		// friendRequestButtonObejct = (Button) fragmentView
		// .getView("button_contacts_notification");
		addFriendButtonObject = (Button) fragmentView
				.getView("button_nearby_contacts_addcontacts");

		initContactView();

		contactTitleObject = (TextView) fragmentView
				.getView("textView_nearby_contacts_title");
		// friendRequestButtonObejct = (Button) fragmentView
		// .getView("button_contacts_notification");
		addFriendButtonObject = (Button) fragmentView
				.getView("button_nearby_contacts_addcontacts");
		contactList = new ArrayList<ContactProperties>();
		contactsInformationAdapter = new ContactsInformationAdapter(
				getActivity(), contactList);
		contactsInformationAdapter.setShowCheckboxes(false);
		setListAdapter(contactsInformationAdapter);

		fetchContacts();

		setTitleStyle();
		applyTheme();
	}

	/**
	 * Method used to initialize the ContactView. Slighly different from others
	 * since this is a fragment.
	 * 
	 * @author tejasvamsingh
	 */
	private void initContactView() {
		context = rootView.getContext();
		fragmentView = new FragmentView(context, rootView);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Yueling Loh
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initContactView();

		View mainLayout = fragmentView.getView("main_nearby_contacts");
		View headerLayout = fragmentView.getView("header_nearby_contacts");
		View buttonBar = fragmentView.getView("buttons_nearby_contacts");

		if (mainLayout == null)
			System.out.println("main layout is null");
		if (headerLayout == null)
			System.out.println("header layout is null");
		if (buttonBar == null)
			System.out.println("button bar is null");

		// View contactsNotificationButton = fragmentView
		// .getView("button_contacts_notification");
		View addContactsButton = fragmentView
				.getView("button_nearby_contacts_addcontacts");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		// ThemeManager.applyButtonColor(contactsNotificationButton);
		ThemeManager.applyButtonColor(addContactsButton);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author tejasvamsingh
	 * 
	 */
	private void setTitleStyle() {

		contactTitleObject.setText("Nearby Contacts");
		swipeTitleObject.setGravity(android.view.Gravity.LEFT);
		swipeTitleObject.setText("<<");

		swipeTitleObject.setTextColor(Color.GRAY);
		contactTitleObject.setGravity(android.view.Gravity.CENTER);

		ThemeManager.setHeaderFont(contactTitleObject);
	}

	/**
	 * This method fetches contacts from the server if the cache is empty.
	 * 
	 * @author tejasvamsingh
	 * @return
	 */
	private void fetchContacts() {

		requestID = "Contact";
		// requestType = "getAll";

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(getActivity(),
							requestMap, requestID, requestType);

			contactList.clear();
			Set<ContactProperties> contactSet = new HashSet<ContactProperties>();

			for (Map<String, String> result : resultMapList) {
				if (result.isEmpty())
					continue;
				contactSet.add(new ContactProperties(result));
			}

			contactList.addAll(contactSet);

			contactsInformationAdapter.notifyDataSetChanged();
			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					false);

		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method for add friends button click
	 * 
	 * @author: Hai Tang
	 */
	public void onAddFriendsButtonClick(View view) {
		Intent intent = new Intent(getActivity(), AddFriendsActivity.class);
		getActivity().startActivity(intent);
	}

	/**
	 * Method for contact notification button click
	 * 
	 * 
	 */
	public void onContactNotificationButtonClick(View view) {
		Intent intent = new Intent(getActivity(),
				DisplayContactNotificationActivity.class);
		getActivity().startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		MessageToasterHelper.isMessageToastable = true;
		if (NotificationAndPostCacheHelper.isServerFetchRequired("contact")) {
			contactList.clear(); // you may no longer have any contacts.
			contactsInformationAdapter.notifyDataSetChanged();
			fetchContacts();
		}
	}

	/**
	 * This method goes to the ContactsProfileActivity when clicking specific
	 * contacts. It also passes the ContactsProperties to the
	 * ContactsProfileActivity.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param position
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		DataCacheHelper.setIActivityPropertiesObject(contactList.get(position));
		Intent intent = new Intent(getActivity(), ContactsProfileActivity.class);
		getActivity().startActivity(intent);

	}

	@Override
	public void onPause() {
		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}

}
