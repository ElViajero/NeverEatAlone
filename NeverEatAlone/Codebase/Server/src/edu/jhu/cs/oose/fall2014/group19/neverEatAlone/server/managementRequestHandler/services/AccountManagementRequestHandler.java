package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.services;

import java.util.List;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IAccountManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services.AccountManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.contracts.IManagementRequestHandler;

/**
 * 
 * This class manages all acount related requests.
 * The operations handled by this class include 
 * account creation,modification and deletion.
 * 
 * @author tejasvamsingh
 *
 */

class AccountManagementRequestHandler{

	IAccountManager IAccountManagerObject;
	
	  List<Map<String,String>> CreateAccountRequest(Map<String,String[]> request){		
		
		 System.out.println("reached CreateAccountRequest");
		//redundant. Use for now, but change soon.
		 IAccountManagerObject = new AccountManager();
		 return IAccountManagerObject.CreateAccount(request);
		
	}
	  
	  List<Map<String,String>> IsValidAccountRequest(Map<String,String[]> request){
		  
		  System.out.println("reached IsValidAccountRequest");
		  //redundant. Use for now, but change soon.
		  IAccountManagerObject = new AccountManager();
		  return IAccountManagerObject.IsValidAccount(request);
	  }
	 
	 
	
	
}
	
	
	
	


