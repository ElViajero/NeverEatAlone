package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

	public void OnBackButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
	
	public void OnAcceptButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
	
	public void OnDeclineButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
}
