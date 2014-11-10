package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText Username = null;
	private EditText Password = null;
	private String RequestID;
	private String RequestType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Username = (EditText) findViewById(R.id.edit_username);
		Password = (EditText) findViewById(R.id.edit_password);
		RequestID = "Login";
		RequestType = "CheckCredentials";
	}

	/**
	 * Event handler for client login requests.
	 * @param view
	 */
	public void OnLoginButtonClick(View view) {
		
		String username = Username.getText().toString();
		String password = Password.getText().toString();
		
		ArrayList<NameValuePair> requestList = new ArrayList<NameValuePair>();
		requestList.add(new BasicNameValuePair("RequestID", RequestID));
		requestList.add(new BasicNameValuePair("RequestType", RequestType));
		requestList.add(new BasicNameValuePair("Username", username));
		requestList.add(new BasicNameValuePair("Password", password));
		
		List<Map<String, String>> resultMapList = 
				RequestHandlerHelper.GetRequestHandlerInstance().HandleRequest(requestList) ;		
		  
		if(resultMapList.get(0).get("Status").equals("Success")){
			Toast.makeText(getApplicationContext(), "Welcome "+username+" !",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this, NotificationTestActivity.class);
			intent.putExtra("Username", username);
			MainActivity.this.startActivity(intent);
		}
		else{
			Toast.makeText(getApplicationContext(), "Invalid Credentials",
					Toast.LENGTH_SHORT).show();
		}
		  		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		// case R.id.action_search:
		// // openSearch();
		// return true;
		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** Called when the user clicks the Sign Up button */
	public void OnSignUpButtonClick(View view) {		
		
		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		MainActivity.this.startActivity(intent);
	}

}
