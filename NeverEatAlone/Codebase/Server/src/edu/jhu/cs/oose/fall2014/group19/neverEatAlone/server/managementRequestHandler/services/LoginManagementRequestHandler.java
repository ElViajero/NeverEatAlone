package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.ILoginDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.RequestExecutorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * 
 * This class manages all login related requests. The operations handled by this
 * class include checking user credentials for a login request.
 * 
 * @author tejasvamsingh
 *
 */

public class LoginManagementRequestHandler implements IManagementRequestHandler {

	@Inject
	ILoginDBRequestHandler iLoginDBRequestHandlerObject;
	@Inject
	IReflectionManager iReflectionManagerObject;
	@Inject
	RequestExecutorHelper requestExecutorHelper;

	/**
	 * This method checks user credentials for a login request.
	 * 
	 * @param request
	 * @return
	 */

	private List<Map<String, String>> checkCredentials(
			Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("Reached CheckCredentialsLoginRequest.");
		// ********* LOGGING *********

		List<Map<String, String>> result = iLoginDBRequestHandlerObject
				.checkCredentials(request);

		String username = request.get("username")[0];
		String password = request.get("password")[0];

		if (result.get(0).get("Status").equals("Success"))
			createNotificationServerUser(username, password,
					new HashMap<String, String>());

		return result;

	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);

	}

	private boolean createNotificationServerUser(String username,
			String password, Map<String, String> requestParameters) {
		requestParameters.put("password", password);
		requestParameters.put("tags", "management");

		String requestURLString = "http://10.0.0.3:15672/api/users/" + username;

		Map<String, String> credentialsMap = new HashMap<String, String>();
		credentialsMap.put("username", "guest");
		credentialsMap.put("password", "guest");

		requestExecutorHelper.executePutRequest(requestParameters,
				requestURLString, credentialsMap);

		requestParameters.clear();

		requestParameters.put("configure", "^" + username);
		requestParameters.put("write", "");
		requestParameters.put("read", "^" + username);

		requestURLString = "http://10.0.0.3:15672/api/permissions/%2f/"
				+ username;

		return requestExecutorHelper.executePutRequest(requestParameters,
				requestURLString, credentialsMap);
	}

}
