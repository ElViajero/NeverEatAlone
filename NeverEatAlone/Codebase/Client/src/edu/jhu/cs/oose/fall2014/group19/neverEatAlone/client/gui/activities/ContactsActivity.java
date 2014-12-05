package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * This class handles controller operations for the contacts tab
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 */
public class ContactsActivity extends ListActivity {
	private ArrayAdapter<ContactProperties> contactsInformationAdapter;

	String requestType;
	String requestID;

	List<ContactProperties> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @param savedInstanceState
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		fetchContacts();
		contactsInformationAdapter = new ContactsInformationAdapter(this,
				contactList);
		setListAdapter(contactsInformationAdapter);
	}

	/**
	 * This method fetches contacts from the server if
	 * the cache is empty.
	 * @author tejasvamsingh
	 * @return
	 */
	private void fetchContacts() {

		requestID = "Contact";
		requestType = "GetAll";
		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("username",
				AccountProperties.getUserAccountInstance().getusername());
		contactList = new ArrayList<ContactProperties>();
		try{

			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,requestMap,requestID,requestType) ;		


			for(Map<String,String> result : resultMapList){
				if(result.isEmpty())
					continue;
				contactList.add(new ContactProperties(result));
			}

		}catch(RequestAbortedException e){
			System.out.println(e.getMessage());
		}

	}


	/**
	 * Method for add friends button click
	 * 
	 * @author: Hai Tang
	 */
	public void onAddFriendsButtonClick(View view) {
		Intent intent = new Intent(ContactsActivity.this,
				AddFriendsActivity.class);
		ContactsActivity.this.startActivity(intent);
	}

	
	
	
	/**
	 * Method for contact notification button click
	 * 
	 * 
	 */
	public void onContactNotificationButtonClick(View view) {
		Intent intent = new Intent(ContactsActivity.this,
				DisplayContactNotificationActivity.class);
		ContactsActivity.this.startActivity(intent);
	}
	
	
	
}
