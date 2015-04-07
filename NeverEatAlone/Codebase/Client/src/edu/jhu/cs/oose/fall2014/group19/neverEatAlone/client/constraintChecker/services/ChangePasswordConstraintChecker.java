package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

/**
 * This class checks password constraints for changing a password. Map takes
 * parameters "old","new","confirm" for old password, new password and confirm
 * new password respectively.
 * 
 * @author tejasvamsingh
 *
 */
public class ChangePasswordConstraintChecker implements IConstraintChecker {

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constriantMap) {

		boolean returnVal = false;

		try {

			if (!constriantMap
					.get("old")
					.toString()
					.equals(AccountProperties.getUserAccountInstance()
							.getpassword())) {
				MessageToasterHelper.toastMessage("Old password is incorrect.");
				return false;
			}

			if (!constriantMap.get("new").toString()
					.equals(constriantMap.get("confirm").toString())) {
				MessageToasterHelper
						.toastMessage("New and Confirm password must match.");
				return false;
			}

			return true;

		} catch (NullPointerException e) {
			return false;
		}

	}
}
