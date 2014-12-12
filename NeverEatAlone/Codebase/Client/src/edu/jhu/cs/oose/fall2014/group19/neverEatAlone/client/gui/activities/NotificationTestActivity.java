package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class NotificationTestActivity extends Activity {


	private List<Map<String,String>> notificationMapList;
	private String requestID;
	private String requestType;
	private String username;
	EditText messageEditTextObject;
	EditText recipientEditTextObject;
	AsyncTask<String, List<Map<String, String>>, List<Map<String, String>>> NotificationExecutorTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_test_ativity);		
		notificationMapList = new ArrayList<Map<String, String>>();
		requestID = "Notification";
		requestType = "meal";
		messageEditTextObject = (EditText) findViewById(R.id.editMessage);
		recipientEditTextObject = (EditText) findViewById(R.id.editRecipient);
		username = getIntent().getStringExtra("username");
		//NotificationExecutorTask= new NotificationExecutor(this).executeonExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);
		System.out.println("ENTERED on CREATE");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification_test_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void UpdatenotificationList(Map<String,String> notification){
		System.out.println("updating notifications");
		notificationMapList.add(notification);
		RefreshNotifications();
	}

	private void RefreshNotifications(){
		for(Map<String, String> notification : notificationMapList){			
			Toast.makeText(getApplicationContext(), notification.get("Message"),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void onPostButtonClick(View view) {

		String message = messageEditTextObject.getText().toString();
		String  recipient = recipientEditTextObject.getText().toString();
		List<NameValuePair> requestList = new ArrayList<NameValuePair>();

		requestList.add(new BasicNameValuePair("requestID", requestID));
		requestList.add(new BasicNameValuePair("requestType", requestType));
		requestList.add(new BasicNameValuePair("username", username));
		requestList.add(new BasicNameValuePair("Message", message));
		requestList.add(new BasicNameValuePair("Recipient", recipient));

		NotificationExecutorTask.cancel(true);
		/*
		List<Map<String, String>> resultMapList = 
				RequestHandlerHelper.getRequestHandlerInstance().handleRequest(requestList) ;
		 */

	}



}
