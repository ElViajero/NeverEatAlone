package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;

/**
 * @author tejasvamsingh
 * This adapter get the contact notification
 * 
 */
public class ContactsNotificationAdapter extends
ArrayAdapter<IActivityProperties> {


	private List<IActivityProperties> contactInfoList;
	private Activity context;

	public ContactsNotificationAdapter(Activity activity,
			List<IActivityProperties> contactInfoList) {

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

		Button accept = (Button) rowView.findViewById(R.id.contacts_notification_accept);
		Button reject = (Button) rowView.findViewById(R.id.contacts_notification_reject);
		accept.setVisibility(View.INVISIBLE);
		reject.setVisibility(View.INVISIBLE);

		NotificationProperties notification = (NotificationProperties)
				contactInfoList.get(position);

		ContactProperties contactPropertiesObject =(ContactProperties)
				notification.getNotificationData();


		name.setText(contactPropertiesObject.getContactusername()
				+ " wants to add you as a friend!");

		return rowView;
	}

}
