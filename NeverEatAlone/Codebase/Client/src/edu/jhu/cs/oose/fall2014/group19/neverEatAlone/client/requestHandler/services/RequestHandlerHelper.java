package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestHandler;


/**
 * Helper class for request handling operations.
 * Every request uses the same requestHandlerInstance.
 * 
 * @author tejasvamsingh
 *
 */
public class RequestHandlerHelper {


	public static IRequestHandler IRequestHandlerInstance;

	/**
	 * Helper method to obtain a handle on the IRequestHandler instance.
	 * @return
	 */
	public static IRequestHandler getRequestHandlerInstance(){

		if(IRequestHandlerInstance==null)
			IRequestHandlerInstance = new RequestHandler();
		return IRequestHandlerInstance;
	}

	public static void cleanUp(){
		IRequestHandlerInstance.cleanUp();
	}

}
