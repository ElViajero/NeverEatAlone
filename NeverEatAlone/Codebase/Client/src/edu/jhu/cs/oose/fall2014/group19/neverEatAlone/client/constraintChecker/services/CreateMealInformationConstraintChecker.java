package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.util.Log;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

public class CreateMealInformationConstraintChecker implements
		IConstraintChecker {

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap) {

		String location = constraintMap.get("location").toString();

		int startYear = (Integer) constraintMap.get("startYear");
		int startMonth = (Integer) constraintMap.get("startMonth");
		int startDay = (Integer) constraintMap.get("startDay");

		int endYear = (Integer) constraintMap.get("endYear");
		int endMonth = (Integer) constraintMap.get("endMonth");
		int endDay = (Integer) constraintMap.get("endDay");

		int endHour = (Integer) constraintMap.get("endHour");
		int endMinute = (Integer) constraintMap.get("endMinute");

		int startHour = (Integer) constraintMap.get("startHour");
		int startMinute = (Integer) constraintMap.get("startMinute");

		if (location.equals("")) {
			MessageToasterHelper.toastMessage("Location cannot be empty.");
			return false;
		}

		// converts the date into a java date type and checks if start date is
		// after end date
		SimpleDateFormat ft = new SimpleDateFormat("yyyy:mm:dd:HH:MM");
		String startDateStr = startYear + ":" + startMonth + ":" + startDay
				+ ":" + startHour + ":" + startMinute;
		String endDateStr = endYear + ":" + endMonth + ":" + endDay + ":"
				+ endHour + ":" + endMinute;
		Date startDate, endDate;
		try {

			startDate = ft.parse(startDateStr);
			endDate = ft.parse(endDateStr);
			if (startDate.after(endDate)) {
				MessageToasterHelper
						.toastMessage("Please enter valid start/end date/time");
				return false;
			}
		} catch (Exception e) {
			MessageToasterHelper
					.toastMessage("Please enter valid start/end date/time");
			Log.e("CreatMealInfo", "date parsing error");
			return false;
		}

		return true;
	}

}
