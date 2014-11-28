package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;

/**
 * Interface that defines the services offered
 * by the RequestHandler.
 * 
 * @author tejasvamsingh
 *
 */
public interface IRequestHandler{

	/**
	 * Method to handle all client requests.
	 * @author tejasvamsingh
	 * @param requestMap
	 * @return
	 */
	public List<Map<String,String>> HandleRequest(Activity activity,
			Map<String,Object>requestMap,
			String requestID,String requestType) throws RequestAbortedException;

	/**
	 * This method is responsible for freeing up http related resources
	 * for graceful shutdown of the request/response framework.
	 * @author tejasvamsingh
	 */
	public void cleanUp();

}
