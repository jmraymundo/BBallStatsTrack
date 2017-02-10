package com.example.bballstatstrack.dialogs;

import android.app.Dialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.DismissDialogListener;
import com.example.bballstatstrack.listeners.ResetShotClockListener;

public class ResetShotClockDialog extends YesNoDialog {

    public ResetShotClockDialog(GameActivity activity) {
        super(activity, R.string.reset_period_clock_confirmation_question);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(R.string.yes),
                new ResetShotClockListener(activity.getGame()));
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(R.string.no), new DismissDialogListener());
    }
}