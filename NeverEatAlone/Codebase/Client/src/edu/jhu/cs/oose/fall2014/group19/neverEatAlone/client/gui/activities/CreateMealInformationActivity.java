package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class CreateMealInformationActivity extends FragmentActivity {

	Button btnSelectStartDate, btnSelectStartTime, btnSelectEndDate,
			btnSelectEndTime;

	static final int START_DATE_DIALOG_ID = 0;
	static final int START_TIME_DIALOG_ID = 1;
	static final int END_DATE_DIALOG_ID = 2;
	static final int END_TIME_DIALOG_ID = 3;

	// variables to save user selected date and time
	public int yearStart, monthStart, dayStart, hourStart, minuteStart;
	public int yearEnd, monthEnd, dayEnd, hourEnd, minuteEnd;

	public CreateMealInformationActivity() {
		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		yearStart = c.get(Calendar.YEAR);
		yearEnd = yearStart;
		monthStart = c.get(Calendar.MONTH);
		monthEnd = monthStart;
		dayStart = c.get(Calendar.DAY_OF_MONTH);
		dayEnd = dayStart;
		hourStart = c.get(Calendar.HOUR_OF_DAY);
		hourEnd = hourStart;
		minuteStart = c.get(Calendar.MINUTE);
		minuteEnd = minuteStart;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meal_information);

		// get the references of buttons
		btnSelectStartDate = (Button) findViewById(R.id.CreateMealInformation_button_startdate);
		btnSelectStartTime = (Button) findViewById(R.id.CreateMealInformation_button_starttime);
		btnSelectEndDate = (Button) findViewById(R.id.CreateMealInformation_button_enddate);
		btnSelectEndTime = (Button) findViewById(R.id.CreateMealInformation_button_endtime);

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
		args.putInt("year", yearStart);
		args.putInt("month", monthStart);
		args.putInt("day", dayStart);
		startdate.setArguments(args);
		// Set Call back to capture selected date
		startdate.setCallBack(onstartdate);
		startdate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showEndDatePicker() {
		DatePickerFragment enddate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", yearEnd);
		args.putInt("month", monthEnd);
		args.putInt("day", dayEnd);
		enddate.setArguments(args);
		// Set Call back to capture selected date
		enddate.setCallBack(onenddate);
		enddate.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showStartTimePicker() {
		TimePickerFragment starttime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", hourStart);
		args.putInt("minute", minuteStart);
		starttime.setArguments(args);
		// Set Call back to capture selected date
		starttime.setCallBack(onstarttime);
		starttime.show(getSupportFragmentManager(), "Date Picker");
	}

	private void showEndTimePicker() {
		TimePickerFragment starttime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", hourEnd);
		args.putInt("minute", minuteEnd);
		starttime.setArguments(args);
		// Set Call back to capture selected date
		starttime.setCallBack(onendtime);
		starttime.show(getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener onstartdate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			yearStart = year;
			monthStart = monthOfYear;
			int dummyMonthStart = monthStart + 1;
			dayStart = dayOfMonth;
			btnSelectStartDate.setText(dayStart + "-" + dummyMonthStart + "-"
					+ yearStart);
		}
	};

	OnDateSetListener onenddate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			yearEnd = year;
			monthEnd = monthOfYear;
			int dummyMonthEnd = monthEnd + 1;
			dayEnd = dayOfMonth;
			btnSelectEndDate.setText(dayEnd + "-" + dummyMonthEnd + "-"
					+ yearEnd);
		}
	};

	OnTimeSetListener onstarttime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hourStart = hourOfDay;
			minuteStart = minute;
			btnSelectStartTime.setText(hourOfDay + ":" + minute);
		}
	};

	OnTimeSetListener onendtime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hourEnd = hourOfDay;
			minuteEnd = minute;
			btnSelectEndTime.setText(hourOfDay + ":" + minute);
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

	public void OnNextButtonClick(View view) {

		/*
		 * ArrayList<NameValuePair> requestList = new
		 * ArrayList<NameValuePair>(); requestList.add(new
		 * BasicNameValuePair("RequestID", RequestID)); requestList.add(new
		 * BasicNameValuePair("RequestType", RequestType)); requestList.add(new
		 * BasicNameValuePair("Recipient", "Tejas")); requestList.add(new
		 * BasicNameValuePair("YearStart", String .valueOf(yearStart)));
		 * requestList.add(new BasicNameValuePair("MonthStart", String
		 * .valueOf(monthStart))); requestList.add(new
		 * BasicNameValuePair("DayStart", String .valueOf(dayStart)));
		 * requestList.add(new BasicNameValuePair("HourStart", String
		 * .valueOf(hourStart))); requestList.add(new
		 * BasicNameValuePair("MinuteStart", String .valueOf(minuteStart)));
		 * requestList.add(new BasicNameValuePair("YearEnd", String
		 * .valueOf(yearEnd))); requestList.add(new
		 * BasicNameValuePair("MonthEnd", String .valueOf(monthEnd)));
		 * requestList .add(new BasicNameValuePair("DayEnd",
		 * String.valueOf(dayEnd))); requestList.add(new
		 * BasicNameValuePair("HourEnd", String .valueOf(hourEnd)));
		 * requestList.add(new BasicNameValuePair("MinuteEnd", String
		 * .valueOf(minuteEnd)));
		 * 
		 * List<Map<String, String>> resultMapList = RequestHandlerHelper
		 * .GetRequestHandlerInstance().HandleRequest(requestList);
		 */

	}

}
