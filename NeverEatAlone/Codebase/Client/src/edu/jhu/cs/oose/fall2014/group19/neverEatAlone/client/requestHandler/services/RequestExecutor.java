package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.Configuration;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.configuration.ConfigurationHelper;
/**
 * This class executes HttpRequest asynchronously.
 * @author tejasvamsingh
 *
 */
public class RequestExecutor extends AsyncTask<List<NameValuePair>, Void, List<Map<String,String>>>  {

	static HttpClient HttpClientInstance;
	static String PostRequestURLString; 
	static Gson GsonObject;


	/**
	 * This method initializes the http 
	 * instance as well as related objects
	 *  the first time an http request is sent.
	 *  Typically called during user log in.
	 *  @author tejasvamsingh
	 */

	private static void InitHttpClienInstance(){
		if(HttpClientInstance==null){						 


			// set timeout parameters
			HttpParams param = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(param, 1000);
			HttpConnectionParams.setSoTimeout(param, 1000);
			// create an HttpClientInstance
			HttpClientInstance=new DefaultHttpClient(param);


			GsonObject = new Gson();
			try {
				Configuration configurationInstance = 
						ConfigurationHelper.GetConfigurationInstance();
				PostRequestURLString =
						configurationInstance.GetProtocol()+
						configurationInstance.GetIPAddress()+":"+
						configurationInstance.GetServerPort()+
						configurationInstance.GetServerURL();


				System.out.println("string is :"+PostRequestURLString);
				System.out.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Cathcing exception in GetHttpClientInstance");
				e.printStackTrace();
			}
			System.out.println("pos string is :"+ PostRequestURLString);
		}		
	}




	/**
	 * This method is responsible for sending an http request and recieving a 
	 * response asynchronously.
	 * @author tejasvamsingh
	 */
	@Override
	protected List<Map<String, String>> doInBackground(
			List<NameValuePair>... params) {

		List<NameValuePair> requestList = params[0];

		System.out.println("THE REQUEST LIST ::: "+requestList);
		System.out.flush();

		List<Map<String,String>> returnMap = null;

		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();

		InitHttpClienInstance();

		// Set up post request.
		System.out.println("STRING IS : "+PostRequestURLString);
		System.out.println("CLIENT IS : "+HttpClientInstance);
		System.out.flush();
		HttpPost httpPost = new HttpPost(PostRequestURLString);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(requestList));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("YOUR ENCODE DID NOT HAPPEN");
			System.out.flush();
		}


		HttpResponse response;
		//process the response.
		try {			

			// Execute the request.
			response = HttpClientInstance.execute(httpPost);	
			HttpEntity entity = response.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity.getContent()));

			// get JSON string.
			String responseString=in.readLine();				            	            
			in.close();

			// De-serialize JSON string.
			returnMap = GsonObject.fromJson(responseString, stringStringMap);
			entity.consumeContent();			
		}catch(Exception e){
			System.out.println("EXCPETION IS :: "+ e.getMessage());
			System.out.flush();
			return null;
			//throw new NullPointerException();
		} 

		System.out.println(returnMap);

		return returnMap;		

	}

	/**
	 * This method id called after the http response is obtained.
	 * @author tejasvamsingh
	 */
	@Override
	protected void onPostExecute(List<Map<String,String>> resultMapList){
		System.out.println("reaching onPostExecute in RequestExecutor.");
		if(resultMapList==null){
			System.out.println("The result is NULL");
		}

	}

	/**
	 * This method is responsible for freeing up http client related resources.
	 * @author tejasvamsingh
	 */
	public static void cleanUp(){
		HttpClientInstance=null;
	}


}
