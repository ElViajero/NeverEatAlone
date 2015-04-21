package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.BitMapHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * ProfileActivity is used to set up the view of Profile page
 * 
 * @author Hai Tang
 */
public class ContactsProfileActivity extends Activity {

	private TextView contactsProfileTitleObject;
	private TextView usernameTextViewObject, aliasTextViewObject,
			nameTextViewObject;
	private TextView workspaceTextViewObject, emailTextViewObject,
			genderTextViewObject;
	private String username;
	private String email;
	private Context context;
	private Activity activity;
	private ProfileView profileView;
	private ContactProperties contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		initView(savedInstanceState);
		getTargetProfileInfo();
	}

	/**
	 * Method used for initializing the view
	 * 
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_profile);

		applyTheme();
	}

	/**
	 * This method is used to set GUI colors
	 * 
	 * @author Hai Tang
	 */
	private void applyTheme() {

		initProfileView();

		View mainLayout = profileView.getView("main_contacts_profile");
		View headerLayout = profileView.getView("header_contacts_profile");
		View buttonBar = profileView.getView("buttons_contacts_profile");
		View deleteContactButton = profileView
				.getView("button_contacts_profile_delete");

		View backContactsProfileButton = profileView
				.getView("button_contacts_profile_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);
		ThemeManager.applyButtonColor(backContactsProfileButton);
		ThemeManager.applyButtonColor(deleteContactButton);
		contactsProfileTitleObject = (TextView) profileView
				.getView("contacts_profile");
		setTitleStyle();

	}

	/**
	 * Method used to initialize ProfileView
	 * 
	 * @author: Hai Tang
	 */
	private void initProfileView() {
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		contactsProfileTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(contactsProfileTitleObject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts_profile, menu);
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
	 * Method for getting profile info from the server and posting it to screen
	 * 
	 * @author tejasvamsingh
	 */
	private void getTargetProfileInfo() {
		// TODO Need to be filled with real target contact's data
		username = AccountProperties.getUserAccountInstance().getusername();
		email = AccountProperties.getUserAccountInstance().getemail();

		initProfileView();
		contact = (ContactProperties) DataCacheHelper
				.getIActivityPropertiesObject();

		// set the avatar
		setAvatar(contact);

		LinearLayout parentLinearLayout = (LinearLayout) findViewById(R.id.contactProfile_dynamicLinearLayout);

		List<Pair> orderedIterationList = contact.getOrderedIterationList();

		for (Pair<?, ?> pair : orderedIterationList) {
			String key = "";
			String value = "";
			try {
				key = pair.first.toString();
				value = pair.second.toString();
			} catch (NullPointerException e) {
				continue;
			}

			if (value.equals(""))
				continue;

			LinearLayout keyValueLayout = (LinearLayout) profileView
					.GetDynamicLayout();
			TextView keyTextView = (TextView) profileView.GetDynamicView();
			TextView valueTextView = (TextView) profileView.GetDynamicView();

			keyTextView.setText(key);
			valueTextView.setText(value);
			setTextViewLayout(keyTextView, valueTextView);

			keyValueLayout.addView(keyTextView);
			keyValueLayout.addView(valueTextView);

			parentLinearLayout.addView(keyValueLayout);

		}

	}

	/**
	 * This helper method sets the spacing between text views.
	 * 
	 * @param keyTextView
	 * @param valueTextView
	 */
	private void setTextViewLayout(TextView keyTextView, TextView valueTextView) {
		keyTextView.setLayoutParams(new TableRow.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 0.5f));
		valueTextView.setLayoutParams(new TableRow.LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 0.5f));

		keyTextView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
		valueTextView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
	}

	/**
	 * This method sets the avatar of the contact.
	 * 
	 * @author tejasvamsingh
	 * @param contact
	 */
	private void setAvatar(ContactProperties contact) {

		if (contact.getContactAvatarString() == null
				|| contact.getContactAvatarString().equals(""))
			return;

		ImageView imageViewObject = (ImageView) profileView
				.getView("imageView_contacts_profile_avatar");

		Bitmap bitmap = BitMapHelper.StringToBitMap(contact
				.getContactAvatarString());

		imageViewObject.setImageBitmap(bitmap);

	}

	/**
	 * This method implements the back button
	 * 
	 * @author Yueling Loh
	 */
	public void onBackButtonClick(View view) {

		Intent intent = new Intent(ContactsProfileActivity.this,
				TabHostActivity.class);
		intent.putExtra("FirstTab", 1);
		ContactsProfileActivity.this.startActivity(intent);
	}

	/**
	 * This method implements the delete contact button
	 * 
	 * @author tejasvamsingh
	 */
	public void onDeleteContactButtonClick(View view) {

		try {

			String requestID = "Contact";
			String requestType = "delete";

			Map<String, Object> requestMap = AccountProperties
					.getUserAccountInstance().toMap();

			requestMap.putAll(contact.toMap());

			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							requestMap, requestID, requestType);

			// set the server fetch bit.
			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					true);
			// invalidate the cache
			DataCacheHelper.cacheResults("contact", null);

			Intent intent = new Intent(ContactsProfileActivity.this,
					TabHostActivity.class);
			intent.putExtra("FirstTab", 1);
			ContactsProfileActivity.this.startActivity(intent);

		} catch (RequestAbortedException e) {
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		MessageToasterHelper.isMessageToastable = true;
	}

	@Override
	protected void onPause() {
		MessageToasterHelper.isMessageToastable = false;
		super.onPause();
	}

}
