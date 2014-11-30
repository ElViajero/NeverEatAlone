package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction
 * for contact related properties.
 * 
 * @author tejasvamsingh
 *
 */
public class ContactProperties implements IActivityProperties {

	//for now, this is the only information about a
	//contact we display.

	private String contactUsername;
	private boolean isChecked;

	public ContactProperties(String username){
		contactUsername = username;
	}

	public ContactProperties(Map<String,String> map) {
		contactUsername = map.get("Username");
		isChecked=false;
	}


	@Override
	public Map<String, Object> toMap() {
		Gson gsonObject = 
				GsonHelper.GetGsonInstance();
		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);
		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gsonObject.fromJson(jsonString, stringObjectMap);
		System.out.println("map is : " +requestMap);
		requestMap.put("Username", 
				AccountProperties.getUserAccountInstance().getUsername());
		requestMap.remove("isChecked");
		return requestMap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactUsername == null) ? 0 : contactUsername.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactProperties other = (ContactProperties) obj;
		if (contactUsername == null) {
			if (other.contactUsername != null)
				return false;
		} else if (!contactUsername.equals(other.contactUsername))
			return false;
		return true;
	}

	public String getContactUsername() {
		return contactUsername;
	}

	public void setContactUsername(String contactUsername) {
		this.contactUsername = contactUsername;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}







}
