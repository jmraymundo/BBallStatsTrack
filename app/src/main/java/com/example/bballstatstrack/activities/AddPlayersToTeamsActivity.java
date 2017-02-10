package com.example.bballstatstrack.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.dialogs.BackConfirmationDialog;
import com.example.bballstatstrack.fragments.TeamFragment;
import com.example.bballstatstrack.listeners.FragmentButtonListener;
import com.example.bballstatstrack.listeners.ProceedButtonListener;
import com.example.bballstatstrack.models.utils.ViewUtils;

public class AddPlayersToTeamsActivity extends Activity {
    public static final String HOME_TEAM = "HomeTeam";

    public static final String AWAY_TEAM = "AwayTeam";

    private TeamFragment mHomeTeamFragment;

    private TeamFragment mAwayTeamFragment;

    private Button mHomeButton;

    private Button mAwayButton;

    private Button mProceedButton;

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new BackConfirmationDialog(AddPlayersToTeamsActivity.this);
        dialog.show();
    }

    public void replaceCurrentFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public void updateView(Fragment current) {
        if (current.equals(mHomeTeamFragment)) {
            mHomeButton.setEnabled(false);
            mAwayButton.setEnabled(true);
        } else if (current.equals(mAwayTeamFragment)) {
            mHomeButton.setEnabled(true);
            mAwayButton.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        View view = (View) findViewById(R.id.newTeamContainer);
        ViewUtils.setupKeyboardHiding(AddPlayersToTeamsActivity.this, view);
        mHomeTeamFragment = new TeamFragment(R.string.home_team);
        mAwayTeamFragment = new TeamFragment(R.string.away_team);
        mHomeButton = (Button) findViewById(R.id.homeTeamButton);
        mHomeButton
                .setOnClickListener(new FragmentButtonListener(AddPlayersToTeamsActivity.this, mHomeTeamFragment));
        mAwayButton = (Button) findViewById(R.id.awayTeamButton);
        mAwayButton
                .setOnClickListener(new FragmentButtonListener(AddPlayersToTeamsActivity.this, mAwayTeamFragment));
        mProceedButton = (Button) findViewById(R.id.proceedButton);
        mProceedButton.setOnClickListener(
                new ProceedButtonListener(AddPlayersToTeamsActivity.this, mHomeTeamFragment, mAwayTeamFragment));
        replaceCurrentFragment(mHomeTeamFragment);
        updateView(mHomeTeamFragment);
    }
}