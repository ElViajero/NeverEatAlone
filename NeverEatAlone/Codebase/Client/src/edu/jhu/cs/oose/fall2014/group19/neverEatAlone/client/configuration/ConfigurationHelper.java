package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ConfigurationHelper {

	static Configuration ConfigurationInstance;
	
	
	public static Configuration GetConfigurationInstance() throws FileNotFoundException, URISyntaxException{
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
				ConfigurationInstance = gson.fromJson(str, Configuration.class);
				System.out.println("the ip is " +ConfigurationInstance.GetIPAddress());
				System.out.println("the protocol is " +ConfigurationInstance.GetProtocol());
				System.out.println("the port is " +ConfigurationInstance.GetServerPort());
				System.out.println("the URL is " +ConfigurationInstance.GetServerURL());
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return ConfigurationInstance;
			
	}
	
}
