package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.services;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
/**
 * 
 * This class construct a date picker.
 * @author Runze Tang
 * 
 */
public class DatePickerFragment extends DialogFragment {
	OnDateSetListener onDateSet;
	private int year, month, day;

	public DatePickerFragment() {
	}

	public void setCallBack(OnDateSetListener ondate) {
		onDateSet = ondate;
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		year = args.getInt("year");
		month = args.getInt("month");
		day = args.getInt("day");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
	}
}