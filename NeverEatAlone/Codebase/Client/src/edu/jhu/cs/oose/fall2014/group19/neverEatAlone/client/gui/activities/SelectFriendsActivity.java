package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This activity is used to handle controller operations for select friends
 * page.
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 *
 */
public class SelectFriendsActivity extends ListActivity {

	private String requestID;
	private String requestType;
	private ArrayAdapter<ContactProperties> selectFriendsAdapter;
	List<ContactProperties> contactList;
	private String postData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
		postData = getIntent().getStringExtra("mealProperties");
		applyTheme();
	}

	private void applyTheme() {
		ThemeManager.applyTheme(findViewById(android.R.id.content));

	}

	/**
	 * Method used to initialize the view.
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_friends);
		fetchContacts();
		selectFriendsAdapter = new ContactsInformationAdapter(this, contactList);
		setListAdapter(selectFriendsAdapter);

		setTitleStyle();
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		TextView tv = (TextView) findViewById(R.id.textView_selectfriends_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);
	}

	/**
	 * This method is used to populate the contacts list.
	 * 
	 * @author tejasvamsingh
	 */

	private void fetchContacts() {

		requestID = "Contact";
		requestType = "getAll";
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_friends, menu);
		return true;
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
	 * Method for back button click
	 * 
	 * @author: Hai Tang
	 */

	public void onBackButtonClick(View view) {
		Intent intent = new Intent(SelectFriendsActivity.this,
				CreateMealInformationActivity.class);
		SelectFriendsActivity.this.startActivity(intent);
	}

	/**
	 * This method is the event handler for the post button.
	 * 
	 * @author tejasvamsingh
	 * @author Runze Tang
	 */
	public void onPostButtonClick(View view) {

		requestID = "Notification";
		requestType = "meal";
		List<String> recipientList = new ArrayList<String>();

		for (ContactProperties contact : contactList) {
			if (contact.isChecked())
				recipientList.add(contact.getContactusername());
		}

		if (recipientList.isEmpty()) {
			Toast.makeText(this, R.string.no_invitees, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		IActivityProperties postProperties = new PostProperties(recipientList,
				"meal", postData);

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postProperties.toMap(), requestID, requestType);

		} catch (RequestAbortedException e) {
			System.out.println("Already Handled");
		}

		/**
		 * Used to go back to the invites page.
		 * 
		 * @author Hai Tang
		 * @author Runze Tang
		 */
		Intent intent = new Intent(SelectFriendsActivity.this,
				InvitesActivity.class);
		SelectFriendsActivity.this.startActivity(intent);

	}

	/**
	 * Method used to select all the friend in the user's contact list.
	 * 
	 * @author: Hai Tang
	 */
	public void onBroadcastButtonClick(View view) {
		for (ContactProperties contact : contactList) {
			contact.setChecked(true);
		}

		updateView(contactList);
	}

	/**
	 * Method used to unselect all the friend in the user's contact list.
	 * 
	 * @author: Hai Tang
	 */
	public void onUnselectAllButtonClick(View view) {
		for (ContactProperties contact : contactList) {
			contact.setChecked(false);
		}

		updateView(contactList);
	}

	/**
	 * Method used to update view after clicking the broadcast button
	 * 
	 * @author: Hai Tang
	 */
	private void updateView(List<ContactProperties> contactList) {

		selectFriendsAdapter = new ContactsInformationAdapter(this, contactList);
		setListAdapter(selectFriendsAdapter);

	}

}
