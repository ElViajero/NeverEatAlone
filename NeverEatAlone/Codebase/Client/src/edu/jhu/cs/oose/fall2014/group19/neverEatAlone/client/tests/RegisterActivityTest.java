package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.MainActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * This class tests the activities on the Register page
 * @author Xiaozhou Zhou
 * @author Yueling Loh
 *
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	private Solo solo; 
	
	public RegisterActivityTest() {
		super(RegisterActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	/**
	 * tests registration with an existing account 
	 * (registration should fail)
	 */
	public void testDuplicateUser() {
		EditText vUserName = (EditText)solo.getView(R.id.edit_username);
		EditText vEditPassword = (EditText)solo.getView(R.id.edit_password);
		EditText vConfirmPassword = (EditText)solo.getView(R.id.edit_confirm_password);
		EditText vEmail = (EditText)solo.getView(R.id.edit_email);

		// create a test account if it does not already exist
		solo.enterText(vUserName, "usr_test");
		solo.enterText(vEmail, "email@test.com");
		solo.enterText(vEditPassword, "pw_test");
		solo.enterText(vConfirmPassword, "pw_test");
		solo.clickOnButton("Register");
		if(solo.getCurrentActivity().getClass().equals(MainActivity.class))
			solo.clickOnButton("Sign Up"); // If it returns to the main page, get in here again
		
		// Registration should fail when user name already exists
		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
		solo.enterText(vUserName, "usr_test");
		solo.enterText(vEmail, "email2@test.com");
		solo.enterText(vEditPassword, "pw_test");
		solo.enterText(vConfirmPassword, "pw_test");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		if(!solo.searchText("Username already exists."))
			fail("Registration should fail when user name already exists in database.");

	}
	
	
//	public void testDuplicateEmail() {
//		EditText vUserName = (EditText)solo.getView(R.id.edit_username);
//		EditText vEditPassword = (EditText)solo.getView(R.id.edit_password);
//		EditText vConfirmPassword = (EditText)solo.getView(R.id.edit_confirm_password);
//		EditText vEmail = (EditText)solo.getView(R.id.edit_email);
//
//		// create a test account if it does not already exist
//		solo.enterText(vUserName, "usr_test");
//		solo.enterText(vEmail, "email@test.com");
//		solo.enterText(vEditPassword, "pw_test");
//		solo.enterText(vConfirmPassword, "pw_test");
//		solo.clickOnButton("Register");
//		if(solo.getCurrentActivity().getClass().equals(MainActivity.class))
//			solo.clickOnButton("Sign Up"); // If it returns to the main page, get in here again
//		
//		// Registration should fail when user name already exists
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr_test");
//		solo.enterText(vEmail, "email2@test.com");
//		solo.enterText(vEditPassword, "pw_test");
//		solo.enterText(vConfirmPassword, "pw_test");
//		solo.clickOnButton("Register");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		if(!solo.searchText("Username already exists."))
//			fail("Registration should fail when user name already exists in database.");
//		
//		// Registration should fail when email name already exists
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr3_test");
//		solo.enterText(vEmail, "email@test.com");
//		solo.enterText(vEditPassword, "pw_test");
//		solo.enterText(vConfirmPassword, "pw_test");
//		solo.clickOnButton("Register");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		if(!solo.searchText("Email already exists."))
//			fail("Registration should fail when email already exists in database.");
//	}
//	
//	/**
//	 * tests registration with an existing account 
//	 * (registration should fail)
//	 */
//	public void testEmptyUsername() {
//		EditText vUserName = (EditText)solo.getView(R.id.edit_username);
//		EditText vEditPassword = (EditText)solo.getView(R.id.edit_password);
//		EditText vConfirmPassword = (EditText)solo.getView(R.id.edit_confirm_password);
//		EditText vEmail = (EditText)solo.getView(R.id.edit_email);
//
//		// Registration should fail when passwords do not match
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr4_test");
//		solo.enterText(vEmail, "email4@test.com");
//		solo.enterText(vEditPassword, "pw4_test");
//		solo.enterText(vConfirmPassword, "pw4_test1");
//		solo.clickOnButton("Register");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		if(!solo.searchText("Passwords do not match."))
//			fail("Registration should fail when password and confirm-password do not match.");
//		
//		// Registration should fail when no user name is provided
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vEmail, "email5@test.com");
//		solo.enterText(vEditPassword, "pw_test");
//		solo.enterText(vConfirmPassword, "pw_test");	
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		solo.clickOnButton("Register");
//		if(!solo.searchText("No username provided."))
//			fail("Registration should fail when no user name is provided.");	
//		
//		// Registration should fail when no email is provided 
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr6_test");
//		solo.enterText(vEditPassword, "pw_test");
//		solo.enterText(vConfirmPassword, "pw_test");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		solo.clickOnButton("Register");
//		if(!solo.searchText("No email provided."))
//			fail("Registration should fail when no email is provided.");
//		
//		// Registration should fail when no password is provided
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr8_test");
//		solo.enterText(vEmail, "email8@test.com");
//		solo.enterText(vConfirmPassword, "pw_test");
//		solo.clickOnButton("Register");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		if(!solo.searchText("No password provided."))
//			fail("Registration should fail when no password is provided.");
//		
//		// Registration should fail when no password is provided (both fields)
//		clearAllFields(vUserName, vEditPassword, vConfirmPassword, vEmail);
//		solo.enterText(vUserName, "usr9_test");
//		solo.enterText(vEmail, "email9@test.com");
//		solo.clickOnButton("Register");
//		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
//		if(!solo.searchText("No password provided."))
//			fail("Registration should fail when passwords are not provided.");
//		
//	}


	private void clearAllFields(EditText vUserName, EditText vEditPassword,
			EditText vConfirmPassword, EditText vEmail) {
		solo.clearEditText(vUserName);
		solo.clearEditText(vEditPassword);
		solo.clearEditText(vConfirmPassword);
		solo.clearEditText(vEmail);
	}

	public void testOnCancelButtonClick() {
		solo.clickOnButton("Cancel");
		solo.assertCurrentActivity("Should go back to main page when canceled", MainActivity.class);

	}

}
