/*package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal.DateAndTimeProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal.MealProperties;

 *//**
 * This class is a helper class
 * that implements the 3 page approach to meal post creation.
 * @author tejasvamsingh
 *
 *//*

public class MealPostPropertiesHelper {

  *//**
  * This method returns a JSON string representing data on the first
  * page of meal post creation
  * 
  * @author tejasvamsingh
  * @param mealProperties
  * @param startDateAndTimeProperties
  * @param endDateAndTimeProperties
  * @return
  *//*


	//NOTE :: I'm temporarily returning a MAP since the GUI doesn't 
	// have pages 2 and 3 and since we are just trying to test
	// whether this works correctly. The return type should be changed to String.
	public static Map<String,List<String>> GetPageOneString(MealProperties mealProperties,
			DateAndTimeProperties startDateAndTimeProperties,
			DateAndTimeProperties endDateAndTimeProperties){


		// Get the meal Map
		Map<String,List<String>> mealMap = mealProperties.GetRequestMap();
		Map<String,List<String>> pageOneMap = new HashMap<String,List<String>>(mealMap);

		// Get the start date and time map
		Map<String,List<String>> startDateAndTimeMap = startDateAndTimeProperties.GetRequestMap();
		// Get the end date and time map
		Map<String,List<String>> endDateAndTimeMap = endDateAndTimeProperties.GetRequestMap();


		//change the fields in the maps
		for (Map.Entry<String, List<String>> entry : startDateAndTimeMap.entrySet()) {

			String key = entry.getKey();
			List<String> value = entry.getValue();

			pageOneMap.put("Start"+key, value);

		}

		//change the fields in the maps
		for (Map.Entry<String, List<String>> entry : endDateAndTimeMap.entrySet()) {

			String key = entry.getKey();
			List<String> value = entry.getValue();
			pageOneMap.put("End"+key, value);

		}

		Random r = new Random();

		// NOTE FROM TEJAS TO HIMSELF :::: REMOVE THIS !!!!!!

		List<String> posterList = new ArrayList<String>();
		List<String> postIDList = new ArrayList<String>();
		List<String> recipientList = new ArrayList<String>();
		recipientList.add("Tejas");
		posterList.add("a");
		postIDList.add(String.valueOf(r.nextInt()));
		pageOneMap.put("Recipient",recipientList);
		pageOneMap.put("Poster",posterList);
		pageOneMap.put("PostID",postIDList);


		// NOTE FROM TEJAS TO HIMSELF :::: REMOVE THIS !!!!!!




		return pageOneMap;


	}


}
   */