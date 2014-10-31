package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface IRequestDispatcher {
	public List<Map<String,String>> DispatchRequest(Map<String,String[]> request);	
}
