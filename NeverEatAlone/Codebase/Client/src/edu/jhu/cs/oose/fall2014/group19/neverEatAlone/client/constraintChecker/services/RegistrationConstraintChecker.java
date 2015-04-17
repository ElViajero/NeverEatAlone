package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

/**
 * Constraint checker for registration.
 * 
 * @author tejasvamsingh
 *
 */
public class RegistrationConstraintChecker implements IConstraintChecker {

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap) {

		String username = constraintMap.get("username").toString();
		String password = constraintMap.get("password").toString();
		String confirmPassword = constraintMap.get("confirmPassword")
				.toString();

		if (username.equals("")) {
			MessageToasterHelper.toastMessage("Username cannot be empty.");
			return false;
		}

		if (password.equals("")) {
			MessageToasterHelper.toastMessage("Password cannot be empty.");
			return false;
		}

		if (!password.equals(confirmPassword)) {
			MessageToasterHelper
					.toastMessage("entries in Password and Confirm Password must match.");
			return false;
		}
		return true;
	}
}
