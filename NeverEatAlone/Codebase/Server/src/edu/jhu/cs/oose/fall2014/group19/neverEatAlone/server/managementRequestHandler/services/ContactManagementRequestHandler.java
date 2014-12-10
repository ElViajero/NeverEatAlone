package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;

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

@Stateless
public class ContactManagementRequestHandler {

	@Inject IContactDBManager IContactDBManagerObject;

	/**
	 * This method handles requests to add a contact.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> AddContactRequest(Map<String,String[]> request){

		System.out.println("Reaching AddContactRequest");
		return IContactDBManagerObject.AddContact(request); 

	}
	
	/**
	 * This method handles requests to fetch all contacts
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> GetAllContactRequest(Map<String,String[]> request) {
		
		System.out.println("Reaching GetAllContactRequest");
		return IContactDBManagerObject.GetAllContacts(request); 
	}
	
	/**
	 * This method handles requests to delete a contact.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> DeleteContactRequest(Map<String,String[]> request){

		System.out.println("Reaching DeleteContactRequest");
		return IContactDBManagerObject.DeleteContact(request); 

	}

}
