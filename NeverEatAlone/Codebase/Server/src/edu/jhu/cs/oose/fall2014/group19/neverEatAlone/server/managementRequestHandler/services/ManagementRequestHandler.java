package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

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
@Stateless
public class ManagementRequestHandler implements IManagementRequestHandler {

	@Inject
	@Any
	Instance<Object> myBeans;

	/**
	 * This class obtains bean reference from container using the passed string.
	 * @param className
	 * @return
	 * @throws Exception
	 */
	private Object getMyBeanFromClassName(String className) throws Exception{    
		Class<?> clazz = Class.forName(className);
		return myBeans.select(clazz).get();  
	}


	/**
	 * 
	 * This method resolves which ManagementRequestHandler class 
	 * will handle the request.
	 * It then passes the request to the specified ManagementRequestHandler class.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,String>> handleManagementRequest(Map<String, String[]> request) {

		try {

			// ********* LOGGING ********* 
			System.out.println("class name : "+request.get("requestID")[0]+"ManagementRequestHandler");
			// ********* LOGGING ********* 

			// obtain class reference
			Object c = getMyBeanFromClassName("edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services."
					+request.get("requestID")[0]+"ManagementRequestHandler");


			// Get method corresponding to requestType.

			Method m = ((Class<? extends Object>) c.getClass()).getDeclaredMethod(request.get("requestType")[0]+
					request.get("requestID")[0]+
					"Request" , Map.class);

			// Invoke the method.
			return (List<Map<String, String>>) m.invoke(c,request);



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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if we reach here, then we were unsuccessful
		// at invoking the method.
		return null;

	}

}
