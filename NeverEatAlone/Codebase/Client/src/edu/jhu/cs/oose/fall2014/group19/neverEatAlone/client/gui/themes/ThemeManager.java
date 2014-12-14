package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * This class handles the GUI themes.
 * 
 * @author tejasvamsingh
 * @author Yueling Loh
 */
public class ThemeManager {

	static int mainTheme = R.style.MainLayoutTheme;
	static int headerTheme = R.style.HeaderTheme;

	public static void applyTheme(View mainView) {

		if (mainView == null) {
			System.out.println("acitivity is null");
		}

		mainView.setBackgroundResource(R.drawable.layout_background_light);

	}

	public static void applyTheme(View mainView, View headerView) {

		if ((mainView == null) || (headerView == null)) {
			System.out.println("acitivity is null");
		}

		mainView.setBackgroundResource(R.drawable.layout_background_light);

		headerView.setBackgroundResource(R.drawable.header_background_light);

		/*
		 * for(int i=0;i<viewGroup.getChildCount();i++){
		 * viewGroup.getChildAt(i).
		 * setBackgroundResource(R.drawable.dark_layout_background); }
		 */
	}

	public static void applyButtonTheme(View buttonView) {

		if (buttonView == null) {
			System.out.println("acitivity is null");
		}

		buttonView.setBackgroundResource(R.drawable.buttonbar_background_light);

		/*
		 * for(int i=0;i<viewGroup.getChildCount();i++){
		 * viewGroup.getChildAt(i).
		 * setBackgroundResource(R.drawable.dark_layout_background); }
		 */
	}

	public static void setHeaderFont(TextView tv) {

		if (tv == null) {
			System.out.println("acitivity is null");
		}

		Typeface tf = Typeface.createFromAsset(tv.getContext().getAssets(),
				"fonts/Chunkfive.otf");
		tv.setTypeface(tf);
		tv.setTextSize(80);

	}

	public static void setTheme(int mainThemeType, int headerThemeType) {
		mainTheme = mainThemeType;
		headerTheme = headerThemeType;
	}

}
