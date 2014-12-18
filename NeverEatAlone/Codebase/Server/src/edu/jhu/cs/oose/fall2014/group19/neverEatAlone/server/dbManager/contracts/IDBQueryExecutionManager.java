package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;


/**
 * 
 * @author tejasvamsingh
 *
 */
@Local

public interface IDBQueryExecutionManager {


	public List<Map<String,String>>
	executeQuery(String query, Map<String,Object> queryParameters);


}
