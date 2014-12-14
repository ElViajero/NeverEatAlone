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

	private String username;
	private String password;
	private String email;

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
		this.username = username;
		this.password = password;
		this.email=email;
	}



	public AccountProperties(Map<String,String> map){
		username = map.get("username");
		password = map.get("password");
		email = map.get("email");
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
				GsonHelper.getGsoninstance();

		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);

		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gsonObject.fromJson(jsonString, stringObjectMap);
		System.out.println("map is : " +requestMap);

		return requestMap;

	}



	// private helper methods.

	@Override
	public void fromMap (Map<String,String> map){
		accountPropertiesInstance = 
				new AccountProperties(map.get("username"), map.get("password"));
		accountPropertiesInstance.setemail(map.get("email"));

	}



	// getters, setters and statics

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}


	public static AccountProperties getUserAccountInstance(){
		return accountPropertiesInstance;
	}






}
