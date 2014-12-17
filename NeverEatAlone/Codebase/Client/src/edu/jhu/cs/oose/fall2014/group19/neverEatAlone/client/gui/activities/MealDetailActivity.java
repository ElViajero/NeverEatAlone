package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
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
public class MealDetailActivity extends Activity {


	private Context context;
	private Activity activity;
	private MealView mealView;
	private TextView mealDetailTitleObject;
	private String requestID;
	private String requestType;
	private NotificationProperties notificationPropertiesObject;

	/**
	 * 
	 *Called when the activity is first created.
	 *@author tejasvamsingh
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);

		initMealView();
		mealDetailTitleObject = (TextView) mealView.getView("textView_mealdetails_title");

		notificationPropertiesObject=
				DataCacheHelper.getNotificationPropertiesObject();

		setTitleStyle();
		populateView();

	}



	/**
	 * Method used to initialize MealView
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
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Chunkfive.otf");
		mealDetailTitleObject.setTypeface(tf);
		mealDetailTitleObject.setTextSize(80);
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

		requestID ="Meal";
		requestType ="accept";

		PostProperties postPropertiesObject=
				PostProperties.notificationToPost(notificationPropertiesObject);

		try{

			List<Map<String, String>> result =
					RequestHandlerHelper.getRequestHandlerInstance().
					handleRequest(this,postPropertiesObject.toMap(),requestID,requestType);

			NotificationAndPostCacheHelper.setServerFetchRequired("meal", true);
			System.out.println("FETCH STATUS :"+
					NotificationAndPostCacheHelper.isServerFetchRequired("meal"));

			Intent intent = new Intent(this, TabHostActivity.class);
			startActivity(intent);


		}catch(RequestAbortedException e){
			return;
		}

	}


	/**
	 * Method used for decline button click
	 * 
	 * @author: Hai Tang
	 */
	public void onDeclineButtonClick(View view) {
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}


	/**
	 * Populates the fields in the view.
	 * @author tejasvamsingh
	 */

	private void populateView() {


		Gson gson = GsonHelper.getGsoninstance();



		MealProperties mealPropertiesObject = (MealProperties)
				notificationPropertiesObject.getNotificationData();


		TextView textStartTimeTextViewObject = 
				(TextView) mealView.getView("textView_mealdetails_startTime_result");
		mealView.setValue(textStartTimeTextViewObject,
				mealPropertiesObject.getStartDateAndTimeProperties().toString());

		TextView textEndTimeTextViewObject = 
				(TextView) mealView.getView("textView_mealdetails_endTime_result");
		mealView.setValue(textEndTimeTextViewObject,
				mealPropertiesObject.getEndDateAndTimeProperties().toString() );

		TextView restaurantTextViewObject = 
				(TextView) mealView.getView("TextView_mealdetails_restaurant_result");

		mealView.setValue(restaurantTextViewObject,
				mealPropertiesObject.getlocation());
	}


}
