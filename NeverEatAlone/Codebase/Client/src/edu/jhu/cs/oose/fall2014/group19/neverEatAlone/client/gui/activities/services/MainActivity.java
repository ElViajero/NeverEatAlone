package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.DateAndTimeProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services.RegistrationAndLoginContraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.LoginView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.locationManager.services.LocationFinder;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.services.NotificationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests.DeletetLocationRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests.GetContactsExecutableRequest;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This is the main activity that is displayed when the application starts.
 * 
 * @author tejasvamsingh
 * @author Yueling Loh
 * @author Hai Tang
 *
 */
public class MainActivity extends Activity {

	private EditText usernameEditTextObject = null;
	private EditText passwordEditTextObject = null;
	private Button loginButtonObject = null;
	private Button signupButtonObject = null;
	// private ImageView logoBigObject = null;
	private String requestID;
	private String requestType;
	private boolean isCreated = false;
	private TextView mainTitle;
	LinearLayout l;
	private Context context;
	private Activity activity;
	private LoginView loginView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLoginView();

		context = this;
		activity = this;
		loginView = new LoginView(context, activity);

		MessageToasterHelper.contextObject = getApplicationContext();

		usernameEditTextObject = (EditText) loginView.getView("edit_username");
		passwordEditTextObject = (EditText) loginView.getView("edit_password");

		loginButtonObject = (Button) loginView.getView("button_login");
		signupButtonObject = (Button) loginView.getView("button_signup");
		// logoBigObject = (ImageView) loginView.getView("logo_big");

		mainTitle = (TextView) loginView.getView("logo_name");

		applyTheme();

		requestID = "Login";
		requestType = "checkCredentials";
		System.out.println("inside onCreate in MainAcitivty");

	}

	/**
	 * Method used to initialize LoginView
	 * 
	 * @author: Hai Tang
	 */
	private void initLoginView() {
		context = this;
		activity = this;
		loginView = new LoginView(context, activity);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Yueling Loh
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initLoginView();
		View mainLayout = loginView.getView("layout_main");

		// ThemeManager.applyBackground(mainLayout);

		ThemeManager.applyTheme(mainLayout);
		ThemeManager.applyButtonColor(loginButtonObject);
		ThemeManager.applyButtonColor(signupButtonObject);

		ThemeManager.setTitleFont(mainTitle);
	}

	/**
	 * Event handler for client login requests.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param view
	 * @throws FileNotFoundException
	 * @throws URISyntaxException
	 */
	public void onLoginButtonClick(View view) throws FileNotFoundException,
			URISyntaxException {
		// ThemeManager.setTheme(R.style.DarkTheme);

		final String username = loginView.getValue(usernameEditTextObject);
		final String password = loginView.getValue(passwordEditTextObject);

		// create the request properties object.
		AccountProperties loginProperties = new AccountProperties(username,
				password);

		AccountProperties.setUserAccountInstance(loginProperties);

		Map<String, Object> constraintMap = new HashMap<String, Object>();

		constraintMap.put("username", username);
		constraintMap.put("password", password);

		if (new RegistrationAndLoginContraintChecker()
				.areConstraintsSatisfied(constraintMap)) {
			try {
				// send the request.
				List<Map<String, String>> resultMapList = RequestHandlerHelper
						.getRequestHandlerInstance()
						.handleRequest(this, loginProperties.toMap(),
								requestID, requestType);

				isCreated = true;
				MessageToasterHelper.toastMessage(this, "Welcome " + username);

				// construct the user account.
				AccountProperties userAccount = new AccountProperties(
						resultMapList.get(0));

				// we want the user string not the encrypted version.
				AccountProperties.getUserAccountInstance()
						.setpassword(password);

				// delete old posts
				deleteOldPosts();

				// get permission to use location services.
				createLocationAlert(username);

			} catch (RequestAbortedException e) {
				RequestHandlerHelper.cleanUp();
				return;
			}
		}

	}

	/**
	 * 
	 * Helper method to create an alert dialog asking for permission to use
	 * location services.
	 * 
	 * @author tejasvamsingh
	 * 
	 * @param username
	 */
	private void createLocationAlert(final String username) {

		new AlertDialog.Builder(this)
				.setTitle("Permission to Use Location")
				.setMessage(
						"NeverEatAlone would like to use your approximate location to find nearby contacts and places to eat.")
				.setPositiveButton("Allow", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startLocationService();
						// fetchContacts
						fetchContacts();
						// start the new activity
						Intent intent = new Intent(MainActivity.this,
								TabHostActivity.class);
						intent.putExtra("username", username);
						MainActivity.this.startActivity(intent);

					}
				})
				.setNegativeButton("Don't Allow",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// fetchContacts
								fetchContacts();

								deleteLocation();

								// start the new activity
								Intent intent = new Intent(MainActivity.this,
										TabHostActivity.class);
								intent.putExtra("username", username);
								MainActivity.this.startActivity(intent);

							}

						}).show();
	}

	/**
	 * Helper method that deletes the user's lacation.
	 */
	private void deleteLocation() {

		DeletetLocationRequestExecutor deletetLocationRequestExecutor = new DeletetLocationRequestExecutor();
		deletetLocationRequestExecutor.executeRequest(this);
	}

	private void startLocationService() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationFinder locationFinder = new LocationFinder(this,
				locationManager);
		locationFinder.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("in onResume");
		cleanUp();
	}

	/**
	 * This method is used to free resources and gracefully shutdown
	 * request/response and notification frameworks when the login page is
	 * reached.
	 * 
	 * @author tejasvamsingh
	 */
	private void cleanUp() {
		if (!isCreated) {
			return;
		}
		RequestHandlerHelper.cleanUp();
		NotificationHelper.cleanUp();
		NotificationAndPostCacheHelper.cleanUp();
		DataCacheHelper.cleanUp();

	}

	/**
	 * This method removes posts that w
	 */
	private void deleteOldPosts() {

		try {
			Map<String, Object> requestMap = AccountProperties
					.getUserAccountInstance().toMap();
			requestMap.put("currentDateAndTimeString", DateAndTimeProperties
					.getCurrentDateAndTimeProperties().toString());
			RequestHandlerHelper.getRequestHandlerInstance().handleRequest(
					this, requestMap, "Meal", "deleteOld");

		} catch (RequestAbortedException e) {
		}

	}

	private void fetchContacts() {

		GetContactsExecutableRequest getContactsExecutableRequest = new GetContactsExecutableRequest();
		getContactsExecutableRequest.executeRequest(this);

	}
}
