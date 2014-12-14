package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes;

import android.view.View;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

public class ThemeManager {

	static int theme = R.style.MainLayoutTheme;

	public static void applyTheme(View view){
		if(view==null){
			System.out.println("acitivity is null");
		}

		view.setBackgroundResource(R.drawable.dark_layout_background);


		/*
		for(int i=0;i<viewGroup.getChildCount();i++){
			viewGroup.getChildAt(i).setBackgroundResource(R.drawable.dark_layout_background);
		}*/
	}

	public static void setTheme(int themeType){
		theme=themeType;
	}



}
