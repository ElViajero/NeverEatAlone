package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;

/**
 * This class is the generic ManagementRequestHandler. 
 * It is called for every request.
 * It forwards the request to the
 * appropriate method(e.g create) of a specific request handler(account).
 * 
 * @author tejasvamsingh
 *
 */
public class ManagementRequestHandler implements IManagementRequestHandler {

	@Override
	public List<Map<String,String>> HandleManagementRequest(Map<String, String[]> request) {
		
		try {
			
			System.out.println("class name : "+request.get("RequestID")[0]+"ManagementRequestHandler");
			
			// Get class corresponding to RequestID.
			Class<?> c = Class.forName(
					"edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services."
					+request.get("RequestID")[0]+"ManagementRequestHandler");			
			
			// Get method corresponding to RequestType.
			Method m = c.getDeclaredMethod(request.get("RequestType")[0]+
							request.get("RequestID")[0]+
							"Request" , Map.class);

			// Invoke the method.
			return (List<Map<String, String>>) m.invoke(c.newInstance(),request);
			
			
			
		// Handle exceptions. For now I've just printed fixes
		// that I had to make in order to get things to work.
			
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("First parameter is the method name. "
					+ "Second is the methodParameter.class");
			e.printStackTrace();
		
		} catch (IllegalArgumentException e) {
			System.out.println("Call classObject.newIstance().");
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			System.out.println("use fully qualified class name "
					+ "(i.e. with package qualification).");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// if we reach here, then we were unsuccessful
		// at invoking the method.
		return null;
		
	}

}
