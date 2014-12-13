package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;

@Stateless
public class DBRequestHandlerHelper {


	/**
	 * This method is used for formatting query parameters.
	 * Note, this is a generic method that assumes every String[] has 
	 * one element.
	 * 
	 * In case of multiple query parameters, 
	 * the calling method must take care of this detail.
	 * If not, the information may be lost.
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String,String> GetQueryParameterMap(Map<String,String[]> request){		 

		Map<String,String> queryParameterMap = new HashMap<String,String>();

		for (Map.Entry<String, String[]> entry : request.entrySet()) {

			//obtain the key and value for the current entry.
			String key = entry.getKey();
			String[] value = entry.getValue();

			// Put the first element of String[] into our new map.
			// Read method documentation for more details.		    		    
			queryParameterMap.put(key, value[0]);		   		   
		}		

		queryParameterMap.remove("requestID");
		queryParameterMap.remove("requestType");
		return queryParameterMap;
	}



	/**
	 * 
	 * This method returns a list of Maps for 
	 * each entry that is part of a Cypher query result. 
	 * 
	 * @param result
	 * @return
	 */		
	public static List<Map<String,String>> GetResultMapList(ExecutionResult result){


		// ************** LOGGING ************************
		System.out.println("Inside DBManager: getting result maplist");
		// ************** LOGGING ************************


		// initialize the map to return.
		List<Map<String,String>> resultMapList = 
				new ArrayList<Map<String,String>>();


		//map to determine status of result ( success or failure).
		Map<String, String> successMap =
				new HashMap<String,String>();
		// default status of query is failed.
		successMap.put("Status","Failed");

		resultMapList.add(0, successMap);

		// Check if result is null. 
		// This happens in the case of violated
		// constraints.		
		if(result == null ){
			System.out.println("Result is NULL");
			return resultMapList;
		}

		// Iterate over the returned rows
		// The keys of the map are the column names and the values are the entries
		ResourceIterator<Map<String,Object>> rows = result.iterator();

		//index of element in the maplist 
		int index=0;

		while(rows.hasNext()){

			index ++; // 0 is for Status
			Map<String,Object> currentRow = rows.next();
			System.out.println("reached row "+index+" : "+currentRow);
			//check if this index exists in the list.
			if(index>=resultMapList.size())
				resultMapList.add(new HashMap<String,String>());

			for(String col : currentRow.keySet()){
				Object entry = currentRow.get(col); 

				//another null check
				if(entry==null)
					continue;

				if(entry instanceof org.neo4j.graphdb.Node){

					// ********* LOGGING *********
					System.out.println("IT IS A NODE");
					// ********* LOGGING *********

					Node currentNode = (Node) entry; 
					// iterate over all the properties for the current node object.
					for(String property : currentNode.getPropertyKeys()){				
						//add the properties. Note the value is returned as string.
						resultMapList.get(index).
						put(property, currentNode.getProperty(property).toString());				

					}
				}

				else if(entry instanceof org.neo4j.graphdb.Relationship){

					// ********* LOGGING *********
					System.out.println("IT IS A RELATIONSHIP");
					// ********* LOGGING *********

					Relationship currentRelation = (Relationship) entry; 
					// iterate over all the properties for the current relationship object.
					for(String property : currentRelation.getPropertyKeys()){				
						//add the properties. Note the value is returned as string.
						resultMapList.get(index).
						put(property, currentRelation.getProperty(property).toString());				

					}
				}

				else{
					System.out.println("DATA : " + entry.toString());
					resultMapList.get(index).put(col,entry.toString());
				}
			}
		}

		// check if we have some result entries.
		// If so, update the query result status.
		if(resultMapList.size()>1)			
			resultMapList.get(0).put("Status", "Success");

		// return the query result.
		return resultMapList;

	} 



}
