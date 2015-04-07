package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts;

import java.util.Map;

/**
 * This contract defines the scheme for checking constraints.
 * 
 * @author tejasvamsingh
 *
 */
public interface IConstraintChecker {

	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap);

}
