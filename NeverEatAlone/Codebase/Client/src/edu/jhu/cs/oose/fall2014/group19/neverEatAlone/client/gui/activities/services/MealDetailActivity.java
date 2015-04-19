package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

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
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.MealView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 * 
 *         MealDetailActivity class is used to set up the view of the Meal
 *         Details page. And also set up the functions after clicking different
 *         buttons.
 */
public class MealDetailActivity extends ListActivity {

	List<ContactProperties> attendingList;
	private ContactsInformationAdapter attendingAdapter;
	private Context context;
	private Activity activity;
	private MealView mealView;
	private TextView mealDetailTitleObject;
	private String requestID;
	private String requestType;
	private NotificationProperties notificationPropertiesObject;

	/**
	 * 
	 * Called when the activity is first created.
	 *
	 * @author tejasvamsingh
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);

		initMealView();
		mealDetailTitleObject = (TextView) mealView
				.getView("textView_mealdetails_title");

		notificationPropertiesObject = (NotificationProperties) DataCacheHelper
				.getIActivityPropertiesObject();

		setTitleStyle();
		populateView();
		applyTheme();

		attendingList = new ArrayList<ContactProperties>();
		attendingAdapter = new ContactsInformationAdapter(this, attendingList);
		attendingAdapter.setShowCheckboxes(false);

		setListAdapter(attendingAdapter);

		fetchAttending();

		if (DataCacheHelper.isAccepted()) {
			Button accept = (Button) mealView
					.getView("button_mealdetails_accept");
			accept.setText("Undo Accept");
		}

	}

	/**
	 * Method used to initialize MealView
	 * 
	 * @author: Hai Tang
	 */
	private void initMealView() {
		context = this;
		activity = this;
		mealView = new MealView(context, activity);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {
		mealDetailTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(mealDetailTitleObject);

	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Yueling Loh
	 * @author tejasvamsingh
	 */
	private void applyTheme() {

		initMealView();
		View mainLayout = mealView.getView("main_mealDetails");
		View headerLayout = mealView.getView("header_mealDetails");
		View buttonBar = mealView.getView("buttons_mealDetails");

		View backButton = mealView.getView("button_mealdetails_back");
		View declineButton = mealView.getView("button_mealdetails_decline");
		View acceptButton = mealView.getView("button_mealdetails_accept");
		// View inviteOthersButton =
		// mealView.getView("button_mealdetails_inviteothers");

		// ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(backButton);
		ThemeManager.applyButtonColor(declineButton);
		ThemeManager.applyButtonColor(acceptButton);
		// ThemeManager.applyButtonColor(inviteOthersButton);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meal_detail, menu);
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
	 * @author: Hai Tang
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}

	/**
	 * click handler for the accept button.
	 * 
	 * @author tejasvamsingh
	 */
	public void onAcceptButtonClick(View view) {

		requestID = "Meal";
		requestType = "accept";

		if (DataCacheHelper.isAccepted())
			requestType = "undoAccept";

		PostProperties postPropertiesObject = PostProperties
				.notificationToPost(notificationPropertiesObject);

		try {

			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postPropertiesObject.toMap(), requestID,
							requestType);

			NotificationAndPostCacheHelper.setServerFetchRequired("meal", true);
			System.out.println("FETCH STATUS :"
					+ NotificationAndPostCacheHelper
							.isServerFetchRequired("meal"));

			notificationPropertiesObject.setAccepted(!DataCacheHelper
					.isAccepted());

			Intent intent = new Intent(this, TabHostActivity.class);
			startActivity(intent);

		} catch (RequestAbortedException e) {
			return;
		}

	}

	/**
	 * Method used for decline button click
	 * 
	 * @author: Hai Tang
	 */
	public void onDeclineButtonClick(View view) {

		requestID = "Meal";
		requestType = "reject";

		PostProperties postPropertiesObject = PostProperties
				.notificationToPost(notificationPropertiesObject);

		try {

			List<Map<String, String>> result = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postPropertiesObject.toMap(), requestID,
							requestType);

			NotificationAndPostCacheHelper.setServerFetchRequired("meal", true);
			System.out.println("FETCH STATUS :"
					+ NotificationAndPostCacheHelper
							.isServerFetchRequired("meal"));

			Intent intent = new Intent(this, TabHostActivity.class);
			startActivity(intent);

		} catch (RequestAbortedException e) {
			return;
		}

	}

	/**
	 * Populates the fields in the view.
	 * 
	 * @author tejasvamsingh
	 */

	private void populateView() {

		Gson gson = GsonHelper.getGsoninstance();

		MealProperties mealPropertiesObject = (MealProperties) notificationPropertiesObject
				.getNotificationData();

		TextView textStartTimeTextViewObject = (TextView) mealView
				.getView("textView_mealdetails_startTime_result");
		mealView.setValue(textStartTimeTextViewObject, mealPropertiesObject
				.getStartDateAndTimeProperties().toString());

		TextView textEndTimeTextViewObject = (TextView) mealView
				.getView("textView_mealdetails_endTime_result");
		mealView.setValue(textEndTimeTextViewObject, mealPropertiesObject
				.getEndDateAndTimeProperties().toString());

		TextView restaurantTextViewObject = (TextView) mealView
				.getView("TextView_mealdetails_restaurant_result");

		MessageToasterHelper.toastMessage(mealPropertiesObject.getlocation());
		mealView.setValue(restaurantTextViewObject,
				mealPropertiesObject.getlocation());

		TextView additionalInformation = (TextView) mealView
				.getView("TextView_additionalinfo");
		additionalInformation.setText(mealPropertiesObject
				.getAdditionalInformation());

	}

	private void fetchAttending() {

		requestID = "Meal";
		requestType = "getAttendingContacts";
		attendingList.clear();
		PostProperties postPropertiesObject = PostProperties
				.notificationToPost(notificationPropertiesObject);

		attendingList.add(new ContactProperties(notificationPropertiesObject
				.getPoster()));

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postPropertiesObject.toMap(), requestID,
							requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;

				attendingList.add(new ContactProperties(result));
			}

		} catch (RequestAbortedException e) {

		}
		attendingAdapter.notifyDataSetChanged();
	}

}
