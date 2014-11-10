package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.id;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.layout;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.menu;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationManager.services.NotificationExecutor;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NotificationTestActivity extends Activity {

	
	private List<Map<String,String>> NotificationMapList;
	private String RequestID;
	private String RequestType;
	private String Username;
	EditText Message;
	EditText Recipient;
	AsyncTask<String, List<Map<String, String>>, List<Map<String, String>>> NotificationExecutorTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_test_ativity);		
		NotificationMapList = new ArrayList<Map<String, String>>();
		RequestID = "Notification";
		RequestType = "Meal";
		Message = (EditText) findViewById(R.id.editMessage);
		Recipient = (EditText) findViewById(R.id.editRecipient);
		Username = getIntent().getStringExtra("Username");
		NotificationExecutorTask= new NotificationExecutor(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Username);
		
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
	
	public void UpdateNotificationList(Map<String,String> notification){
		System.out.println("updating notifications");
		NotificationMapList.add(notification);
		RefreshNotifications();
	}
	
	private void RefreshNotifications(){
		for(Map<String, String> notification : NotificationMapList){			
			Toast.makeText(getApplicationContext(), notification.get("Message"),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public void OnPostButtonClick(View view) {
		
		String message = Message.getText().toString();
		String  recipient = Recipient.getText().toString();
		List<NameValuePair> requestList = new ArrayList<NameValuePair>();
		
		requestList.add(new BasicNameValuePair("RequestID", RequestID));
		requestList.add(new BasicNameValuePair("RequestType", RequestType));
		requestList.add(new BasicNameValuePair("Username", Username));
		requestList.add(new BasicNameValuePair("Message", message));
		requestList.add(new BasicNameValuePair("Recipient", recipient));
		
		NotificationExecutorTask.cancel(true);
		List<Map<String, String>> resultMapList = 
				RequestHandlerHelper.GetRequestHandlerInstance().HandleRequest(requestList) ;
		
		
	}
	
	
	
}
