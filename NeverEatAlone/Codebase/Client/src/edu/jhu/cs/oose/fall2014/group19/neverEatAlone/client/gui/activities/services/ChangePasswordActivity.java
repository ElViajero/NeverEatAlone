package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;

/**
 * Activity used for the change password page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 * @author Runze Tang
 *
 */
public class ChangePasswordActivity extends Activity {

	private Context context;
	private Activity activity;
	private ProfileView profileView;
	private TextView changePasswordTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		initProfileView();

		changePasswordTitle = (TextView) profileView
				.getView("textView_changepassword_title");

		setTitleStyle();
		applyTheme();
	}

	private void initProfileView() {
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 */
	private void applyTheme() {

		initProfileView();
		View mainLayout = profileView.getView("main_changePassword");
		View headerLayout = profileView.getView("header_changePassword");
		View buttonBar = profileView.getView("buttons_password");

		View registerButton = profileView
				.getView("button_changepassword_confirm");
		View cancelButton = profileView.getView("button_changepassword_cancel");

		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(registerButton);
		ThemeManager.applyButtonColor(cancelButton);

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 */
	private void setTitleStyle() {
		ThemeManager.setHeaderFont(changePasswordTitle);
	}

	/**
	 * Method used when confirm buttton is clicked
	 * 
	 * @author: Hai Tang
	 * @author Runze Tang
	 */
	public void onConfirmButtonClick(View view) {
		Intent intent = new Intent(ChangePasswordActivity.this,
				TabHostActivity.class);
		// Go to the specific tab.
		intent.putExtra("FirstTab", 2);
		ChangePasswordActivity.this.startActivity(intent);
	}

	/**
	 * Method used when cancel buttton is clicked
	 * 
	 * @author: Hai Tang
	 * @author Runze Tang
	 */
	public void onCancelButtonClick(View view) {
		Intent intent = new Intent(ChangePasswordActivity.this,
				TabHostActivity.class);
		// Go to the specific tab.
		intent.putExtra("FirstTab", 2);
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
