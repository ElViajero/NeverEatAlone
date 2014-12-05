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
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles controller logic for the registration 
 * Activity.
 * 
 * @author tejasvamsingh,
 *
 */
public class RegisterActivity extends Activity {

	// fields used by the register activity.
	private EditText usernameEditTextObject;
	private EditText emailEditTextObject;
	private EditText passwordEditTextObject;
	private EditText Confirmpassword;
	private String requestType;
	private String requestID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// binding the fields to class variables.
		usernameEditTextObject = (EditText) findViewById(R.id.edit_username);
		passwordEditTextObject = (EditText) findViewById(R.id.edit_password);
		Confirmpassword = (EditText) findViewById(R.id.edit_confirm_password);
		emailEditTextObject = (EditText) findViewById(R.id.edit_email);

		// set the requestType and requestID fields.

		requestID = "Account";
		requestType = "Create";

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
	 * @param view
	 */
	public void onRegisterButtonClick(View view) {

		// Fetch the fields from the GUI.
		String username = usernameEditTextObject.getText().toString();
		String password = passwordEditTextObject.getText().toString();
		String email = emailEditTextObject.getText().toString();
		String confirmpassword = Confirmpassword.getText().toString();

		System.out.println(password);
		System.out.println(confirmpassword);

		// ************ THIS WASN'T WRITTEN BY TEJAS. ***************//
		// *************WHY IS THIS HERE ? !!!! ******************//		
		// ************* REMOVE THIS !!!!!!!!!!! ****************///

		if(username.equals("")){
			Toast.makeText(getApplicationContext(), 
					"username Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(email.equals("")){
			Toast.makeText(getApplicationContext(), 
					"email Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(password.equals("")){
			Toast.makeText(getApplicationContext(), 
					"passwords Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(!password.equals(confirmpassword)){
			Toast.makeText(getApplicationContext(), 
					"passwords Don't Match !", Toast.LENGTH_SHORT).show();
			return;
		}

		// ************ THIS WASN'T WRITTEN BY TEJAS. ***************//
		// *************WHY IS THIS HERE ? !!!! ******************//
		// ************* REMOVE THIS !!!!!!!!!!! ****************///


		// ********** TEJAS' CODE STARTS HERE ******************//

		// Create a properties object.
		AccountProperties registerProperties = 
				new AccountProperties(username, password,email);


		//Get the request Map 
		Map<String, Object> requestMap = registerProperties.toMap();

		try{
			// Initiate the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,requestMap,requestID,requestType);
			// Handle the result.
			MessageToasterHelper.toastMessage(this, "Registration Successful");
			Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
			RegisterActivity.this.startActivity(intent);
		}catch(RequestAbortedException e){
			return;
		}

	}

	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		RegisterActivity.this.startActivity(intent);
	}
}
