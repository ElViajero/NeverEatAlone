package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;

/**
 * 
 * This class checks if change of post status is acceptable and satisifies all
 * constraints.
 * 
 * @author tejasvamsingh
 *
 */
public class changePostStatusConstraintChecker implements IConstraintChecker {

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap) {

		String previousStatus = constraintMap.get("previousStatus").toString();
		String newStatus = constraintMap.get("newStatus").toString();
		if (previousStatus.equalsIgnoreCase(newStatus)) {
			return false;
		}

		if (previousStatus.equalsIgnoreCase("CANCELLED")) {
			MessageToasterHelper.toastMessage("Cannot undo a cancelled post.");
			return false;
		}

		return true;

	}

}
