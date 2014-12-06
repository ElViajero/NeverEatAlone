package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
/**
 * 
 * @author Hai Tang
 * MealDetailActivity class is used to set up the view of the Meal Details page.
 * And also set up the functions after clicking different buttons.
 */
public class MealDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);
		
		setTitleStyle();
	}
	
	/**
	 * This method is used to set the font style of the title of each page
	 * @author: Hai Tang
	 */
	private void setTitleStyle() {
		TextView tv =
				(TextView) findViewById(R.id.textView_mealdetails_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Windsong.ttf");
		tv.setTypeface(tf);
		tv.setTextSize(100);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meal_detail, menu);
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
	 * Method used for back button click
	 * @author: Hai Tang
	 */
	public void onBackButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Method used for accept button click
	 * @author: Hai Tang
	 */
	public void onAcceptButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Method used for decline button click
	 * @author: Hai Tang
	 */
	public void onDeclineButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
}
