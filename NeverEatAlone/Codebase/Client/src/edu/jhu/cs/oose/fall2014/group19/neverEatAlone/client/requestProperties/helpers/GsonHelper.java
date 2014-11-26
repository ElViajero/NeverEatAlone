package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers;

import com.google.gson.Gson;

/**
 * This class is a Gson Helper class that returns 
 * a Gson instance that is shared by all client requests.
 * @author tejasvamsingh
 *
 */

public class GsonHelper {

	static Gson GsonInstance;

	/**
	 * This method returns the Gson instance
	 * to be shared by all client requests.
	 * @author tejasvamsingh
	 * @return
	 */
	public static Gson GetGsonInstance(){

		if(GsonInstance==null)
			GsonInstance = new Gson();
		return GsonInstance;
	}


}
