package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author tejasvamsingh
 *
 */
public interface INotificationManagementRequestHandler {
	public List<Map<String,String>> accept(Map<String,String[]> request);
	public List<Map<String,String>> reject(Map<String,String[]> request);
}
