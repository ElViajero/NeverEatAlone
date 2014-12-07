package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.lang.reflect.Type;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
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

	/**
	 * 
	 * This method fetches mealProperties information from the InvitesActivity.
	 * 
	 * @author: Runze Tang
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);

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

		TextView textStartTime = (TextView) findViewById(R.id.textView_mealdetails_startTime_result);
		textStartTime.setText(startDay + "/" + startMonth + "/" + startYear
				+ "-" + startHour + ":" + startMinute);

		TextView textEndTime = (TextView) findViewById(R.id.textView_mealdetails_endTime_result);
		textEndTime.setText(endDay + "/" + endMonth + "/" + endYear + "-"
				+ endHour + ":" + endMinute);

		TextView restaurant = (TextView) findViewById(R.id.TextView_mealdetails_restaurant_result);
		restaurant.setText(location);

		// TextView additionalinfo = (TextView)
		// findViewById(R.id.textView_mealdetails_additionalinfo);
		// additionalinfo.setText(postDataMap.toString());
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		TextView tv = (TextView) findViewById(R.id.textView_mealdetails_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Windsong.ttf");
		tv.setTypeface(tf);
		tv.setTextSize(100);
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
}
