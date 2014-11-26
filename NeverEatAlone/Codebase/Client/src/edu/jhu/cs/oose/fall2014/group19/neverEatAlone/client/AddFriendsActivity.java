package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;

/**
 * This activity is used to set the view and control the logic related to add
 * friends function
 * 
 * @author Hai Tang
 *
 */
public class AddFriendsActivity extends Activity {

	private EditText Username;
	private EditText Email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initview(savedInstanceState);

		Username = (EditText) findViewById(R.id.editText_addfriends_username);
		Email = (EditText) findViewById(R.id.editText_addfriends_email);

	}

	/**
	 * Method used to initialize the view
	 * 
	 * @author: Hai Tang
	 */
	private void initview(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friends);
	}

	/**
	 * Method used for clicking the back button
	 * 
	 * @author: Hai Tang
	 */
	public void OnBackButtonClick(View view) {
		Intent intent = new Intent(AddFriendsActivity.this,
				TabHostActivity.class);
		AddFriendsActivity.this.startActivity(intent);
	}

	/**
	 * Method used for clicking the search button. 
	 * @author: Hai Tang
	 */
	public void OnSearchButtonClick(View view) {
		Intent intent = new Intent(AddFriendsActivity.this,
				TabHostActivity.class);
		AddFriendsActivity.this.startActivity(intent);

		String username = Username.getText().toString();
		String email = Email.getText().toString();
		Toast.makeText(getApplicationContext(),
				"Username: " + username + "\n" + "Email: " + email + "\n",
				Toast.LENGTH_SHORT).show();
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
