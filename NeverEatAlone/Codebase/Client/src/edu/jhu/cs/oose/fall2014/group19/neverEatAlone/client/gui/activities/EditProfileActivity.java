package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity used for edit profile page
 * 
 * @author Hai Tang
 *
 */
public class EditProfileActivity extends Activity {

	private EditText Username;
	private EditText Name;
	private EditText Email;
	private EditText Gender;
	private EditText Workspace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);

		Username = (EditText) findViewById(R.id.editText_editprofile_username);
		Name = (EditText) findViewById(R.id.editText_editprofile_name);
		Email = (EditText) findViewById(R.id.editText_editprofile_email);
		Gender = (EditText) findViewById(R.id.editText_editprofile_gender);
		Workspace = (EditText) findViewById(R.id.editText_editprofile_workspace);
	}

	/**
	 * Method for initializing the view
	 * 
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		setTitleStyle();
	}
	
	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.textView_Title_Edit_Profile);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Windsong.ttf");
		tv.setTypeface(tf);
		tv.setTextSize(100);
	}

	/**
	 * Method used when confirm button is clicked. Gather all the revised
	 * information and return to TabHost Page.
	 * 
	 * @author: Hai Tang
	 */
	public void OnComfirmButtonClick(View view) {
		Intent intent = new Intent(EditProfileActivity.this,
				TabHostActivity.class);
		EditProfileActivity.this.startActivity(intent);

		String username = Username.getText().toString();
		String name = Name.getText().toString();
		String email = Email.getText().toString();
		String gender = Gender.getText().toString();
		String workspace = Workspace.getText().toString();

		Toast.makeText(
				getApplicationContext(),
				"Username: " + username + "\n" + "Name: " + name + "\n"
						+ "Email: " + email + "\n" + "Gender: " + gender + "\n"
						+ "Workspace: " + workspace, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Method used when cancel button is clicked
	 * 
	 * @author: Hai Tang
	 */
	public void OnCancelButtonClick(View view) {
		Intent intent = new Intent(EditProfileActivity.this,
				TabHostActivity.class);
		EditProfileActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
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
