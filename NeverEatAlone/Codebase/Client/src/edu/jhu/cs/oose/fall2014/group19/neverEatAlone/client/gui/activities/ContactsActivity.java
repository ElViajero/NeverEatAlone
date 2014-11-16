package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.InteractiveArrayAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.Model;
/**
 * 
 * @author Hai Tang
 *
 */
public class ContactsActivity extends ListActivity{
	 @Override
     public void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);      
         ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
        	        getModel());
        	    setListAdapter(adapter);
//         setContentView(R.layout.activity_contacts);

     }
	 
	  private List<Model> getModel() {
		    List<Model> list = new ArrayList<Model>();
		    list.add(get("Tejas"));
		    list.add(get("Runze"));
		    list.add(get("Xiaozhou"));
		    list.add(get("Yuelin"));
		    list.add(get("Hai Tang"));
		    // Initially select one of the items
		    list.get(1).setSelected(true);
		    return list;
		  }

		  private Model get(String s) {
		    return new Model(s);
		  }
}
