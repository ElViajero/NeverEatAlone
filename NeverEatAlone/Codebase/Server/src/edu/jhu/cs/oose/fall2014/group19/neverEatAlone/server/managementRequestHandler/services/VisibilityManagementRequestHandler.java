package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IVisibilityDBManager;

/**
 * This class handles all the requests related to setting or unsetting Visibility
 * @author Xiaozhou Zhou
 *
 */

@Stateless
public class VisibilityManagementRequestHandler {

	@Inject IVisibilityDBManager iVisibilityDBManagerObject;
	//@Inject IReflectionManager iReflectionManagerObject;
	/**
	 * This method handles requests to set visible to a contact
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> setVisibilityRequest(Map<String,String[]> request){

		System.out.println("Reaching SetVisibilityRequest");
		return iVisibilityDBManagerObject.setVisibility(request); 

	}

	/**
	 * This method handles requests to set invisible to a contact
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> UnsetVisibilityRequest(Map<String,String[]> request) {

		System.out.println("Reaching UnsetVisibilityRequest");
		return iVisibilityDBManagerObject.unsetVisibility(request); 
	}


}
