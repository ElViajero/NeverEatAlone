package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;


/**
 * 
 * Interface for visibility management services.
 * 
 * @author Xiaozhou Zhou
 *
 */
public interface IVisibilityDBRequestHandler {

	/**
	 * Method to add visibility to some contacts
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> add(Map<String,String[]> request);

	/**
	 * method to delete visibility to some contacts
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> delete(Map<String,String[]> request);
	
	/**
	 * method to set status of visibility
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> update(Map<String,String[]> request);
	
	/**
	 * method to get all contacts that are visible
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> getAll(Map<String,String[]> request);



}
