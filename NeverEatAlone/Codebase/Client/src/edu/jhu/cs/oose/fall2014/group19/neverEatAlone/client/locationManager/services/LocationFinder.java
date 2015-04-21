package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.locationManager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.LocationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class LocationFinder extends AsyncTask<Void, Void, Void> {

	static Activity activityObject;
	static LocationManager locationManagerObject;
	static int timeToCheck;
	static boolean firstTime = true;

	public LocationFinder(Activity activity, LocationManager locationManager) {
		activityObject = activity;
		locationManagerObject = locationManager;
	}

	private void getLocationProperties() {

		locationManagerObject.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5, 0, new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLocationChanged(Location location) {

						LocationProperties locationProperties = new LocationProperties();
						locationProperties.setLatitude(location.getLatitude());
						locationProperties.setLongitude(location.getLongitude());
						if (LocationProperties.hasLocationChanged(
								DataCacheHelper.getCachedLocation(),
								locationProperties) // multiple users same
													// device
								|| LocationProperties.hasLocationChanged(
										DataCacheHelper.getCachedLocation(),
										AccountProperties
												.getUserAccountInstance()
												.getLocationProperties())) {

							AccountProperties
									.getUserAccountInstance()
									.getLocationProperties()
									.setLatitude(
											locationProperties.getLatitude());
							AccountProperties
									.getUserAccountInstance()
									.getLocationProperties()
									.setLongitude(
											locationProperties.getLongitude());
							getLocation();
							fetchAutoCompleteFields();

							DataCacheHelper
									.setCachedLocation(locationProperties);
						}

						// multiple users same device log in/ log out clause

					}
				});

	}

	@SuppressWarnings("unchecked")
	private void fetchAutoCompleteFields() {
		try {

			// send the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(
							activityObject,
							AccountProperties.getUserAccountInstance()
									.getLocationProperties().toMap(),
							"Location", "getNearbyPlaces");

			if (DataCacheHelper.getAdapter("restaurantAutoComplete") == null) {
				DataCacheHelper.registerAdapter(new ArrayAdapter<String>(
						activityObject,
						android.R.layout.simple_dropdown_item_1line,
						new ArrayList<String>()), "restaurantAutoComplete");
			}

			ArrayAdapter<String> restaurantAutoCompleteArrayAdapter = (ArrayAdapter<String>) DataCacheHelper
					.getAdapter("restaurantAutoComplete");

			restaurantAutoCompleteArrayAdapter.clear();

			for (Map<String, String> map : resultMapList) {
				restaurantAutoCompleteArrayAdapter.add(map.get("name"));
			}

			restaurantAutoCompleteArrayAdapter.notifyDataSetChanged();
			DataCacheHelper.registerAdapter(restaurantAutoCompleteArrayAdapter,
					"restaurantAutoComplete");

		} catch (RequestAbortedException e) {
			return;
		}

	}

	private void getLocation() {
		try {

			Map<String, Object> requestMap = AccountProperties
					.getUserAccountInstance().toMap();

			requestMap.putAll(AccountProperties.getUserAccountInstance()
					.getLocationProperties().toMap());

			// send the request.
			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activityObject,
							requestMap, "Location", "getLocation");

			for (Map<String, String> map : resultMapList) {
				AccountProperties.getUserAccountInstance()
						.getLocationProperties()
						.setLocationName(map.get("locationName"));
				break;
			}

		} catch (RequestAbortedException e) {
			return;
		}

	}

	@Override
	protected Void doInBackground(Void... params) {
		timeToCheck = 0;
		while (timeToCheck <= 10000 && !firstTime)
			timeToCheck++;
		if (firstTime)
			firstTime = false;
		return null;
	}

	@Override
	protected void onPostExecute(Void nullItem) {

		getLocationProperties();
	}
}
