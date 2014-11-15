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

}
