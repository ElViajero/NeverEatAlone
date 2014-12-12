package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.ManagemenRequestHandlerHelper;

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

	@Inject IContactDBManager IContactDBManagerObject;

	/**
	 * This method handles requests to add a contact.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String,String>> Add(Map<String,String[]> request){

		System.out.println("Reaching AddContactRequest");
		return IContactDBManagerObject.AddContact(request); 

	}

	/**
	 * This method handles requests to fetch all contacts
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> GetAll(Map<String,String[]> request) {

		System.out.println("Reaching GetAllContactRequest");
		return IContactDBManagerObject.GetAllContacts(request); 
	}

	@Override
	public List<Map<String, String>> HandleManagementRequest(
			Map<String, String[]> request) {
		System.out.println("Inside HandleManagementRequest");
		return ManagemenRequestHandlerHelper.invokeMethod(this,
				request.get("requestType")[0], request);

	}

}
