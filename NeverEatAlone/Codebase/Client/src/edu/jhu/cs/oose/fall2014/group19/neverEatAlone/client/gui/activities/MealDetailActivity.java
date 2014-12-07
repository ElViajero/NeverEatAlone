package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

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
		String location = null;
		String startDay = null;
		String endDay = null;
		String startMonth = null;
		String endMonth = null;
		String startYear = null;
		String endYear = null;
		String startHour = null;
		String endHour = null;
		String startMinute = null;
		String endMinute = null;

		// TODO
		// Here I am trying to transform the string in Json to a map.
		// There might be better ways to do this.
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(postData);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jObj
					.getString("isNotificationExtendible");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			location = jObj.getString("location");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jObj.getString("maxNumberOfInvitees");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			endDay = jObj.getString("endday");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			endHour = jObj.getString("endhour");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			endMinute = jObj.getString("endminute");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			endMonth = jObj.getString("endmonth");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			endYear = jObj.getString("endyear");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			startDay = jObj.getString("startday");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			startHour = jObj.getString("starthour");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			startMinute = jObj.getString("startminute");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			startYear = jObj.getString("startyear");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			startMonth = jObj.getString("startmonth");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		TextView textStartTime = (TextView) findViewById(R.id.textView_mealdetails_startTime_result);
		textStartTime.setText(startDay + "/" + startMonth + "/" + startYear
				+ "-" + startHour + ":" + startMinute);

		TextView textEndTime = (TextView) findViewById(R.id.textView_mealdetails_endTime_result);
		textEndTime.setText(endDay + "/" + endMonth + "/" + endYear
				+ "-" + endHour + ":" + endMinute);
		
		TextView restaurant = (TextView) findViewById(R.id.TextView_mealdetails_restaurant_result);
		restaurant.setText(location);

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
