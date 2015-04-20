package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.authenticationManager.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.authenticationManager.contracts.IAuthenticationManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts.IRequestDispatcher;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts.ISecurityManager;

/**
 * This class manages user authentication.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class AuthenticationManager implements IAuthenticationManager {

	@Singleton
	Map<String, String> authenticatedUsersMap;
	@Inject
	ISecurityManager iSecurityManager;
	@Inject
	IRequestDispatcher iRequestDispatcher;

	/**
	 * This method checks if user is authenticated.
	 */
	@Override
	public boolean isUserAuthenticated(String userPassString) {

		System.out
				.println("inside isUserAuthenticated in AuthenticationManager");

		String[] parts = userPassString.split(":");
		String username = parts[0];
		String password = parts[1];
		String token = iSecurityManager.getEncryptedString(password);

		if (authenticatedUsersMap != null
				&& authenticatedUsersMap.containsKey(username)
				&& authenticatedUsersMap.get(username).equals(token))
			return true;

		// If user doesn't have a valid session, we need to go to the back end

		System.out.println("NOT IN MAP");

		String[] passwordArray = new String[1];
		passwordArray[0] = password;

		String[] usernameArray = new String[1];
		usernameArray[0] = username;

		Map<String, String[]> requestMap = new HashMap<String, String[]>();

		requestMap.put("username", usernameArray);
		requestMap.put("password", passwordArray);
		requestMap.put("requestID", new String[] { "Login" });
		requestMap.put("requestType", new String[] { "checkCredentials" });

		List<Map<String, String>> result = iRequestDispatcher
				.dispatchRequest(requestMap);
		if (result.get(0).get("Status").equals("Success")) {

			System.out.println("checkCredentials is succeeding");
			System.out.flush();
			generateToken(result);

			return true;
		}

		return false;

	}

	/**
	 * Method generates a user token which is used for subsequent requests by
	 * the user.
	 * 
	 * @author tejasvamsingh
	 * @param result
	 */
	private void generateToken(List<Map<String, String>> result) {

		if (authenticatedUsersMap == null)
			authenticatedUsersMap = new HashMap<String, String>();
		String password = result.get(1).get("password");
		String username = result.get(1).get("username");
		authenticatedUsersMap.put(username, password);
	}

}
