package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAlertHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class GetContactNotificationExecutableRequest implements
		IRequestExecutor {

	@Override
	public void executeRequest(Activity activity) {

		System.out.println("entering getContactNotificationRequest");

		String requestID = "Contact";
		String requestType = "fetchRequests";
		List<IActivityProperties> contactList = new ArrayList<IActivityProperties>();

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activity,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;
				System.out.println("RESULT IS :" + result);
				result.put("postType", "contact");
				contactList.add(new NotificationProperties(result));
			}

			if (!contactList.isEmpty())
				NotificationAlertHelper.enableNotificationAlert("contact");

		} catch (RequestAbortedException e) {
			return;
		} finally {
			DataCacheHelper.cacheResults("contactRequest", contactList);
			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					false);
		}
	}

}
