package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class is an abstraction for post related properties.
 * 
 * @author tejasvamsingh
 *
 */
public class PostProperties implements IActivityProperties {

	private String postStatus;
	private static int postIDNumber=0;
	private String poster;
	private List<String> recipientList;
	private String postID;
	private String postType;
	private String postData;

	public PostProperties(List<String> recipientList,String postType,
			String postData){
		poster = 
				AccountProperties.getUserAccountInstance().getusername();
		postID = poster+postIDNumber;
		postIDNumber++;

		this.recipientList = recipientList;
		this.postData=postData;
		this.postType =postType;
		this.postStatus="OPEN";
	}

	@Override
	public Map<String, Object> toMap() {

		Gson gson = GsonHelper.getGsoninstance();
		String json = gson.toJson(this);
		System.out.println("json is : " +json);
		Type stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> requestMap = gson.fromJson(json, stringObjectMap);
		System.out.println("map is : " +requestMap);

		requestMap.remove("postData");

		Map<String,Object> postDataMap = gson.fromJson(postData, stringObjectMap);
		System.out.println("map is : " +postDataMap);

		for (Map.Entry<String, Object> entry : postDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			requestMap.put(key,value);
		}

		System.out.println("FINAL POST MAP" + requestMap);


		if(!requestMap.containsKey("postStatus"))
			MessageToasterHelper.toastMessage("NO DAMN STU+ADNKAN");

		return requestMap;
	}

	@Override
	public void fromMap(Map<String, String> map) {


	}	

	public static void initPostID(String PostIDString){
		postIDNumber=Integer.parseInt(PostIDString.substring(
				AccountProperties.getUserAccountInstance().getusername().length(),
				PostIDString.length()));
		postIDNumber++;

	}

	public static PostProperties  notificationToPost(NotificationProperties notification){

		String pType = notification.getNotificationType(); 
		List<String> pRecipientList = new ArrayList<String>();
		pRecipientList.add(notification.getPoster());
		String pData =
				GsonHelper.getGsoninstance().toJson(notification.getNotificationData().toMap());
		PostProperties post = 
				new PostProperties(pRecipientList, pType, pData);
		post.setPostID(notification.getNotificationID());

		return post;

	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}







}
