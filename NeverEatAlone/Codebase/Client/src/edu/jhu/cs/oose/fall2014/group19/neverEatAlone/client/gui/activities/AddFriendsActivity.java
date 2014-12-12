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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This activity is used to set the view and control the logic related to add
 * friends function
 * 
 * @author Hai Tang
 * @author tejasvamsingh
 *
 */
public class AddFriendsActivity extends Activity {

	private EditText usernameEditTextObject;
	private EditText emailEditTextObject;
	private String requestID;
	private String requestType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initview(savedInstanceState);

		usernameEditTextObject = (EditText) findViewById(R.id.editText_addfriends_username);
		emailEditTextObject = (EditText) findViewById(R.id.editText_addfriends_email);

	}

	/**
	 * Method used to initialize the view
	 * 
	 * @author: Hai Tang
	 */
	private void initview(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friends);

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
				(TextView) findViewById(R.id.textView_addfriends_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);
	}

	/**
	 * Method used for clicking the back button
	 * 
	 * @author: Hai Tang
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(AddFriendsActivity.this,
				TabHostActivity.class);
		AddFriendsActivity.this.startActivity(intent);
	}

	/**
	 * Method used for clicking the search button.
	 * @author tejasvamsingh 
	 * @author: Hai Tang
	 */
	public void onSearchButtonClick(View view) {

		String username = usernameEditTextObject.getText().toString();
		String email = emailEditTextObject.getText().toString();

		requestID = "Contact";
		requestType="add";
		IActivityProperties contact = new ContactProperties(username);

		try{

			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,contact.toMap(),requestID,requestType) ;		


			if(resultMapList.get(0).get("Status").equals("Success"))
				MessageToasterHelper.toastMessage(this, "Contact Added !");

			//start the new activity
			Intent intent = new Intent(this, TabHostActivity.class);
			this.startActivity(intent);


		}catch(RequestAbortedException e){

		}

		Intent intent = new Intent(AddFriendsActivity.this,
				TabHostActivity.class);
		AddFriendsActivity.this.startActivity(intent);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friends, menu);
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
}
