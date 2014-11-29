package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountDBManager;

/**
 * 
 * This class manages all acount related requests.
 * The operations handled by this class include 
 * account creation,modification and deletion.
 * 
 * @author tejasvamsingh
 *
 */

@Stateless
public class AccountManagementRequestHandler{

	@Inject IAccountDBManager IAccountManagerObject;

	/**
	 * This method handles requests to create an account.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> CreateAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached CreateAccountRequest");
		System.out.println(IAccountManagerObject);
		// ********* LOGGING ********* 

		return IAccountManagerObject.CreateAccount(request);
	}

	/**
	 * This method handles requests to check the validity of
	 * a given user account.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> IsValidAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached IsValidAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.IsValidAccount(request);
	}

	public List<Map<String,String>> DeleteAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached DeleteAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.DeleteAccount(request);
	}

	
	public List<Map<String,String>> SetAvailabilityAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached SetAvailabilityAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.SetAvailability(request);
	}
	
	public List<Map<String,String>> GetAvailabilityAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached GetAvailabilityAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.GetAvailability(request);
	}
	
	public List<Map<String,String>> UpdateAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached UpdateAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.UpdateAccount(request);
	}
	
	public List<Map<String,String>> GetInfoAccountRequest(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached GetInfoAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.GetInfo(request);
	}
}






