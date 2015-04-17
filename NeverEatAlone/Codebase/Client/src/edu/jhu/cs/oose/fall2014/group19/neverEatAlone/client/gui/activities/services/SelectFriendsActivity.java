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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This activity is used to handle controller operations for select friends
 * page.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 *
 */
public class SelectFriendsActivity extends ListFragment {

	private String requestID;
	private String requestType = "getAll";
	private ContactsInformationAdapter selectFriendsAdapter;
	List<ContactProperties> contactList;
	private String postData;
	private TextView selectFriendTitle;
	private TextView swipeTitle;
	private Context context;
	private Activity activity;
	private FragmentView fragmentView;
	private PostProperties populateFromPost;
	private View rootView;

	Button broadcastSelectfriendsButton;
	Button postSelectfriendsButton;
	Button backSelectfriendsButton;

	// some constructors. Needed to instantiate the correct
	// fragment.

	public SelectFriendsActivity() {
		requestType = "getAll";
	}

	public SelectFriendsActivity(String requestType, String postData) {
		this.requestType = requestType;
		this.postData = postData;
	}

	// the activity definition begins here

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_select_friends,
				container, false);
		initView(savedInstanceState);
		populateFromPost = null;
		// check if we're editing a post
		if (DataCacheHelper.getGenericFlag()
				&& DataCacheHelper.getIActivityPropertiesObject() != null) {
			try {
				populateFromPost = (PostProperties) DataCacheHelper
						.getIActivityPropertiesObject();
			} catch (ClassCastException e) {
			} finally {
				// DataCacheHelper.setGenericFlag(false);
			}
		}
		initView(savedInstanceState);
		// postData = getIntent().getStringExtra("mealProperties");
		return rootView;

	}

	/**
	 * Method used to initialize the view.
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initContactView();
		selectFriendTitle = (TextView) fragmentView
				.getView("textView_selectfriends_title");
		swipeTitle = (TextView) fragmentView.getView("textView_swipe_title");

		contactList = new ArrayList<ContactProperties>();
		selectFriendsAdapter = new ContactsInformationAdapter(getActivity(),
				contactList);
		selectFriendsAdapter.setShowCheckboxes(true);
		setListAdapter(selectFriendsAdapter);

		fetchContacts();
		setTitleStyle();
		applyTheme();

		// set the button listeners
		setBroadcastButtonListener();
		setPostButtonListener();
		setBackButtonListener();

		setSelectedContacts();
	}

	private void setBackButtonListener() {
		View.OnClickListener backButtonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (populateFromPost != null) {
					DataCacheHelper.setGenericFlag(true);
					populateFromPost = null;
				}

				Intent intent = new Intent(getActivity(),
						CreateMealInformationActivity.class);
				SelectFriendsActivity.this.startActivity(intent);

			}
		};

		backSelectfriendsButton.setOnClickListener(backButtonListener);

	}

	private void setPostButtonListener() {

		View.OnClickListener postButtonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				requestID = "Meal";
				requestType = "create";
				if (populateFromPost != null)
					requestType = "update";

				List<String> recipientList = new ArrayList<String>();

				for (ContactProperties contact : contactList) {
					if (contact.isChecked())
						recipientList.add(contact.getContactusername());
				}

				if (recipientList.isEmpty()) {
					Toast.makeText(getActivity(), R.string.no_invitees,
							Toast.LENGTH_SHORT).show();
					return;
				}

				PostProperties postProperties = new PostProperties(
						recipientList, "meal", postData);

				// if we're editing
				if (populateFromPost != null) {
					postProperties.setPostID(populateFromPost.getPostID());
					requestType = "update";
					populateFromPost = null;
					DataCacheHelper.setGenericFlag(false);
				}

				try {
					List<Map<String, String>> resultMapList = RequestHandlerHelper
							.getRequestHandlerInstance().handleRequest(
									getActivity(), postProperties.toMap(),
									requestID, requestType);

					NotificationAndPostCacheHelper.addPost(postProperties,
							"mealPost");

				} catch (RequestAbortedException e) {
					System.out.println("Already Handled");
				}

				Intent intent = new Intent(getActivity(), TabHostActivity.class);
				SelectFriendsActivity.this.startActivity(intent);

			}
		};

		postSelectfriendsButton.setOnClickListener(postButtonListener);

	}

	/**
	 * This method sets the button listener for the broadcast button
	 */
	private void setBroadcastButtonListener() {
		View.OnClickListener broadcastButtonListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (ContactProperties contact : contactList) {
					contact.setChecked(true);
				}
				updateView(contactList);

			}
		};

		broadcastSelectfriendsButton
				.setOnClickListener(broadcastButtonListener);
	}

	/**
	 * This method selects contacts that were original recipients in case of an
	 * edited post.
	 * 
	 * @author tejasvamsingh
	 */
	private void setSelectedContacts() {
		if (populateFromPost == null)
			return;

		// get the recipients for the post being edited.

		try {
			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(getActivity(),
							populateFromPost.toMap(), "Meal", "getRecipients");
			List<String> recipientList = new ArrayList<String>();
			for (Map<String, String> map : result) {
				if (map.containsKey("recipientUsername"))
					recipientList.add(map.get("recipientUsername"));
			}
			populateFromPost.setRecipientList(recipientList);

		} catch (RequestAbortedException e) {
		}

		for (ContactProperties contact : contactList) {
			if (populateFromPost.getRecipientList().contains(
					contact.getContactusername())) {
				contact.setChecked(true);
				selectFriendsAdapter.addUncheckableContact(contact);
			}

		}

		selectFriendsAdapter.notifyDataSetChanged();
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initContactView();
		View mainLayout = fragmentView.getView("layout_selectfriends");
		View headerLayout = fragmentView.getView("header_selectfriends");
		View buttonBar = fragmentView.getView("buttons_selectfriends");
		View buttonBarButtom = fragmentView
				.getView("buttons_selectfriends_buttom");

		backSelectfriendsButton = (Button) fragmentView
				.getView("button_selectfriends_back");

		postSelectfriendsButton = (Button) fragmentView
				.getView("button_selectfriends_post");

		broadcastSelectfriendsButton = (Button) fragmentView
				.getView("button_selectfriends_broadcast");

		// View unselectSelectfriendsButton =
		// contactsView.getView("button_selectfriends_unselectall");

		ThemeManager.applyDoubleBarTheme(mainLayout, headerLayout, buttonBar,
				buttonBarButtom);
		// ThemeManager.applyPlainTheme(mainLayout, headerLayout,
		// buttonBarButtom);

		ThemeManager.applyButtonColor(backSelectfriendsButton);
		ThemeManager.applyButtonColor(postSelectfriendsButton);
		ThemeManager.applyButtonColor(broadcastSelectfriendsButton);
		// ThemeManager.applyButtonColor(unselectSelectfriendsButton);

	}

	/**
	 * Method used to initialize ContactView.
	 * 
	 * @author: Hai Tang
	 */
	private void initContactView() {
		context = rootView.getContext();
		fragmentView = new FragmentView(context, rootView);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		if (requestType.equals("getAll")) {
			selectFriendTitle.setText("All Contacts");
			swipeTitle.setText("Nearby >>");
			selectFriendTitle.setGravity(android.view.Gravity.LEFT);
			swipeTitle.setGravity(android.view.Gravity.RIGHT);
		} else {
			selectFriendTitle.setText("Nearby Contacts");
			selectFriendTitle.setGravity(android.view.Gravity.RIGHT);
			swipeTitle.setGravity(android.view.Gravity.LEFT);
			swipeTitle.setText("<< All");
		}
		ThemeManager.setHeaderFont(selectFriendTitle);
	}

	/**
	 * This method is used to populate the contacts list.
	 * 
	 * @author tejasvamsingh
	 */

	private void fetchContacts() {

		requestID = "Contact";
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());
		// new ArrayList<ContactProperties>();
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
			selectFriendsAdapter.notifyDataSetChanged();
		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Method used to unselect all the friend in the user's contact list.
	 * 
	 * @author: Hai Tang
	 */

	private void updateView(List<ContactProperties> contactList) {
		selectFriendsAdapter.notifyDataSetChanged();
	}

}
