package com.example.bballstatstrack.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity
{
    public static final String EXTRA_GAME_ID = "gameID";

    private Game mGame;

    private int mGameClock;

    private int mShotClockReset;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );
        setGameClock();
    }

    private void setGameClock()
    {
        final AlertDialog dialog = buildDialog();
        TextView detailedQuestionView = ( TextView ) dialog.findViewById( R.id.requested_clock_text );
        detailedQuestionView.setText( R.string.max_game_clock );
        dialog.show();
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                setClockFromDialog( dialog, mGameClock );
                setShotClockReset();
            }
        } );
    }

    private void setShotClockReset()
    {
        final AlertDialog dialog = buildDialog();
        TextView detailedQuestionView = ( TextView ) dialog.findViewById( R.id.requested_clock_text );
        detailedQuestionView.setText( R.string.reset_shot_clock );
        dialog.show();
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                setClockFromDialog( dialog, mShotClockReset );
                startNewGame();
            }
        } );
    }

    private AlertDialog buildDialog()
    {
        Builder builder = new AlertDialog.Builder( GameActivity.this );
        View dialogView = getLayoutInflater().inflate( R.layout.dialog_get_clock, null );
        builder.setView( dialogView );
        builder.setTitle( R.string.set_time_title );
        builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // Will be overridden
            }
        } );
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
        dialog.setCanceledOnTouchOutside( false );
        return dialog;
    }

    public void startNewGame()
    {
        Team homeTeam = getTeam( AddPlayersToTeamsActivity.HOME_TEAM_NAME, AddPlayersToTeamsActivity.HOME_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.HOME_TEAM_MEMBER_NAMES );
        Team awayTeam = getTeam( AddPlayersToTeamsActivity.AWAY_TEAM_NAME, AddPlayersToTeamsActivity.AWAY_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.AWAY_TEAM_MEMBER_NAMES );
        mGame = new Game( mGameClock, 5, mShotClockReset, homeTeam, awayTeam );
    }

    private void setClockFromDialog( DialogInterface dialog, int clock )
    {
        AlertDialog alertDialog = ( AlertDialog ) dialog;
        EditText playerNumberText = ( EditText ) alertDialog.findViewById( R.id.requested_clock_field );
        String playerNumberString = playerNumberText.getText().toString();
        if( playerNumberString.isEmpty() )
        {
            Toast.makeText( GameActivity.this, getResources().getString( R.string.clock_input_error ),
                    Toast.LENGTH_SHORT ).show();
            return;
        }
        clock = Integer.parseInt( playerNumberString );
        dialog.dismiss();
    }

    public Team getTeam( String teamNameExtra, String teamNumbersExtra, String teamPlayersExtra )
    {
        Intent intent = getIntent();
        String homeName = intent.getStringExtra( teamNameExtra );
        int[] homePlayerNumbers = intent.getIntArrayExtra( teamNumbersExtra );
        String[] homePlayerNames = intent.getStringArrayExtra( teamPlayersExtra );
        List< Player > homePlayerList = new ArrayList< Player >();
        for( int index = 0; index < homePlayerNumbers.length; index++ )
        {
            Player player = new Player( homePlayerNumbers[index], homePlayerNames[index] );
            homePlayerList.add( player );
        }
        return new Team( homeName, homePlayerList );
    }
}
