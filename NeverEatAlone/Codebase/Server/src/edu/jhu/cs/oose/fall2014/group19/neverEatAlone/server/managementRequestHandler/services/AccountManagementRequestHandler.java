package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.ManagemenRequestHandlerHelper;

/**
 * 
 * This class manages all acount related requests.
 * The operations handled by this class include 
 * account creation,modification and deletion.
 * 
 * @author tejasvamsingh
 *
 */


public class AccountManagementRequestHandler implements IManagementRequestHandler{

	@Inject IAccountDBManager IAccountManagerObject;

	/**
	 * This method handles requests to create an account.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> Create(Map<String,String[]> request){

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
	private List<Map<String,String>> IsValid(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached IsValidAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.IsValidAccount(request);
	}

	private List<Map<String,String>> Delete(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached DeleteAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.DeleteAccount(request);
	}


	private List<Map<String,String>> Update(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached UpdateAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.UpdateAccount(request);
	}

	private List<Map<String,String>> GetInfo(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached GetInfoAccountRequest");
		// ********* LOGGING ********* 

		return IAccountManagerObject.GetInfo(request);
	}

	@Override
	public List<Map<String, String>> HandleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return ManagemenRequestHandlerHelper.invokeMethod(this,
				request.get("requestType")[0], request);
	}

}






