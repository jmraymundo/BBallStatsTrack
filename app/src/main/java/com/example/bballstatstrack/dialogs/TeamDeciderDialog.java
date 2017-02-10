package com.example.bballstatstrack.dialogs;

import android.app.AlertDialog;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.models.Team;

public class TeamDeciderDialog extends AlertDialog {
    protected Team mHomeTeam;

    protected Team mAwayTeam;

    public TeamDeciderDialog(GameActivity activity) {
        super(activity);
        mHomeTeam = activity.getGame().getHomeTeam();
        mAwayTeam = activity.getGame().getAwayTeam();
        setTitle(getContext().getText(R.string.ball_possession_question));
        setCancelable(false);
    }
}
