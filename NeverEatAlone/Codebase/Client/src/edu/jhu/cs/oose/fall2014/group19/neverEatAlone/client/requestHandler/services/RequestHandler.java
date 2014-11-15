package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestHandler;

/**
 * This class is the handler class for all client 
 * requests.
 * For every request, the HandleRequest method of this class is called.
 * 
 * @author tejasvamsingh
 *
 */

public class RequestHandler implements IRequestHandler {

	
	
	
	/**
	 * This method handles requests that the client wishes to send to the server.
	 * It returns the response to the appropriate event handler.
	 * It executes the requests by calling the execute method on RequestExecutor. 
	 * 
	 * @author tejasvamsingh
	 */
	
	public List<Map<String, String>> HandleRequest(
			Map<String,List<String>> requestMap,String requestID,String requestType) {
	
		
		// Get the name value pair list.
		List<NameValuePair> requestList = GetRequestList(requestMap);
		
		// Add request headers.
		requestList.add(new BasicNameValuePair("RequestID", requestID));
		requestList.add(new BasicNameValuePair("RequestType", requestType));
		
		
		// call the asynchronous result executor.
		List<Map<String, String>> resultMapList=null;
		try {
			
			resultMapList = new RequestExecutor().execute(requestList).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMapList;
	}
	
	
	/**
	 * This method formats the request correctly
	 * before sending it to the RequestExecutor
	 * 
	 * @return
	 */
	
	private List<NameValuePair> GetRequestList(Map<String,List<String>> requestMap){
	
		List<NameValuePair> requestList = new
				ArrayList<NameValuePair>();
		
		for (Map.Entry<String, List<String>> entry : requestMap.entrySet()) {
		    
			String key = entry.getKey();
		    List<String> valueList = entry.getValue();
		    
		    for(String value : valueList){
		    	requestList.add(new BasicNameValuePair(key, value));		    	
		    }
		}
		
		return requestList;
		
		
	}

}
