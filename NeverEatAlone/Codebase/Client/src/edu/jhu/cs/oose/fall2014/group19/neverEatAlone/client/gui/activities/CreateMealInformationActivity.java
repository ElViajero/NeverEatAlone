package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class CreateMealInformationActivity extends Activity {

	Button btnSelectDate, btnSelectTime;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	// variables to save user selected date and time
	public int year, month, day, hour, minute;
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
		btnSelectDate = (Button) findViewById(R.id.CreateMealInformation_button_startdate);
		btnSelectTime = (Button) findViewById(R.id.CreateMealInformation_button_starttime);

		// Set ClickListener on btnSelectDate
		btnSelectDate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Show the DatePickerDialog
				showDialog(DATE_DIALOG_ID);
			}
		});

		// Set ClickListener on btnSelectTime
		btnSelectTime.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Show the TimePickerDialog
				showDialog(TIME_DIALOG_ID);
			}
		});

	}

	// Register DatePickerDialog listener
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// the callback received when the user "sets" the Date in the
		// DatePickerDialog
		public void onDateSet(DatePicker view, int yearSelected,
				int monthOfYear, int dayOfMonth) {
			year = yearSelected;
			month = monthOfYear;
			day = dayOfMonth;
			// Set the Selected Date in Select date Button
			btnSelectDate.setText(day + "-" + month + "-" + year);
		}
	};

	// Register TimePickerDialog listener
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		// the callback received when the user "sets" the TimePickerDialog in
		// the dialog
		public void onTimeSet(TimePicker view, int hourOfDay, int min) {
			hour = hourOfDay;
			minute = min;
			// Set the Selected Date in Select date Button
			btnSelectTime.setText(hour + ":" + minute);
		}
	};

	// Method automatically gets Called when you call showDialog() method
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// create a new DatePickerDialog with values you want to show
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
			// create a new TimePickerDialog with values you want to show
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);

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
}
