package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.workflow.tests.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for building various request for DBManagers
 * @author Xiaozhou Zhou
 *
 */
public class TestRequestBuilder {

	
	/**
	 * Build CreateAccount request
	 * @return
	 */
	public static Map<String,String[]> createAccountRequest(String username, String password, String email){
		
		Map<String,String[]> request = new HashMap<String,String[]>(); 
		
		request.put("requestID", new String[]{"Account"});
		request.put("requestType", new String[]{"Create"});
		request.put("username", new String[]{username});
		request.put("password", new String[]{password});
		request.put("email", new String[]{email});
				
		return request; 
	}

	/**
	 * Build UpdateAccount request
	 * @return
	 */
	public static Map<String,String[]> updateAccountRequest(String username, Map<String,String[]> parameters){
		
		Map<String,String[]> request = new HashMap<String,String[]>(parameters); 
				
		request.put("requestID", new String[]{"Account"});
		request.put("requestType", new String[]{"Update"});
		request.put("username", new String[]{username});
				
		return request; 
	}
	
	/**
	 * Build AddContact request
	 * @return
	 */
	public static Map<String,String[]> addContactRequest(String username, String contact){
		
		Map<String,String[]> request = new HashMap<String,String[]>(); 
		
		request.put("requestID", new String[]{"Contact"});
		request.put("requestType", new String[]{"Add"});
		request.put("username", new String[]{username});
		request.put("contactusername", new String[]{contact});
				
		return request; 
	}
	
	
	
}
