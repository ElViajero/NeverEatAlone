package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration;

/**
 * Class that holds configuration properties.
 * 
 * @author tejasvamsingh
 *
 */
public class Configuration {

	private String IPAddress;
	private String Protocol;
	private String ServerPort;
	private String ServerURL;

	public String GetIPAddress(){
		return IPAddress;
	}

	public void SetIPAdress(String ipAddress){
		IPAddress=ipAddress;
	}

	public String GetProtocol() {
		return Protocol;
	}

	public void SetProtocol(String protocol) {
		Protocol = protocol;
	}

	public String GetServerPort() {
		return ServerPort;
	}

	public void SetServerPort(String serverPort) {
		ServerPort = serverPort;
	}

	public String GetServerURL() {
		return ServerURL;
	}

	public void SetServerURL(String serverURL) {
		ServerURL = serverURL;
	}





}
