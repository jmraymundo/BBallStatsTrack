package com.example.bballstatstrack.dialogs;

import android.app.Dialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.ShootEventListener;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

public class ShotTypeDialog extends YesNoDialog {
    public ShotTypeDialog(GameActivity activity, ShootEvent shootEvent, ShootingFoulEvent shootingFoulEvent) {
        super(activity, R.string.shoot_dialog_type_question);
        setCancelable(false);
        setButton(Dialog.BUTTON_POSITIVE, getContext().getText(R.string.yes),
                new ShootEventListener(activity, shootEvent, true, shootingFoulEvent));
        setButton(Dialog.BUTTON_NEGATIVE, getContext().getText(R.string.no),
                new ShootEventListener(activity, shootEvent, false, shootingFoulEvent));
    }
}
