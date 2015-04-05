package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.contracts.IView;

/**
 * This class handles all the view components for the Contacts related Page.
 * 
 * @author Hai Tang
 *
 */
public class ContactsView implements IView {

	private View viewObject = null;
	private String value = null;

	Context context;
	Activity activity;

	/**
	 * The constructor is used to get the content from the ContactsActivity in
	 * order to use getResources() and getPackageName()
	 * 
	 * @author Hai Tang
	 */
	public ContactsView(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
	}

	/**
	 * Overriden method used to get the view of different ViewObject.
	 * 
	 * @author Hai Tang
	 */
	@Override
	public View getView(String viewName) {

		int resourceID = context.getResources().getIdentifier(viewName, "id",
				context.getPackageName());
		viewObject = activity.findViewById(resourceID);

		return viewObject;
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub

	}

	/**
	 * Overriden method used to get value from ViewObject
	 * 
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

	@Override
	public View GetDynamicView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewGroup GetDynamicLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
