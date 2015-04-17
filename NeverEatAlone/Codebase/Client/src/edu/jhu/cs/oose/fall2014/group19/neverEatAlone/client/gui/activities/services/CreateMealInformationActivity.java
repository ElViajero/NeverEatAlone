package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.DateAndTimeProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.PostProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.DataCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.MealView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class handles the meal information of the creation of an invite.
 * 
 * @author Runze Tang
 * @author Yueling Loh
 * @author tejasvamsingh
 * @author Hai Tang
 * 
 */

public class CreateMealInformationActivity extends FragmentActivity {

	private Button btnSelectStartDateObject, btnSelectstartTimeObject,
			btnSelectEndDateObject, btnSelectendTimeObject;
	private TextView createMealInfoTitleObject;
	private EditText placeEditViewObject;
	private EditText maxNumberEditViewObject;
	private EditText additionalInformation;

	private Switch allowFriendInviteSwitchObject;
	private Context context;
	private Activity activity;
	private MealView mealView;

	private AutoCompleteTextView restaurantAutoCompleteTextView;
	LocationManager locationManagerObject;
	LocationListener locationListenerObject;
	static boolean isListFetched = false;

	private PostProperties populateFromPost;

	private boolean isPostBeingEdited = false; // set to true if we are
												// editing a post.

	ArrayAdapter<String> restaurantAutoCompleteArrayAdapter;

	// private View mainLayout;
	// private View headerLayout;
	// private View buttonBar;

	// variables to save user selected date and time
	public int startYear, startMonth, startDay, startHour, startMinute;
	public int endYear, endMonth, endDay, endHour, endMinute;

	/**
	 * Initialization the calendar.
	 * 
	 * @author Runze Tang
	 * 
	 */

	public CreateMealInformationActivity() {
		initiCalendar();
	}

	/**
	 * 
	 * This method helps set the initial value of fields in case we are editing
	 * a post.
	 * 
	 */
	private void populateFromPost() {
		populateFromPost = null;
		// if (!isPostBeingEdited)
		// return;
		if (DataCacheHelper.getGenericFlag()
				&& DataCacheHelper.getIActivityPropertiesObject() != null) {
			try {
				populateFromPost = (PostProperties) DataCacheHelper
						.getIActivityPropertiesObject();
			} catch (ClassCastException e) {
			} finally {
				DataCacheHelper.setGenericFlag(false);
			}
		}
		if (populateFromPost != null) {
			Gson gson = GsonHelper.getGsoninstance();
			String postData = populateFromPost.getPostData();
			Type stringStringMap = new TypeToken<Map<String, String>>() {
			}.getType();

			Map<String, String> postDataMap = gson.fromJson(postData,
					stringStringMap);
			MealProperties mealPropertiesObject = new MealProperties(
					postDataMap);
			restaurantAutoCompleteTextView.setText(mealPropertiesObject
					.getlocation());
			maxNumberEditViewObject.setText(mealPropertiesObject
					.getMaxNumberOfInvitees());

			additionalInformation.setText(mealPropertiesObject
					.getAdditionalInformation());

			DateAndTimeProperties startDateAndTimeProperties = mealPropertiesObject
					.getStartDateAndTimeProperties();
			startYear = startDateAndTimeProperties.getYear();
			startMonth = startDateAndTimeProperties.getMonth();
			startDay = startDateAndTimeProperties.getDay();
			startHour = startDateAndTimeProperties.getHour();
			startMinute = startDateAndTimeProperties.getMinute();

			DateAndTimeProperties endDateAndTimeProperties = mealPropertiesObject
					.getEndDateAndTimeProperties();
			endYear = endDateAndTimeProperties.getYear();
			endMonth = endDateAndTimeProperties.getMonth();
			endDay = endDateAndTimeProperties.getDay();
			endHour = endDateAndTimeProperties.getHour();
			endMinute = endDateAndTimeProperties.getMinute();
			btnSelectStartDateObject.setText(startDateAndTimeProperties
					.getDateString());
			btnSelectstartTimeObject.setText(startDateAndTimeProperties
					.getTimeString());
			btnSelectEndDateObject.setText(endDateAndTimeProperties
					.getDateString());
			btnSelectendTimeObject.setText(endDateAndTimeProperties
					.getTimeString());
		}
	}

