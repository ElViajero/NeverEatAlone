package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.id;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.layout;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.menu;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

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
		
		View backContactsProfileButton = profileView.getView("button_contacts_profile_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);
		ThemeManager.applyButtonColor(backContactsProfileButton);

	}
	
	/**
	 * Method used to initialize ProfileView
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
	 * @author Hai Tang
	 */
	private void getTargetProfileInfo() {
		//TODO Need to be filled with real target contact's data
		username = AccountProperties.getUserAccountInstance().getusername();
		email = AccountProperties.getUserAccountInstance().getemail();

		initProfileView();

		contactsProfileTitleObject = (TextView) profileView.getView("contacts_profile");
		setTitleStyle();
		usernameTextViewObject = (TextView) profileView
				.getView("textView_contactsprofile_username");
		aliasTextViewObject = (TextView) profileView.getView("textView_contactsprofile_alias");
		nameTextViewObject = (TextView) profileView.getView("textView_contactsprofile_Name");
		workspaceTextViewObject = (TextView) profileView
				.getView("textView_contactsprofile_workspace");
		emailTextViewObject = (TextView) profileView.getView("textView_contactsprofile_email");
		genderTextViewObject = (TextView) profileView
				.getView("textView_contactsprofile_Gender");

		profileView.setValue(usernameTextViewObject, username);
		profileView.setValue(emailTextViewObject, email);
		// TODO: Need to be filled with real Strings
		profileView.setValue(aliasTextViewObject, "Needs to be changed");
		profileView.setValue(nameTextViewObject, "Needs to be changed");
		profileView.setValue(workspaceTextViewObject, "Needs to be changed");
		profileView.setValue(genderTextViewObject, "Needs to be changed");

	}
	
	/**
	 * This method implements the back button
	 * 
	 * @author Yueling Loh
	 */
	public void onBackButtonClick(View view) {

		Intent intent = new Intent(ContactsProfileActivity.this, TabHostActivity.class);
		intent.putExtra("FirstTab", 1);
		ContactsProfileActivity.this.startActivity(intent);
	}
}
