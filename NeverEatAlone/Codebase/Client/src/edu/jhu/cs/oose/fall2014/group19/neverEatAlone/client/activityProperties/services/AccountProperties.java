package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction
 * for account related properties.
 * 
 * @author tejasvamsingh
 *
 */
public class AccountProperties implements IActivityProperties {

	private String Username;
	private String Password;
	private String Email;

	// one object because at any time, ther is only
	// one user who is logged in on one client.
	private static AccountProperties accountPropertiesInstance;

	/***
	 * Constructor that accepts strings.
	 * @param username
	 * @param password
	 */
	public AccountProperties(String username,String password){


		this(username,password,"");

	}

	public AccountProperties(String username,String password,String email){
		Username = username;
		Password = password;
		Email=email;
	}



	public AccountProperties(Map<String,String> map){
		Username = map.get("Username");
		Password = map.get("Password");
		Email = map.get("Email");
		fromMap(map);
	}


	/**
	 * Method that returns a Map 
	 * corresponding to request properties 
	 * 
	 * @return
	 */
	@Override
	public Map<String,Object> toMap(){

		Gson gsonObject = 
				GsonHelper.GetGsonInstance();

		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);

		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gsonObject.fromJson(jsonString, stringObjectMap);
		System.out.println("map is : " +requestMap);

		return requestMap;

	}



	// private helper methods.

	private AccountProperties fromMap (Map<String,String> map){
		accountPropertiesInstance = 
				new AccountProperties(map.get("Username"), map.get("Password"));
		accountPropertiesInstance.setEmail(map.get("Email"));
		return accountPropertiesInstance;
	}



	// getters, setters and statics

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}


	public static AccountProperties getUserAccountInstance(){
		return accountPropertiesInstance;
	}






}
