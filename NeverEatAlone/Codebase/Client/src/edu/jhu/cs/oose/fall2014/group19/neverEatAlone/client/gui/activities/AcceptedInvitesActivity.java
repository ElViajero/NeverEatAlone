package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.InvitesView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
/**
 * 
 * @author Hai Tang
 * @author tejasvamsingh
 *
 */
public class AcceptedInvitesActivity extends ListActivity {

	private TextView acceptedInvitesTitleObject;
	private Context context;
	private Activity activity;
	private InvitesView invitesView;
	private String requestID;
	private	String requestType;

	private ArrayAdapter<IActivityProperties> acceptedInvitesAdapter;
	private List<IActivityProperties> acceptedInvitesList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accepted_invites);

		initView(savedInstanceState);
		initInvitesView();
		acceptedInvitesTitleObject = (TextView) invitesView.getView("accepted_invites_title");
		setTitleStyle();
	}

	/**
	 * Method used to initialize the InvitesView
	 * @author: Hai Tang
	 */
	private void initInvitesView() {
		context = this;
		activity = this;
		invitesView = new InvitesView(context, activity);
	}

	/**
	 * This method updates the GUI.
	 * 
	 * @author tejasvamsingh
	 * @author Hai Tang
	 * @param savedInstanceState
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accepted_invites);

		acceptedInvitesList= new ArrayList<IActivityProperties>();

		acceptedInvitesAdapter=
				new MealNotificationAdapter(this, acceptedInvitesList);
		setListAdapter(acceptedInvitesAdapter);
		fetchAcceptedInvites();

		applyTheme();

	}



	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {

		ThemeManager.setHeaderFont(acceptedInvitesTitleObject);
	}

	/**
	 * This method applies the GUI's color theme.

	 * @author Hai Tang
	 */
	private void applyTheme() {
		initInvitesView();
		View mainLayout = invitesView.getView("layout_accepted_invites");
		View headerLayout = invitesView.getView("header_accepted_invites");
		View buttonBar = invitesView.getView("buttons_accepted_invites");

		View backAcceptedInviteButton = invitesView.getView("accepted_invites_button_back");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);
		ThemeManager.applyButtonColor(backAcceptedInviteButton);


	}

	public void onBackButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(AcceptedInvitesActivity.this,
				TabHostActivity.class);
		AcceptedInvitesActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accepted_invites, menu);
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
	 * @author tejasvamsingh
	 */
	private void fetchAcceptedInvites() {

		requestID="Meal";
		requestType="fetchAccepted";

		acceptedInvitesList.clear();

		try{

			List<Map<String, String>> resultMapList =
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,
							AccountProperties.getUserAccountInstance().toMap(),
							requestID,requestType);

			for(Map<String, String> result : resultMapList){

				if(result.isEmpty())
					continue;
				System.out.println("RESULT IS :"+ result);				
				acceptedInvitesList.add(new NotificationProperties(result));
			}

			System.out.println("accepted invite list : "+ acceptedInvitesList);
			acceptedInvitesAdapter.notifyDataSetChanged();

		}catch(RequestAbortedException e){
			return;
		}

	}
}
