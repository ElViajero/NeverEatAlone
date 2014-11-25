package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.InteractiveArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.ContactsModel;
/**
 * 
 * @author Hai Tang
 * This class handles controller operations for the contacts tab
 */
public class ContactsActivity extends ListActivity{
	private ArrayAdapter<ContactsModel> adapter;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);      
		setContentView(R.layout.activity_contacts);
		
		adapter = new InteractiveArrayAdapter(this,
				getModel());
		setListAdapter(adapter);
		         

	}

	private List<ContactsModel> getModel() {
		List<ContactsModel> list = new ArrayList<ContactsModel>();
		list.add(get("Tejas"));
		list.add(get("Runze"));
		list.add(get("Xiaozhou"));
		list.add(get("Yueling"));
		list.add(get("Hai"));
		// Initially select one of the items
		list.get(1).setSelected(true);
		return list;
	}

	private ContactsModel get(String s) {
		return new ContactsModel(s);
	}
}
