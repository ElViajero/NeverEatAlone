package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MealNotificationAdapter;

/**
 * 
 * @author Hai Tang
 *
 */
public class InvitesActivity extends ListActivity {
	private ArrayAdapter MealNotificationArrayAdapter;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);

	}

	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invites);

		MealNotificationArrayAdapter = new MealNotificationAdapter(this,
				new String[10]);

		setListAdapter(MealNotificationArrayAdapter);
	
		/*
		 * The following code use StableArrayAdapter to show a list of view, disappears after clicking
		 * 
		 * final ListView listview = (ListView)
		 * findViewById(R.id.listView_invitations); String[] values = new
		 * String[] { "Invitation 1", "Invitation2", "Invitation3" };
		 * 
		 * final ArrayList<String> list = new ArrayList<String>(); for (int i =
		 * 0; i < values.length; ++i) { list.add(values[i]); } final
		 * StableArrayAdapter adapter = new StableArrayAdapter(this,
		 * android.R.layout.simple_list_item_1, list);
		 * listview.setAdapter(adapter);
		 * 
		 * listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		 * {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, final View
		 * view, int position, long id) { final String item = (String)
		 * parent.getItemAtPosition(position);
		 * view.animate().setDuration(2000).alpha(0) .withEndAction(new
		 * Runnable() {
		 * 
		 * @Override public void run() { list.remove(item);
		 * adapter.notifyDataSetChanged(); view.setAlpha(1); } }); }
		 * 
		 * });
		 */

	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent  = new  Intent(this, MealDetailActivity.class);
    	startActivity(intent);
    }

	public void OnCreateButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(InvitesActivity.this,
				CreateMealInformationActivity.class);
		InvitesActivity.this.startActivity(intent);
	}
}
