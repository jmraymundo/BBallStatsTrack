package com.example.bballstatstrack.dialogs;

import android.app.Dialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.AssistCheckDialogListener;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class AssistCheckDialog extends YesNoDialog {
    public AssistCheckDialog(GameActivity activity, ShootEvent shootEvent, ShootingFoulEvent shootingFoulEvent) {
        super(activity, R.string.shoot_dialog_assist_check_question);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(R.string.yes),
                new AssistCheckDialogListener(activity, shootEvent, shootingFoulEvent, true));
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(R.string.no),
                new AssistCheckDialogListener(activity, shootEvent, shootingFoulEvent, false));
        setCancelable(false);
    }
}
