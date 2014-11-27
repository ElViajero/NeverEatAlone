package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.id;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.layout;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.menu;
/**
 * Activity used for the change password page
 * @author Hai Tang
 *
 */
public class ChangePasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}

	/**
	 * Method used when confirm buttton is clicked
	 * @author: Hai Tang
	 */
	public void OnConfirmButtonClick(View view){
		Intent intent = new Intent(ChangePasswordActivity.this, TabHostActivity.class);
		ChangePasswordActivity.this.startActivity(intent);
	}
	
	/**
	 * Method used when cancel buttton is clicked
	 * @author: Hai Tang
	 */
	public void OnCancelButtonClick(View view){
		Intent intent = new Intent(ChangePasswordActivity.this, TabHostActivity.class);
		ChangePasswordActivity.this.startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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
