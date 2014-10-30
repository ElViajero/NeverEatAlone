package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.services;

import java.util.List;
import java.util.Map;

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

public class RequestDispatcher implements IRequestDispatcher {

	IManagementRequestHandler IManagementRequestHandlerObject;
	
	@Override
	public List<Map<String,String>> DispatchRequest(Map<String, String[]> request) {
		System.out.println("reached DispatchRequest");
		System.out.println("Request Keys : ");
		System.out.println(request.keySet());
		IManagementRequestHandlerObject = new ManagementRequestHandler();
		return IManagementRequestHandlerObject.HandleManagementRequest(request);
	}	
	
	
}
