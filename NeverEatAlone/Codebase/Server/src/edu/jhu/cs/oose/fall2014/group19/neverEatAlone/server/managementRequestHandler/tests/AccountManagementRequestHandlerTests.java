package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.tests;

import java.util.HashMap;
import static org.junit.Assert.*;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services.ManagementRequestHandler;
/**
 * Test cases for the AccountManagementRequestHandler class.
 * @author tejasvamsingh
 *
 */
public class AccountManagementRequestHandlerTests {

	
	/**
	 * This was used to test reachability of the methods during development.
	 * As it stands, it will always pass.
	 * It's useful nonetheless, because it causes exceptions to be
	 * thrown when the method cannot be invoked.
	 */
	@Test
	public void ReachabilityTest() {
		
		Map<String,String[]> request = new HashMap<String,String[]>();
		request.put("RequestID",new String[]{"Account"});
		request.put("RequestType",new String[]{"Create"});
		IManagementRequestHandler iManagementRequestHandler = new ManagementRequestHandler();
		iManagementRequestHandler.HandleManagementRequest(request);	
		
	}

}
