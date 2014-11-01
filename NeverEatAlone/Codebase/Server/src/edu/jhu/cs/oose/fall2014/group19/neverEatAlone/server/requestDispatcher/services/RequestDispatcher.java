package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services.ManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts.IRequestDispatcher;

/**
 * Dispatches request to the appropriate ManagementRequestHandler.
 * This class is the start of our business logic.
 * 
 * @author tejasvamsingh
 *
 */

@Stateless
public class RequestDispatcher implements IRequestDispatcher {

	@Inject IManagementRequestHandler IManagementRequestHandlerObject;
	
	
	/**
	 * This method dispatches request to the ManagementRequestHandler layer
	 */
	@Override
	public List<Map<String,String>> DispatchRequest(Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("reached DispatchRequest");
		System.out.println("Request Keys : ");
		System.out.println(request.keySet());
		// ********* LOGGING ********* 
		
		return IManagementRequestHandlerObject.HandleManagementRequest(request);
	}	
	
	
}
