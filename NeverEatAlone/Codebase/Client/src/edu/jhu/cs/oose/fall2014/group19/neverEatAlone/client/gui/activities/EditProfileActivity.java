package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity used for edit profile page
 * 
 * @author Hai Tang
 *
 */
public class EditProfileActivity extends Activity {

	private EditText usernameEditTextObject;
	private EditText nameEditTextObject;
	private EditText emailEditTextObject;
	private EditText genderEditTextObject;
	private EditText workspaceEditTextObject;
	
	private String requestID;
	private String requestType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);

		usernameEditTextObject = (EditText) findViewById(R.id.editText_editprofile_username);
		nameEditTextObject = (EditText) findViewById(R.id.editText_editprofile_name);
		emailEditTextObject = (EditText) findViewById(R.id.editText_editprofile_email);
		genderEditTextObject = (EditText) findViewById(R.id.editText_editprofile_gender);
		workspaceEditTextObject = (EditText) findViewById(R.id.editText_editprofile_workspace);
		
		usernameEditTextObject.setText(AccountProperties.getUserAccountInstance().getusername());
		
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
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.textView_Title_Edit_Profile);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);
	}

	/**
	 * Method used when confirm button is clicked. Gather all the revised
	 * information and return to TabHost Page.
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	public void onComfirmButtonClick(View view) {
		Intent intent = new Intent(EditProfileActivity.this,
				TabHostActivity.class);
		EditProfileActivity.this.startActivity(intent);

		String username = usernameEditTextObject.getText().toString();
		String name = nameEditTextObject.getText().toString();
		String email = emailEditTextObject.getText().toString();
		String gender = genderEditTextObject.getText().toString();
		String workspace = workspaceEditTextObject.getText().toString();
		
		//CHECK VALUE OF QUOTATION MARKS
		//sets what kind of request to make
		requestID = "Account";
		requestType = "Update";
		
		Map<String,Object> requestMap = new HashMap<String,Object>();
		
		//CHECK VALUE OF QUOTATION MARKS
		//sets the values
		requestMap.put("username",username);
		requestMap.put("name",name);
		requestMap.put("email",email);
		requestMap.put("gender",gender);
		requestMap.put("workspace",workspace);
		
		try{
			// send the request.
			List<Map<String, String>> resultMapList = 
							RequestHandlerHelper.getRequestHandlerInstance().
							handleRequest(this,requestMap,requestID,requestType) ;
			Map<String, String> profile = resultMapList.get(0);
		}catch(RequestAbortedException e){
					// This is necessary. The exception has
					//already been handled in the RequestHandler
					//class.
		return;}
	}

	/**
	 * Method used when cancel button is clicked
	 * 
	 * @author: Hai Tang
	 */
	public void onCancelButtonClick(View view) {
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
		requestMap.put("username",AccountProperties.getUserAccountInstance().getusername());
		try{
			// send the request.
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,requestMap,requestID,requestType) ;
			
			Map<String, String> profile = resultMapList.get(0);
			
			//CHECK VALUE OF QUOTATION MARKS
			//set to profile to values from the server
			
			nameEditTextObject.setText(profile.get("name"));
			workspaceEditTextObject.setText(profile.get("workspace"));
			emailEditTextObject.setText(profile.get("email"));
			genderEditTextObject.setText(profile.get("gender"));
	

		}catch(RequestAbortedException e){
			// This is necessary. The exception has
			//already been handled in the RequestHandler
			//class.
			return;}
	
	}
}
