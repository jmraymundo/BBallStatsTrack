package com.example.bballstatstrack.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.GameMainStatsFragment;
import com.example.bballstatstrack.fragments.TeamInGameFragment;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.utils.PlayerNumberWatcher;

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

    private int mGameClock = -1;

    private int mShotClockReset = -1;

    private GameMainStatsFragment mStatsFragment;

    private TeamInGameFragment mHomeInGameFragment;

    private TeamInGameFragment mAwayInGameFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );
        setGameClock();
    }

    private void setGameClock()
    {
        final AlertDialog dialog = buildDialog( R.string.max_game_clock );
        dialog.show();
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                mGameClock = setClockFromDialog( dialog, 12 );
                if( mGameClock == -1 )
                {
                    return;
                }
                setShotClockReset();
            }
        } );
    }

    private void setShotClockReset()
    {
        final AlertDialog dialog = buildDialog( R.string.reset_shot_clock );
        dialog.show();
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                mShotClockReset = setClockFromDialog( dialog, 24 );
                if( mShotClockReset == -1 )
                {
                    return;
                }
                startNewGame();
            }
        } );
    }

    private AlertDialog buildDialog( int detailedTextID )
    {
        Builder builder = new AlertDialog.Builder( GameActivity.this );
        View dialogView = getLayoutInflater().inflate( R.layout.dialog_get_clock, null );
        builder.setView( dialogView );
        builder.setTitle( R.string.set_time_title );
        TextView detailedQuestionView = ( TextView ) dialogView.findViewById( R.id.requested_clock_text );
        detailedQuestionView.setText( detailedTextID );
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

    private int setClockFromDialog( DialogInterface dialog, int max )
    {
        AlertDialog alertDialog = ( AlertDialog ) dialog;
        EditText clockField = ( EditText ) alertDialog.findViewById( R.id.requested_clock_field );
        clockField.addTextChangedListener( new PlayerNumberWatcher( 0, max ) );
        String clockValue = clockField.getText().toString();
        if( clockValue.isEmpty() )
        {
            Toast.makeText( GameActivity.this, getResources().getString( R.string.clock_input_error ),
                    Toast.LENGTH_SHORT ).show();
            return -1;
        }
        int number = Integer.parseInt( clockValue );
        dialog.dismiss();
        return number;
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
