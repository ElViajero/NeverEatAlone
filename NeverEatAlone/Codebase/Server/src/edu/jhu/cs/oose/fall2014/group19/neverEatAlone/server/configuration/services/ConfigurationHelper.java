package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.contracts.IConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.properties.ConfigurationProperties;


/**
 * Helper class to facilitate easy retrieval of configuration
 * properties.
 * 
 * @author tejasvamsingh
 *
 */


@Stateless
public class ConfigurationHelper implements IConfigurationHelper {

	static ConfigurationProperties ConfigurationInstance;


	/**
	 * 
	 * Method that returns a configuration properties object.
	 * One for the entire application.
	 * 
	 * @author tejasvamsingh
	 * @return
	 * @throws FileNotFoundException
	 * @throws URISyntaxException
	 */
	@Override
	public ConfigurationProperties GetConfigurationInstance(){

		if(ConfigurationInstance==null){
			Gson gson = new Gson();
			String str = null;
			try {
				InputStream url = ConfigurationHelper.class.getResourceAsStream("Config.json")	;			
				str = IOUtils.toString(url);
				System.out.println("Str + "+str);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			try {
				ConfigurationInstance = gson.fromJson(str, ConfigurationProperties.class);
				System.out.println("the ip is " +ConfigurationInstance.GetIPAddress());				
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		return ConfigurationInstance;

	}

}
