package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;


import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText username = null;
	private EditText password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.edit_username);
		password = (EditText) findViewById(R.id.edit_password);
	}

	public void login(View view) {
		if (username.getText().toString().equals("admin")
				&& password.getText().toString().equals("admin")) {
			Toast.makeText(getApplicationContext(), "Redirecting...",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "Wrong Credentials",
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
	public void sendMessage(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		// Intent intent = new Intent(this, DisplayMessageActivity.class);
		startActivity(intent);
	}

}
