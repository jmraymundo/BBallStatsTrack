package com.example.bballstatstrack.listeners.cancel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.dialogs.SubstitutionOutDialog;
import com.example.bballstatstrack.models.Team;

public class SubstitutionInDialogOnCancelListener implements OnCancelListener {
    private Team mTeam;

    private GameActivity mActivity;

    public SubstitutionInDialogOnCancelListener(GameActivity activity, Team team) {
        mActivity = activity;
        mTeam = team;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
        AlertDialog returnDialog = new SubstitutionOutDialog(mActivity, mTeam);
        returnDialog.show();
    }
}
