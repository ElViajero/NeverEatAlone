package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.properties;

public class ConfigurationProperties {

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

	public String getProtocol() {
		return Protocol;
	}

	public void SetProtocol(String protocol) {
		Protocol = protocol;
	}

	public String getServerPort() {
		return ServerPort;
	}

	public void SetServerPort(String serverPort) {
		ServerPort = serverPort;
	}

	public String getServerURL() {
		return ServerURL;
	}

	public void SetServerURL(String serverURL) {
		ServerURL = serverURL;
	}





}
