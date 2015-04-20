package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IActivityProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.MealNotificationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.FragmentView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;

/**
 * 
 * @author Hai Tang
 * @author tejasvamsingh
 *
 */
public class AcceptedInvitesActivity extends ListFragment {

	private TextView acceptedInvitesTitleObject;
	private TextView acceptedInvitesSwipeTitleObject;
	private Context context;
	private Activity activity;
	private FragmentView invitesView;
	private String requestID;
	private String requestType;

	Button createInviteButton;

	private ArrayAdapter<IActivityProperties> acceptedInvitesAdapter;
	private List<IActivityProperties> acceptedInvitesList;

	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_accepted_invites,
				container, false);

		initView(savedInstanceState);
		initInvitesView();
		acceptedInvitesTitleObject = (TextView) invitesView
				.getView("accepted_invites_title");
		acceptedInvitesSwipeTitleObject = (TextView) invitesView
				.getView("accepted_invites_swipe_title");

		setTitleStyle();
		setCreateInviteButtonHandler();

		return rootView;

	}

	/**
	 * This method sets the onclick handler for the create invites button.
	 * 
	 * @author tejasvamsingh
	 */
	private void setCreateInviteButtonHandler() {

		View.OnClickListener createInviteButtonClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						CreateMealInformationActivity.class);
				getActivity().startActivity(intent);
			}
		};

		createInviteButton.setOnClickListener(createInviteButtonClickListener);
	}

	/**
	 * Method used to initialize the InvitesView
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 */
	private void initInvitesView() {
		context = rootView.getContext();
		invitesView = new FragmentView(context, rootView);
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

		acceptedInvitesList = new ArrayList<IActivityProperties>();

		acceptedInvitesAdapter = new MealNotificationAdapter(getActivity(),
				acceptedInvitesList);
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

		acceptedInvitesSwipeTitleObject.setText("<<");
		acceptedInvitesSwipeTitleObject.setTextColor(Color.GRAY);
		acceptedInvitesSwipeTitleObject.setGravity(android.view.Gravity.LEFT);
		acceptedInvitesTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(acceptedInvitesTitleObject);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initInvitesView();
		View mainLayout = invitesView.getView("layout_accepted_invites");
		View headerLayout = invitesView.getView("header_accepted_invites");
		View buttonBar = invitesView.getView("buttons_accepted_invites");

		// View backAcceptedInviteButton = invitesView
		// .getView("accepted_invites_button_back");

		createInviteButton = (Button) invitesView
				.getView("accepted_invites_button_create");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);
		ThemeManager.applyButtonColor(createInviteButton);

	}

	public void onBackButtonClick(View view) {
		// Intent intent = new Intent(RegisterActivity.this,
		// MainActivity.class);
		Intent intent = new Intent(getActivity(), TabHostActivity.class);
		AcceptedInvitesActivity.this.startActivity(intent);
	}

	/**
	 * This method goes to the MealDetailActivity when clicking. It also passes
	 * the mealProperties to the MealDetailActivity.
	 * 
	 * @author tejasvamsingh
	 * @param position
	 * 
	 * 
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		DataCacheHelper.setAccepted(true);
		DataCacheHelper.setIActivityPropertiesObject(acceptedInvitesAdapter
				.getItem(position));
		Intent intent = new Intent(getActivity(), MealDetailActivity.class);
		startActivity(intent);

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

		requestID = "Meal";
		requestType = "fetchAccepted";

		try {
			acceptedInvitesList.clear();
		} catch (NullPointerException e) {
			return;
		}

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(getActivity(),
							AccountProperties.getUserAccountInstance().toMap(),
							requestID, requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;
				System.out.println("RESULT IS :" + result);
				acceptedInvitesList.add(new NotificationProperties(result));
			}

			System.out.println("accepted invite list : " + acceptedInvitesList);
			acceptedInvitesAdapter.notifyDataSetChanged();

		} catch (RequestAbortedException e) {
			return;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		fetchAcceptedInvites();
	}

}
