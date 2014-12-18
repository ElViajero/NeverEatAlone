package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;

/**
 * ProfileActivity is used to set up the view of Profile page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 * @author tejasvamsingh
 * @author Runze Tang
 */
public class ProfileActivity extends Activity {

	private PopupWindow deleteAccountPopupWindow;
	private TextView profileTitleObject;
	private TextView usernameTextViewObject, aliasTextViewObject,
			nameTextViewObject;
	private TextView workspaceTextViewObject, emailTextViewObject,
			genderTextViewObject;
	private String username;
	private String email;
	private Context context;
	private Activity activity;
	private ProfileView profileView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		initView(savedInstanceState);
		getProfileInfo();
	}

	/**
	 * Method used for initializing the view
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		applyTheme();
	}

	/**
	 * This method is used to set GUI colors
	 * 
	 * @author: Yueling Loh
	 * @author Hai Tang
	 */

	private void applyTheme() {
		
		initProfileView();
		
		View mainLayout = profileView.getView("main_profile");
		View headerLayout = profileView.getView("header_profile");
		View buttonBarBottom = profileView.getView("buttons_profile");
		View buttonBarTop = profileView.getView("buttons_profile_top");
		
		View changePasswordButton = profileView.getView("button_profile_changepassword");
		View editProfileButton = profileView.getView("button_profile_edit");
		View logoutButton = profileView.getView("button_profile_logout");
		View deleteAccountButton = profileView.getView("button_delete_account");

		
		ThemeManager.applyDoubleBarTheme(mainLayout, headerLayout,buttonBarTop, buttonBarBottom); 
		
		ThemeManager.applyButtonColor(changePasswordButton);
		ThemeManager.applyButtonColor(editProfileButton);
		ThemeManager.applyButtonColor(logoutButton );
		ThemeManager.applyButtonColor(deleteAccountButton);


	}
	

	/**
	 * Method used to initialize ProfileView
	 * 
	 * @author: Hai Tang
	 */
	private void initProfileView() {
		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		ThemeManager.setHeaderFont(profileTitleObject);
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
		
		final View mainPopUpView = popupview
				.findViewById(R.id.textView_popup_deletehint);
		final View buttonPopUpView = popupview
				.findViewById(R.id.buttons_delete_account_popup);
		
		ThemeManager.applyPopUpTheme(mainPopUpView, buttonPopUpView);
		ThemeManager.applyButtonColor(confirmButton);
		ThemeManager.applyButtonColor(cancelButton);


		/**
		 * OnClickListener for the confirm button in the popup window. Account
		 * deleted and return to the login page.
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

	/**
	 * Method for getting profile info from the server and posting it to screen
	 * 
	 * @author Hai Tang
	 * @author Yueling Loh
	 */
	private void getProfileInfo() {

		username = AccountProperties.getUserAccountInstance().getusername();
		email = AccountProperties.getUserAccountInstance().getemail();

		initProfileView();

		profileTitleObject = (TextView) profileView.getView("profile");
		setTitleStyle();
		usernameTextViewObject = (TextView) profileView
				.getView("textView_username");
		aliasTextViewObject = (TextView) profileView.getView("textView_alias");
		nameTextViewObject = (TextView) profileView.getView("textView_Name");
		workspaceTextViewObject = (TextView) profileView
				.getView("textView_workspace");
		emailTextViewObject = (TextView) profileView.getView("textView_email");
		genderTextViewObject = (TextView) profileView
				.getView("textView_Gender");

		profileView.setValue(usernameTextViewObject, username);
		profileView.setValue(emailTextViewObject, email);
		// TODO: Need to be filled with real Strings
		profileView.setValue(aliasTextViewObject, " ");
		profileView.setValue(nameTextViewObject, " ");
		profileView.setValue(workspaceTextViewObject, " ");
		profileView.setValue(genderTextViewObject, " ");

	}

}
