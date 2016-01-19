package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.models.Team;

import android.app.Activity;
import android.os.Bundle;

public class DetailedTeamReviewActivity extends Activity
{
    public static final String TEAM_ID = "TeamID";

    private Team mTeam;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        mTeam = getTeam();
    }

    private Team getTeam()
    {
        return ( Team ) getIntent().getParcelableExtra( TEAM_ID );
    }
}
