package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * Interface that supplies request dispatching services
 * to the ManagementRequestHandler layer.
 * 
 * @author tejasvamsingh
 *
 */

@Local
public interface IRequestDispatcher {
	
	/**
	 * This method dispatches the request to 
	 * the appropriate management request handler.
	 *  
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> DispatchRequest(Map<String,String[]> request);	
}
