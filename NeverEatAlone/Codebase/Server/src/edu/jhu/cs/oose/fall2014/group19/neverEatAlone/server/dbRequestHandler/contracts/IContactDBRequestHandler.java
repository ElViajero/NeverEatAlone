package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

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
public interface IContactDBRequestHandler {

	/**
	 * Method to add a contact.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> add(Map<String,String[]> request);

	/**
	 * method to fetch all contacts of a user
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> getAll(Map<String,String[]> request);

	/**
	 * method to delete a contact
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> delete(Map<String,String[]> request);

	/**
	 * method to update a contact
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> update(Map<String, String[]> request);


	public List<Map<String,String>> fetchRequests(Map<String,String[]> request);



}
