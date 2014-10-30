package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts;

import java.util.List;
import java.util.Map;

public interface IManagementRequestHandler {
	
	public List<Map<String,String>> HandleManagementRequest(Map<String,String[]> request);	
}
