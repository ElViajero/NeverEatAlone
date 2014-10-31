package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface ILoginManager {
	public List<Map<String,String>> CheckCredentials(Map<String,String[]> request);
}
