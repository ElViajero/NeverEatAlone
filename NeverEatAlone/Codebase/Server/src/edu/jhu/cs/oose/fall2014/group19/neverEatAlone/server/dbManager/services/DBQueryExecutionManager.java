package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBInstanceManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBQueryExecutionManager;

/**
 * 
 * @author tejasvamsingh
 *
 */

public class DBQueryExecutionManager implements IDBQueryExecutionManager {

	@Inject
	IDBInstanceManager iDBInstanceMangerObject;

	@Override
	public List<Map<String, String>> executeQuery(String query,
			Map<String, Object> queryParameters) {

		System.out.println("Inside executeQuery in DBManager");
		GraphDatabaseService graphDatabaseInstance = iDBInstanceMangerObject
				.getGraphDatabaseInstance();

		ExecutionEngine executionEngine = new ExecutionEngine(
				graphDatabaseInstance, StringLogger.SYSTEM);
		ExecutionResult result;
		System.out.println("Begining query execution in DBManager");

		try (Transaction tx = graphDatabaseInstance.beginTx()) {
			result = executionEngine.execute(query, queryParameters);
			tx.success();
			return GetResultMapList(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			List<Map<String, String>> resultMapList = GetResultMapList(null);
			resultMapList.get(0).put("Reason", e.getMessage());
			return resultMapList;
		}
	}

	/**
	 * 
	 * This method returns a list of Maps for each entry that is part of a
	 * Cypher query result.
	 * 
	 * @param result
	 * @return
	 */
	private static List<Map<String, String>> GetResultMapList(
			ExecutionResult result) {

		// ************** LOGGING ************************
		System.out.println("Inside GetResultMapList: getting result maplist");
		// ************** LOGGING ************************

		// initialize the map to return.
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();

		// map to determine status of result ( success or failure).
		Map<String, String> successMap = new HashMap<String, String>();
		// default status of query is failed.
		successMap.put("Status", "Failed");

		resultMapList.add(0, successMap);

		// Check if result is null.
		// This happens in the case of violated
		// constraints.
		if (result == null) {
			System.out.println("Result is NULL");
			return resultMapList;
		}

		// Iterate over the returned rows
		// The keys of the map are the column names and the values are the
		// entries
		ResourceIterator<Map<String, Object>> rows = result.iterator();

		if (!rows.hasNext()) {
			resultMapList.get(0).put("Reason", "EMPTY");
			return resultMapList;
		}

		// index of element in the maplist
		int index = 0;

		while (rows.hasNext()) {

			index++; // 0 is for Status
			Map<String, Object> currentRow = rows.next();
			System.out.println("reached row " + index + " : " + currentRow);
			// check if this index exists in the list.
			if (index >= resultMapList.size())
				resultMapList.add(new HashMap<String, String>());

			for (String col : currentRow.keySet()) {
				Object entry = currentRow.get(col);

				// another null check
				if (entry == null)
					continue;

				if (entry instanceof org.neo4j.graphdb.PropertyContainer) {

					// ********* LOGGING *********
					System.out.println("IT IS A NODE OR RELATIONSHIP");
					// ********* LOGGING *********

					PropertyContainer currentEntry = (PropertyContainer) entry;
					// iterate over all the properties for the current node
					// object.
					for (String property : currentEntry.getPropertyKeys()) {
						// add the properties. Note the value is returned as
						// string.
						resultMapList.get(index).put(property,
								currentEntry.getProperty(property).toString());

					}
				}

				else {
					System.out.println("DATA : " + entry.toString());
					resultMapList.get(index).put(col, entry.toString());
				}
			}
		}

		// check if we have some result entries.
		// If so, update the query result status.
		if (resultMapList.size() > 1)
			resultMapList.get(0).put("Status", "Success");

		// return the query result.
		return resultMapList;

	}

}
