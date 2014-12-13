package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;
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

	IManagementRequestHandler iManagementRequestHandlerObject;
	@Inject IReflectionManager iReflectionManagerObject;

	final String PACKAGE_NAME = "edu.jhu.cs.oose.fall2014."
			+ "group19.neverEatAlone.server"
			+ ".managementRequestHandler.services.";

	final String CLASS_SUFFIX = "ManagementRequestHandler";


	/**
	 * This method dispatches request to the ManagementRequestHandler layer
	 */
	@Override
	public List<Map<String,String>> dispatchRequest(Map<String, String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("reached DispatchRequest");
		System.out.println("Request Keys : ");
		System.out.println(request.keySet());
		// ********* LOGGING ********* 


		String classPrefix = request.get("requestID")[0]; 

		// ********* LOGGING ********* 
		System.out.println("class name : "+PACKAGE_NAME+classPrefix+CLASS_SUFFIX);
		// ********* LOGGING ********* 

		// obtain class reference		
		iManagementRequestHandlerObject = (IManagementRequestHandler) 
				iReflectionManagerObject.getClass(PACKAGE_NAME+classPrefix+CLASS_SUFFIX);

		// ********* LOGGING *********
		System.out.println("object reference : "+ iManagementRequestHandlerObject);
		// ********* LOGGING *********

		// Delegate the request to the appropriate class.
		return (iManagementRequestHandlerObject.handleManagementRequest(request));

	}	


}
