package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views;

import javax.swing.text.AbstractDocument.Content;

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
	
	private EditText usernameEditTextObject = null;
	
	Context context;
	Activity activity;
	
	/*
	 * The constructor is used to get the content from the MainActivity
	 * in order to use getResources() and getPackageName()
	 * @author Hai Tang
	 */
	public LoginView(Context context, Activity activity){
		this.context = context;
		this.activity = activity;
	}
	
	
	@Override
	public View getView(String viewName) {
		
		//Hai's comments: this piece of codes works in MainActivity, but not here.
		//Hai's comments: OK, this one is working as well!
		int resID = context.getResources().getIdentifier(viewName,
			    "id", context.getPackageName());		
		usernameEditTextObject = (EditText) activity.findViewById(resID);
		
		//Hai's comments:findViewById not working outside Activity.
		//Hai's comments: OK, have made this one working!
//		usernameEditTextObject = (EditText) activity.findViewById(R.id.edit_username);
//		
		
		return usernameEditTextObject;
//		return null;
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue(View view) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String string) {
		// TODO Auto-generated method stub
		
	}

	

}
