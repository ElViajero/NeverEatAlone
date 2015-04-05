package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.ContactsActivity;

public class ContactsActivityTest extends ActivityInstrumentationTestCase2<ContactsActivity> {

	private Solo solo; 

	public ContactsActivityTest() {
		super(ContactsActivity.class);
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
	 * Tests the checkboxes 
	 */
	public void testCheckBox(){
		solo.assertCurrentActivity("should be on ContactsActivity", ContactsActivity.class);
		assertTrue("checkbox 0 should be unchecked!", !solo.isCheckBoxChecked(0));
		assertTrue("checkbox 1 should be checked!", solo.isCheckBoxChecked(1));
		assertTrue("checkbox 3 should be unchecked!", !solo.isCheckBoxChecked(3));

		solo.clickOnCheckBox(0);
		assertTrue("checkbox 0 is checked!", solo.isCheckBoxChecked(0));

		solo.clickOnCheckBox(1);
		assertTrue("checkbox 1 is unchecked!", !solo.isCheckBoxChecked(1));

		solo.clickOnCheckBox(3);
		assertTrue("checkbox 3 is checked!", solo.isCheckBoxChecked(3));
		//		}
	}

}
