package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration;

/**
 * Class that holds configuration properties.
 * 
 * @author tejasvamsingh
 *
 */
public class Configuration {

	private String ipAddress;
	private String protocol;
	private String serverPort;
	private String serverURL;

	public String getIPAddress(){
		return ipAddress;
	}

	public void setIPAdress(String ipAddress){
		this.ipAddress=ipAddress;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}





}
