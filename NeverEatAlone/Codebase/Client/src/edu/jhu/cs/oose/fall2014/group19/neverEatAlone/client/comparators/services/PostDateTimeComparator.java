package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.comparators.services;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * Comparator used for sorting in order of event start times.
 * 
 * @author tejasvamsingh
 *
 */
public class PostDateTimeComparator implements Comparator<IActivityProperties> {

	@Override
	public int compare(IActivityProperties lhsActivityProperties,
			IActivityProperties rhsActivityProperties) {

		PostProperties lhs = (PostProperties) lhsActivityProperties;
		PostProperties rhs = (PostProperties) rhsActivityProperties;

		Gson gson = GsonHelper.getGsoninstance();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();

		Map<String, String> lhsPostDataMap = gson.fromJson(lhs.getPostData(),
				stringStringMap);

		Map<String, String> rhsPostDataMap = gson.fromJson(rhs.getPostData(),
				stringStringMap);

		MealProperties lhsMealProperties = new MealProperties(lhsPostDataMap);
		MealProperties rhsMealProperties = new MealProperties(rhsPostDataMap);

		String lhsString = lhsMealProperties.getStartDateAndTimeProperties()
				.toString();

		System.out.println("lhsString : " + lhsString);

		String rhsString = rhsMealProperties.getStartDateAndTimeProperties()
				.toString();

		System.out.println("rhsString : " + rhsString);

		return lhsString.compareTo(rhsString);

	}
}
