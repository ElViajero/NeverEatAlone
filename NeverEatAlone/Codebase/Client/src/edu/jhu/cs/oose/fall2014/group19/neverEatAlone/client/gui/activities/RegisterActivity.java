package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	// fields used by the register activity.
	private EditText Username;
	private EditText Email;
	private EditText Password;
	private EditText ConfirmPassword;
	private String RequestType;
	private String RequestID;
	private List<NameValuePair> requestList;

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

	public void OnRegisterButtonClick(View view) {

		// Fetch the fields from the GUI.
		String username = Username.getText().toString();
		String password = Password.getText().toString();
		String email = Email.getText().toString();
		String confirmPassword = ConfirmPassword.getText().toString();

		requestList = new ArrayList<NameValuePair>();
		requestList.add(new BasicNameValuePair("RequestID", RequestID));
		requestList.add(new BasicNameValuePair("RequestType", RequestType));
		requestList.add(new BasicNameValuePair("Username", username));
		requestList.add(new BasicNameValuePair("Password", password));
		requestList.add(new BasicNameValuePair("Email", email));

		List<Map<String, String>> resultMapList = RequestHandlerHelper
				.GetRequestHandlerInstance().HandleRequest(requestList);

		if (resultMapList.get(0).get("Status").equals("Success")) {
			Toast.makeText(getApplicationContext(), "Registration Succesful !",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Username already exists",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void OnCancelButtonClick(View view) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		RegisterActivity.this.startActivity(intent);
	}
}
