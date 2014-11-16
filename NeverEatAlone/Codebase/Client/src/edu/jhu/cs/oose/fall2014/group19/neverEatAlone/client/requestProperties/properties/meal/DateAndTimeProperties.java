package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.contracts.IRequestProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction
 * of the properties required for the date and time.
 * 
 * @author tejasvamsingh
 *
 */
public class DateAndTimeProperties implements IRequestProperties  {


	List<String> Day;
	List<String> Month;
	List<String> Year;
	List<String> Hour;
	List<String> Minute;


	public DateAndTimeProperties(int day,int month,
			int year,int hour, int minute) {

		// Initialize the lists.
		Day = new ArrayList<String>();
		Month = new ArrayList<String>();
		Year = new ArrayList<String>();
		Hour = new ArrayList<String>();
		Minute = new ArrayList<String>();

		// add the values.
		Day.add(String.valueOf(day));
		Month.add(String.valueOf(month));
		Year.add(String.valueOf(year));
		Hour.add(String.valueOf(hour));
		Minute.add(String.valueOf(minute));

	}




	/**
	 * Method that returns a 
	 * modified Json of this class.
	 * Written so that 
	 * 
	 * @return
	 */


	@Override
	public Map<String, List<String>> GetRequestMap() {

		Gson gsonObject = 
				GsonHelper.GetGsonInstance();

		String jsonString = gsonObject.toJson(this);
		System.out.println("json is : " +jsonString);
		Type stringListStringMap = new TypeToken<Map<String, List<String>>>(){}.getType();
		Map<String,List<String>> requestMap = gsonObject.fromJson(jsonString, stringListStringMap);
		System.out.println("map is : " +requestMap);

		return requestMap;

	}




}
