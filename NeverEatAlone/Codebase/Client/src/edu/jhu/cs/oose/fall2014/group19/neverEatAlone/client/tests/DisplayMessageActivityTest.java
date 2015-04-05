package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.DisplayMessageActivity;
import android.test.ActivityInstrumentationTestCase2;

public class DisplayMessageActivityTest extends
		ActivityInstrumentationTestCase2<DisplayMessageActivity> {
	private Solo solo; 
	
	public DisplayMessageActivityTest() {
		super(DisplayMessageActivity.class);
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
