package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class DeletetLocationRequestExecutor implements IRequestExecutor {

	@Override
	public void executeRequest(Activity activity) {

		String requestID = "Location";
		String requestType = "delete";

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activity,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

		} catch (RequestAbortedException e) {
			System.out.println("Already Handled");
		}

	}

}
