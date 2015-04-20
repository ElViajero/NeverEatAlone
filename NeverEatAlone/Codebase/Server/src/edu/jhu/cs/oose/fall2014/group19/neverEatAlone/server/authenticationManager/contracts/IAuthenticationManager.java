package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.authenticationManager.contracts;

import javax.ejb.Local;

@Local
public interface IAuthenticationManager {

	public boolean isUserAuthenticated(String userPassString);

}
