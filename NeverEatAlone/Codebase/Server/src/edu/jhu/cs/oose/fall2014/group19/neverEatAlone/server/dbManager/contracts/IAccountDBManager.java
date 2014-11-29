package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

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
public interface IAccountDBManager {

	/**
	 * Method to create an user account.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> CreateAccount(Map<String,String[]> request);
	
	/**
	 * 
	 * Method to update an user account 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> UpdateAccount(Map<String,String[]> request);
	
	/**
	 * Method to get contact information 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> GetInfo(Map<String,String[]> request);
	
	/**
	 * Method to get availability information 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> GetAvailability(Map<String,String[]> request);
	
	/**
	 * Method to set availability information 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> SetAvailability(Map<String,String[]> request);
	
	/**
	 * Method to delete an user account.
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> DeleteAccount(Map<String,String[]> request);
		
	
	/**
	 * Method to check whether an user account is valid. 
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> IsValidAccount(Map<String,String[]> request);
	

}
