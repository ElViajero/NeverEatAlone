package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;


import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.MainActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.RegisterActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * This class tests the activities on the Main(login/register) page
 * @author Xiaozhou Zhou
 *
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo; 

	public MainActivityTest() {
		super(MainActivity.class);
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
	 * test registration(sign up) activity
	 * Clicking the Sign Up button should take us to the RegisterActivity page.
	 * 
	 */
	public void testOnSignUpButtonClick() {
		solo.clickOnButton("Sign Up");
		solo.assertCurrentActivity("Should go to sign up page", RegisterActivity.class);

	}

	/**
	 * test login activity
	 * If user name or password is not in the database, it should be rejected; 
	 * If user name and password match, it should go to the TabHostActivity page 
	 * (the user account is already created in testOnSignUpButtonClick)
	 */
	public void testOnLoginButtonClick() {
		
		// create a test account if it does not already exist
		solo.clickOnButton("Sign Up");
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_confirm_password), "pw_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_email), "email@test.com");
		solo.clickOnButton("Register");
		if(solo.getCurrentActivity().getClass().equals(RegisterActivity.class))
			solo.clickOnButton("Cancel"); // TODO: if registered, should go back to main page automatically and don't need this

		// reject empty user-pw
		solo.clickOnButton("Login");
		solo.assertCurrentActivity("Should stay on main page", MainActivity.class);
		if(!solo.searchText("Invalid Credentials"))
			fail("empty user-pw");
		
		// reject wrong pw
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_wrong");
		solo.clickOnButton("Login");
		solo.assertCurrentActivity("Should stay on main page", MainActivity.class);
		if(!solo.searchText("Invalid Credentials"))
			fail("wrong password");
		
		// reject wrong user name
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_wrong");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.clickOnButton("Login");
		solo.assertCurrentActivity("Should stay on main page", MainActivity.class);
		if(!solo.searchText("Invalid Credentials"))
			fail("wrong user name");
		
		// correct user name and pw: login succeeds
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username));
		solo.clearEditText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password));
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_username), "usr_test");
		solo.enterText((EditText)solo.getCurrentActivity().findViewById(R.id.edit_password), "pw_test");
		solo.clickOnButton("Login");
		if(!solo.searchText("Welcome usr_test !"))
			fail("should show welcome message");
		solo.assertCurrentActivity("Should go to TabHost page", TabHostActivity.class);

	}

}
