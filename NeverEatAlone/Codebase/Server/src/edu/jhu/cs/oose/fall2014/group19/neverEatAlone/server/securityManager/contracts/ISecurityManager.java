package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts;

import javax.ejb.Local;

@Local
public interface ISecurityManager {
	public String getEncryptedString(String unencryptedString);

}
