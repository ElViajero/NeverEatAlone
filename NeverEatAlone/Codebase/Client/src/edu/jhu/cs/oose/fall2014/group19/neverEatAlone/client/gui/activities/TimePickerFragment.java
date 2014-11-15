package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
	OnTimeSetListener OnTimeSet;
	private int Hour, Minute;

	public TimePickerFragment() {
	}

	public void setCallBack(OnTimeSetListener ontime) {
		OnTimeSet = ontime;
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		Hour = args.getInt("hour");
		Minute = args.getInt("minute");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), OnTimeSet, Hour, Minute, true);
	}
}