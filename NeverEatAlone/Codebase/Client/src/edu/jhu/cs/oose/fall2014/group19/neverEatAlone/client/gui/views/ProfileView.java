package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IView;

/**
 * This class handles all the view components for the Profile related Page.
 * @author Hai Tang
 *
 */
public class ProfileView implements IView{
	
	private View viewObject = null;
	private String value = null;
	
	Context context;
	Activity activity;
	
	/**
	 * The constructor is used to get the content from the ProfileActivity
	 * in order to use getResources() and getPackageName()
	 * @author Hai Tang
	 */
	public ProfileView(Context context, Activity activity){
		this.context = context;
		this.activity = activity;
	}

	/**
	 * Overriden method used to get the view of different ViewObject.
	 * @author Hai Tang
	 */
	@Override
	public View getView(String viewName) {
		
		int resourceID = context.getResources().getIdentifier(viewName,
			    "id", context.getPackageName());		
		viewObject = activity.findViewById(resourceID);

		return viewObject;
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue(View view) {
		value = ((EditText) view).getText().toString();
		return value;
	}

	/**
	 * Overriden method used to set the value of different ViewObject.
	 * @author Hai Tang
	 */
	@Override
	public void setValue(View view, String value) {
		((TextView) view).setText(value);
		
	}

	
}
