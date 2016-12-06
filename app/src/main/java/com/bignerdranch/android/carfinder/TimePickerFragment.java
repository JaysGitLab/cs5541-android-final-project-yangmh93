package com.bignerdranch.android.carfinder;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Michael on 12/5/2016.
 */
public class TimePickerFragment extends PickerDialogFragment {
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = getArgs(date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    protected View initLayout() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        } else {
            mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }
        return v;
    }

    protected Date getDate() {
        //TimePicker only sets the time. The date remains the same
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        //Get the time from the timepicker
        int hour;
        int minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = mTimePicker.getHour();
            minute = mTimePicker.getMinute();
        } else {
            hour = mTimePicker.getCurrentHour();
            minute = mTimePicker.getCurrentMinute();
        }

        return new GregorianCalendar(year, month, day, hour, minute).getTime();
    }
}

