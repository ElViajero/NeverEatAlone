package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		RegisterActivity ar = new RegisterActivity(); 
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
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
	public void testOnRegisterButtonClick() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnCancelButtonClick() {
		fail("Not yet implemented");
	}

}
