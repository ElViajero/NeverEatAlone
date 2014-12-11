package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * ProfileActivity is used to set up the view of Profile page
 * 
 * @author Hai Tang
 */
public class ProfileActivity extends Activity {

	private PopupWindow deleteAccountPopupWindow;

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

		setTitleStyle();
	}
	
	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.profile);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);
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
	 * Methods for delete account button click. A popup window will be shown for
	 * confirmation.
	 * 
	 * @author: Hai Tang
	 */
	public void onDeleteAccountButtonClick(View view) {

		View popupview = initPopupWindow();

		final Button confirmButton = (Button) popupview
				.findViewById(R.id.button_popup_confirm);
		final Button cancelButton = (Button) popupview
				.findViewById(R.id.button_popup_cancel);

		/**
<<<<<<< HEAD
		 * onClickListener for the confirm button in the popup window
=======
		 * OnClickListener for the confirm button in the popup window. Account deleted 
		 * and return to the login page.
>>>>>>> refs/heads/myGUI_Iter5_v9
		 * 
		 * @author: Hai Tang
		 */
		confirmButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteAccountPopupWindow.dismiss();

				Intent intent = new Intent(ProfileActivity.this,
						MainActivity.class);
				ProfileActivity.this.startActivity(intent);
			}

		});

		/**
		 * onClickListener for the cancel button in the popup window
		 * 
		 * @author: Hai Tang
		 */
		cancelButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteAccountPopupWindow.dismiss();
			}

		});
	}

	/**
	 * Method used to initialize the popup window
	 * 
	 * @author: Hai Tang
	 */
	private View initPopupWindow() {
		LayoutInflater inflator = (LayoutInflater) ProfileActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupview = inflator.inflate(R.layout.popup_delete_account_layout,
				null);
		deleteAccountPopupWindow = new PopupWindow(popupview,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		deleteAccountPopupWindow
		.showAtLocation(popupview, Gravity.CENTER, 0, 0);
		deleteAccountPopupWindow.setFocusable(true);
		deleteAccountPopupWindow.setAnimationStyle(BIND_IMPORTANT);

		return popupview;
	}

	/**
	 * Methods for edit profile button click
	 * 
	 * @author: Hai Tang
	 */
	public void onEditButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this,
				EditProfileActivity.class);
		ProfileActivity.this.startActivity(intent);
	}

	/**
	 * Methods for change password button click
	 * 
	 * @author: Hai Tang
	 */
	public void onChangepasswordButtonClick(View view) {
		Intent intent = new Intent(ProfileActivity.this,
				ChangePasswordActivity.class);
		ProfileActivity.this.startActivity(intent);
	}

}
