package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;

/**
 * This class is a helper class
 * that contains functionality to aid
 * the mangement request handler classes.
 * @author tejasvamsingh
 *
 */
@Stateless
public class ManagemenRequestHandlerHelper {

	public static List<Map<String,String>> invokeMethod(
			IManagementRequestHandler classInst,String methodName, Object methodParameters){

		System.out.println("Method is "+methodName);



		try {
			Method methodToInvoke = 
					classInst.getClass().getDeclaredMethod(methodName, Map.class);
			methodToInvoke.setAccessible(true);
			return (List<Map<String, String>>) methodToInvoke.invoke(classInst, methodParameters);

		} catch (NoSuchMethodException | SecurityException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
