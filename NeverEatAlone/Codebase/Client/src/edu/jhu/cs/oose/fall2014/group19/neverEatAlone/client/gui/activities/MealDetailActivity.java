package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.MealView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * 
 * @author Hai Tang
 * @author Runze Tang
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
	
	/**
	 * 
	 * This method fetches mealProperties information from the InvitesActivity.
	 * 
	 * @author: Runze Tang
	 * @author Hai Tang
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);

		initMealView();
		mealDetailTitleObject = (TextView) mealView.getView("textView_mealdetails_title");
		
		setTitleStyle();

		String postData = getIntent().getStringExtra("mealProperties");

		Gson gson = GsonHelper.getGsoninstance();
		Type stringObjectMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> postDataMap = gson.fromJson(postData,
				stringObjectMap);

		
		// String isNotificationExtendible =
		// postDataMap.get("isNotificationExtendible").toString();
		String location = postDataMap.get("location").toString();
		String startYear = postDataMap.get("startyear").toString();
		String startMonth = postDataMap.get("startmonth").toString();
		String startDay = postDataMap.get("startday").toString();
		String startHour = postDataMap.get("starthour").toString();
		String startMinute = postDataMap.get("startminute").toString();
		String endYear = postDataMap.get("endyear").toString();
		String endMonth = postDataMap.get("endmonth").toString();
		String endDay = postDataMap.get("endday").toString();
		String endHour = postDataMap.get("endhour").toString();
		String endMinute = postDataMap.get("endminute").toString();

		TextView textStartTimeTextViewObject = (TextView) mealView.getView("textView_mealdetails_startTime_result");
		mealView.setValue(textStartTimeTextViewObject, timeToString(startDay) + "/"
				+ timeToString(startMonth) + "/" + startYear + "-"
				+ timeToString(startHour) + ":" + timeToString(startMinute));

		TextView textEndTimeTextViewObject = (TextView) mealView.getView("textView_mealdetails_endTime_result");
		mealView.setValue(textEndTimeTextViewObject, timeToString(endDay) + "/" + timeToString(endMonth)
				+ "/" + endYear + "-" + timeToString(endHour) + ":"
				+ timeToString(endMinute));

		TextView restaurantTextViewObject = (TextView) mealView.getView("TextView_mealdetails_restaurant_result");
		mealView.setValue(restaurantTextViewObject, location);

		// TextView additionalinfo = (TextView)
		// findViewById(R.id.textView_mealdetails_additionalinfo);
		// additionalinfo.setText(postDataMap.toString());
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
	 * Method used for accept button click
	 * 
	 * @author: Hai Tang
	 */
	public void onAcceptButtonClick(View view) {
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
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
	 * Set the right form for time and date.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private String timeToString(String time) {
		if (time.length() > 1) {
			return time;
		} else {
			return "0" + time;
		}
	}
}
