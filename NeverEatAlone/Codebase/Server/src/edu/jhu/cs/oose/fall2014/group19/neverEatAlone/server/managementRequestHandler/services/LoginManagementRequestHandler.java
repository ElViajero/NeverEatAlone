package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.ILoginDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;


/**
 * 
 * This class manages all login related requests.
 * The operations handled by this class include 
 * checking user credentials for a login request. 
 * 
 * @author tejasvamsingh
 *
 */

public class LoginManagementRequestHandler implements IManagementRequestHandler {

	@Inject ILoginDBManager iLoginManagerObject;
	@Inject IReflectionManager iReflectionManagerObject;
	/**
	 * This method checks user credentials for a login request.
	 * 
	 * @param request
	 * @return
	 */

	private List<Map<String,String>> checkCredentials(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("Reached CheckCredentialsLoginRequest.");
		// ********* LOGGING *********

		return iLoginManagerObject.checkCredentials(request);
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);

	}

}
