package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.managementRequestHandler.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
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
	 * This method executes http requests that typically communicate with
	 * external web services. It creates a map that it returns to the calling
	 * method.
	 * 
	 * @return
	 */
	public Map<String, Object> executeRequest(String requestURLString) {

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
			System.out.println("EXCPETIon IS :: " + e.getMessage());
			System.out.flush();
			return null;
			// throw new NullPointerException();
		}

	}

}