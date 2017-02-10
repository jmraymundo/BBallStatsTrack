package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Team;

public class TeamHomeStartersConfirmationListener extends TeamStartersConfirmationListener {
    public TeamHomeStartersConfirmationListener(GameActivity activity, Team team, AlertDialog dialog) {
        super(activity, team, dialog);
    }

    @Override
    public void onClick(View v) {
        if (mTeam.isStartersInvalid()) {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.need_five_players),
                    Toast.LENGTH_SHORT).show();
        } else {
            mActivity.fetchAwayStarters();
            mDialog.dismiss();
        }
    }
}
