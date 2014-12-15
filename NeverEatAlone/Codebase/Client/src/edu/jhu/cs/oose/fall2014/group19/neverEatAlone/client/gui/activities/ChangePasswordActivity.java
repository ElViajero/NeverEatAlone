package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;

/**
 * Activity used for the change password page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 *
 */
public class ChangePasswordActivity extends Activity {

	private Context context;
	private Activity activity;
	private ProfileView profileView;
	private TextView changePasswordTextViewTitleObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);
		
		setTitleStyle();
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		changePasswordTextViewTitleObject = (TextView) profileView.getView("textView_changepassword_title");
		ThemeManager.setHeaderFont(changePasswordTextViewTitleObject);
	}
	
	/**
	 * Method used when confirm buttton is clicked
	 * 
	 * @author: Hai Tang
	 */
	public void onConfirmButtonClick(View view) {
		Intent intent = new Intent(ChangePasswordActivity.this,
				TabHostActivity.class);
		ChangePasswordActivity.this.startActivity(intent);
	}

	/**
	 * Method used when cancel buttton is clicked
	 * 
	 * @author: Hai Tang
	 */
	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(ChangePasswordActivity.this,
				TabHostActivity.class);
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
