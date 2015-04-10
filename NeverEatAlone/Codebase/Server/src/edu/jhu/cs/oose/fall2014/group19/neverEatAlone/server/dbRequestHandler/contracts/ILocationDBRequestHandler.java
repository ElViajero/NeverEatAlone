package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * 
 * This interface defines the contract for location persistence services.
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface ILocationDBRequestHandler {

	public List<Map<String, String>> updateUserLocation(
			Map<String, String[]> request);

}
