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
			Map<String,List<String>>requestMap,
			String requestID,String requestType) throws RequestAbortedException;

	public void cleanUp();

}
