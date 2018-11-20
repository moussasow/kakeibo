package com.mas.kakeibo.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.mas.kakeibo.R;
import com.mas.kakeibo.events.DatePickerEvent;

import java.util.Calendar;

/**
 * Created by sow.m on 2018/11/16.
 */
public class ProductDatePicker extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerEvent mDatePickerEvent;

    public ProductDatePicker() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),
                this,
                year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        if (mDatePickerEvent == null) {
            return;
        }

        final String date = String.format(getString(R.string.fragment_input_date_format), year, month, dayOfMonth);
        mDatePickerEvent.onDatePicked(date);
    }

    public void createDateEvent(DatePickerEvent event) {
        mDatePickerEvent = event;
    }
}
