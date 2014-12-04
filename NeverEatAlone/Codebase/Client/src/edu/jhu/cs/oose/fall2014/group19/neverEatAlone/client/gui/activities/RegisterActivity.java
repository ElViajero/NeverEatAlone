package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles controller logic for the registration Activity.
 * 
 * @author tejasvamsingh,
 *
 */
public class RegisterActivity extends Activity {

	// fields used by the register activity.
	private EditText Username;
	private EditText Email;
	private EditText Password;
	private EditText ConfirmPassword;
	private String RequestType;
	private String RequestID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// binding the fields to class variables.
		Username = (EditText) findViewById(R.id.edit_username);
		Password = (EditText) findViewById(R.id.edit_password);
		ConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
		Email = (EditText) findViewById(R.id.edit_email);

		// set the RequestType and RequestID fields.
		RequestID = "Account";
		RequestType = "Create";

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	 * Event Handler method for the register button
	 * 
	 * @author tejasvamsingh
	 * @author Yueling Loh
	 * @param view
	 */
	public void OnRegisterButtonClick(View view) {

		// Fetch the fields from the GUI.
		String username = Username.getText().toString();
		String password = Password.getText().toString();
		String email = Email.getText().toString();
		String confirmPassword = ConfirmPassword.getText().toString();

		System.out.println(password);
		System.out.println(confirmPassword);

		// Check if entries are valid
		boolean validRegistration = checkValidRegistration(username, password,
				email, confirmPassword);
		if (!validRegistration)
			return;

		// Create a properties object.
		AccountProperties registerProperties = new AccountProperties(username,
				password, email);

		// Get the request Map
		Map<String, Object> requestMap = registerProperties.toMap();

		try {
			// Initiate the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.GetRequestHandlerInstance().HandleRequest(this,
							requestMap, RequestID, RequestType);
			// Handle the result.
			MessageToasterHelper.toastMessage(this, "Registration Successful");
			Intent intent = new Intent(RegisterActivity.this,
					MainActivity.class);
			RegisterActivity.this.startActivity(intent);
		} catch (RequestAbortedException e) {
			return;
		}

	}

	private boolean checkValidRegistration(String username, String password,
			String email, String confirmPassword) {

		if (username.equals("")) {
			MessageToasterHelper.toastMessage(this, R.string.empty_username);
			return false;
		}

		if (email.equals("")) {
			MessageToasterHelper.toastMessage(this, R.string.empty_email);
			return false;
		}

		if (password.equals("")) {
			MessageToasterHelper.toastMessage(this, R.string.empty_password);
			return false;
		}

		if (!password.equals(confirmPassword)) {
			MessageToasterHelper.toastMessage(this,
					R.string.different_passwords);
			return false;
		}
		return true;
	}

	public void OnCancelButtonClick(View view) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		RegisterActivity.this.startActivity(intent);
	}
}
