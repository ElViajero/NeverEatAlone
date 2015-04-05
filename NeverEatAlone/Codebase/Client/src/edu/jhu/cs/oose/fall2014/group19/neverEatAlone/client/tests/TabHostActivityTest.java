package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.tests;

import com.robotium.solo.Solo;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services.TabHostActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TabHostActivityTest extends
		ActivityInstrumentationTestCase2<TabHostActivity> {
	
	private Solo solo; 
	
	public TabHostActivityTest() {
		super(TabHostActivity.class);
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
	 * Tests the tab button  
	 */
	public void testView(){
		
	}
}
