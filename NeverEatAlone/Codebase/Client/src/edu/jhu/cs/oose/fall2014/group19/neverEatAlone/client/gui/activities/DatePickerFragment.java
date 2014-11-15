package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
	OnDateSetListener OnDateSet;
	private int Year, Month, Day;

	public DatePickerFragment() {
	}

	public void setCallBack(OnDateSetListener ondate) {
		OnDateSet = ondate;
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		Year = args.getInt("year");
		Month = args.getInt("month");
		Day = args.getInt("day");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), OnDateSet, Year, Month, Day);
	}
}