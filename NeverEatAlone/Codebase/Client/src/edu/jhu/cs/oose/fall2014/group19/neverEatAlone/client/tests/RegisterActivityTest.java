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
 *
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	private Solo solo; 
	
	public RegisterActivityTest() {
		super(RegisterActivity.class);
	}


	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}


	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	/**
	 * tests registration with an existing account 
	 * (registration should fail)
	 */
	public void testOnRegisterButtonClick() {
		//TODO add testing register with an non-existing account here
		// when deleting account functionality is implemented 
		
		//TODO check the pattern of username, pw and email. eg, no space, allowed characters, email pattern, etc.  
		
		// create a test account if it does not already exist
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");
		solo.clickOnButton("Register");
		if(solo.getCurrentActivity().getClass().equals(MainActivity.class))
			solo.clickOnButton("Sign Up"); // If it returns to the main page, get in here again
		
		// registration should fail when user name already exists
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		if(!solo.searchText("Username already exists"))
			fail("Registration should fail when user name already exists.");
		
		// registration should fail when password and confirm-password do not match 
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test1");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		if(!solo.searchText("Passwords Don't Match !"))
			fail("Registration should fail when password and confirm-password do not match.");
		
				
		// registration should fail when no user name is provided
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");		
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		solo.clickOnButton("Register");
		if(!solo.searchText("Username Is Empty !"))
			fail("Registration should fail when no user name is provided.");	
		
		// registration should fail when no email is provided 
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test");
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		solo.clickOnButton("Register");
		if(!solo.searchText("Email Is Empty !"))
			fail("Registration should fail when no email is provided.");
		
		// registration should fail when no password is provided
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("registration fails, should stay on Register page", RegisterActivity.class);
		if(!solo.searchText("Passwords Is Empty !"))
			fail("Registration should fail when no password is provided.");
		
	}

	public void testOnCancelButtonClick() {
		solo.clickOnButton("Cancel");
		solo.assertCurrentActivity("Should go back to main page when canceled", MainActivity.class);

	}

}
