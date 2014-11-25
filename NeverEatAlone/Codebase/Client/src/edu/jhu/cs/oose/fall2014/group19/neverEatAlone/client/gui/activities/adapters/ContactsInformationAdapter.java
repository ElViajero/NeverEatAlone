package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.ContactsModel;

/**
 * 
 * @author Hai Tang
 *This adapter adds a listener on the Checkbox view. 
 *If the checkbox is selected the underlying data of the model is changed. 
 *Checkbox gets the corresponding model element assigned via the getTag() method.
 */
public class ContactsInformationAdapter extends ArrayAdapter<Map<String, String>> {

  private List<Map<String, String>> contactInfoList;
  private Activity context;

  public ContactsInformationAdapter(Activity activity, List<Map<String, String>> contactInfoList) {
    super(activity, R.layout.row_contact_item_layout, contactInfoList);
    this.context = activity;
    this.contactInfoList = contactInfoList;
  }

  static class ViewHolder {
    protected TextView name;
    protected CheckBox checkbox;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = null;
    if (convertView == null) {
      LayoutInflater inflator = context.getLayoutInflater();
      view = inflator.inflate(R.layout.row_contact_item_layout, null);
      final ViewHolder viewHolder = new ViewHolder();
      viewHolder.name = (TextView) view.findViewById(R.id.contacts_name);
      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.contacts_check);
      viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	List<Map<String, String>> contactelement = (List<Map<String, String>>) viewHolder.checkbox.getTag();
//              contactelement.setSelected(buttonView.isChecked());
            }
          });
      
      view.setTag(viewHolder);
      viewHolder.checkbox.setTag(contactInfoList.get(position));
    } else {
      view = convertView;
      ((ViewHolder) view.getTag()).checkbox.setTag(contactInfoList.get(position));
    }
    ViewHolder holder = (ViewHolder) view.getTag();
//    holder.name.setText(contactInfoList.get(position).get("Name"));
//    holder.checkbox.setChecked(contactInfoList.get(position).isSelected());
    return view;
  }
} 
