package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * This class handles the GUI themes.
 * 
 * @author tejasvamsingh
 * @author Yueling Loh
 */
public class ThemeManager {

	// static int mainTheme = R.style.MainLayoutTheme;
	// static int headerTheme = R.style.HeaderTheme;

	static int mainTheme = R.drawable.layout_background_light;
	static int headerTheme = R.drawable.header_background_light;
	static int buttonBarTheme = R.drawable.buttonbar_background_light;
	static int tabTheme = R.drawable.tab_background_light;
	static int buttonColor = R.drawable.button_background_light;
	static int editTextColor = R.drawable.edittext_background_light;
	static int availabilityBGColor = R.drawable.availability_background_light;
	static int availabilityThumbColor = R.drawable.availability_thumb_light;
	
	/**
	 * LEGACY: TO BE DELETED
	 * 
	 */
	public static void applyTheme(View mainView) {

		if (mainView == null) {
			System.out.println("acitivity is null");
		}

		mainView.setBackgroundResource(mainTheme);
	}



	/**
	 * This changes the background colors of the header and the main layout.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyTheme(View mainView, View headerView) {

		if ((mainView == null) || (headerView == null)) {
			System.out.println("acitivity is null");
		}
		mainView.setBackgroundResource(mainTheme);
		headerView.setBackgroundResource(headerTheme);

	}

	/**
	 * This changes the background colors of the button bar.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyButtonBarTheme(View buttonBarView) {

		if (buttonBarView == null) {
			System.out.println("acitivity is null");
		}

		buttonBarView.setBackgroundResource(buttonBarTheme);

	}

	public static void applyTabTheme(View tabView) {

		if (tabView == null) {
			System.out.println("acitivity is null");
		}
		tabView.setBackgroundResource(tabTheme);

	}

	
	/**
	 * This changes the button colors.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyButtonColor(View buttonView) {

		if (buttonView == null) {
			System.out.println("acitivity is null");
		}

		buttonView.setBackgroundResource(buttonColor);

	}
	
	public static void applyAvailabilityColor(Switch switchView) {

		if (switchView == null) {
			System.out.println("acitivity is null");
		}

		switchView.setBackgroundResource(availabilityBGColor);
		switchView.setThumbResource(availabilityThumbColor);

	}

	/**
	 * This changes the color of the EditText fields.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyEditTextColor(View editTextView) {

		if (editTextView == null) {
			System.out.println("acitivity is null");
		}

		editTextView.setBackgroundResource(editTextColor);

	}

	/**
	 * This changes the Header font.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void setHeaderFont(TextView tv) {

		if (tv == null) {
			System.out.println("acitivity is null");
		}

		Typeface tf = Typeface.createFromAsset(tv.getContext().getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextColor(Color.parseColor("#FFFFFF"));
		tv.setTextSize(80);

	}
	
	/**
	 * This changes the Tab font.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void setTabFont(TextView tv) {

		if (tv == null) {
			System.out.println("acitivity is null");
		}

		Typeface tf = Typeface.createFromAsset(tv.getContext().getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(20);

	}
	
	/**
	 * This sets the theme.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void setTheme(int mainThemeType, int headerThemeType) {
		mainTheme = //mainThemeType;
				headerTheme = headerThemeType;
	}

}
