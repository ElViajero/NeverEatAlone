package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.ContactsModel;

/**
 * This class handles controller operations for the contacts tab
 * 
 * @author Hai Tang
 */
public class ContactsActivity extends ListActivity {
	private ArrayAdapter<ContactsModel> contactsInformationAdapter;
	List<ContactsModel> contactsInfoList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * 
	 * @author: Hai Tang
	 * @param savedInstanceState
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		contactsInfoList = getModel();
		contactsInformationAdapter = new ContactsInformationAdapter(this,
				contactsInfoList);
		setListAdapter(contactsInformationAdapter);
	}

	/**
	 * This method creates the list data model used to show in the contact page
	 * 
	 * @author: Hai Tang
	 */
	private List<ContactsModel> getModel() {
		List<ContactsModel> contactsInfoList = new ArrayList<ContactsModel>();
		contactsInfoList.add(get("Tejas"));
		contactsInfoList.add(get("Runze"));
		contactsInfoList.add(get("Xiaozhou"));
		contactsInfoList.add(get("Yueling"));
		contactsInfoList.add(get("Hai"));
		// Initially select one of the items
		contactsInfoList.get(1).setSelected(true);
		return contactsInfoList;
	}

	/**
	 * This method is used to get dummy data string
	 * 
	 * @author: Hai Tang
	 */
	private ContactsModel get(String s) {
		return new ContactsModel(s);
	}

	/**
	 * Method for add friends button click
	 * 
	 * @author: Hai Tang
	 */
	public void OnAddFriendsButtonClick(View view) {
		Intent intent = new Intent(ContactsActivity.this,
				AddFriendsActivity.class);
		ContactsActivity.this.startActivity(intent);
	}

}
