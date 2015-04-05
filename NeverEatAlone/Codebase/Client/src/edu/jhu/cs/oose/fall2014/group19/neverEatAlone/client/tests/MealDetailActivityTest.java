package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.MealDetailActivity;
import android.test.ActivityInstrumentationTestCase2;

public class MealDetailActivityTest extends
		ActivityInstrumentationTestCase2<MealDetailActivity> {
	
	private Solo solo; 
	
	public MealDetailActivityTest() {
		super(MealDetailActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
	
}
