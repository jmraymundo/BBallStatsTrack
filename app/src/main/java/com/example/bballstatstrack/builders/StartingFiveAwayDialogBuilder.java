package com.example.bballstatstrack.builders;

import android.app.AlertDialog;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.TeamAwayStartersConfirmationListener;
import com.example.bballstatstrack.models.Team;

public class StartingFiveAwayDialogBuilder extends StartingFiveDialogBuilder {
    public StartingFiveAwayDialogBuilder(GameActivity activity, Team team) {
        super(activity, team);
    }

    @Override
    public AlertDialog create() {
        AlertDialog dialog = super.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new TeamAwayStartersConfirmationListener(mActivity, mTeam, dialog));
        return dialog;
    }
}
