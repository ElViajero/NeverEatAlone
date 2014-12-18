package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

/**
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class DBRequestHandlerHelper {


	/**
	 * This method is used for formatting query parameters.
	 * Note, this is a generic method that assumes every String[] has 
	 * one element.
	 * 
	 * In case of multiple query parameters, 
	 * the calling method must take care of this detail.
	 * If not, the information may be lost.
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String,String> GetQueryParameterMap(Map<String,String[]> request){		 

		Map<String,String> queryParameterMap = new HashMap<String,String>();

		for (Map.Entry<String, String[]> entry : request.entrySet()) {

			//obtain the key and value for the current entry.
			String key = entry.getKey();
			String[] value = entry.getValue();

			// Put the first element of String[] into our new map.
			// Read method documentation for more details.		    		    
			queryParameterMap.put(key, value[0]);		   		   
		}		

		queryParameterMap.remove("requestID");
		queryParameterMap.remove("requestType");
		return queryParameterMap;
	}



}
