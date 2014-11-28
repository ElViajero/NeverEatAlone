package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts;

import java.util.Map;

/**
 * Interface that defines the
 * services offered by RequestProperty classes.
 * 
 * @author tejasvamsingh
 *
 */
public interface IActivityProperties {

	/**
	 * This method returns a map consisting of request data.
	 * @author tejasvamsingh
	 * @return
	 */

	public Map<String,Object> toMap();




}

