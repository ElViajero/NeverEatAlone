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

/**
 * This class handles controller logic for the registration 
 * Activity.
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
		
		setTitleStyle();

	}
	
	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.register);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Windsong.ttf");
		tv.setTypeface(tf);
		tv.setTextSize(100);
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
	public void OnRegisterButtonClick(View view) {

		// Fetch the fields from the GUI.
		String username = Username.getText().toString();
		String password = Password.getText().toString();
		String email = Email.getText().toString();
		String confirmPassword = ConfirmPassword.getText().toString();

		System.out.println(password);
		System.out.println(confirmPassword);

		// ************ THIS WASN'T WRITTEN BY TEJAS. ***************//
		// *************WHY IS THIS HERE ? !!!! ******************//		
		// ************* REMOVE THIS !!!!!!!!!!! ****************///

		if(username.equals("")){
			Toast.makeText(getApplicationContext(), 
					"Username Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(email.equals("")){
			Toast.makeText(getApplicationContext(), 
					"Email Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(password.equals("")){
			Toast.makeText(getApplicationContext(), 
					"Passwords Is Empty !", Toast.LENGTH_SHORT).show();
			return;
		}

		if(!password.equals(confirmPassword)){
			Toast.makeText(getApplicationContext(), 
					"Passwords Don't Match !", Toast.LENGTH_SHORT).show();
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
					.GetRequestHandlerInstance().HandleRequest(this,requestMap,RequestID,RequestType);
			// Handle the result.
			MessageToasterHelper.toastMessage(this, "Registration Successful");
			Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
			RegisterActivity.this.startActivity(intent);
		}catch(RequestAbortedException e){
			return;
		}

	}

	public void OnCancelButtonClick(View view) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		RegisterActivity.this.startActivity(intent);
	}
}
