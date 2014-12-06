package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;

/**
 * 
 * This adapter get the contact notification
 * 
 */
public class ContactsNotificationAdapter extends
		ArrayAdapter<ContactProperties> {

	// TODO
	// We need to change the contact to contact notification.
	
	private List<ContactProperties> contactInfoList;
	private Activity context;

	public ContactsNotificationAdapter(Activity activity,
			List<ContactProperties> contactInfoList) {
		super(activity, R.layout.row_contact_notification_item_layout,
				contactInfoList);
		this.context = activity;
		// TODO
		// Need revise here
		this.contactInfoList = contactInfoList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			rowView = inflator.inflate(
					R.layout.row_contact_notification_item_layout, parent,
					false);

			// TODO
			// Need revise here
			
			TextView name = (TextView) rowView.findViewById(R.id.contacts_name);

			rowView.setTag(name);

		} else {
			rowView = convertView;

		}
		TextView name = (TextView) rowView.getTag();
		
		// TODO
		// Need revise here
		
		name.setText(contactInfoList.get(position).getContactusername()
				+ " wants to add you as a friend!");
		// holder.checkbox.setChecked(contactInfoList.get(position).isSelected());
		return rowView;
	}

}
