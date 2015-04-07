package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableRow;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.BitMapHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ProfileView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

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
	Bitmap drawnBitmap = null; // currently drawn bitmap. Used to check bitmap
								// recycling.

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

		View changePasswordButton = profileView
				.getView("button_profile_changepassword");
		View editProfileButton = profileView.getView("button_profile_edit");
		View logoutButton = profileView.getView("button_profile_logout");
		View deleteAccountButton = profileView.getView("button_delete_account");

		ThemeManager.applyDoubleBarTheme(mainLayout, headerLayout,
				buttonBarTop, buttonBarBottom);

		ThemeManager.applyButtonColor(changePasswordButton);
		ThemeManager.applyButtonColor(editProfileButton);
		ThemeManager.applyButtonColor(logoutButton);
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
	 * @author tejasvamsingh
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
		final ProfileActivity instance = this;

		/**
		 * OnClickListener for the confirm button in the popup window. Account
		 * deleted and return to the login page.
		 * 
		 * @author tejasvamsingh
		 * @author: Hai Tang
		 */
		confirmButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					String requestID = "Account";
					String requestType = "delete";
					List<Map<String, String>> resultMapList = RequestHandlerHelper
							.getRequestHandlerInstance().handleRequest(
									instance,
									AccountProperties.getUserAccountInstance()
											.toMap(), requestID, requestType);

					Intent intent = new Intent(ProfileActivity.this,
							MainActivity.class);
					ProfileActivity.this.startActivity(intent);

				} catch (RequestAbortedException e) {
					return;
				} finally {
					deleteAccountPopupWindow.dismiss();
				}
			}

		});

		/**
		 * onClickListener for the cancel button in the popup window
		 * 
		 * @author tejasvamsingh
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
	 * 
	 * @author: Hai Tang
	 */
	public void onChangepasswordButtonClick(View view) {

		Intent intent = new Intent(this, ChangePasswordActivity.class);
		this.startActivity(intent);
	}

	/**
	 * Method for getting profile info from the server or AccountProperties
	 * object and posting it to screen
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @author Yueling Loh
	 * 
	 * 
	 */
	private void getProfileInfo() {

		username = AccountProperties.getUserAccountInstance().getusername();
		email = AccountProperties.getUserAccountInstance().getemail();
		String name = AccountProperties.getUserAccountInstance().getName();

		initProfileView();

		profileTitleObject = (TextView) profileView.getView("profile");
		setTitleStyle();

		LinearLayout rv = (LinearLayout) profileView.getView(// "layout_profile_container");
				"profile_dynamic_layout");

		rv.removeAllViews();

		// add the profile image.

		ImageView imageView = (ImageView) profileView
				.getView("imageView_profile_avatar");

		String avatarString = AccountProperties.getUserAccountInstance()
				.getAvatar();
		Bitmap avatarBitmap = BitMapHelper.StringToBitMap(avatarString);

		checkAndSetImageView(imageView, avatarBitmap);

		for (Pair<?, ?> pairObject : AccountProperties.getUserAccountInstance()
				.getOrderedIterationList()) {

			String key = pairObject.first.toString();
			String value = pairObject.second.toString();

			if (value.equals(""))
				continue;

			LinearLayout linearLayout = (LinearLayout) profileView
					.GetDynamicLayout();

			TextView keyView = (TextView) profileView.GetDynamicView();
			keyView.setText(key);

			keyView.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 0.3f));

			linearLayout.addView(keyView);

			TextView valueView = (TextView) profileView.GetDynamicView();
			valueView.setText(value);
			valueView.setLayoutParams(new TableRow.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 0.7f));

			valueView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
			linearLayout.addView(valueView);

			rv.addView(linearLayout);

		}
	}

	/**
	 * This method checks for bitmap recycling and sets the imageView to the
	 * correct image.
	 * 
	 * @param imageView
	 * @param avatar
	 * @author tejasvamsingh
	 */
	private void checkAndSetImageView(ImageView imageView, Bitmap avatar) {
		if (avatar != null)
			imageView.setImageBitmap(avatar);
	}

	/**
	 * Entered when the page resumes.
	 * 
	 * @author tejasvamsingh
	 */

	@Override
	protected void onResume() {
		super.onResume();
		getProfileInfo();
	}

}
