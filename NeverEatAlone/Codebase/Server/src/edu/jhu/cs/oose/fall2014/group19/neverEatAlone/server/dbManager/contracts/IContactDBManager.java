package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * 
 * Interface for contact management services.
 * Accessible to the management request handler layers. 
 * 
 * @author tejasvamsingh
 * @author Xiaozhou Zhou
 *
 */
@Local
public interface IContactDBManager {

	/**
	 * Method to add a contact.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> AddContact(Map<String,String[]> request);
	
	/**
	 * method to fetch all contacts of a user
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> GetAllContacts(Map<String,String[]> request);


}
