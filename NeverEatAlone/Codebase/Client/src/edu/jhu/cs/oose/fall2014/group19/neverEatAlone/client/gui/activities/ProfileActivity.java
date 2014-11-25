package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.ChangePasswordActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.EditProfileActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * ProfileActivity is used to set up the view of Profile page
 * @author Hai Tang 
 */
public class ProfileActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * Method used for initializing the view
	 * 
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}

	/**
	 * Method for logout button click
	 * 
	 * @author: Hai Tang
	 */
	public void onLogoutButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
		ProfileActivity.this.startActivity(intent);
	}

	/**
	 * Methods for delete account button click
	 * 
	 * @author: Hai Tang
	 */
	public void onDeleteAccountButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
		ProfileActivity.this.startActivity(intent);
	}

	/**
	 * Methods for edit profile button click
	 * 
	 * @author: Hai Tang
	 */
	public void OnEditButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this,
				EditProfileActivity.class);
		ProfileActivity.this.startActivity(intent);
	}

	/**
	 * Methods for change password button click
	 * 
	 * @author: Hai Tang
	 */
	public void OnChangePasswordButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this,
				ChangePasswordActivity.class);
		ProfileActivity.this.startActivity(intent);
	}
	
}
