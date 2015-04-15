package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.helpers.ActivityPropertiesHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * @author tejasvamsingh
 *
 */
public class MealProperties implements IActivityProperties {

	// Instance variables
	String location;
	String maxNumberOfInvitees;
	String isNotificationExtendible;
	String additionalInformation;
	DateAndTimeProperties startDateAndTimeProperties;
	DateAndTimeProperties endDateAndTimeProperties;

	// constructors

	public MealProperties() {
	}

	public MealProperties(String location, String maxNumberOfInvitees,
			String isNotificationExtendible,
			DateAndTimeProperties startDateAndTimeProperties,
			DateAndTimeProperties endDateAndTimeProperties,
			String additionalInformation) {

		this.location = location;
		this.maxNumberOfInvitees = maxNumberOfInvitees;
		this.isNotificationExtendible = isNotificationExtendible;
		this.startDateAndTimeProperties = startDateAndTimeProperties;
		this.endDateAndTimeProperties = endDateAndTimeProperties;
		this.additionalInformation = additionalInformation;

	}

	public MealProperties(Map<String, String> map) {
		fromMap(map);
	}

	// interface methods

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> toMap() {

		Gson gson = GsonHelper.getGsoninstance();
		String json = gson.toJson(this);
		System.out.println("json is : " + json);
		Type stringObjectMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> requestMap = gson.fromJson(json, stringObjectMap);
		System.out.println("map is : " + requestMap);

		requestMap.remove("startDateAndTimeProperties");
		requestMap.remove("endDateAndTimeProperties");

		DateAndTimeProperties.setPrefix("start");
		Map<String, Object> startDateAndTimeMap = startDateAndTimeProperties
				.toMap();
		DateAndTimeProperties.setPrefix("end");
		Map<String, Object> endDateAndTimeMap = endDateAndTimeProperties
				.toMap();

		return ActivityPropertiesHelper.combineMaps(requestMap,
				startDateAndTimeMap, endDateAndTimeMap);

	}

	@Override
	public void fromMap(Map<String, String> map) {
		location = map.get("location");
		maxNumberOfInvitees = map.get("maxNumberOfInvitees");
		isNotificationExtendible = map.get("isNotificationExtendible");
		DateAndTimeProperties.setPrefix("start");
		startDateAndTimeProperties = new DateAndTimeProperties(map);
		DateAndTimeProperties.setPrefix("end");
		endDateAndTimeProperties = new DateAndTimeProperties(map);
		additionalInformation = map.get("additionalInformation");
	}

	// getters and setters

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

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	// private helper methods

}
