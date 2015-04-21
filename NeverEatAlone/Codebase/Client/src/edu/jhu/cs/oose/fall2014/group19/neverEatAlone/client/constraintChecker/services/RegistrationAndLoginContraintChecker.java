package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

public class RegistrationAndLoginContraintChecker implements IConstraintChecker {

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap) {

		String username = constraintMap.get("username").toString();
		String password = constraintMap.get("password").toString();

		if (username.equals("")) {
			MessageToasterHelper.toastMessage("Username cannot be blank.");
			return false;
		}

		if (password.equals("")) {
			MessageToasterHelper.toastMessage("Password cannot be blank.");
			return false;
		}

		if (username.length() < 5 || username.length() > 14) {

			MessageToasterHelper
					.toastMessage("Username must be between 5 and 14 characters");
			return false;
		}

		if (password.length() < 5) {

			MessageToasterHelper
					.toastMessage("Password must be at least 5 characters long.");
			return false;
		}

		return true;
	}

}
