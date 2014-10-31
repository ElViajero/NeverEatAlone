package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.ILoginManager;


public class LoginManagementRequestHandler {

	@Inject ILoginManager ILoginManagerObject;
	
	public List<Map<String,String>> CheckCredentialsLoginRequest(Map<String,String[]> request){
		
		// ********* LOGGING ********* 
		System.out.println("Reached CheckCredentialsLoginRequest.");
		// ********* LOGGING *********
		
		return ILoginManagerObject.CheckCredentials(request);
	}
	
}
