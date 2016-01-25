package com.example.bballstatstrack.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.MaxShotClockResetDialogListener;

public class MaxShotClockResetDialog extends ClockDialog {

    public MaxShotClockResetDialog(GameActivity activity) {
        super(activity, R.string.set_reset_shot_clock_title, 24);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(
                new MaxShotClockResetDialogListener(mActivity, MaxShotClockResetDialog.this, mClockField));
    }
}
