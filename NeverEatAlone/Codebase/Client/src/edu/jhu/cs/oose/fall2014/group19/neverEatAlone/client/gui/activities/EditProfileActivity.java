package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.EmailValidatorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity used for edit profile page
 * 
 * @author Hai Tang
 * @author Runze Tang
 *
 */
public class EditProfileActivity extends Activity {

	private TextView usernameTextObject;
	private EditText nameEditTextObject;
	private EditText emailEditTextObject;
	private EditText genderEditTextObject;
	private EditText workspaceEditTextObject;
	private EditText aliasEditTextObject;
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
		genderEditTextObject = (EditText) profileView
				.getView("editText_editprofile_gender");
		workspaceEditTextObject = (EditText) profileView
				.getView("editText_editprofile_workspace");
		aliasEditTextObject = (EditText) profileView
				.getView("editText_editprofile_alias");

		profileView.setValue(usernameTextObject, AccountProperties
				.getUserAccountInstance().getusername());

		validator = new EmailValidatorHelper();

		setTitleStyle();
		applyTheme();
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
		ThemeManager.applyEditTextColor(genderEditTextObject);
		ThemeManager.applyEditTextColor(workspaceEditTextObject);
		ThemeManager.applyEditTextColor(aliasEditTextObject);

	}

	/**
	 * Method used when confirm button is clicked. Gather all the revised
	 * information and return to TabHost Page.
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	public void onComfirmButtonClick(View view) {
		Intent intent = new Intent(EditProfileActivity.this,
				TabHostActivity.class);
		// Go to the specific tab.
		intent.putExtra("FirstTab", 2);
		EditProfileActivity.this.startActivity(intent);

		// String username = usernameTextObject.getText().toString();
		String name = profileView.getValue(nameEditTextObject);
		String email = profileView.getValue(emailEditTextObject);
		String gender = profileView.getValue(genderEditTextObject);
		String workspace = profileView.getValue(workspaceEditTextObject);
		String alias = profileView.getValue(aliasEditTextObject);

		if (!validator.isValid(email)) {
			Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Set new values to AccountProperties to show in Profile page
		AccountProperties.getUserAccountInstance().setemail(email);
		// TODO Hai's Comment:Need to populate other values

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
