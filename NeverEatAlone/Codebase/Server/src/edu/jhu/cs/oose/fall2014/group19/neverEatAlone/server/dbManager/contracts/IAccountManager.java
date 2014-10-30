package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import java.util.List;
import java.util.Map;

public interface IAccountManager {

	public List<Map<String,String>> CreateAccount(Map<String,String[]> request);
	public List<Map<String,String>> UdateAccount(Map<String,String[]> request);
	public List<Map<String,String>> DeleteAccount(Map<String,String[]> request);
	public List<Map<String,String>> IsValidAccount(Map<String,String[]> request);
	
}
