package com.example.bballstatstrack.dialogs;

import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.listeners.ReboundEventListener;
import com.example.bballstatstrack.listeners.cancel.ReboundEventOnCancelListener;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class ReboundEventDialog extends MultipleButtonsDialog {
    public ReboundEventDialog(GameActivity activity, ShootEvent shootEvent, Team team) {
        super(activity, R.string.rebound_dialog_player_question);
        setOnCancelListener(new ReboundEventOnCancelListener(activity, shootEvent));

        ReboundType type = getReboundType(activity, team);
        setPlayerListeners(activity, shootEvent, team, type);

        Button teamRebound = new Button(activity);
        teamRebound.setText(R.string.rebound_dialog_team_rebound);
        teamRebound.setTag(shootEvent);
        ReboundEvent reboundEvent = new ReboundEvent(type, null, team);
        teamRebound.setOnClickListener(new ReboundEventListener(activity, ReboundEventDialog.this, reboundEvent));
        mDialogView.addView(teamRebound);
    }

    private ReboundType getReboundType(GameActivity activity, Team team) {
        if (activity.getGame().getTeamWithPossession().equals(team)) {
            return ReboundType.OFFENSIVE;
        }
        return ReboundType.DEFENSIVE;
    }

    private void setPlayerListeners(GameActivity activity, ShootEvent shootEvent, Team team, ReboundType type) {
        for (Player player : team.getInGamePlayers()) {
            Button button = new Button(activity);
            button.setText(player.toString());
            button.setTag(shootEvent);
            ReboundEvent reboundEvent = new ReboundEvent(type, player, team);
            button.setOnClickListener(new ReboundEventListener(activity, ReboundEventDialog.this, reboundEvent));
            mDialogView.addView(button);
        }
    }
}
