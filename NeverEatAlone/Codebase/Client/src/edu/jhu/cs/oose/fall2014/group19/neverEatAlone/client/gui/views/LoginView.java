package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts.IView;


/**
 * This class handles all the view components for the Login Page.
 * @author Hai Tang
 *
 */
public class LoginView implements IView{
	
	private View viewObject = null;
	private String value = null;
	
	Context context;
	Activity activity;
	
	/**
	 * The constructor is used to get the content from the MainActivity
	 * in order to use getResources() and getPackageName()
	 * @author Hai Tang
	 */
	public LoginView(Context context, Activity activity){
		this.context = context;
		this.activity = activity;
	}
	
	/**
	 * Overriden method used to get the view of different ViewObject.
	 * @author Hai Tang
	 */
	@Override
	public View getView(String viewName) {
		
		//Hai's comments: this piece of codes works in MainActivity, but not here.
		//Hai's comments: OK, this one is working as well!
		int resourceID = context.getResources().getIdentifier(viewName,
			    "id", context.getPackageName());		
		viewObject = activity.findViewById(resourceID);
		
		//Hai's comments:findViewById not working outside Activity.
		//Hai's comments: OK, have made this one working!
//		usernameEditTextObject = (EditText) activity.findViewById(R.id.edit_username);
//		
		
		return viewObject;
//		return null;
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Overriden method used to get value from ViewObject
	 * @author Hai Tang
	 */
	@Override
	public String getValue(View view) {
		value = ((EditText) view).getText().toString();
		return value;
	}

	@Override
	public void setValue(View view, String value) {
		// TODO Auto-generated method stub
		
	}

	

}
