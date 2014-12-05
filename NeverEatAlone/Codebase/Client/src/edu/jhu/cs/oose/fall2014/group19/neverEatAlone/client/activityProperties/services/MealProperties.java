package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

public class MealProperties implements IActivityProperties{


	String location;
	String maxNumberOfInvitees;
	String isNotificationExtendible;
	DateAndTimeProperties startDateAndTimeProperties;
	DateAndTimeProperties endDateAndTimeProperties;

	public MealProperties(String location,
			String maxNumberOfInvitees, String isNotificationExtendible,
			DateAndTimeProperties startDateAndTimeProperties,
			DateAndTimeProperties endDateAndTimeProperties) {

		this.location = location;
		this.maxNumberOfInvitees = maxNumberOfInvitees;
		this.isNotificationExtendible = isNotificationExtendible;
		this.startDateAndTimeProperties = startDateAndTimeProperties;
		this.endDateAndTimeProperties = endDateAndTimeProperties;

	}


	public MealProperties(Map<String,String> map) {
		location = map.get("location");
		maxNumberOfInvitees = map.get("maxNumberOfInvitees");
		isNotificationExtendible = map.get("isNotificationExtendible");
		startDateAndTimeProperties = constructStartDateAndTimeProperties(map);
		endDateAndTimeProperties = constructEndDateAndTimeProperties(map);


	}


	private DateAndTimeProperties constructEndDateAndTimeProperties(
			Map<String, String> map) {

		HashMap<String, String> endDateAndTimeMap = 
				new HashMap<String,String>();

		endDateAndTimeMap.put("day", map.get("endday"));
		endDateAndTimeMap.put("hour", map.get("endhour"));
		endDateAndTimeMap.put("minute", map.get("endminute"));
		endDateAndTimeMap.put("month", map.get("endmonth"));
		endDateAndTimeMap.put("year", map.get("endyear"));
		return new DateAndTimeProperties(endDateAndTimeMap);
	}


	private DateAndTimeProperties constructStartDateAndTimeProperties(
			Map<String, String> map) {

		HashMap<String, String> startDateAndTimeMap = 
				new HashMap<String,String>();

		startDateAndTimeMap.put("day", map.get("startday"));
		startDateAndTimeMap.put("hour", map.get("starthour"));
		startDateAndTimeMap.put("minute", map.get("startminute"));
		startDateAndTimeMap.put("month", map.get("startmonth"));
		startDateAndTimeMap.put("year", map.get("startyear"));
		return new DateAndTimeProperties(startDateAndTimeMap);
	}


	@Override
	public Map<String, Object> toMap(){

		Gson gson = GsonHelper.getGsoninstance();
		String json = gson.toJson(this);
		System.out.println("json is : " +json);
		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gson.fromJson(json, stringObjectMap);
		System.out.println("map is : " +requestMap);

		requestMap.remove("startDateAndTimeProperties");
		requestMap.remove("endDateAndTimeProperties");

		Map<String, Object> startDateAndTimeMap = 
				startDateAndTimeProperties.toMap();
		Map<String, Object> endDateAndTimeMap =
				endDateAndTimeProperties.toMap();

		for (Map.Entry<String, Object> entry : endDateAndTimeMap.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			requestMap.put("end"+key, value);

		}

		for (Map.Entry<String, Object> entry : startDateAndTimeMap.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			requestMap.put("start"+key, value);

		}
		return requestMap;

	}

	public String getlocation() {
		return location;
	}


	public void setlocation(String location) {
		this.location = location;
	}


	public String getMaxNumberOfInvitees() {
		return maxNumberOfInvitees;
	}


	public void setMaxNumberOfInvitees(String maxNumberOfInvitees) {
		this.maxNumberOfInvitees = maxNumberOfInvitees;
	}


	public String getIsNotificationExtendible() {
		return isNotificationExtendible;
	}


	public void setIsNotificationExtendible(String isNotificationExtendible) {
		this.isNotificationExtendible = isNotificationExtendible;
	}


	public DateAndTimeProperties getStartDateAndTimeProperties() {
		return startDateAndTimeProperties;
	}


	public void setStartDateAndTimeProperties(
			DateAndTimeProperties startDateAndTimeProperties) {
		this.startDateAndTimeProperties = startDateAndTimeProperties;
	}


	public DateAndTimeProperties getEndDateAndTimeProperties() {
		return endDateAndTimeProperties;
	}


	public void setEndDateAndTimeProperties(
			DateAndTimeProperties endDateAndTimeProperties) {
		this.endDateAndTimeProperties = endDateAndTimeProperties;
	}

}


