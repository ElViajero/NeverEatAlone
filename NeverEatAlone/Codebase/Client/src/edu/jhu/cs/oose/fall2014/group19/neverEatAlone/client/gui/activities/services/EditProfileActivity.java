package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.EmailValidatorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * Activity used for edit profile page
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * @author Runze Tang
 *
 */
public class EditProfileActivity extends Activity {

	private TextView usernameTextObject;
	private EditText nameEditTextObject;
	private EditText emailEditTextObject;
	private Spinner genderSpinnerObject;
	private EditText workspaceEditTextObject;
	// private EditText aliasEditTextObject;
	private EmailValidatorHelper validator;
	private TextView editProfileTitle;

	private Context context;
	private Activity activity;
	private ProfileView profileView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);

		initViewObjects();

		editProfileTitle = (TextView) profileView
				.getView("textView_Title_Edit_Profile");
		usernameTextObject = (TextView) profileView
				.getView("textView_editprofile_username2");
		nameEditTextObject = (EditText) profileView
				.getView("editText_editprofile_name");
		emailEditTextObject = (EditText) profileView
				.getView("editText_editprofile_email");
		genderSpinnerObject = (Spinner) profileView
				.getView("spinner_editprofile_gender");
		workspaceEditTextObject = (EditText) profileView
				.getView("editText_editprofile_workspace");

		setFields();

		validator = new EmailValidatorHelper();

		setTitleStyle();
		applyTheme();
	}

	/**
	 * 
	 * This method populates fields based on current values in our user account
	 * object.
	 *
	 * @author tejasvamsingh
	 */
	private void setFields() {

		profileView.setValue(usernameTextObject, AccountProperties
				.getUserAccountInstance().getusername());
		profileView.setValue(emailEditTextObject, AccountProperties
				.getUserAccountInstance().getemail());
		profileView.setValue(nameEditTextObject, AccountProperties
				.getUserAccountInstance().getName());
		profileView.setValue(workspaceEditTextObject, AccountProperties
				.getUserAccountInstance().getWorkPlace());

		String gender = AccountProperties.getUserAccountInstance().getGender();
		if (gender.equals("Male"))
			genderSpinnerObject.setSelection(1);
		else if (gender.equals("Female"))
			genderSpinnerObject.setSelection(2);

	}

	/**
	 * Method used to initialize View Objects
	 * 
	 * @author: Hai Tang
	 */
	private void initViewObjects() {
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		ThemeManager.setHeaderFont(editProfileTitle);
	}

	private void applyTheme() {
		initViewObjects();

		View mainLayout = profileView.getView("main_editProfile");
		View headerLayout = profileView.getView("header_editProfile");
		View buttonBar = profileView.getView("buttons_editProfile");

		View comfirmButton = profileView.getView("button_editprofile_comfirm");
		View cancelButton = profileView.getView("button_editprofile_cancel");

		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(comfirmButton);
		ThemeManager.applyButtonColor(cancelButton);

		ThemeManager.applyEditTextColor(nameEditTextObject);
		ThemeManager.applyEditTextColor(emailEditTextObject);
		ThemeManager.applyButtonColor(genderSpinnerObject);
		ThemeManager.applyEditTextColor(workspaceEditTextObject);
		// ThemeManager.applyEditTextColor(aliasEditTextObject);

	}

	/**
	 * Method used when confirm button is clicked. Gather all the revised
	 * information and return to TabHost Page.
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 * 
	 */
	public void onConfirmButtonClick(View view) {

		String username = AccountProperties.getUserAccountInstance()
				.getusername();
		String password = AccountProperties.getUserAccountInstance()
				.getpassword();

		String name = profileView.getValue(nameEditTextObject);
		String email = profileView.getValue(emailEditTextObject);
		String workPlace = profileView.getValue(workspaceEditTextObject);

		String gender = genderSpinnerObject.getSelectedItem().toString();
		MessageToasterHelper.toastMessage(gender);
		if (gender.equals("--"))
			gender = "";

		AccountProperties newAccountPropertiesObject = new AccountProperties(
				username, password);

		newAccountPropertiesObject.setName(name);
		newAccountPropertiesObject.setemail(email);
		newAccountPropertiesObject.setWorkPlace(workPlace);
		newAccountPropertiesObject.setGender(gender);

		if (!validator.isValid(email)) {
			Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		String requestID = "Account";
		String requestType = "update";

		try {
			// send the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							newAccountPropertiesObject.toMap(), requestID,
							requestType);

			AccountProperties userAccountProperties = AccountProperties
					.getUserAccountInstance();

			userAccountProperties.setName(name);
			userAccountProperties.setemail(email);
			userAccountProperties.setWorkPlace(workPlace);
			userAccountProperties.setGender(gender);

			Intent intent = new Intent(this, TabHostActivity.class);
			this.startActivity(intent);

		} catch (RequestAbortedException e) {
			return;
		}

		// if we reach here, we need to try to send the request to the server.

	}

	/**
	 * Method used when cancel button is clicked
	 * 
	 * @author: Hai Tang
	 * @author Runze Tang
	 */
	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(EditProfileActivity.this,
				TabHostActivity.class);
		// Go to the specific tab.
		intent.putExtra("FirstTab", 2);
		EditProfileActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
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

}
