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

	private String contactusername;
	private boolean isChecked;

	public ContactProperties(String username){
		contactusername = username;
	}

	public ContactProperties(Map<String,String> map) {
		fromMap(map);
	}

	public ContactProperties(){};


	@Override
	public Map<String, Object> toMap() {
		Gson gsonObject = 
				GsonHelper.getGsoninstance();
		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);
		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gsonObject.fromJson(jsonString, stringObjectMap);
		System.out.println("map is : " +requestMap);
		requestMap.put("username", 
				AccountProperties.getUserAccountInstance().getusername());
		requestMap.remove("isChecked");
		return requestMap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactusername == null) ? 0 : contactusername.hashCode());
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
		if (contactusername == null) {
			if (other.contactusername != null)
				return false;
		} else if (!contactusername.equals(other.contactusername))
			return false;
		return true;
	}

	public String getContactusername() {
		return contactusername;
	}

	public void setContactusername(String contactusername) {
		this.contactusername = contactusername;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public void fromMap(Map<String, String> map) {
		contactusername = map.get("username");
		isChecked=false;
	}







}
