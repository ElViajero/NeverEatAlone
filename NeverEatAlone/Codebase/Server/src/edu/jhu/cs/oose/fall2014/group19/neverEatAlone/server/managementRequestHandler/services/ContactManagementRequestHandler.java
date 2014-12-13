package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * 
 * This class manages all contact related requests.
 * The operations handled by this class include 
 * adding/removing contacts etc.
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */


public class ContactManagementRequestHandler implements IManagementRequestHandler {

	@Inject IContactDBManager iContactDBManagerObject;
	@Inject IReflectionManager iReflectionManagerObject;
	/**
	 * This method handles requests to add a contact.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> add(Map<String,String[]> request){

		System.out.println("Reaching AddContactRequest");
		return iContactDBManagerObject.add(request); 

	}

	/**
	 * This method handles requests to fetch all contacts
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> getAll(Map<String,String[]> request) {

		System.out.println("Reaching GetAllContactRequest");
		return iContactDBManagerObject.getAll(request); 
	}

	/**
	 * This method handles requests to delete a contact.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> deleteContactRequest(Map<String,String[]> request){

		System.out.println("Reaching DeleteContactRequest");
		return iContactDBManagerObject.delete(request); 

	}

	/**
	 * This method handles requests to update a contact's information.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> updateContactRequest(Map<String,String[]> request){

		System.out.println("Reaching UpdateContactRequest");
		return iContactDBManagerObject.update(request); 

	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {
		System.out.println("Inside HandleManagementRequest");
		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);

	}

}
