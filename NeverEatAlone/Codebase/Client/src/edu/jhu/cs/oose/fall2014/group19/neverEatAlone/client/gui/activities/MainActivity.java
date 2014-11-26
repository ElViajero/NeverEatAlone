package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;
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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.LoginRequestProperties;

public class MainActivity extends Activity {

	private EditText Username = null;
	private EditText Password = null;
	private String RequestID;
	private String RequestType;
	private boolean isCreated=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Username = (EditText) findViewById(R.id.edit_username);
		Password = (EditText) findViewById(R.id.edit_password);
		RequestID = "Login";
		RequestType = "CheckCredentials";
		System.out.println("inside onCreate in MainAcitivty");
		MessageToasterHelper.toastMessage(this, "inside oncreate");
	}

	/**
	 * Event handler for client login requests.
	 * @author tejasvamsingh
	 * @param view
	 * @throws FileNotFoundException 
	 * @throws URISyntaxException 
	 */
	public void OnLoginButtonClick(View view) throws FileNotFoundException, URISyntaxException {


		String username = Username.getText().toString();
		String password = Password.getText().toString();

		// create the request properties object.
		LoginRequestProperties loginProperties  = new LoginRequestProperties(username, password);
		try{
			// send the request.
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.GetRequestHandlerInstance().
					HandleRequest(this,loginProperties.GetRequestMap(),RequestID,RequestType) ;		

			MessageToasterHelper.toastMessage(this, "Welcome "+username);
			//start the new activity
			Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
			intent.putExtra("Username", username);
			MainActivity.this.startActivity(intent);

		}catch(RequestAbortedException e){
			// This is necessary. The exception has
			//already been handled in the RequestHandler
			//class
			return;}



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

	@Override
	protected void onResume(){
		super.onResume();
		System.out.println("in onResume");
		cleanUp();
	}


	private void cleanUp() {
		if(!isCreated){
			isCreated=true;
			return;
		}
		RequestHandlerHelper.cleanUp();
		NotificationHelper.cleanUp();

	}

}
