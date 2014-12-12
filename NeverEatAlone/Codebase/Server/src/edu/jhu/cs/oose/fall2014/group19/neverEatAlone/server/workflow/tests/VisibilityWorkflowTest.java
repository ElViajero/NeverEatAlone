package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests;

import java.io.IOException;

import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers.WorkflowTestHelper;

public class VisibilityWorkflowTest {
	
	/**
	 * This method creates 3 accounts UserA, UserB, UserC. 
	 * Set UserA 
	 * @throws IOException 
	 */
	@Test
	public void GetContactsTest() throws IOException {
		
	
	// book keeping 
	System.out.println("deleting all test accounts...");
	WorkflowTestHelper.DeleteTestAccount("UserA");
	WorkflowTestHelper.DeleteTestAccount("UserB");
	WorkflowTestHelper.DeleteTestAccount("UserC");

	// create 3 accounts
	System.out.println("creating test accounts...");
	WorkflowTestHelper.CreateTestAccount("UserA", "pwA", "emailA");
	WorkflowTestHelper.CreateTestAccount("UserB", "pwB", "emailB");
	WorkflowTestHelper.CreateTestAccount("UserC", "pwC", "emailC");
	
	
	
	}
	

}