	/**
	 * Set the calendar to initial.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private void initiCalendar() {
		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		startYear = c.get(Calendar.YEAR);
		endYear = startYear;
		startMonth = c.get(Calendar.MONTH);
		endMonth = startMonth;
		startDay = c.get(Calendar.DAY_OF_MONTH);
		endDay = startDay;
		startHour = c.get(Calendar.HOUR_OF_DAY);
		endHour = startHour;
		startMinute = c.get(Calendar.MINUTE);
		endMinute = startMinute;
	}

	/**
	 * Get references of button and edit text.
	 * 
	 * @author Runze Tang
	 * @author Hai Tang
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meal_information);

		MessageToasterHelper
				.toastMessage("inside onCreate in CreateMealInformationActivity");
		initMealView();

		// get the references of buttons
		btnSelectStartDateObject = (Button) mealView
				.getView("CreateMealInformation_button_startDate");
		btnSelectstartTimeObject = (Button) mealView
				.getView("CreateMealInformation_button_startTime");
		btnSelectEndDateObject = (Button) mealView
				.getView("CreateMealInformation_button_endDate");
		btnSelectendTimeObject = (Button) mealView
				.getView("CreateMealInformation_button_endTime");

		additionalInformation = (EditText) mealView
				.getView("edit_additionalInformation");

		// placeEditViewObject = (EditText) mealView.getView("edit_restaurant");
		initAutoComplete();

		maxNumberEditViewObject = (EditText) mealView.getView("edit_maxnumber");
		allowFriendInviteSwitchObject = (Switch) mealView
				.getView("switch_allowfriendinvite");
		createMealInfoTitleObject = (TextView) mealView
				.getView("CreateMealInformation_text_mealinformation");

		setTitleStyle();
		applyTheme();
		populateFromPost();
	}

	@SuppressWarnings("unchecked")
	private void initAutoComplete() {
		restaurantAutoCompleteTextView = (AutoCompleteTextView) mealView
				.getView("edit_restaurant_auto");

		if (DataCacheHelper.getAdapter("restaurantAutoComplete") == null) {
			restaurantAutoCompleteArrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,
					new ArrayList<String>());

			DataCacheHelper.registerAdapter(restaurantAutoCompleteArrayAdapter,
					"restaurantAutoComplete");

			restaurantAutoCompleteTextView
					.setAdapter(restaurantAutoCompleteArrayAdapter);
		}

		restaurantAutoCompleteArrayAdapter = (ArrayAdapter<String>) DataCacheHelper
				.getAdapter("restaurantAutoComplete");

		restaurantAutoCompleteTextView
				.setAdapter(restaurantAutoCompleteArrayAdapter);
	}

	/**
	 * Method used to initialize MealView
	 * 
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

		ThemeManager.setHeaderFont(createMealInfoTitleObject);
	}

	/**
	 * This method is used to set GUI colors
	 * 
	 * @author: Yueling Loh
	 */
	private void applyTheme() {

		initMealView();
		View mainLayout = mealView.getView("main_createMealInfo");
		View headerLayout = mealView.getView("header_createMealInfo");
		View buttonBar = mealView.getView("buttons_createMealInfo");

		View backButton = mealView.getView("CreateMealInformation_button_back");
		View nextButton = mealView.getView("CreateMealInformation_button_next");

		ThemeManager.applyPlainTheme(mainLayout, headerLayout, buttonBar);

		ThemeManager.applyButtonColor(btnSelectStartDateObject);
		ThemeManager.applyButtonColor(btnSelectstartTimeObject);
		ThemeManager.applyButtonColor(btnSelectEndDateObject);
		ThemeManager.applyButtonColor(btnSelectendTimeObject);
		ThemeManager.applyButtonColor(allowFriendInviteSwitchObject);

		ThemeManager.applyButtonColor(backButton);
		ThemeManager.applyButtonColor(nextButton);

		// ThemeManager.applyEditTextColor(placeEditViewObject);
		ThemeManager.applyEditTextColor(restaurantAutoCompleteTextView);
		ThemeManager.applyEditTextColor(maxNumberEditViewObject);
		ThemeManager.applyEditTextColor(additionalInformation);
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

	/**
	 * Set back button.
	 * 
	 * @author Runze Tang
	 * 
	 */
	public void onBackButtonClick(View view) {
		Intent intent = new Intent(CreateMealInformationActivity.this,
				TabHostActivity.class);
		CreateMealInformationActivity.this.startActivity(intent);
	}

	/**
	 * Set the selected start date into dialog.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private void showStartDatePicker() {
		DatePickerFragment startdate = new DatePickerFragment();
		// Set selected date into dialog. Default current date.
		Bundle args = new Bundle();
		args.putInt("year", startYear);
		args.putInt("month", startMonth);
		args.putInt("day", startDay);
		startdate.setArguments(args);
		// Set Call back to capture selected date
		startdate.setCallBack(onstartdate);
		startdate.show(getSupportFragmentManager(), "Date Picker");
	}

	/**
	 * Set the selected end date into dialog.
	 * 
	 * @author Runze Tang
	 * 
	 */
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

	/**
	 * Set the selected start time into dialog.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private void showstartTimePicker() {
		TimePickerFragment startTime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", startHour);
		args.putInt("minute", startMinute);
		startTime.setArguments(args);
		// Set Call back to capture selected date
		startTime.setCallBack(onstartTime);
		startTime.show(getSupportFragmentManager(), "Date Picker");
	}

	/**
	 * Set the selected end time into dialog.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private void showendTimePicker() {
		TimePickerFragment startTime = new TimePickerFragment();
		// Set selected time into dialog. Default current time.
		Bundle args = new Bundle();
		args.putInt("hour", endHour);
		args.putInt("minute", endMinute);
		startTime.setArguments(args);
		// Set Call back to capture selected date
		startTime.setCallBack(onendTime);
		startTime.show(getSupportFragmentManager(), "Date Picker");
	}

	/**
	 * Set listener on the DatePicker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	OnDateSetListener onstartdate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startYear = year;
			startMonth = monthOfYear;
			int dummyMonthStart = startMonth + 1;
			startDay = dayOfMonth;
			btnSelectStartDateObject.setText(timeToString(startDay) + "-"
					+ timeToString(dummyMonthStart) + "-" + startYear);
		}
	};

	/**
	 * Set listener on the DatePicker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	OnDateSetListener onenddate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endYear = year;
			endMonth = monthOfYear;
			int dummyMonthEnd = endMonth + 1;
			endDay = dayOfMonth;
			btnSelectEndDateObject.setText(timeToString(endDay) + "-"
					+ timeToString(dummyMonthEnd) + "-" + endYear);
		}
	};

	/**
	 * Set listener on the TimePicker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	OnTimeSetListener onstartTime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			startHour = hourOfDay;
			startMinute = minute;
			btnSelectstartTimeObject.setText(timeToString(hourOfDay) + ":"
					+ timeToString(minute));
		}
	};

	/**
	 * Set listener on the TimePicker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	OnTimeSetListener onendTime = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			endHour = hourOfDay;
			endMinute = minute;
			btnSelectendTimeObject.setText(timeToString(hourOfDay) + ":"
					+ timeToString(minute));
		}
	};

	/**
	 * Set start date button to show start date picker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	public void onStartDataButtonClick(View view) {
		showStartDatePicker();
	}

	/**
	 * Set start time button to show start time picker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	public void onstartTimeButtonClick(View view) {
		showstartTimePicker();
	}

	/**
	 * Set end date button to show end date picker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	public void onEndDataButtonClick(View view) {
		showEndDatePicker();
	}

	/**
	 * Set end time button to show end time picker.
	 * 
	 * @author Runze Tang
	 * 
	 */
	public void onendTimeButtonClick(View view) {
		showendTimePicker();
	}

	/**
	 * Set the right form for time and date.
	 * 
	 * @author Runze Tang
	 * 
	 */
	private String timeToString(int time) {
		if (time >= 10) {
			return String.valueOf(time);
		} else {
			return "0" + time;
		}
	}

	/**
	 * Handler for the meal creation event.
	 * 
	 * @author tejasvamsingh
	 * @author Runze Tang
	 * @author Yueling Loh
	 * @author Hai Tang
	 * @param view
	 */
	public void onNextButtonClick(View view) {

		initMealView();

		// String location = mealView.getValue(placeEditViewObject);
		String location = mealView.getValue(restaurantAutoCompleteTextView);
		String maxNumberOfInvitees = mealView.getValue(maxNumberEditViewObject);
		String isNotificationExtendible = allowFriendInviteSwitchObject
				.isChecked() ? "YES" : "NO";
		String additionalInformationString = mealView
				.getValue(additionalInformation);

		if (location.equals("")) {
			Toast.makeText(this, R.string.location_empty, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (maxNumberOfInvitees.length() == 0) {
			Toast.makeText(this, R.string.number_invitees_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (Integer.parseInt(maxNumberOfInvitees) < 2) {
			Toast.makeText(this, R.string.invitees_not_enough,
					Toast.LENGTH_SHORT).show();
			return;
		}

		// converts the date into a java date type and checks if start date is
		// after end date
		SimpleDateFormat ft = new SimpleDateFormat("yyyy:mm:dd:HH:MM");
		String startDateStr = startYear + ":" + startMonth + ":" + startDay
				+ ":" + startHour + ":" + startMinute;
		String endDateStr = endYear + ":" + endMonth + ":" + endDay + ":"
				+ endHour + ":" + endMinute;
		Date startDate, endDate;
		try {

			startDate = ft.parse(startDateStr);
			endDate = ft.parse(endDateStr);
			if (startDate.after(endDate)) {
				Toast.makeText(this, R.string.date_error, Toast.LENGTH_SHORT)
						.show();
				return;
			}
		} catch (Exception e) {
			Log.e("CreatMealInfo", "date parsing error");
			return;
		}

		// ************************** PAGE onE REQUEST CREATIon STARTS HERE
		// **************************

		int dummyStartMonth = startMonth + 1;
		int dummyEndMonth = endMonth + 1;
		// Create Date and Time Properties Objects
		DateAndTimeProperties startDateAndTimeProperties = new DateAndTimeProperties(
				startDay, dummyStartMonth, startYear, startHour, startMinute);

		DateAndTimeProperties endDateAndTimeProperties = new DateAndTimeProperties(
				endDay, dummyEndMonth, endYear, endHour, endMinute);

		// Create a Meal Object
		MealProperties mealProperties = new MealProperties(location,
				maxNumberOfInvitees, isNotificationExtendible,
				startDateAndTimeProperties, endDateAndTimeProperties,
				additionalInformationString);

		// this happens if we are editing a previous post.
		if (populateFromPost != null) {
			DataCacheHelper.setGenericFlag(true);
			populateFromPost = null;
		}

		// the same old passing to the next activity.
		Map<String, Object> mealPropertiesMap = mealProperties.toMap();

		Intent intent = new Intent(CreateMealInformationActivity.this,
				SelectFriendsFragmentActivity.class);
		intent.putExtra("mealProperties",
				GsonHelper.getGsoninstance().toJson(mealPropertiesMap));
		CreateMealInformationActivity.this.startActivity(intent);

	}

}
