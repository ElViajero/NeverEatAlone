package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class FetchPostRequestExecutor implements IRequestExecutor {

	@Override
	public void executeRequest(Activity activity) {

		String requestID = "Meal";
		String requestType = "fetchPosts";

		NotificationAndPostCacheHelper.clearAdapterDataMap("mealPost");
		List<IActivityProperties> postList = new ArrayList<IActivityProperties>();
		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activity,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;

				NotificationProperties notification = new NotificationProperties(
						result);

				PostProperties post = PostProperties
						.notificationToPost(notification);
				NotificationAndPostCacheHelper.addPost(post, "mealPost");

				postList.add(post);

			}
			NotificationAndPostCacheHelper.setServerFetchRequired("mealPost",
					false);

		} catch (RequestAbortedException e) {
			System.out.println("Already Handled");
		} finally {
			DataCacheHelper.cacheResults("mealPost", postList);
		}

	}

}
