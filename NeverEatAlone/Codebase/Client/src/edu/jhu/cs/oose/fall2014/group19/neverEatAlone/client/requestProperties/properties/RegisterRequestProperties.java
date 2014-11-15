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
 * of the properties required for the register request.
 * 
 * @author tejasvamsingh
 *
 */
public class RegisterRequestProperties implements IRequestProperties {

	List<String> Username;
	List<String> Password;
	List<String> Email;
	
	
	
	/**
	 * Constructor that accepts strings.
	 * @param username
	 * @param password
	 * @param email
	 */
	public RegisterRequestProperties(String username,String password,String email){

		// initialize the lists
		Username = new ArrayList<String>();
		Password = new ArrayList<String>();
		Email = new ArrayList<String>();

		// add the fields to the list;
		Username.add(username);
		Password.add(password);
		Email.add(email);								
	}
	
	/**
	 * Method that returns a Map 
	 * corresponding to request properties
	 * @return
	 */
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
