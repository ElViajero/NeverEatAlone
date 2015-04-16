package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class provides an abstraction of the properties required for the date
 * and time.
 * 
 * @author tejasvamsingh
 *
 */
public class DateAndTimeProperties implements IActivityProperties {

	// instance variables

	String day;
	String month;
	String year;
	String hour;
	String minute;
	static String prefix = "";

	// constructors
	public DateAndTimeProperties(int day, int month, int year, int hour,
			int minute, String prefixString) {
		this(day, month, year, hour, minute);
		prefix = prefixString;

	}

	public DateAndTimeProperties(int day, int month, int year, int hour,
			int minute) {

		// Initialize the lists.
		prefix = "";
		this.day = String.valueOf(day);
		this.month = String.valueOf(month);
		this.year = String.valueOf(year);
		this.hour = String.valueOf(hour);
		this.minute = String.valueOf(minute);

		if (this.minute.length() == 1)
			this.minute = "0" + this.minute;
		if (this.hour.length() == 1)
			this.hour = "0" + this.hour;

	}

	public DateAndTimeProperties(Map<String, String> map) {
		fromMap(map);
	}

	// interface methods

	/**
	 * Method that returns a modified Json of this class.
	 * 
	 * @return
	 */
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

		return addPrefix(requestMap);
	}

	@Override
	public void fromMap(Map<String, String> map) {
		day = map.get(prefix + "day");
		month = map.get(prefix + "month");
		hour = map.get(prefix + "hour");
		minute = map.get(prefix + "minute");
		year = map.get(prefix + "year");
	}

	// getters,setters, toString()
	@Override
	public String toString() {
		return month + "/" + day + "/" + year + "   " + hour + ":" + minute;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefixString) {
		prefix = prefixString;
	}

	// private helper methods
	private Map<String, Object> addPrefix(Map<String, Object> requestMap) {

		Map<String, Object> prefixRequestMap = new HashMap<String, Object>();

		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (!key.equals("prefix"))
				key = prefix + key;
			prefixRequestMap.put(key, value);
		}
		return prefixRequestMap;
	}

	public int getDay() {
		return Integer.parseInt(day);
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getMonth() {
		return Integer.parseInt(month);
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return Integer.parseInt(year);
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getHour() {
		return Integer.parseInt(hour);
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return Integer.parseInt(minute);
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getDateString() {
		return day + "-" + month + "-" + year;
	}

	public String getTimeString() {
		return hour + ":" + minute;
	}

}
