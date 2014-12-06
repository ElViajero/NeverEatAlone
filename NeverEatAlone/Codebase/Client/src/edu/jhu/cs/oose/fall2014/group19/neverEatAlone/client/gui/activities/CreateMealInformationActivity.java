package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.Calendar;
import java.util.Map;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.DateAndTimeProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

public class CreateMealInformationActivity extends FragmentActivity {

	Button BtnSelectStartDate, BtnSelectstartTime, BtnSelectEndDate,
	BtnSelectendTime;

	private EditText place;
	private EditText maxNumber;
	private Switch allowFriendInvite;

	// variables to save user selected date and time
	public int startYear, startmonth, startDay, starthour, startminute;
	public int endYear, endMonth, endDay, endhour, endminute;

	public CreateMealInformationActivity() {
		initiCalendar();
	}

	private void initiCalendar() {
		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		startYear = c.get(Calendar.YEAR);
		endYear = startYear;
		startmonth = c.get(Calendar.MONTH);
		endMonth = startmonth;
		startDay = c.get(Calendar.DAY_OF_MONTH);
		endDay = startDay;
		starthour = c.get(Calendar.HOUR);
		endhour = starthour;
		startminute = c.get(Calendar.MINUTE);
		endminute = startminute;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meal_information);

		// get the references of buttons
		BtnSelectStartDate = (Button) findViewById(R.id.CreateMealInformation_button_startDate);
		BtnSelectstartTime = (Button) findViewById(R.id.CreateMealInformation_button_startTime);
		BtnSelectEndDate = (Button) findViewById(R.id.CreateMealInformation_button_endDate);
		BtnSelectendTime = (Button) findViewById(R.id.CreateMealInformation_button_endTime);


		place = (EditText) findViewById(R.id.edit_restaurant);
		maxNumber = (EditText) findViewById(R.id.edit_maxnumber);
		allowFriendInvite = (Switch) findViewById(R.id.switch_allowfriendinvite);

		
		setTitleStyle();
	}
	
	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.CreateMealInformation_text_mealinformation);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Windsong.ttf");
		tv.setTypeface(tf);
		tv.setTextSize(100);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_meal_information, menu);
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

	public void onBackButtonClick(View view) {
		Intent intent = new Intent(CreateMealInformationActivity.this,
				TabHostActivity.class);
		CreateMealInformationActivity.this.startActivity(intent);
	}

	private void showStartDatePicker() {
		DatePickerFragment startdate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", startYear);
		args.putInt("month", startmonth);
		args.putInt("day", startDay);
		startdate.setArguments(args);
		// Set Call back to capture selected date
		startdate.setCallBack(onstartdate);
		startdate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showEndDatePicker() {
		DatePickerFragment enddate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", endYear);
		args.putInt("month", endMonth);
		args.putInt("day", endDay);
		enddate.setArguments(args);
		// Set Call back to capture selected date
		enddate.setCallBack(onenddate);
		enddate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showstartTimePicker() {
		TimePickerFragment startTime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", starthour);
		args.putInt("minute", startminute);
		startTime.setArguments(args);
		// Set Call back to capture selected date
		startTime.setCallBack(onstartTime);
		startTime.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showendTimePicker() {
		TimePickerFragment startTime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", endhour);
		args.putInt("minute", endminute);
		startTime.setArguments(args);
		// Set Call back to capture selected date
		startTime.setCallBack(onendTime);
		startTime.show(getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener onstartdate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startYear = year;
			startmonth = monthOfYear;
			int dummyMonthStart = startmonth + 1;
			startDay = dayOfMonth;
			BtnSelectStartDate.setText(startDay + "-" + dummyMonthStart + "-"
					+ startYear);
		}
	};

	OnDateSetListener onenddate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endYear = year;
			endMonth = monthOfYear;
			int dummyMonthEnd = endMonth + 1;
			endDay = dayOfMonth;
			BtnSelectEndDate.setText(endDay + "-" + dummyMonthEnd + "-"
					+ endYear);
		}
	};

	OnTimeSetListener onstartTime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			starthour = hourOfDay;
			startminute = minute;
			BtnSelectstartTime.setText(hourOfDay + ":" + minute);
		}
	};

	OnTimeSetListener onendTime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			endhour = hourOfDay;
			endminute = minute;
			BtnSelectendTime.setText(hourOfDay + ":" + minute);
		}
	};

	public void onStartDataButtonClick(View view) {
		showStartDatePicker();
	}

	public void onstartTimeButtonClick(View view) {
		showstartTimePicker();
	}

	public void onEndDataButtonClick(View view) {
		showEndDatePicker();
	}

	public void onendTimeButtonClick(View view) {
		showendTimePicker();
	}

	/**
	 * Handler for the meal creation event.
	 * 
	 * @author tejasvamsingh
	 * @param view
	 */
	public void onNextButtonClick(View view) {



		String location = place.getText().toString();
		String maxNumberOfInvitees = maxNumber.getText().toString();
		String isNotificationExtendible = allowFriendInvite.isChecked() ? "YES" : "NO";

		// ************************** PAGE onE REQUEST CREATIon STARTS HERE
		// **************************

		// Create Date and Time Properties Objects
		DateAndTimeProperties startDateAndTimeProperties = new DateAndTimeProperties(
				startDay, startmonth, startYear, starthour, startminute);

		DateAndTimeProperties endDateAndTimeProperties = new DateAndTimeProperties(
				endDay, endMonth, endYear, endhour, endminute);

		// Create a Meal Object
		MealProperties mealProperties = new MealProperties(location,
				maxNumberOfInvitees, isNotificationExtendible,
				startDateAndTimeProperties, endDateAndTimeProperties);

		Map<String, Object> mealPropertiesMap = mealProperties.toMap();

		Intent intent = new Intent(CreateMealInformationActivity.this,
				SelectFriendsActivity.class);
		intent.putExtra("mealProperties",
				GsonHelper.getGsoninstance().toJson(mealPropertiesMap));
		CreateMealInformationActivity.this.startActivity(intent);

	}
}
