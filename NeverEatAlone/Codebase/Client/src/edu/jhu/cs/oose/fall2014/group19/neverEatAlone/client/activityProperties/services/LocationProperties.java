package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * This class is a location properties class that handles latitude, longitude
 * and location name.
 * 
 * @author tejasvamsingh
 *
 */
public class LocationProperties implements IActivityProperties {

	private String latitude;
	private String longitude;
	private String locationName;

	public LocationProperties() {
		latitude = "0";
		longitude = "0";
		locationName = "";
	}

	public LocationProperties(double latitude, double longitude) {
		this.latitude = String.valueOf(latitude);
		this.longitude = String.valueOf(longitude);
		locationName = "";
	}

	@Override
	public Map<String, Object> toMap() {

		Gson gson = GsonHelper.getGsoninstance();
		String json = gson.toJson(this);
		System.out.println("json is : " + json);
		Type stringObjectMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> requestMap = gson.fromJson(json, stringObjectMap);
		System.out.println("map is : " + requestMap);
		return requestMap;
	}

	@Override
	public void fromMap(Map<String, String> map) {
		locationName = map.get("location");
	}

	public double getLatitude() {
		return Double.parseDouble(latitude);
	}

	public void setLatitude(double latitude) {
		this.latitude = String.valueOf(latitude);
	}

	public double getLongitude() {
		return Double.parseDouble(longitude);
	}

	public void setLongitude(double longitude) {
		this.longitude = String.valueOf(longitude);
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * 
	 * This method checks if the location change is significant.
	 * 
	 * @author tejasvamsingh
	 * @param initialLocation
	 * @param newLocation
	 * @return
	 */
	public static boolean hasLocationChanged(
			LocationProperties initialLocation, LocationProperties newLocation) {

		double initialLatitude = initialLocation.getLatitude();
		double newLatitude = newLocation.getLatitude();

		if (Math.abs(initialLatitude - newLatitude) > .1)
			return true;

		double initalLongitude = initialLocation.getLongitude();
		double newLongitude = newLocation.getLongitude();

		if (Math.abs(initalLongitude - newLongitude) > .1)
			return true;

		return false;
	}

	@Override
	public boolean equals(Object o) {

		LocationProperties other = (LocationProperties) o;
		return (other.latitude == latitude && other.longitude == longitude);

	}

}
