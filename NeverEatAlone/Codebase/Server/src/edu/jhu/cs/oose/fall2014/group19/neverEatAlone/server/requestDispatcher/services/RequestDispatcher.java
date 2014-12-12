package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
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


	@Inject
	@Any
	Instance<Object> myBeans;

	/**
	 * This class obtains bean reference from container using the passed string.
	 * @param className
	 * @return
	 * @throws Exception
	 */
	private Object getMyBeanFromClassName(String className) throws Exception{    
		Class<?> clazz = Class.forName(className);
		return myBeans.select(clazz).get();  
	}


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



		// ********* LOGGING ********* 
		System.out.println("class name : "+request.get("requestID")[0]+"ManagementRequestHandler");
		// ********* LOGGING ********* 

		// obtain class reference
		try {
			iManagementRequestHandlerObject = (IManagementRequestHandler) 
					getMyBeanFromClassName
					("edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server."
							+ "managementRequestHandler.services."
							+request.get("requestID")[0]+"ManagementRequestHandler");
			System.out.println("obect reference : "+ iManagementRequestHandlerObject);
			return (iManagementRequestHandlerObject.handleManagementRequest(request));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in DispatchRequest :  ");
			e.printStackTrace();
		}




		return null;
		/*
	 	 return IManagementRequestHandlerObject.HandleManagementRequest(request);*/
	}	


}
