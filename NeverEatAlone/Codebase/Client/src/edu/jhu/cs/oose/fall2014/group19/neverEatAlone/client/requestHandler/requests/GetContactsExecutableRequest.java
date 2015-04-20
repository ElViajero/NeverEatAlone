package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IExecutableRequest;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

public class GetContactsExecutableRequest implements IExecutableRequest {

	@Override
	public void executeRequest(Activity activity) {

		String requestID = "Contact";
		String requestType = "getAll";

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());

		List<ContactProperties> contactList = new ArrayList<ContactProperties>();

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(activity,
							requestMap, requestID, requestType);

			contactList.clear();

			Set<ContactProperties> contactSet = new HashSet<ContactProperties>();

			for (Map<String, String> result : resultMapList) {
				if (result.isEmpty())
					continue;
				contactSet.add(new ContactProperties(result));
			}

			contactList.addAll(contactSet);

			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					false);

		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		} finally {
			DataCacheHelper.cacheResults("contact", contactList);
		}

	}

}
