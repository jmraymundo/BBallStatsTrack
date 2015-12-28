package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity
{
	public static final String EXTRA_GAME_ID = "gameID";

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		Intent intent = getIntent();
		int[] homeNumbers = intent.getIntArrayExtra( AddPlayersToTeamsActivity.HOME_TEAM_NUMBERS );
		String[] homeNames = intent.getStringArrayExtra( AddPlayersToTeamsActivity.HOME_TEAM_NAMES );
		int[] awayNumbers = intent.getIntArrayExtra( AddPlayersToTeamsActivity.AWAY_TEAM_NUMBERS );
		String[] awayNames = intent.getStringArrayExtra( AddPlayersToTeamsActivity.AWAY_TEAM_NAMES );
		setContentView( R.layout.activity_game );
	}
}
