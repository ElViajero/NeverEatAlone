package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.xml.ws.AsyncHandler;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

public interface IRequestHandler{

	public List<Map<String,String>> HandleRequest(List<NameValuePair> requestList);
			
}
