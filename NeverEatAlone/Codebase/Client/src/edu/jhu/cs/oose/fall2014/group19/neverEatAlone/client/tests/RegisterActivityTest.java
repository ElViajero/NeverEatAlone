package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This class tests the view and work flow of registration 
 * @author Xiaozhou Zhou
 *
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

	public RegisterActivityTest() {
		super(RegisterActivity.class);
	}


	protected void setUp() throws Exception {
		super.setUp();
		RegisterActivity ar = new RegisterActivity(); 
	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testOnCreateOptionsMenuMenu() {
		fail("Not yet implemented");
	}


	public void testOnOptionsItemSelectedMenuItem() {
		fail("Not yet implemented");
	}

	public void testOnRegisterButtonClick() {
		fail("Not yet implemented");
	}

	public void testOnCancelButtonClick() {
		fail("Not yet implemented");
	}

}
