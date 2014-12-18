package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.reflectionManager.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
/**
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface IReflectionManager {

	public List<Map<String,String>> invokeMethod(Object classInstance,
			String methodName,Object methodParameters);

	public Object getClass(String className);

}
