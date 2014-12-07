package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 
 * This class construct a time picker.
 * @author Runze Tang
 * 
 */
public class TimePickerFragment extends DialogFragment {
	OnTimeSetListener onTimeSet;
	private int hour, minute;

	public TimePickerFragment() {
	}

	public void setCallBack(OnTimeSetListener ontime) {
		onTimeSet = ontime;
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		hour = args.getInt("hour");
		minute = args.getInt("minute");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), onTimeSet, hour, minute, true);
	}
}