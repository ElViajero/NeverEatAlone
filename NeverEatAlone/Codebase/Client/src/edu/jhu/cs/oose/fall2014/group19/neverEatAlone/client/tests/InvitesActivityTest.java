package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.InvitesActivity;
import android.test.ActivityInstrumentationTestCase2;

public class InvitesActivityTest extends ActivityInstrumentationTestCase2<InvitesActivity> {

	private Solo solo; 
	
	public InvitesActivityTest() {
		super(InvitesActivity.class);
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
