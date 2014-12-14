package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IVisibilityDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * This class handles all the requests related to setting or unsetting Visibility
 * @author Xiaozhou Zhou
 *
 */

@Stateless
public class VisibilityManagementRequestHandler  implements IManagementRequestHandler {

	@Inject IVisibilityDBRequestHandler iVisibilityDBManagerObject;
	@Inject IReflectionManager iReflectionManagerObject;
	
	/**
	 * This method handles requests to add visibility to contacts
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> add(Map<String,String[]> request){

		System.out.println("Reaching AddVisibilityRequest");
		return iVisibilityDBManagerObject.add(request); 

	}

	/**
	 * This method handles requests to delete invisible to contacts
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> delete(Map<String,String[]> request) {

		System.out.println("Reaching DeleteVisibilityRequest");
		return iVisibilityDBManagerObject.delete(request); 
	}
	

	/**
	 * This method handles requests to set status of invisibility to contacts
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> set(Map<String,String[]> request) {

		System.out.println("Reaching SetVisibilityRequest");
		return iVisibilityDBManagerObject.update(request); 
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);
	}


}
