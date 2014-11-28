package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;


import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction
 * of the properties required for the date and time.
 * 
 * @author tejasvamsingh
 *
 */
public class DateAndTimeProperties implements IActivityProperties  {


	String Day;
	String Month;
	String Year;
	String Hour;
	String Minute;


	public DateAndTimeProperties(int day,int month,
			int year,int hour, int minute) {

		// Initialize the lists.
		Day =String.valueOf(day);
		Month = String.valueOf(month);
		Year = String.valueOf(year);
		Hour = String.valueOf(hour);
		Minute = String.valueOf(minute);

	}

	public DateAndTimeProperties(Map<String,String> map) {

		Day = map.get("Day");
		Month = map.get("Month");
		Hour = map.get("Hour");
		Minute = map.get("Minute");

	}


	/**
	 * Method that returns a 
	 * modified Json of this class.
	 *  
	 * @return
	 */
	@Override
	public Map<String, Object> toMap() {

		Gson gsonObject = 
				GsonHelper.GetGsonInstance();
		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);
		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gsonObject.fromJson(jsonString, stringObjectMap);
		System.out.println("map is : " +requestMap);
		return requestMap;

	}




}
