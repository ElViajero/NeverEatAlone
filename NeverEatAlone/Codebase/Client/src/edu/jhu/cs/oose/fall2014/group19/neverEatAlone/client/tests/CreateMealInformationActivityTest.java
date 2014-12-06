package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.CreateMealInformationActivity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

/**
 * This class tests the activities on the CreateMealInformationActivitiy
 * 
 * @author Yueling Loh
 *
 */
public class CreateMealInformationActivityTest extends
		ActivityInstrumentationTestCase2<CreateMealInformationActivity> {

	private Solo solo;

	public CreateMealInformationActivityTest() {
		super(CreateMealInformationActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	/**
	 * Test if start date is shown correctly
	 * 
	 * @author Yueling Loh
	 *
	 */
	public void testStartDate() throws Exception {
		// Check we have the right activity
		solo.assertCurrentActivity("Wrong Activity",
				CreateMealInformationActivity.class);

		// Check if button displays correctly
		View button = solo.getView(R.id.CreateMealInformation_button_startDate);
		solo.clickOnView(button);
		solo.setDatePicker(0, 2014, 12, 17);
		solo.clickOnText("Done");
		assertTrue(solo.searchText("17-12-2014"));

	}

	/**
	 * Test if end date is shown correctly
	 * 
	 * @author Yueling Loh
	 *
	 */
	public void testEndDate() throws Exception {
		// Check we have the right activity
		solo.assertCurrentActivity("Wrong Activity",
				CreateMealInformationActivity.class);

		// Check if button displays correctly
		View button = solo.getView(R.id.CreateMealInformation_button_endDate);
		solo.clickOnView(button);
		solo.setDatePicker(0, 2014, 12, 18);
		solo.clickOnText("Done");
		assertTrue(solo.searchText("18-12-2014"));

	}

	/**
	 * Tests if start time is shown correctly
	 * 
	 * @author Yueling Loh
	 * 
	 * */
	public void testStartTime() throws Exception {
		// Check we have the right activity
		solo.assertCurrentActivity("Wrong Activity",
				CreateMealInformationActivity.class);

		// Check if button displays correctly
		View button = solo.getView(R.id.CreateMealInformation_button_startTime);
		solo.clickOnView(button);
		solo.setTimePicker(0, 5, 31);
		solo.clickOnText("Done");
		assertTrue(solo.searchText("5:31"));

	}

	/**
	 * Test if end time is shown correctly
	 * 
	 * @author Yueling Loh
	 *
	 */
	public void testEndTime() throws Exception {
		// Check we have the right activity
		solo.assertCurrentActivity("Wrong Activity",
				CreateMealInformationActivity.class);

		// Check if button displays correctly
		View button = solo.getView(R.id.CreateMealInformation_button_endTime);
		solo.clickOnView(button);
		solo.setTimePicker(0, 17, 31);
		solo.clickOnText("Done");
		assertTrue(solo.searchText("17:31"));

	}

	// Test if end date is bigger or equal to start date
	// Test if end time is bigger or equal to start time

	// /**
	// * Test if fails when Restaurant field is empty
	// *
	// * @author Yueling Loh
	// *
	// */
	// public void testEmptyRestaurant() {
	// EditText vRestaurantName = (EditText) solo
	// .getView(R.id.edit_restaurant);
	//
	// setStartEndDatesAndTimes();
	// solo.enterText(vRestaurantName, "");
	//
	// solo.clickOnButton("Next");
	// solo.assertCurrentActivity(
	// "Should remain on page, since no Restaurant inputed.",
	// CreateMealInformationActivity.class);
	//
	// }

	// Test if Maximum Number Attendance is int > 0

	/**
	 * Test if allow friends slider is working
	 * 
	 * @author Yueling Loh
	 *
	 */
	public void testAllowFriendInvite() {
		Activity a = getActivity();
		String switchName = a.getString(R.string.capital_no);
		solo.clickOnToggleButton(switchName);
		switchName = a.getString(R.string.capital_yes);
		solo.clickOnToggleButton(switchName);
	}

	/**
	 * Test if back button is working
	 * 
	 * @author Yueling Loh
	 *
	 */
	public void testOnBackButtonClick() {
		solo.clickOnButton("Back");
		solo.assertCurrentActivity(
				"Should go back to main tabs page when canceled",
				TabHostActivity.class);
	}

	// /**
	// * Test if Next button is working, assuming that fields have been properly
	// * filled
	// *
	// * @author Yueling Loh
	// *
	// */
	// public void testOnNextButtonClick() {
	// // Check we have the right activity
	// solo.assertCurrentActivity("Wrong Activity",
	// CreateMealInformationActivity.class);
	//
	// setStartEndDatesAndTimes();
	//
	// solo.clickOnButton("Next");
	// solo.assertCurrentActivity(
	// "Should go back to Select Friends page when finished",
	// SelectFriendsActivity.class);
	// }

	// private void setStartEndDatesAndTimes() {
	// // Select start date
	// View button = solo.getView(R.id.CreateMealInformation_button_startDate);
	// solo.clickOnView(button);
	// solo.setDatePicker(0, 2014, 12, 17);
	// solo.clickOnText("Done");
	//
	// // Select start time
	// button = solo.getView(R.id.CreateMealInformation_button_startTime);
	// solo.clickOnView(button);
	// solo.setTimePicker(0, 17, 30);
	// solo.clickOnText("Done");
	//
	// // Select end date
	// button = solo.getView(R.id.CreateMealInformation_button_endDate);
	// solo.clickOnView(button);
	// solo.setDatePicker(0, 2014, 12, 17);
	// solo.clickOnText("Done");
	//
	// // Select end time
	// button = solo.getView(R.id.CreateMealInformation_button_endTime);
	// solo.clickOnView(button);
	// solo.setTimePicker(0, 18, 30);
	// solo.clickOnText("Done");
	// }
}
