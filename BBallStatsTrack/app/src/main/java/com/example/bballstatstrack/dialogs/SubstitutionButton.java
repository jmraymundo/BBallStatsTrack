package com.example.bballstatstrack.dialogs;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.SubstitutionDialogListener;
import com.example.bballstatstrack.listeners.cancel.SubstitutionButtonOnCancelListener;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Team;

public class SubstitutionButton extends AlertDialog {
    public SubstitutionButton(GameActivity activity) {
        super(activity);
        View view = View.inflate(getContext(), R.layout.dialog_substitution_button, null);
        setView(view);
        setOnCancelListener(new SubstitutionButtonOnCancelListener(activity));

        Game game = activity.getGame();

        Button homeTeamButton = (Button) view.findViewById(R.id.substitution_dialog_home_button);
        Button awayTeamButton = (Button) view.findViewById(R.id.substitution_dialog_away_button);

        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        homeTeamButton.setText(homeTeam.getName());
        awayTeamButton.setText(awayTeam.getName());

        homeTeamButton
                .setOnClickListener(new SubstitutionDialogListener(activity, SubstitutionButton.this, homeTeam));
        awayTeamButton
                .setOnClickListener(new SubstitutionDialogListener(activity, SubstitutionButton.this, awayTeam));

        activity.startEvent();
    }
}