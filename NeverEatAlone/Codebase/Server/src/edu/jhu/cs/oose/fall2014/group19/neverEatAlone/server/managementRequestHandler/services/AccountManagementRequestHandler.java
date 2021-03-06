package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts.IAccountDBRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers.RequestExecutorHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * 
 * This class manages all acount related requests. The operations handled by
 * this class include account creation,modification and deletion.
 * 
 * @author tejasvamsingh
 *
 */

public class AccountManagementRequestHandler implements
		IManagementRequestHandler {

	@Inject
	IAccountDBRequestHandler iAccountManagerObject;
	@Inject
	IReflectionManager iReflectionManagerObject;
	@Inject
	RequestExecutorHelper requestExecutorHelper;

	/**
	 * This method handles requests to create an account.
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> create(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("reached CreateAccountRequest");
		System.out.println(iAccountManagerObject);
		// ********* LOGGING *********

		String username = request.get("username")[0];
		String password = request.get("password")[0];

		Map<String, String> requestParameters = new HashMap<String, String>();
		boolean createdNotificationCredentials = createNotificationServerUser(
				username, password, requestParameters);

		List<Map<String, String>> result = iAccountManagerObject
				.create(request);

		if (!createdNotificationCredentials) {
			result.get(0).put("Status", "Failed");
			result.get(0)
					.put("Reason",
							"Failed to authorize user on the notification server. Please try again or user a different username/password combination.");

			// roll back account creation.
			delete(request);
		}
		return result;
	}

	/**
	 * 
	 * This method creates a new user on the notification server.
	 * 
	 * @author tejasvamsingh
	 * 
	 * @param username
	 * @param password
	 * @param requestParameters
	 */
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

	/**
	 * This method handles requests to check the validity of a given user
	 * account.
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, String>> isValid(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("reached IsValidAccountRequest");
		// ********* LOGGING *********

		return iAccountManagerObject.isValid(request);
	}

	private List<Map<String, String>> delete(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("reached DeleteAccountRequest");
		// ********* LOGGING *********

		return iAccountManagerObject.delete(request);
	}

	private List<Map<String, String>> update(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("reached UpdateAccountRequest");
		// ********* LOGGING *********

		List<Map<String, String>> result = iAccountManagerObject
				.update(request);

		if (result.get(0).get("Status").equals("Success")) {
			String username = request.get("username")[0];
			String password = request.get("password")[0];// update notification
															// credentials
			Map<String, String> requestParameters = new HashMap<String, String>();
			boolean createdNotificationUser = createNotificationServerUser(
					username, password, requestParameters);

			if (!createdNotificationUser) {
				result.get(0)
						.put("Reason",
								"Failed to authorize user on the notification server. Please try again later. Password was updated.");

			}

		}
		return result;

	}

	private List<Map<String, String>> getInfo(Map<String, String[]> request) {

		// ********* LOGGING *********
		System.out.println("reached GetInfoAccountRequest");
		// ********* LOGGING *********

		return iAccountManagerObject.getInfo(request);
	}

	@Override
	public List<Map<String, String>> handleManagementRequest(
			Map<String, String[]> request) {

		System.out.println("Inside HandleManagementRequest");

		return iReflectionManagerObject.invokeMethod(this,
				request.get("requestType")[0], request);
	}

}
