package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.TabActivity;//Cannot extends Activity
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class TabHostActivity extends TabActivity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_host);

            // create the TabHost that will contain the Tabs
            TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

            TabSpec tab_invites = tabHost.newTabSpec("Invites Tab");
            TabSpec tab_contacts = tabHost.newTabSpec("Contacts Tab");
            TabSpec tab_profile = tabHost.newTabSpec("Profile Tab");

           // Set the Tab name and Activity
           // that will be opened when particular Tab will be selected
            tab_invites.setIndicator("Invites");
            tab_invites.setContent(new Intent(this,InvitesActivity.class));
            
            tab_contacts.setIndicator("Contacts");
            tab_contacts.setContent(new Intent(this,ContactsActivity.class));

            tab_profile.setIndicator("Profile");
            tab_profile.setContent(new Intent(this,ProfileActivity.class));
            
            /** Add the tabs  to the TabHost to display. */
            tabHost.addTab(tab_invites);
            tabHost.addTab(tab_contacts);
            tabHost.addTab(tab_profile);

    }
}
