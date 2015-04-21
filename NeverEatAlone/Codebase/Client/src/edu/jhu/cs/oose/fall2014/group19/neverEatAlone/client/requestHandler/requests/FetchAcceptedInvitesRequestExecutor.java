package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class FetchAcceptedInvitesRequestExecutor implements IRequestExecutor {

	@Override
	public void executeRequest(Activity activity) {
		String requestID = "Meal";
		String requestType = "fetchAccepted";

		List<NotificationProperties> acceptedInvitesList = new ArrayList<NotificationProperties>();
		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activity,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;

				acceptedInvitesList.add(new NotificationProperties(result));
			}

		} catch (RequestAbortedException e) {
			return;
		} finally {
			DataCacheHelper.cacheResults("acceptedMeal", acceptedInvitesList);
		}

	}
}
