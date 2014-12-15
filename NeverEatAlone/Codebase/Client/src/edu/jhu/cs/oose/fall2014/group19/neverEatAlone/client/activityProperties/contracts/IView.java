package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.contracts;

import android.view.View;

/**
 * This is the interface class used to get/set the values/views for View Components in the app.
 * @author Hai Tang
 *
 */
public interface IView {
	
	//Hai's comment: may need to redesin as getEditTextView, getButtonView etc.
	public View getView(String viewName);
	public void setView(View view);
	public String getValue(View view);
	public void setValue(View view, String value);
	
}
