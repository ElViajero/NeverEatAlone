package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.MealView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * @author tejasvamsingh
 * 
 * 
 *         MyPostsDetailActivity class is used to set up the view of the My
 *         Posts Details page. And also set up the functions after clicking
 *         different buttons.
 */
public class MyPostsDetailActivity extends ListActivity {

	private ArrayAdapter<ContactProperties> attendingContactsAdapter;
	private Context context;
	private Activity activity;
	private MealView myPostsView;
	private TextView myPostsDetailTitleObject;
	private TextView additionalInformation;
	private String requestID;
	private String requestType;
	private PostProperties postPropertiesObject;

	List<ContactProperties> attendingContactsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts_detail);

		initMyPostsView();
		myPostsDetailTitleObject = (TextView) myPostsView
				.getView("textView_myPostsDetail_title");

		attendingContactsList = new ArrayList<ContactProperties>();
		attendingContactsAdapter = new ContactsInformationAdapter(this,
				attendingContactsList);
		setListAdapter(attendingContactsAdapter);

		postPropertiesObject = (PostProperties) DataCacheHelper
				.getIActivityPropertiesObject();

		fetchAttending();

		if (attendingContactsList.isEmpty()) {
			TextView withWhom = (TextView) myPostsView
					.getView("TextView_myPostsDetail_withwhom");
			withWhom.setText("With Whom?\n\nNo attendees yet.");
		}

		// additionalInformation.setText(postPropertiesObject.get);

		populateView();
		setTitleStyle();
		applyTheme();

	}

	private void initMyPostsView() {
		context = this;
		activity = this;
		myPostsView = new MealView(context, activity);
	}

	private void setTitleStyle() {
		myPostsDetailTitleObject.setGravity(android.view.Gravity.CENTER);
		ThemeManager.setHeaderFont(myPostsDetailTitleObject);

	}

	private void applyTheme() {

		initMyPostsView();
		View mainLayout = myPostsView.getView("main_myPostsDetail");
		View headerLayout = myPostsView.getView("header_myPostsDetail");
		View buttonBar = myPostsView.getView("buttons_myPostsDetail");

		View backButton = myPostsView.getView("button_myPostsDetail_back");
		View closeButton = myPostsView.getView("button_myPostsDetail_edit");
		// View inviteOthersButton = myPostsView
		// .getView("button_myPostsDetail_inviteothers");
		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(backButton);
		ThemeManager.applyButtonColor(closeButton);
		// ThemeManager.applyButtonColor(inviteOthersButton);
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
		InvitesFragmentActivity.setFragmentpostion(0);
		Intent intent = new Intent(this, InvitesFragmentActivity.class);
		startActivity(intent);

	}

	/**
	 * click handler for the edit profile button.
	 * 
	 */
	public void onEditPostButtonClick(View view) {

		DataCacheHelper.setGenericFlag(true);
		// MessageToasterHelper.toastMessage("edit clicked");
		Intent i = new Intent(this, CreateMealInformationActivity.class);
		startActivity(i);

	}

	private void fetchAttending() {

		requestID = "Meal";
		requestType = "getAttendingContacts";
		attendingContactsList.clear();

		try {

			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							postPropertiesObject.toMap(), requestID,
							requestType);

			for (Map<String, String> result : resultMapList) {

				if (result.isEmpty())
					continue;

				attendingContactsList.add(new ContactProperties(result));
			}

			attendingContactsAdapter.notifyDataSetChanged();

		} catch (RequestAbortedException e) {

		}
	}

	/**
	 * Populates the fields in the view.
	 * 
	 * @author tejasvamsingh
	 */

	private void populateView() {

		Gson gson = GsonHelper.getGsoninstance();

		String postData = postPropertiesObject.getPostData();

		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();

		Map<String, String> postDataMap = gson.fromJson(postData,
				stringStringMap);

		MealProperties mealPropertiesObject = new MealProperties(postDataMap);

		TextView textStartTimeTextViewObject = (TextView) myPostsView
				.getView("textView_myPostsDetail_startTime_result");
		myPostsView.setValue(textStartTimeTextViewObject, mealPropertiesObject
				.getStartDateAndTimeProperties().toString());

		TextView textEndTimeTextViewObject = (TextView) myPostsView
				.getView("textView_myPostsDetail_endTime_result");
		myPostsView.setValue(textEndTimeTextViewObject, mealPropertiesObject
				.getEndDateAndTimeProperties().toString());

		TextView restaurantTextViewObject = (TextView) myPostsView
				.getView("TextView_myPostsDetail_restaurant_result");

		additionalInformation = (TextView) myPostsView
				.getView("TextView_additionalinfo_result");

		myPostsView.setValue(restaurantTextViewObject,
				mealPropertiesObject.getlocation());

		myPostsView.setValue(additionalInformation,
				mealPropertiesObject.getAdditionalInformation());
	}

}
