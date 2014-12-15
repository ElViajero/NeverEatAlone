package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.LoginView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * ProfileActivity is used to set up the view of Profile page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 * @author tejasvamsingh
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

	private void applyTheme() {
		ThemeManager.applyTheme(findViewById(android.R.id.content));

	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		profileTitleObject.setTypeface(tf);
		profileTitleObject.setTextSize(80);
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

		context = this;
		activity = this;
		profileView = new ProfileView(context, activity);

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
		// TODO: Need to be filled with real Strings
		profileView.setValue(aliasTextViewObject, "abc");
		profileView.setValue(nameTextViewObject, "abc");
		profileView.setValue(workspaceTextViewObject, "abc");
		profileView.setValue(emailTextViewObject, email);
		profileView.setValue(genderTextViewObject, "abc");

	}

}
