package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.RequestExecutorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

public class LocationManagementRequestHandler implements
		IManagementRequestHandler {

	@Inject
	IReflectionManager iReflectionManagerObject;
	@Inject
	RequestExecutorHelper requestExecutorHelper;

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

		// if(location.equals(""))

		System.out.println("inside the right deal");

		List<Map<String, String>> placeNameMapList = new ArrayList<Map<String, String>>();
		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("Status", "Success");
		placeNameMapList.add(statusMap);
		// worst case
		// return an
		// empty list.

		String radius = "500";
		String type = "restaurant";

		String requestURLString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
				+ "location="
				+ latitude
				+ ","
				+ longitude
				+ "&radius="
				+ radius + "&types=" + type + "&key=";

		Map<String, Object> responseMap = requestExecutorHelper
				.executeRequest(requestURLString);
		if (responseMap == null)
			return placeNameMapList;

		System.out.println(responseMap.size());
		System.out.println(responseMap.keySet());

		// we actually got some data back
		if (responseMap.get("status").toString().equalsIgnoreCase("OK")) {

			List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) responseMap
					.get("results");

			// add the restaurant names and locality information.
			for (Map<String, Object> restaurantMap : resultMapList) {
				Map<String, String> placeMap = new HashMap<>();
				placeMap.put("name", restaurantMap.get("name").toString() + ","
						+ restaurantMap.get("vicinity").toString());
				System.out.println("placemap :  " + placeMap);
				placeNameMapList.add(placeMap);
			}
			System.out.println("placemapList :  " + placeNameMapList);

		}

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

		String latitude = request.get("latitude")[0];
		String longitude = request.get("longitude")[0];

		String requestURLString = "https://maps.googleapis.com/"
				+ "maps/api/geocode/json?" + "latlng=" + latitude + ","
				+ longitude + "&key=";

		return null;
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);
	}

}
