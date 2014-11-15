package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.contracts;

import javax.ejb.Local;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.properties.ConfigurationProperties;



@Local
public interface IConfigurationHelper {
	public ConfigurationProperties GetConfigurationInstance();
}
