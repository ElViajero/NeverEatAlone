package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.HashMap;
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
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services.ChangePasswordConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * Activity used for the change password page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 * @author Runze Tang
 *
 */
public class ChangePasswordActivity extends Activity {

	private Context context;
	private Activity activity;
	private ProfileView profileView;
	private TextView changePasswordTitle;
	private EditText oldPasswordEditText;
	private EditText newPasswordEditText;
	private EditText confirmPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MessageToasterHelper.toastMessage("Inside OCCC");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		initProfileView();

		changePasswordTitle = (TextView) profileView
				.getView("textView_changepassword_title");

		setTitleStyle();
		applyTheme();
	}

	private void initProfileView() {
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 */
	private void applyTheme() {

		initProfileView();
		View mainLayout = profileView.getView("main_changePassword");
		View headerLayout = profileView.getView("header_changePassword");
		View buttonBar = profileView.getView("buttons_password");

		View confirmButton = profileView
				.getView("button_changepassword_confirm");
		View cancelButton = profileView.getView("button_changepassword_cancel");

		oldPasswordEditText = (EditText) profileView
				.getView("editText_changepassword_oldpassword");
		newPasswordEditText = (EditText) profileView
				.getView("editText_changepassword_newpassword");
		confirmPasswordEditText = (EditText) profileView
				.getView("editText_changepassword_confirmpassword");

		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(confirmButton);
		ThemeManager.applyButtonColor(cancelButton);

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 */
	private void setTitleStyle() {
		ThemeManager.setHeaderFont(changePasswordTitle);
	}

	/**
	 * Method used when confirm buttton is clicked
	 * 
	 * @author tejasvamsingh
	 * 
	 * 
	 */
	public void onConfirmButtonClick(View view) {

		// check the fields.
		String oldPassword = oldPasswordEditText.getText().toString();
		String newPassword = newPasswordEditText.getText().toString();
		String confirmPassword = confirmPasswordEditText.getText().toString();

		Map<String, Object> constraintMap = new HashMap<String, Object>();
		constraintMap.put("old", oldPassword);
		constraintMap.put("new", newPassword);
		constraintMap.put("confirm", confirmPassword);

		IConstraintChecker iConstraintChecker = new ChangePasswordConstraintChecker();

		// if everything is ok, send the request to the server.
		if (iConstraintChecker.areConstraintsSatisfied(constraintMap)) {
			try {

				// set to the new password.
				AccountProperties.getUserAccountInstance().setpassword(
						newPassword);
				// send the request.
				List<Map<String, String>> resultMapList = RequestHandlerHelper
						.getRequestHandlerInstance().handleRequest(
								this,
								AccountProperties.getUserAccountInstance()
										.toMap(), "Account", "update");

				MessageToasterHelper
						.toastMessage("Password changed successfully !");

				// go back to the profile activity
				Intent intent = new Intent(this, ProfileActivity.class);
				this.startActivity(intent);

			} catch (RequestAbortedException e) {
				AccountProperties.getUserAccountInstance().setpassword(
						oldPassword); // rollback to the old password.
				MessageToasterHelper.toastMessage("Password not changed.");
				return;
			}
		}
	}

	/**
	 * Method used when cancel buttton is clicked
	 * 
	 * @author: Hai Tang
	 * @author Runze Tang
	 */
	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(ChangePasswordActivity.this,
				TabHostActivity.class);
		// Go to the specific tab.
		intent.putExtra("FirstTab", 2);
		ChangePasswordActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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
