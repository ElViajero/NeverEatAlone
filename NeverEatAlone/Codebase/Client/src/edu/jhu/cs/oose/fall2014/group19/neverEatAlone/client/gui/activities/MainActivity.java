package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;
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
import android.widget.LinearLayout;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.LoginView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This is the main activity that is displayed when the application starts. 
 * @author tejasvamsingh
 *
 */
public class MainActivity extends Activity {

	private EditText usernameEditTextObject = null;
	private EditText passwordEditTextObject = null;
	private String requestID;
	private String requestType;
	private boolean isCreated=false;
	LinearLayout l;
	Context context;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		activity = this;
		LoginView lv = new LoginView(context, activity);
//		usernameEditTextObject = (EditText) findViewById(R.id.edit_username);
		
//		String string = "edit_username";
//		int resID = getResources().getIdentifier(string,
//			    "id", getPackageName());		
//		usernameEditTextObject = (EditText) findViewById(resID);
		
		usernameEditTextObject = (EditText) lv.getView("edit_username");
		
		passwordEditTextObject = (EditText) findViewById(R.id.edit_password);
		l = (LinearLayout) findViewById(R.id.layout_main);
		//		l.setBackgroundResource(R.drawable.dark_layout_background);
		requestID = "Login";
		requestType = "checkCredentials";
		ThemeManager.applyTheme(l);
		System.out.println("inside onCreate in MainAcitivty");
		MessageToasterHelper.toastMessage(this, "inside oncreate");


		//		TextView tv =
		//				(TextView) findViewById(R.id.tv1);
		//		Typeface tf = Typeface.createFromAsset(getAssets(),
		//				"fonts/Windsong.ttf");
		//		tv.setTypeface(tf);
		//		tv.setTextSize(100);
		//		tv.setText("NeverEatAlone");
	}

	/**
	 * Event handler for client login requests.
	 * @author tejasvamsingh
	 * @param view
	 * @throws FileNotFoundException 
	 * @throws URISyntaxException 
	 */
	public void onLoginButtonClick(View view) throws FileNotFoundException, URISyntaxException {
		ThemeManager.setTheme(R.style.DarkTheme);


		String username = usernameEditTextObject.getText().toString();
		String password = passwordEditTextObject.getText().toString();

		// create the request properties object.
		AccountProperties loginProperties  = new AccountProperties(username, password);
		try{
			//send the request.
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,loginProperties.toMap(),requestID,requestType) ;		
			isCreated=true;
			MessageToasterHelper.toastMessage(this, "Welcome "+username);

			//construct the user account.
			AccountProperties userAccount = new AccountProperties(resultMapList.get(1));

			//start the new activity
			Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
			intent.putExtra("username", username);
			MainActivity.this.startActivity(intent);

		}catch(RequestAbortedException e){
			// This is necessary. The exception has
			//already been handled in the RequestHandler
			//class.
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

	/** 
	 * Called when the user clicks the Sign Up button 
	 * */
	public void onSignUpButtonClick(View view) {		

		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		MainActivity.this.startActivity(intent);
	}

	/**
	 * This method is called when the activity resumes.
	 * @author tejasvamsingh
	 */
	@Override
	protected void onResume(){
		super.onResume();
		System.out.println("in onResume");
		cleanUp();
	}


	/**
	 * This method is used to free resources and gracefully shutdown 
	 * request/response and notification frameworks when the login page
	 * is reached.
	 * @author tejasvamsingh
	 */
	private void cleanUp() {
		if(!isCreated){			
			return;
		}
		RequestHandlerHelper.cleanUp();
		NotificationHelper.cleanUp();

	}

}
