package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers;

import com.google.gson.Gson;


/**
 * This class is a Gson Helper class that returns 
 * a Gson instance that is shared by all client requests.
 * @author tejasvamsingh
 *
 */

public class GsonHelper {

	static Gson gsonInstance;

	/**
	 * This method returns the Gson instance
	 * to be shared by all client requests.
	 * @author tejasvamsingh
	 * @return
	 */
	public static Gson getGsoninstance(){

		if(gsonInstance==null)
			gsonInstance = new Gson();
		return gsonInstance;
	}


}
