package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
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
	 * 
	 * This is a standalone test.
	 * Since actual reachability is container managed because of bean 
	 * annotations.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */

	@Test
	public void ReachabilityTest() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {


		Map<String,String[]> request = new HashMap<String,String[]>();
		request.put("requestID",new String[]{"Account"});
		request.put("requestType",new String[]{"Create"});


		// Get class corresponding to requestID.
		Class<?> c = Class.forName(
				"edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services."
						+request.get("requestID")[0]+"ManagementRequestHandler");			

		// Get method corresponding to requestType.
		Method m = (c).getDeclaredMethod(request.get("requestType")[0]+
				request.get("requestID")[0]+
				"Request" , Map.class);

		//just check if the method is reachable by
		//verifying return type.
		assertTrue(m.getReturnType().equals(List.class));									

	}

}
