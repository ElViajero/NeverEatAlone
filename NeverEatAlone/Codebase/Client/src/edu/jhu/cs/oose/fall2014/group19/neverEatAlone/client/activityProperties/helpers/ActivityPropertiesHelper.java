package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

public class ActivityPropertiesHelper {

	static Map<String,String> activityPropertTypeMap;
	static final String PACKAGE_NAME="edu.jhu.cs.oose."
			+ "fall2014.group19.neverEatAlone.client."
			+ "activityProperties.services.";

	public static Class<?> getPropertyTypes(String postType){
		System.out.println("postType is "+ postType);
		if(activityPropertTypeMap==null)
			initPropertyTypes();
		System.out.println("types map : "+ activityPropertTypeMap);
		Class<?> classObject;
		try {
			classObject = Class.forName(
					PACKAGE_NAME+activityPropertTypeMap.get(postType));
			return classObject;
		} catch (ClassNotFoundException e) {
			System.out.println("No such property.");
			e.printStackTrace();

		}
		return null;
	}



	private static void initPropertyTypes() {

		Gson gson = GsonHelper.getGsoninstance();
		String str = null;
		try {
			InputStream url = ActivityPropertiesHelper.class.getResourceAsStream("postTypes.json")	;			
			str = IOUtils.toString(url);
			System.out.println("Str + "+str);
		} catch (IOException e1) {
			System.out.println("IOException in initPrpopertyTypes "+ e1.getMessage());
			e1.printStackTrace();
		}

		Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
		activityPropertTypeMap = gson.fromJson(str, stringStringMap);

	}


	public static Map<String,Object> combineMaps(Map<String,Object>...args ){

		Map<String,Object> combinedMap=
				new HashMap<String,Object>();

		for( Map<String, Object> map : args ){

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				combinedMap.put(entry.getKey(),entry.getValue());
			}
		}
		return combinedMap;
	}


}
