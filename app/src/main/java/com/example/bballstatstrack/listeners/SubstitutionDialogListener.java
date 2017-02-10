package com.example.bballstatstrack.listeners;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.SubstitutionOutDialog;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

public class SubstitutionDialogListener implements OnClickListener {
    private GameActivity mActivity;

    private AlertDialog mDialog;

    private Team mTeam;

    public SubstitutionDialogListener(GameActivity activity, AlertDialog dialog, Team team) {
        mActivity = activity;
        mDialog = dialog;
        mTeam = team;
    }

    @Override
    public void onClick(View v) {
        PlayerList inGame = mTeam.getPlayers();
        if (inGame.getSize() > 5) {
            showSubstitutionOutDialog(mTeam);
        } else {
            Toast.makeText(mActivity, mActivity.getString(R.string.substitution_toast_not_enough_players),
                    Toast.LENGTH_SHORT).show();
        }
        mDialog.dismiss();
    }

    private void showSubstitutionOutDialog(Team team) {
        AlertDialog dialog = new SubstitutionOutDialog(mActivity, team);
        dialog.show();
    }

}
