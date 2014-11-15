package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestHandler;

public class RequestExecutor extends AsyncTask<List<NameValuePair>, Void, List<Map<String,String>>>  {
	
	static HttpClient HttpClientInstance;
	static String PostRequestURLString = "http://10.188.170.66:8080/NeverEatAloneServer/RequestHandler"; 
	
	private HttpClient GetHttpClientInstance(){
		if(HttpClientInstance==null)
			HttpClientInstance=new DefaultHttpClient();
		return HttpClientInstance;
	}
	
	 
	
	
	@Override
	protected List<Map<String, String>> doInBackground(
			List<NameValuePair>... params) {
	
		List<NameValuePair> requestList = params[0];
		List<Map<String,String>> returnMap = null;
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<List<Map<String, String>>>(){}.getType();
		
		
				
		// Set up post request.
		HttpPost httpPost = new HttpPost(PostRequestURLString);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(requestList));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		HttpResponse response;
		//process the response.
		try {
			// Execute the request.
			response = GetHttpClientInstance().execute(httpPost);	
			HttpEntity entity = response.getEntity();
			//do something useful with the response body
			// and ensure it is fully consumed
			BufferedReader in = 
					new BufferedReader( new InputStreamReader( entity.getContent()));
			
			// get JSON string.
			String responseString=in.readLine();				            	            
			in.close();
			
			// De-serialize JSON string.
			returnMap = gson.fromJson(responseString, stringStringMap);
			entity.consumeContent();			
		}catch(Exception e){
			System.out.println(e.getMessage());
			} 
		
		System.out.println(returnMap);
		
		return returnMap;		
	
	}

	

}
