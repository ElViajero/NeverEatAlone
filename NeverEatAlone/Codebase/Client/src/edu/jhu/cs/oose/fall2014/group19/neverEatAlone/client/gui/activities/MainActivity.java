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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * @author Hai Tang
 *
 */
public class MainActivity extends Activity {

	private EditText usernameEditTextObject = null;
	private EditText passwordEditTextObject = null;
	private Button loginButtonObject = null;
	private Button signupButtonObject = null;
	private ImageView logoBigObject = null;
	private String requestID;
	private String requestType;
	private boolean isCreated=false;
	LinearLayout l;
	private Context context;
	private Activity activity;
	private LoginView loginView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		activity = this;
		loginView = new LoginView(context, activity);
		//		usernameEditTextObject = (EditText) findViewById(R.id.edit_username);
		//		passwordEditTextObject = (EditText) findViewById(R.id.edit_password);

		//		String string = "edit_username";
		//		int resID = getResources().getIdentifier(string,
		//			    "id", getPackageName());		
		//		usernameEditTextObject = (EditText) findViewById(resID);

		usernameEditTextObject = (EditText) loginView.getView("edit_username");
		passwordEditTextObject = (EditText) loginView.getView("edit_password");

		loginButtonObject = (Button) loginView.getView("button_login");
		signupButtonObject = (Button) loginView.getView("button_signup");
		logoBigObject = (ImageView) loginView.getView("logo_big");

		applyTheme();
		
		requestID = "Login";
		requestType = "checkCredentials";
		System.out.println("inside onCreate in MainAcitivty");
		MessageToasterHelper.toastMessage(this, "inside oncreate");
		
		

	}
	
	
	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Yueling Loh
	 */
	private void applyTheme() {
		View mainLayout = findViewById(R.id.layout_main);

		ThemeManager.applyBackground(mainLayout);
		
		ThemeManager.applyButtonColor(loginButtonObject);
		ThemeManager.applyButtonColor(signupButtonObject);
	}


	/**
	 * Event handler for client login requests.
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param view
	 * @throws FileNotFoundException 
	 * @throws URISyntaxException 
	 */
	public void onLoginButtonClick(View view) throws FileNotFoundException, URISyntaxException {
		// ThemeManager.setTheme(R.style.DarkTheme);


		//		String username = usernameEditTextObject.getText().toString();
		//		String password = passwordEditTextObject.getText().toString();

		//		String username = usernameEditTextObject.getValue();
		String username = loginView.getValue(usernameEditTextObject);
		String password = loginView.getValue(passwordEditTextObject);

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
			AccountProperties userAccount = new AccountProperties(resultMapList.get(0));

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
