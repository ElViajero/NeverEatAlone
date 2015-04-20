package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.BitMapHelper;

/**
 * 
 * 
 * Adapter for contact noifications. Used for Friend Requests.
 * 
 * @author tejasvamsingh
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

		this.contactInfoList = contactInfoList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = null;
		LayoutInflater inflator = context.getLayoutInflater();
		rowView = inflator.inflate(
				R.layout.row_contact_notification_item_layout, parent, false);

		NotificationProperties currentContacNotification = (NotificationProperties) contactInfoList
				.get(position);

		ContactProperties currentContact = (ContactProperties) currentContacNotification
				.getNotificationData();

		ImageView avatarImageView = (ImageView) rowView
				.findViewById(R.id.contacts_avatar);

		TextView username = (TextView) rowView.findViewById(R.id.contacts_name);

		String avatarString = currentContact.getContactAvatarString();

		Button acceptButton = (Button) rowView
				.findViewById(R.id.contacts_notification_accept);
		acceptButton.setVisibility(View.INVISIBLE);
		Button rejectButton = (Button) rowView
				.findViewById(R.id.contacts_notification_reject);
		rejectButton.setVisibility(View.INVISIBLE);

		if (avatarString != null && !avatarString.equals(""))
			avatarImageView.setImageBitmap(BitMapHelper
					.StringToBitMap(avatarString));

		username.setText(currentContact.getContactusername());

		return rowView;
	}
}
