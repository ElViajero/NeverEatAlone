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

	static int frontTheme = R.drawable.layout_background_light_outer;
	static int headerTheme = R.drawable.header_background_light;
	static int mainTheme = R.drawable.layout_background_light;
	static int mainThemePlain = R.drawable.plain_layout_background_light;
	static int mainThemePlainSides = R.drawable.plain_layout_background_light_sides;
	static int buttonBarTheme = R.drawable.buttonbar_background_light;
	static int buttonBarThemePlain = R.drawable.plain_buttonbar_background_light;
	static int popUpTheme = R.drawable.popupwindow_background_light;
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
	 * This changes the background color.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyBackground(View mainView) {

		if (mainView == null) {
			System.out.println("acitivity is null");
		}

		mainView.setBackgroundResource(frontTheme);
	}

	/**
	 * This changes the background colors of the header and the main layout.
	 * Assumes the button bar is at the top.
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
	 * This changes the background colors of the header, the main layout and the button bar.
	 * The button bar is at the bottom.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyPlainTheme(View mainView, View headerView,View buttonBarView) {

		if ((mainView == null) || (headerView == null)|| (buttonBarView == null)) {
			System.out.println("acitivity is null");
		}
		mainView.setBackgroundResource(mainThemePlain);
		headerView.setBackgroundResource(headerTheme);
		buttonBarView.setBackgroundResource(buttonBarThemePlain);

	}
	
	/**
	 * This changes the background colors of the header, the main layout and the button bar.
	 * There is a button bar at the top and the bottom.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyDoubleBarTheme(View mainView, View headerView,View buttonBarViewTop, View buttonBarViewBottom) {

		if ((mainView == null) || (headerView == null)|| (buttonBarViewTop == null)|| (buttonBarViewBottom == null)) {
			System.out.println("acitivity is null");
		}
		headerView.setBackgroundResource(headerTheme);
		buttonBarViewTop.setBackgroundResource(buttonBarTheme);
		mainView.setBackgroundResource(mainThemePlainSides);
		buttonBarViewBottom.setBackgroundResource(buttonBarThemePlain);

	}

	/**
	 * This changes the background colors of a pop up.
	 * There is a button bar at the bottom.
	 * 
	 * @author Yueling Loh
	 * 
	 */
	public static void applyPopUpTheme(View mainView, View buttonBarViewBottom) {

		if ((mainView == null) || (buttonBarViewBottom == null)) {
			System.out.println("acitivity is null");
		}
		
		mainView.setBackgroundResource(popUpTheme);
		buttonBarViewBottom.setBackgroundResource(buttonBarThemePlain);

	}
	
	/**
	 * This changes the background colors of the button bar at the top.
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
		tv.setTextColor(Color.parseColor("#00B9FF"));

	}
	
	public static void setTitleFont(TextView tv) {

		if (tv == null) {
			System.out.println("acitivity is null");
		}

		Typeface tf = Typeface.createFromAsset(tv.getContext().getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);

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
