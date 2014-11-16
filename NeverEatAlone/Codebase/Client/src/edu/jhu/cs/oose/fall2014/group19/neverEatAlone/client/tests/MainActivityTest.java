package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo; 

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	@After
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	@Test
	public void testOnLoginButtonClick() {
		solo.clickOnButton("Login");
		solo.searchText("Invalid Credentials");
	}

	@Test
	public void testOnCreateOptionsMenuMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnOptionsItemSelectedMenuItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnSignUpButtonClick() {
		solo.clickOnButton("Sign Up");
	}

}
