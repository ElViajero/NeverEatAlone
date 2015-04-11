package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.ILocationDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.RequestExecutorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

public class LocationManagementRequestHandler implements
		IManagementRequestHandler {

	@Inject
	IReflectionManager iReflectionManagerObject;
	@Inject
	RequestExecutorHelper requestExecutorHelper;
	@Inject
	ILocationDBRequestHandler iLocationDBRequestHandler;

	Map<String, Map<String, Object>> nearbyPlacesMap;

	final String locationAPIKey = "";

	/**
	 * This method returns a list of places that are nearby to the provided
	 * location.
	 * 
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private List<Map<String, String>> getNearbyPlaces(
			Map<String, String[]> request) {

		String latitude = request.get("latitude")[0];
		String longitude = request.get("longitude")[0];
		String location = request.get("locationName")[0];
		String radius = "1000";
		String type = "restaurant";

		// if(location.equals(""))

		System.out.println("inside the right deal");

		List<Map<String, String>> placeNameMapList = new ArrayList<Map<String, String>>();
		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("Status", "Success");
		placeNameMapList.add(statusMap);
		// worst case
		// return an
		// empty list.

		String requestURLString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
				+ "location="
				+ latitude
				+ ","
				+ longitude
				+ "&radius="
				+ radius + "&types=" + type + "&key=" + locationAPIKey;
		/*
		 * Map<String, Object> responseMap = requestExecutorHelper
		 * .executeRequest(requestURLString); if (responseMap == null) return
		 * placeNameMapList;
		 * 
		 * System.out.println(responseMap.size());
		 * System.out.println(responseMap.keySet());
		 * 
		 * // we actually got some data back if
		 * (responseMap.get("status").toString().equalsIgnoreCase("OK")) {
		 * 
		 * List<Map<String, Object>> resultMapList = (List<Map<String, Object>>)
		 * responseMap .get("results");
		 * 
		 * // add the restaurant names and locality information. for
		 * (Map<String, Object> restaurantMap : resultMapList) { Map<String,
		 * String> placeMap = new HashMap<>(); placeMap.put("name",
		 * restaurantMap.get("name").toString() + "," +
		 * restaurantMap.get("vicinity").toString());
		 * System.out.println("placemap :  " + placeMap);
		 * placeNameMapList.add(placeMap); }
		 * System.out.println("placemapList :  " + placeNameMapList);
		 * 
		 * }
		 */
		return placeNameMapList;
	}

	/**
	 * This method returns a reverse geocoded location based on latitude and
	 * longitude positions.
	 * 
	 * @author tejasvamsingh
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unused")
	private List<Map<String, String>> getLocation(Map<String, String[]> request) {

		System.out
				.println("Reached getLocation in LocationManagementRequestHandler");

		String username = request.get("username")[0];
		String latitude = request.get("latitude")[0];
		String longitude = request.get("longitude")[0];

		String requestURLString = "https://maps.googleapis.com/"
				+ "maps/api/geocode/json?" + "latlng=" + latitude + ","
				+ longitude + "&key=" + locationAPIKey;
		/*
		 * Map<String, Object> responseMap = requestExecutorHelper
		 * .executeRequest(requestURLString);
		 * 
		 * List<Map<String, String>> locationMapList = new ArrayList<Map<String,
		 * String>>();
		 * 
		 * Map<String, String> statusMap = new HashMap<String, String>();
		 * statusMap.put("Status", "Failed"); // initially we don't know if //
		 * our request succeeds.
		 * 
		 * locationMapList.add(statusMap);
		 * 
		 * if (responseMap == null) // if null we weren't able to determine the
		 * // location. return locationMapList;
		 * 
		 * try {
		 * 
		 * // check if result succeeded. if
		 * (responseMap.get("status").toString().equalsIgnoreCase("OK")) {
		 * 
		 * // first change the status of our request.
		 * locationMapList.get(0).put("Status", "Success");
		 * 
		 * // next process the location
		 * 
		 * @SuppressWarnings("unchecked") List<Map<String, Object>>
		 * resultMapList = (List<Map<String, Object>>) responseMap
		 * .get("results");
		 * 
		 * // get the location for (Map<String, Object> restaurantMap :
		 * resultMapList) { String locationName = restaurantMap
		 * .get("formatted_address").toString(); String[] parts =
		 * locationName.split(",");
		 * 
		 * if (parts.length == 3) { Map<String, String> locationMap = new
		 * HashMap<String, String>(); System.out.println("LOCATION IS :::  " +
		 * locationName); locationMap.put("locationName", locationName);
		 * locationMapList.add(locationMap); } }
		 * 
		 * }
		 * 
		 * persistLocation(locationMapList, username);
		 * 
		 * } catch (NullPointerException e) { return locationMapList; }
		 */
		return null;
	}

	/**
	 * This method persists the location of the user. It gets called implicitly
	 * by getLocation.
	 * 
	 * @author tejasvamsingh
	 * @param locationMapList
	 */

	private void persistLocation(List<Map<String, String>> locationMapList,
			String username) {

		System.out.println("locationMapList is : " + locationMapList);

		Map<String, String[]> requestMap = new HashMap<String, String[]>();
		requestMap.put("username", new String[1]);
		requestMap.get("username")[0] = username;

		int numLocations = locationMapList.size();// 1 is the status map
		requestMap.put("locationName", new String[numLocations - 1]);

		int index = 0;
		for (Map<String, String> locationMap : locationMapList) {
			if (!locationMap.containsKey("locationName"))
				continue;
			String location = locationMap.get("locationName");
			System.out.println("location in persistLocation is : " + location);
			requestMap.get("locationName")[index] = location;
			index++;
		}

		List<Map<String, String>> result = iLocationDBRequestHandler
				.updateUserLocation(requestMap);

		if (result.get(0).get("Status").equals("Success"))
			System.out.println("SUCCESSFUL LOCATION UPDATE");

	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);

	}

}
