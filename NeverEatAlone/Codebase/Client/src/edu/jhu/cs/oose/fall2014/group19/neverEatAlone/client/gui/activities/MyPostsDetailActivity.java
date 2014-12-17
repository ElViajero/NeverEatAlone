package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R.layout;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.MealView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Runze Tang
 * 
 *         MyPostsDetailActivity class is used to set up the view of the My
 *         Posts Details page. And also set up the functions after clicking
 *         different buttons.
 */
public class MyPostsDetailActivity extends Activity {

	private ArrayAdapter<ContactProperties> contactsInformationAdapter;
	private Context context;
	private Activity activity;
	private MealView myPostsView;
	private TextView myPostsDetailTitleObject;
	private String requestID;
	private String requestType;
	private NotificationProperties notificationPropertiesObject;

	List<ContactProperties> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts_detail);

		initMyPostsView();
		myPostsDetailTitleObject = (TextView) myPostsView
				.getView("textView_myPostsDetail_title");

		// TODO need changes here
		contactList = new ArrayList<ContactProperties>();
		contactsInformationAdapter = new ContactsInformationAdapter(this,
				contactList);
		// setListAdapter(contactsInformationAdapter);

		fetchContacts();

		notificationPropertiesObject = DataCacheHelper
				.getNotificationPropertiesObject();

		setTitleStyle();
		applyTheme();

	}

	private void initMyPostsView() {
		context = this;
		activity = this;
		myPostsView = new MealView(context, activity);
	}

	private void setTitleStyle() {

		ThemeManager.setHeaderFont(myPostsDetailTitleObject);

	}

	private void applyTheme() {

		initMyPostsView();
		View mainLayout = myPostsView.getView("main_myPostsDetail");
		View headerLayout = myPostsView.getView("header_myPostsDetail");
		View buttonBar = myPostsView.getView("buttons_myPostsDetail");

		View backButton = myPostsView.getView("button_myPostsDetail_back");
		View closeButton = myPostsView.getView("button_myPostsDetail_close");

		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(backButton);
		ThemeManager.applyButtonColor(closeButton);

	}

	// TODO need changes here
	private void fetchContacts() {

		requestID = "Contact";
		requestType = "getAll";

		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("username", AccountProperties.getUserAccountInstance()
				.getusername());

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							requestMap, requestID, requestType);
			contactList.clear();
			for (Map<String, String> result : resultMapList) {
				if (result.isEmpty())
					continue;
				contactList.add(new ContactProperties(result));
			}

			MessageToasterHelper.toastMessage(this, contactList.get(0)
					.getContactusername());
			contactsInformationAdapter.notifyDataSetChanged();
			NotificationAndPostCacheHelper.setServerFetchRequired("contact",
					false);

		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_posts_detail, menu);
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
	 * Method used for back button click
	 * 
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(this, MyPostsActivity.class);
		startActivity(intent);

	}

	/**
	 * click handler for the close button.
	 * 
	 */
	public void onCloseButtonClick(View view) {

		// TODO

	}
}
