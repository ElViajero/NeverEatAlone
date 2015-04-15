package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.BitMapHelper;

/**
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * 
 *         This adapter adds a listener on the Checkbox view. If the checkbox is
 *         selected the underlying data of the model is changed. Checkbox gets
 *         the corresponding model element assigned via the getTag() method.
 */
public class ContactsInformationAdapter extends ArrayAdapter<ContactProperties> {

	private List<ContactProperties> contactInfoList;
	private Activity context;
	private boolean showCheckboxes;

	private List<ContactProperties> unCheckableContactsList;

	public ContactsInformationAdapter(Activity activity,
			List<ContactProperties> contactInfoList) {
		super(activity, R.layout.row_contact_item_layout, contactInfoList);
		this.context = activity;
		this.contactInfoList = contactInfoList;
		unCheckableContactsList = new ArrayList<ContactProperties>();
	}

	static class ViewHolder {
		protected TextView name;
		protected CheckBox checkbox;
		protected ImageView avatar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row_contact_item_layout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view.findViewById(R.id.contacts_name);
			viewHolder.checkbox = (CheckBox) view
					.findViewById(R.id.contacts_check);
			if (!showCheckboxes)
				viewHolder.checkbox.setVisibility(android.view.View.GONE);
			if (unCheckableContactsList.contains(contactInfoList.get(position)))
				viewHolder.checkbox.setClickable(false);

			viewHolder.avatar = (ImageView) view
					.findViewById(R.id.contacts_avatar);
			ContactProperties contact = (ContactProperties) viewHolder.checkbox
					.getTag();
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							ContactProperties contact = (ContactProperties) viewHolder.checkbox
									.getTag();
							contact.setChecked(buttonView.isChecked());
						}
					});

			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(contactInfoList.get(position));
		} else {
			// System.out.println("contactInfoList" + contactInfoList);
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(contactInfoList
					.get(position));
		}

		ContactProperties contact = contactInfoList.get(position);
		String avatarString = contact.getContactAvatarString();
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.name.setText(contactInfoList.get(position).getContactusername());
		holder.checkbox.setChecked(contactInfoList.get(position).isChecked());

		if (avatarString != null)
			holder.avatar.setImageBitmap(BitMapHelper
					.StringToBitMap(avatarString));
		return view;
	}

	public boolean isShowCheckboxes() {
		return showCheckboxes;
	}

	public void setShowCheckboxes(boolean showCheckboxes) {
		this.showCheckboxes = showCheckboxes;
	}

	public List<ContactProperties> getUnCheckableContactsList() {
		return unCheckableContactsList;
	}

	public void setUnCheckableContactsList(
			List<ContactProperties> unCheckableContactsList) {
		this.unCheckableContactsList = unCheckableContactsList;
	}

	public void addUncheckableContact(ContactProperties contact) {
		unCheckableContactsList.add(contact);
	}

}
