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

	@Inject IAccountDBManager iAccountManagerObject;

	/**
	 * This method handles requests to create an account.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> create(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached CreateAccountRequest");
		System.out.println(iAccountManagerObject);
		// ********* LOGGING ********* 

		return iAccountManagerObject.create(request);
	}

	/**
	 * This method handles requests to check the validity of
	 * a given user account.
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> isValid(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached IsValidAccountRequest");
		// ********* LOGGING ********* 

		return iAccountManagerObject.isValid(request);
	}

	private List<Map<String,String>> delete(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached DeleteAccountRequest");
		// ********* LOGGING ********* 

		return iAccountManagerObject.delete(request);
	}


	private List<Map<String,String>> update(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached UpdateAccountRequest");
		// ********* LOGGING ********* 

		return iAccountManagerObject.update(request);
	}

	private List<Map<String,String>> getInfo(Map<String,String[]> request){

		// ********* LOGGING ********* 
		System.out.println("reached GetInfoAccountRequest");
		// ********* LOGGING ********* 

		return iAccountManagerObject.getInfo(request);
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return ManagemenRequestHandlerHelper.invokeMethod(this,
				request.get("requestType")[0], request);
	}

}






