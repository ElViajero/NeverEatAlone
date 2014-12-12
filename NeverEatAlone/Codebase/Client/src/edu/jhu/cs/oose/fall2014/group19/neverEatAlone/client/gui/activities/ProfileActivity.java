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
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * ProfileActivity is used to set up the view of Profile page
 * 
 * @author Hai Tang
 * @author Yueling Loh
 */
public class ProfileActivity extends Activity {
	
	private PopupWindow deleteAccountPopupWindow;
	private TextView usernameTextView,aliasTextView, nameTextView;
	private TextView workspaceTextView,emailTextView;
	private String username;
	private String requestID;
	private String requestType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		username = AccountProperties.getUserAccountInstance().getusername();
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

		setTitleStyle();
		usernameTextView = (TextView) findViewById(R.id.textView_username);
		aliasTextView = (TextView) findViewById(R.id.textView_alias);
		nameTextView = (TextView) findViewById(R.id.textView_Name);
		workspaceTextView = (TextView) findViewById(R.id.textView_workspace);
		emailTextView =  (TextView) findViewById(R.id.textView_email);
		
		usernameTextView.setText(username);
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
	
	
	/**
	 * Method for getting profile info from the server
	 * and posting it to screen
	 * 
	 * @author Yueling Loh
	 */
	private void getProfileInfo(){
		
		//CHECK VALUE OF QUOTATION MARKS
		//set the kind of request
		requestID = "Account";
		requestType = "GetInfo";
		
		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("username",username);
		try{
			// send the request.
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,requestMap,requestID,requestType) ;
			
			Map<String, String> profile = resultMapList.get(0);
			
			//CHECK VALUE OF QUOTATION MARKS
			//set to profile to values from the server
			aliasTextView.setText(profile.get("alias"));
			nameTextView.setText(profile.get("name"));
			workspaceTextView.setText(profile.get("workspace"));
			emailTextView.setText(profile.get("email"));
			
	

		}catch(RequestAbortedException e){
			// This is necessary. The exception has
			//already been handled in the RequestHandler
			//class.
			return;}
	
	}

}
