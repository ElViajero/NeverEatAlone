package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * 
 * Interface for account management services.
 * Accessible to the management request handler layers. 
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface IAccountDBRequestHandler {

	/**
	 * Method to create an user account.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> create(Map<String,String[]> request);

	/**
	 * 
	 * Method to update an user account 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> update(Map<String,String[]> request);

	/**
	 * Method to get contact information 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> getInfo(Map<String,String[]> request);


	/**
	 * Method to delete an user account.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> delete(Map<String,String[]> request);


	/**
	 * Method to check whether an user account is valid. 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> isValid(Map<String,String[]> request);


}
