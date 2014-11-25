package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.meal.MealPostPropertiesHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal.DateAndTimeProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal.MealProperties;

public class CreateMealInformationActivity extends FragmentActivity {

	Button BtnSelectStartDate, BtnSelectStartTime, BtnSelectEndDate,
	BtnSelectEndTime;

	private EditText Place;
	private EditText MaxNumber;
	private Switch AllowFriendInvite;

	// variables to save user selected date and time
	public int YearStart, MonthStart, DayStart, HourStart, MinuteStart;
	public int YearEnd, MonthEnd, DayEnd, HourEnd, MinuteEnd;

	public CreateMealInformationActivity() {
		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		YearStart = c.get(Calendar.YEAR);
		YearEnd = YearStart;
		MonthStart = c.get(Calendar.MONTH);
		MonthEnd = MonthStart;
		DayStart = c.get(Calendar.DAY_OF_MONTH);
		DayEnd = DayStart;
		HourStart = c.get(Calendar.HOUR_OF_DAY);
		HourEnd = HourStart;
		MinuteStart = c.get(Calendar.MINUTE);
		MinuteEnd = MinuteStart;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meal_information);

		// get the references of buttons
		BtnSelectStartDate = (Button) findViewById(R.id.CreateMealInformation_button_startdate);
		BtnSelectStartTime = (Button) findViewById(R.id.CreateMealInformation_button_starttime);
		BtnSelectEndDate = (Button) findViewById(R.id.CreateMealInformation_button_enddate);
		BtnSelectEndTime = (Button) findViewById(R.id.CreateMealInformation_button_endtime);

		Place = (EditText) findViewById(R.id.edit_restaurant);
		MaxNumber = (EditText) findViewById(R.id.edit_maxnumber);
		AllowFriendInvite = (Switch) findViewById(R.id.switch_allowfriendinvite);
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

	public void OnBackButtonClick(View view) {
		Intent intent = new Intent(CreateMealInformationActivity.this,
				TabHostActivity.class);
		CreateMealInformationActivity.this.startActivity(intent);
	}

	private void showStartDatePicker() {
		DatePickerFragment startdate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", YearStart);
		args.putInt("month", MonthStart);
		args.putInt("day", DayStart);
		startdate.setArguments(args);
		// Set Call back to capture selected date
		startdate.setCallBack(onstartdate);
		startdate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showEndDatePicker() {
		DatePickerFragment enddate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", YearEnd);
		args.putInt("month", MonthEnd);
		args.putInt("day", DayEnd);
		enddate.setArguments(args);
		// Set Call back to capture selected date
		enddate.setCallBack(onenddate);
		enddate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showStartTimePicker() {
		TimePickerFragment starttime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", HourStart);
		args.putInt("minute", MinuteStart);
		starttime.setArguments(args);
		// Set Call back to capture selected date
		starttime.setCallBack(onstarttime);
		starttime.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showEndTimePicker() {
		TimePickerFragment starttime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", HourEnd);
		args.putInt("minute", MinuteEnd);
		starttime.setArguments(args);
		// Set Call back to capture selected date
		starttime.setCallBack(onendtime);
		starttime.show(getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener onstartdate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			YearStart = year;
			MonthStart = monthOfYear;
			int dummyMonthStart = MonthStart + 1;
			DayStart = dayOfMonth;
			BtnSelectStartDate.setText(DayStart + "-" + dummyMonthStart + "-"
					+ YearStart);
		}
	};

	OnDateSetListener onenddate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			YearEnd = year;
			MonthEnd = monthOfYear;
			int dummyMonthEnd = MonthEnd + 1;
			DayEnd = dayOfMonth;
			BtnSelectEndDate.setText(DayEnd + "-" + dummyMonthEnd + "-"
					+ YearEnd);
		}
	};

	OnTimeSetListener onstarttime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			HourStart = hourOfDay;
			MinuteStart = minute;
			BtnSelectStartTime.setText(hourOfDay + ":" + minute);
		}
	};

	OnTimeSetListener onendtime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			HourEnd = hourOfDay;
			MinuteEnd = minute;
			BtnSelectEndTime.setText(hourOfDay + ":" + minute);
		}
	};

	public void OnStartDataButtonClick(View view) {
		showStartDatePicker();
	}

	public void OnStartTimeButtonClick(View view) {
		showStartTimePicker();
	}

	public void OnEndDataButtonClick(View view) {
		showEndDatePicker();
	}

	public void OnEndTimeButtonClick(View view) {
		showEndTimePicker();
	}


	/**
	 * Handler for the meal creation event.
	 * 
	 * @author tejasvamsingh
	 * @param view
	 */
	public void OnNextButtonClick(View view) {

		int dummymonthstart = MonthStart + 1;
		int dummymonthend = MonthEnd + 1;

		String place = Place.getText().toString();
		String maxnumber = MaxNumber.getText().toString();
		String allowfriendinvite;
		if (AllowFriendInvite.isChecked()) {
			allowfriendinvite = "Yes";
		} else {
			allowfriendinvite = "No";
		}

		String location = Place.getText().toString();
		String maxNumberOfInvitees = MaxNumber.getText().toString();
		String isNotificationExtendible = AllowFriendInvite.getText().toString();

		Toast.makeText(
				getApplicationContext(),
				"Start Time: " + DayStart + "/" + dummymonthstart + "/"
						+ YearStart + " " + HourStart + ":" + MinuteStart + "\n"
						+ "End Time: " + DayEnd + "/" + dummymonthend + "/"
						+ YearEnd + " " + HourEnd + ":" + MinuteEnd + "\n"
						+ "Place: " + location + "\n" + "Max Number: " + maxNumberOfInvitees
						+ "\n" + "Allow Friend Invite: " + isNotificationExtendible,
						Toast.LENGTH_SHORT).show();



		// ************************** PAGE ONE REQUEST CREATION STARTS HERE **************************


		// Create Date and Time Properties Objects
		DateAndTimeProperties startDateAndTimeProperties = 
				new DateAndTimeProperties(DayStart, MonthStart, YearStart, HourStart, MinuteStart);

		DateAndTimeProperties endDateAndTimeProperties = 
				new DateAndTimeProperties(DayEnd, MonthEnd, YearEnd, HourEnd, MinuteEnd);

		//Create a Meal Object
		MealProperties mealProperties = new MealProperties(location,
				maxNumberOfInvitees, isNotificationExtendible); 

		// NOTE FROM TEJAS TO HIMSELF ::: CHANGE RETURN TYPE TO JSON STRING LATER !!!!!!!

		Map<String, List<String>> pageOneMap = MealPostPropertiesHelper.GetPageOneString(mealProperties, 
				startDateAndTimeProperties, endDateAndTimeProperties);

		try{
			// Send the Request
			List<Map<String, String>> resultMapList = 
					RequestHandlerHelper.GetRequestHandlerInstance().
					HandleRequest(this,pageOneMap,"Notification","Meal") ;
		}catch(NullPointerException e){
			return;
		}




	}
}
