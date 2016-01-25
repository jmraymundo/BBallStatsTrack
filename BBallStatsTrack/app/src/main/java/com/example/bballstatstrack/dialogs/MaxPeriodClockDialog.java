package com.example.bballstatstrack.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.MaxPeriodClockDialogListener;

public class MaxPeriodClockDialog extends ClockDialog {
    public MaxPeriodClockDialog(GameActivity activity) {
        super(activity, R.string.set_max_period_clock_title, 12);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(
                new MaxPeriodClockDialogListener(mActivity, MaxPeriodClockDialog.this, mClockField));
    }
}
