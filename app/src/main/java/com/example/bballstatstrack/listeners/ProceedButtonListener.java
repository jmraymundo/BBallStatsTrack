package com.example.bballstatstrack.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.bballstatstrack.activities.AddPlayersToTeamsActivity;
import com.example.bballstatstrack.activities.GameActivity;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.models.PlayerList;
import com.example.bballstatstrack.models.Team;

public class ProceedButtonListener implements OnClickListener {
    private Activity mActivity;

    private TeamFragment mHomeFragment;

    private TeamFragment mAwayFragment;

    private Team mHomeTeam;

    private Team mAwayTeam;

    public ProceedButtonListener(Activity activity, TeamFragment home, TeamFragment away) {
        mActivity = activity;
        mHomeFragment = home;
        mAwayFragment = away;
    }

    @Override
    public void onClick(View v) {
        mHomeTeam = mHomeFragment.getTeam();
        mAwayTeam = mAwayFragment.getTeam();
        if (isNamesInvalid(mHomeTeam, mAwayTeam)) {
            Toast.makeText(mActivity, "A team has an invalid name. Cannot proceed.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isTeamPlayersInvalid(mHomeTeam, mAwayTeam)) {
            Toast.makeText(mActivity, "A team has insufficient players. Cannot proceed.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mActivity, GameActivity.class);
        intent.putExtra(AddPlayersToTeamsActivity.HOME_TEAM, mHomeTeam);
        intent.putExtra(AddPlayersToTeamsActivity.AWAY_TEAM, mAwayTeam);
        mActivity.finish();
        mActivity.startActivity(intent);
    }

    private boolean isNamesInvalid(Team home, Team away) {
        String homeName = home.getName();
        String awayName = away.getName();
        return homeName == null || awayName == null || homeName.isEmpty() || awayName.isEmpty()
                || homeName.equals(awayName);
    }

    private boolean isTeamPlayersInvalid(Team team) {
        PlayerList pList = team.getPlayers();
        if (pList.getSize() < 5) {
            return true;
        }
        return false;
    }

    private boolean isTeamPlayersInvalid(Team home, Team away) {
        return isTeamPlayersInvalid(home) || isTeamPlayersInvalid(away);
    }
}