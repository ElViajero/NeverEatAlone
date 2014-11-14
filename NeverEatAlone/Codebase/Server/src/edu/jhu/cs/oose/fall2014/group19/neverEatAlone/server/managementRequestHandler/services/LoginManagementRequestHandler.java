package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.ILoginDBManager;


/**
 * 
 * This class manages all login related requests.
 * The operations handled by this class include 
 * checking user credentials for a login request. 
 * 
 * @author tejasvamsingh
 *
 */
public class LoginManagementRequestHandler {

	@Inject ILoginDBManager ILoginManagerObject;
	
	/**
	 * This method checks user credentials for a login request.
	 * 
	 * @param request
	 * @return
	 */
	
	public List<Map<String,String>> CheckCredentialsLoginRequest(Map<String,String[]> request){
		
		// ********* LOGGING ********* 
		System.out.println("Reached CheckCredentialsLoginRequest.");
		// ********* LOGGING *********
		
		return ILoginManagerObject.CheckCredentials(request);
	}
	
}
