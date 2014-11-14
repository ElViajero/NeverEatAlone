package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestHandler;

public class RequestHandler implements IRequestHandler {

	
	
	
	/**
	 * This method handles requests that the client wishes to send to the server.
	 * It returns the response to the appropriate event handler.
	 * It executes the requests by calling the execute method on RequestExecutor. 
	 */
	
	public List<Map<String, String>> HandleRequest(
			List<NameValuePair> requestList) {
	
		
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

}
