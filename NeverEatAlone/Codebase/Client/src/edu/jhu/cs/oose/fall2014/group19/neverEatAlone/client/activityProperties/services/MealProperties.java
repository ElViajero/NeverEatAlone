package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;


import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

public class MealProperties implements IActivityProperties{


	String Location;
	String MaxNumberOfInvitees;
	String IsNotificationExtendible;
	DateAndTimeProperties startDateAndTimeProperties;
	DateAndTimeProperties endDateAndTimeProperties;

	public MealProperties(String location,
			String maxNumberOfInvitees, String isNotificationExtendible,
			DateAndTimeProperties startDateAndTimeProperties,
			DateAndTimeProperties endDateAndTimeProperties) {


		Location = location;
		MaxNumberOfInvitees = maxNumberOfInvitees;
		IsNotificationExtendible = isNotificationExtendible;
		this.startDateAndTimeProperties = startDateAndTimeProperties;
		this.endDateAndTimeProperties = endDateAndTimeProperties;

	}




	@Override
	public Map<String, Object> toMap(){

		Gson gson = GsonHelper.GetGsonInstance();
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
			requestMap.put("End"+key, value);

		}

		for (Map.Entry<String, Object> entry : startDateAndTimeMap.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			requestMap.put("Start"+key, value);

		}


		return requestMap;

	}

}


