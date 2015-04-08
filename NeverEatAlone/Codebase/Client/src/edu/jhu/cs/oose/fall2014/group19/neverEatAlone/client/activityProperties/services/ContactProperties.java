package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IOrderedIterator;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction for contact related properties.
 * 
 * @author tejasvamsingh
 *
 */
public class ContactProperties implements IActivityProperties, IOrderedIterator {

	private String contactusername;
	private String contactAvatarString;
	private String contactName;
	private String contactGender;
	private String contactWorkplace;

	private boolean isChecked;

	public ContactProperties(String username) {
		contactusername = username;
	}

	public ContactProperties(Map<String, String> map) {
		fromMap(map);
	}

	public ContactProperties() {
	};

	@Override
	public Map<String, Object> toMap() {
		Gson gsonObject = GsonHelper.getGsoninstance();
		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " + jsonString);
		Type stringObjectMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> requestMap = gsonObject.fromJson(jsonString,
				stringObjectMap);
		System.out.println("map is : " + requestMap);
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());
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

	@Override
	public void fromMap(Map<String, String> map) {
		contactusername = map.get("username");
		contactName = map.get("name");
		contactAvatarString = map.get("avatar");
		contactGender = map.get("gender");
		contactWorkplace = map.get("workPlace");
		isChecked = false;
	}

	@Override
	public List<Pair> getOrderedIterationList() {

		List<Pair> orderedIterationList = new ArrayList<Pair>();

		Pair<String, String> pairObject = new Pair<String, String>("Username",
				contactusername);
		orderedIterationList.add(pairObject);
		pairObject = new Pair<String, String>("Name", contactName);
		orderedIterationList.add(pairObject);
		pairObject = new Pair<String, String>("Gender", contactGender);
		orderedIterationList.add(pairObject);
		pairObject = new Pair<String, String>("Workplace", contactWorkplace);
		orderedIterationList.add(pairObject);

		return orderedIterationList;
	}

	// getters and setters
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

	public String getContactAvatarString() {
		return contactAvatarString;
	}

	public void setContactAvatarString(String contactAvatarString) {
		this.contactAvatarString = contactAvatarString;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactGender() {
		return contactGender;
	}

	public void setContactGender(String contactGender) {
		this.contactGender = contactGender;
	}

	public String getContactWorkplace() {
		return contactWorkplace;
	}

	public void setContactWorkplace(String contactWorkplace) {
		this.contactWorkplace = contactWorkplace;
	}

}
