package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.EmailValidatorHelper;

/**
 * This class handles controller logic for the registration Activity.
 * 
 * @author tejasvamsingh,
 * @author Yueling Loh
 *
 */
public class RegisterActivity extends Activity {

	// fields used by the register activity.
	private EditText usernameET;
	private EditText emailET;
	private EditText passwordET;
	private EditText confirmPasswordET;
	private String requestType;
	private String requestID;
	private EmailValidatorHelper validator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// binding the fields to class variables.
		usernameET = (EditText) findViewById(R.id.edit_username);
		passwordET = (EditText) findViewById(R.id.edit_password);
		confirmPasswordET = (EditText) findViewById(R.id.edit_confirm_password);
		emailET = (EditText) findViewById(R.id.edit_email);

		// set the RequestType and RequestID fields.

		requestID = "Account";
		requestType = "Create";
		validator = new EmailValidatorHelper();

		
		setTitleStyle();
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
				(TextView) findViewById(R.id.register);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);;
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
	public void onRegisterButtonClick(View view) {

		// Fetch the fields from the GUI.
		String username = usernameET.getText().toString();
		String password = passwordET.getText().toString();
		String email = emailET.getText().toString();
		String confirmPassword = confirmPasswordET.getText().toString();

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
					.getRequestHandlerInstance().handleRequest(this,
							requestMap, requestID, requestType);
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
			Toast.makeText(this, R.string.empty_username,Toast.LENGTH_SHORT).show();
			return false;
		}

		if (email.equals("")) {
			Toast.makeText(this, R.string.empty_email,Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(!validator.isValid(email)){
			Toast.makeText(this, R.string.invalid_email,Toast.LENGTH_SHORT).show();
			return false;
		}

		if (password.equals("")) {
			Toast.makeText(this, R.string.empty_password,Toast.LENGTH_SHORT).show();
			return false;
		}

		if (!password.equals(confirmPassword)) {
			Toast.makeText(this, R.string.different_passwords,Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		RegisterActivity.this.startActivity(intent);
	}
}
