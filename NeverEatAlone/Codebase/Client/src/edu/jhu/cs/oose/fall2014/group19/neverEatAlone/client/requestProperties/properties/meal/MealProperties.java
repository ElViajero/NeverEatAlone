package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MealProperties {


	List<String> StartTime;
	List<String> EndTime;
	List<String> Location;
	List<String> MaxNumberOfInvitees;
	List<String> IsNotificationExtendible;
	
	
	public MealProperties(String startTime, String endTime, String location,
			String maxNumberOfInvitees, String isNotificationExtendible) {
		
		StartTime = new ArrayList<String>();
		StartTime.add(startTime);
		EndTime = new ArrayList<String>();
		EndTime.add(endTime);
		Location = new ArrayList<String>();
		Location.add(location);
		MaxNumberOfInvitees = new ArrayList<String>();
		MaxNumberOfInvitees.add(maxNumberOfInvitees);
		IsNotificationExtendible = new ArrayList<String>();
		IsNotificationExtendible.add(isNotificationExtendible);
	}
	
	public static void GetRequestMap(){
		
		Gson gson = new Gson();
		String json = gson.toJson(new MealProperties("1","2","3","4","5"));
		System.out.println("json is : " +json);
		Type stringStringMap = new TypeToken<Map<String, List<String>>>(){}.getType();
		Map<String,List<String>> str = gson.fromJson(json, stringStringMap);
		System.out.println("map is : " +str);
	}
	
}


