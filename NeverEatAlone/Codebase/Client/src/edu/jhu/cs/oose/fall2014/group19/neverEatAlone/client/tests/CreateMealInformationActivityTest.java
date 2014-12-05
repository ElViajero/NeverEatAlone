package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.CreateMealInformationActivity;
import android.test.ActivityInstrumentationTestCase2;


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
		solo = new Solo(getInstrumentation(),getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
	
	// Test if start date is picked right
	// Test if start time is picked right
	// Test if end date is picked right
	// Test if end time is correct
	// Test if end date is bigger or equal to start date
	// Test if end time is bigger or equal to start time
	// Test if fails if Restaurant is empty
	// Test if Maximum Number Attendance is int > 0

	
}
