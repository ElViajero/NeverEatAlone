package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.contracts.IRequestProperties;

public class MealProperties implements IRequestProperties{


	List<String> Location;
	List<String> MaxNumberOfInvitees;
	List<String> IsNotificationExtendible;

	public MealProperties(String location,
			String maxNumberOfInvitees, String isNotificationExtendible) {


		Location = new ArrayList<String>();
		Location.add(location);
		MaxNumberOfInvitees = new ArrayList<String>();
		MaxNumberOfInvitees.add(maxNumberOfInvitees);
		IsNotificationExtendible = new ArrayList<String>();
		IsNotificationExtendible.add(isNotificationExtendible);



	}

	@Override
	public Map<String, List<String>> GetRequestMap(){

		Gson gson = new Gson();
		String json = gson.toJson(this);
		System.out.println("json is : " +json);
		Type stringStringMap = new TypeToken<Map<String, List<String>>>(){}.getType();
		Map<String,List<String>> requestMap = gson.fromJson(json, stringStringMap);
		System.out.println("map is : " +requestMap);

		return requestMap;

	}

}


