package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.contracts.IRequestProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction
 * of the properties required for the login request.
 * 
 * @author tejasvamsingh
 *
 */
public class LoginRequestProperties implements IRequestProperties {

	List<String> Username;
	List<String> Password;

	/***
	 * Constructor that accepts strings.
	 * @param username
	 * @param password
	 */
	public LoginRequestProperties(String username,String password){

		// initialize the lists
		Username = new ArrayList<String>();
		Password = new ArrayList<String>();

		// add the fields to the list;
		Username.add(username);
		Password.add(password);

	}


	/**
	 * Method that returns a Map 
	 * corresponding to request properties 
	 * 
	 * @return
	 */
	@Override
	public Map<String,List<String>> GetRequestMap(){

		Gson gsonObject = 
				GsonHelper.GetGsonInstance();


		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);

		Type stringListStringMap = new TypeToken<Map<String, List<String>>>(){}.getType();
		Map<String,List<String>> requestMap = gsonObject.fromJson(jsonString, stringListStringMap);
		System.out.println("map is : " +requestMap);



		return requestMap;

	}

}
