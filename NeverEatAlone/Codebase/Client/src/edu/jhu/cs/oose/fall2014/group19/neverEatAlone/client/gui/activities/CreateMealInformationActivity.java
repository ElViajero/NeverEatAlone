package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class CreateMealInformationActivity extends Activity {

	Button btnSelectStartDate, btnSelectStartTime, btnSelectEndDate,
	btnSelectEndTime;

	static final int START_DATE_DIALOG_ID = 0;
	static final int START_TIME_DIALOG_ID = 1;
	static final int END_DATE_DIALOG_ID = 2;
	static final int END_TIME_DIALOG_ID = 3;

	// variables to save user selected date and time
	public int yearStart, monthStart, dayStart, hourStart, minuteStart;
	public int yearEnd, monthEnd, dayEnd, hourEnd, minuteEnd;
	// declare the variables to Show/Set the date and time when Time and Date
	// Picker Dialog first appears
	private int mYear, mMonth, mDay, mHour, mMinute;

	// constructor

	public CreateMealInformationActivity() {
		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
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

		// Set ClickListener on btnSelectDate
		btnSelectStartDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Show the DatePickerDialog
				showDialog(START_DATE_DIALOG_ID);
			}
		});
		btnSelectEndDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Show the DatePickerDialog
				showDialog(END_DATE_DIALOG_ID);
			}
		});

		// Set ClickListener on btnSelectTime
		btnSelectStartTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Show the TimePickerDialog
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		btnSelectEndTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Show the TimePickerDialog
				showDialog(END_TIME_DIALOG_ID);
			}
		});

	}

	// Register DatePickerDialog listener
	private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// the callback received when the user "sets" the Date in the
		// DatePickerDialog
		@Override
		public void onDateSet(DatePicker view, int yearSelected,
				int monthOfYear, int dayOfMonth) {
			yearStart = yearSelected;
			monthStart = monthOfYear + 1;
			dayStart = dayOfMonth;
			// Set the Selected Date in Select date Button
			btnSelectStartDate.setText(dayStart + "-" + monthStart + "-"
					+ yearStart);
		}
	};

	private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// the callback received when the user "sets" the Date in the
		// DatePickerDialog
		@Override
		public void onDateSet(DatePicker view, int yearSelected,
				int monthOfYear, int dayOfMonth) {
			yearStart = yearSelected;
			monthStart = monthOfYear + 1;
			dayStart = dayOfMonth;
			// Set the Selected Date in Select date Button
			btnSelectEndDate.setText(dayStart + "-" + monthStart + "-"
					+ yearStart);
		}
	};

	// Register TimePickerDialog listener
	private TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		// the callback received when the user "sets" the TimePickerDialog in
		// the dialog
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int min) {
			hourStart = hourOfDay;
			minuteStart = min;
			// Set the Selected Date in Select date Button
			btnSelectStartTime.setText(hourStart + ":" + minuteStart);
		}
	};

	private TimePickerDialog.OnTimeSetListener endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		// the callback received when the user "sets" the TimePickerDialog in
		// the dialog
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int min) {
			hourStart = hourOfDay;
			minuteStart = min;
			// Set the Selected Date in Select date Button
			btnSelectEndTime.setText(hourStart + ":" + minuteStart);
		}
	};

	// Method automatically gets Called when you call showDialog() method
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case START_DATE_DIALOG_ID:
			// create a new DatePickerDialog with values you want to show
			return new DatePickerDialog(this, startDateSetListener, mYear,
					mMonth, mDay);
			// create a new TimePickerDialog with values you want to show
		case START_TIME_DIALOG_ID:
			return new TimePickerDialog(this, startTimeSetListener, mHour,
					mMinute, false);
		case END_DATE_DIALOG_ID:
			// create a new DatePickerDialog with values you want to show
			return new DatePickerDialog(this, endDateSetListener, mYear,
					mMonth, mDay);
			// create a new TimePickerDialog with values you want to show
		case END_TIME_DIALOG_ID:
			return new TimePickerDialog(this, endTimeSetListener, mHour,
					mMinute, false);
		}
		return null;
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

	public void OnNextButtonClick(View view) {

		/*
		ArrayList<NameValuePair> requestList = new ArrayList<NameValuePair>();
		requestList.add(new BasicNameValuePair("RequestID", RequestID));
		requestList.add(new BasicNameValuePair("RequestType", RequestType));
		requestList.add(new BasicNameValuePair("Recipient", "Tejas"));
		requestList.add(new BasicNameValuePair("YearStart", String
				.valueOf(yearStart)));
		requestList.add(new BasicNameValuePair("MonthStart", String
				.valueOf(monthStart)));
		requestList.add(new BasicNameValuePair("DayStart", String
				.valueOf(dayStart)));
		requestList.add(new BasicNameValuePair("HourStart", String
				.valueOf(hourStart)));
		requestList.add(new BasicNameValuePair("MinuteStart", String
				.valueOf(minuteStart)));
		requestList.add(new BasicNameValuePair("YearEnd", String
				.valueOf(yearEnd)));
		requestList.add(new BasicNameValuePair("MonthEnd", String
				.valueOf(monthEnd)));
		requestList
				.add(new BasicNameValuePair("DayEnd", String.valueOf(dayEnd)));
		requestList.add(new BasicNameValuePair("HourEnd", String
				.valueOf(hourEnd)));
		requestList.add(new BasicNameValuePair("MinuteEnd", String
				.valueOf(minuteEnd)));

		List<Map<String, String>> resultMapList = RequestHandlerHelper
				.GetRequestHandlerInstance().HandleRequest(requestList);
		 */

	}

}
