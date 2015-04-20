package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.Configuration;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.ConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class executes HttpRequest asynchronously.
 * 
 * @author tejasvamsingh
 *
 */
public class RequestExecutor extends
		AsyncTask<List<NameValuePair>, Void, List<Map<String, String>>> {

	static HttpClient httpClientInstance;
	static String postRequestURLString;
	static Gson gsonObject;
	static String username;
	static String password;
	static HttpClientContext context;

	/**
	 * This method initializes the http instance as well as related objects the
	 * first time an http request is sent. Typically called during user log in.
	 * 
	 * @author tejasvamsingh
	 */

	private static void InitHttpClienInstance() {
		if (httpClientInstance == null) {

			username = AccountProperties.getUserAccountInstance().getusername();
			password = AccountProperties.getUserAccountInstance().getpassword();

			// set timeout parameters
			// HttpParams param = new BasicHttpParams();
			// HttpConnectionParams.setConnectionTimeout(param, 1000);
			// HttpConnectionParams.setSoTimeout(param, 3000);

			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(1000).setSocketTimeout(3000).build();

			// create an httpClientInstance
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(username, password));

			HttpHost targetHost = new HttpHost("10.0.0.3", 8080, "http");
			AuthCache authCache = new BasicAuthCache();
			authCache.put(targetHost, new BasicScheme());

			// Add AuthCache to the execution context
			context = HttpClientContext.create();
			context.setCredentialsProvider(credentialsProvider);
			context.setAuthCache(authCache);

			httpClientInstance = HttpClientBuilder.create()
					.setDefaultRequestConfig(requestConfig).build();
			// httpClientInstance = HttpClients.custom()
			// .setDefaultCredentialsProvider(credentialsProvider)
			// .setDefaultRequestConfig(requestConfig).build();

			gsonObject = GsonHelper.getGsoninstance();

			try {
				Configuration configurationInstance = ConfigurationHelper
						.getConfigurationInstance();
				postRequestURLString = configurationInstance.getProtocol()
						+ configurationInstance.getIPAddress() + ":"
						+ configurationInstance.getServerPort()
						+ configurationInstance.getServerURL();

				System.out.println("string is :" + postRequestURLString);
				System.out.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out
						.println("Cathcing exception in GethttpClientInstance");
				e.printStackTrace();
			}
			System.out.println("pos string is :" + postRequestURLString);
		}
	}

	/**
	 * This method is responsible for sending an http request and recieving a
	 * response asynchronously.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	protected List<Map<String, String>> doInBackground(
			List<NameValuePair>... params) {

		List<NameValuePair> requestList = params[0];

		System.out.println("THE REQUEST LIST ::: " + requestList);
		System.out.flush();

		List<Map<String, String>> returnMap = null;

		Type stringStringMap = new TypeToken<List<Map<String, String>>>() {
		}.getType();

		InitHttpClienInstance();

		// Set up post request.
		System.out.println("STRING IS : " + postRequestURLString);
		System.out.println("CLIENT IS : " + httpClientInstance);
		System.out.flush();
		HttpPost httpPost = new HttpPost(postRequestURLString);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(requestList));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("YOUR ENCODE DID NOT HAPPEN");
			System.out.flush();
		}

		HttpResponse response;
		// process the response.
		try {

			// Execute the request.
			response = httpClientInstance.execute(httpPost, context);
			HttpEntity entity = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			// get JSon string.
			String responseString = in.readLine();
			in.close();

			// De-serialize JSon string.
			returnMap = gsonObject.fromJson(responseString, stringStringMap);
			entity.consumeContent();
		} catch (Exception e) {
			System.out.println("EXCPETIon IS :: " + e.getMessage());
			System.out.flush();
			return null;
			// throw new NullPointerException();
		}

		System.out.println(returnMap);

		return returnMap;

	}

	/**
	 * This method id called after the http response is obtained.
	 * 
	 * @author tejasvamsingh
	 */
	@Override
	protected void onPostExecute(List<Map<String, String>> resultMapList) {
		System.out.println("reaching onPostExecute in RequestExecutor.");
		if (resultMapList == null) {
			System.out.println("The result is NULL");
		}

	}

	/**
	 * This method is responsible for freeing up http client related resources.
	 * 
	 * @author tejasvamsingh
	 */
	public static void cleanUp() {
		httpClientInstance = null;
	}

}
