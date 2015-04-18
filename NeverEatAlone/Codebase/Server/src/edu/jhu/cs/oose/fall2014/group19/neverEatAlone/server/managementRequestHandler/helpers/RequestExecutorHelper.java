package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * This class is a helper class that executes http requests in order to
 * communicate with other web services.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class RequestExecutorHelper {

	/**
	 * 
	 * This method executes http post requests that typically communicate with
	 * external web services. It creates a map that it returns to the calling
	 * method.
	 * 
	 * @return
	 */
	public Map<String, Object> executePostRequest(String requestURLString) {

		HttpClient httpClientInstance = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet(requestURLString);
		HttpResponse response;

		Map<String, Object> responseMap = new HashMap<>();

		// process the response.
		try {

			// Execute the request.
			response = httpClientInstance.execute(httpPost);
			System.out.println("reas : " + response);

			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			// get JSon string.
			String jsonString = "";
			String responseString = in.readLine();
			while (responseString != null) {
				System.out.println("response String : : " + responseString);
				jsonString += responseString;
				responseString = in.readLine();
			}

			Type stringObjectMap = new TypeToken<Map<String, Object>>() {
			}.getType();
			Gson gsonObject = new Gson();
			responseMap = gsonObject.fromJson(jsonString, stringObjectMap);

			in.close();
			entity.consumeContent();
			return responseMap;

		} catch (Exception e) {
			System.out.println("EXCPETION IS :: " + e.getMessage());
			System.out.flush();
			return null;
			// throw new NullPointerException();
		}

	}

	/**
	 * This method executes put requests. Typically used to communicate with the
	 * notification server management system.
	 * 
	 * @author tejasvamsingh
	 * 
	 * @param requestParameters
	 * @param requestURLString
	 * @param credentialsMap
	 */
	public void executePutRequest(Map<String, String> requestParameters,
			String requestURLString, Map<String, String> credentialsMap) {

		try {
			URL url = new URL(requestURLString);
			HttpURLConnection httpCon = (HttpURLConnection) url
					.openConnection();

			String userpass = "guest:guest";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(userpass.getBytes());

			httpCon.setRequestProperty("Authorization", basicAuth);
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			httpCon.setRequestProperty("Content-Type", "application/json");

			JsonObject keyArg = new JsonObject();

			for (String key : requestParameters.keySet()) {
				keyArg.addProperty(key, requestParameters.get(key));
			}

			OutputStreamWriter out = new OutputStreamWriter(
					httpCon.getOutputStream());
			out.write(keyArg.toString());
			out.close();
			httpCon.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}