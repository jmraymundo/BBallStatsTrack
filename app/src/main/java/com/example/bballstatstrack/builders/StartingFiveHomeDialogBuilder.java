package com.example.bballstatstrack.builders;

import android.app.AlertDialog;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TeamHomeStartersConfirmationListener;
import com.example.bballstatstrack.models.Team;

public class StartingFiveHomeDialogBuilder extends StartingFiveDialogBuilder {
    public StartingFiveHomeDialogBuilder(GameActivity activity, Team team) {
        super(activity, team);
    }

    @Override
    public AlertDialog create() {
        AlertDialog dialog = super.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new TeamHomeStartersConfirmationListener(mActivity, mTeam, dialog));
        return dialog;
    }
}
