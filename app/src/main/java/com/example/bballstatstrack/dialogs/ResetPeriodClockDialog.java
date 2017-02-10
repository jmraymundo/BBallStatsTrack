package com.example.bballstatstrack.dialogs;

import android.app.Dialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.listeners.ResetPeriodClockListener;

public class ResetPeriodClockDialog extends YesNoDialog {
    public ResetPeriodClockDialog(GameActivity activity) {
        super(activity, R.string.reset_period_clock_confirmation_question);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(R.string.yes),
                new ResetPeriodClockListener(activity.getGame()));
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(R.string.no), new DismissDialogListener());
    }
}
