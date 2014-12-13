package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.services;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts.IReflectionManager;

/**
 * This class is a helper class
 * that contains functionality for reflection 
 * and querying the container.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class ReflectionManager implements IReflectionManager {

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




	@Override
	public List<Map<String,String>>  invokeMethod(
			Object classInst,String methodName, Object methodParameters){

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



	@Override
	public Object getClass(String className){

		System.out.println("Class is "+ className);
		try {
			return getMyBeanFromClassName(className);
		} catch (Exception e) {
			System.out.println("Exception in ReflectionManager getClass()");			
			e.printStackTrace();
			return null;
		}

	}





}
