package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.ContactsModel;

/**
 * This activity is used to handle controller operations for select friends
 * page.
 * 
 * @author Hai Tang
 *
 */
public class SelectFriendsActivity extends ListActivity {

	private ArrayAdapter<ContactsModel> selectFriendsAdapter;
	List<ContactsModel> friendsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * Method used to initialize the view.
	 * 
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_friends);
		/*
		friendsList = getModel();
		selectFriendsAdapter = new ContactsInformationAdapter(this,
				selectFriendsList);
		setListAdapter(selectFriendsAdapter);
		 */
	}

	/**
	 * Method used to update view after clicking the broadcast button
	 * @author: Hai Tang
	 */
	private void updateView(List<ContactsModel> selectFriendsList){
		//selectFriendsList = this.selectFriendsList;
		setContentView(R.layout.activity_select_friends);
		/*
		selectFriendsAdapter = new ContactsInformationAdapter(this,
				selectFriendsList);

		setListAdapter(selectFriendsAdapter);
		 */
	}

	/**
	 * This method creates the list data model used to show in the select
	 * friends page
	 * 
	 * @author: Hai Tang
	 */
	private List<ContactsModel> getModel() {
		final List<ContactsModel> selectFriendsList = new ArrayList<ContactsModel>();
		selectFriendsList.add(get("Tejas"));
		selectFriendsList.add(get("Runze"));
		selectFriendsList.add(get("Xiaozhou"));
		selectFriendsList.add(get("Yueling"));
		selectFriendsList.add(get("Hai"));

		return selectFriendsList;
	}

	/**
	 * This method is used to get dummy data string
	 * 
	 * @author: Hai Tang
	 */
	private ContactsModel get(String s) {
		return new ContactsModel(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_friends, menu);
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

	/**
	 * Method for back button click
	 * 
	 * @author: Hai Tang
	 */

	public void OnBackButtonClick(View view) {
		Intent intent = new Intent(SelectFriendsActivity.this,
				CreateMealInformationActivity.class);
		SelectFriendsActivity.this.startActivity(intent);
	}

	/**
	 * Method for broadcast button click
	 * 
	 * @author: Hai Tang
	 */

	/*
	public void OnBroadcastButtonClick(View view) {
		for (int i = 0; i < selectFriendsList.size(); i++) {
			selectFriendsList.get(i).setSelected(true);
		}
		updateView(selectFriendsList);
	}*/

	/**
	 * Method for next button click
	 * 
	 * @author: Hai Tang
	 */
	public void OnNextButtonClick(View view) {
		Intent intent = new Intent(SelectFriendsActivity.this,
				PostInformationActivity.class);
		SelectFriendsActivity.this.startActivity(intent);
	}
}
