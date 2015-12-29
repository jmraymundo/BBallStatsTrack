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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
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

    private GameMainStatsFragment mStatsFragment = new GameMainStatsFragment();

    private TeamInGameFragment mHomeInGameFragment;

    private TeamInGameFragment mAwayInGameFragment;

    private Team mHomeTeam;

    private Team mAwayTeam;

    public void getAwayStarters()
    {
        mAwayTeam = getTeam( AddPlayersToTeamsActivity.AWAY_TEAM_NAME, AddPlayersToTeamsActivity.AWAY_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.AWAY_TEAM_MEMBER_NAMES );
        selectStartingFiveAway();
    }

    public void getHomeStarters()
    {
        mHomeTeam = getTeam( AddPlayersToTeamsActivity.HOME_TEAM_NAME, AddPlayersToTeamsActivity.HOME_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.HOME_TEAM_MEMBER_NAMES );
        selectStartingFiveHome();
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

    public void selectStartingFiveAway()
    {
        Builder builder = new Builder( GameActivity.this );
        String title = getResources().getString( R.string.select_starters ) + " ";
        builder.setTitle( title.concat( mAwayTeam.getName() ) );
        final SparseArray< Player > players = mAwayTeam.getPlayers();
        int size = players.size();
        String[] playersArray = new String[size];
        for( int i = 0; i < size; i++ )
        {
            playersArray[i] = players.valueAt( i ).toString();
        }
        final List< Integer > selected = new ArrayList< Integer >();
        builder.setMultiChoiceItems( playersArray, null, new OnMultiChoiceClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which, boolean isChecked )
            {
                if( isChecked )
                {
                    selected.add( Integer.valueOf( players.keyAt( which ) ) );
                }
                else
                {
                    selected.remove( Integer.valueOf( players.keyAt( which ) ) );
                }
            }
        } );
        builder.setPositiveButton( getResources().getString( R.string.proceed ), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // Will be overridden
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton( AlertDialog.BUTTON_POSITIVE )
                .setOnClickListener( new TeamStartersConfirmationListener( mAwayTeam, dialog, selected ) );
    }

    public void selectStartingFiveHome()
    {
        Builder builder = new Builder( GameActivity.this );
        String title = getResources().getString( R.string.select_starters ) + " ";
        builder.setTitle( title.concat( mHomeTeam.getName() ) );
        final SparseArray< Player > players = mHomeTeam.getPlayers();
        int size = players.size();
        String[] playersArray = new String[size];
        for( int i = 0; i < size; i++ )
        {
            playersArray[i] = players.valueAt( i ).toString();
        }
        final List< Integer > selected = new ArrayList< Integer >();
        builder.setMultiChoiceItems( playersArray, null, new OnMultiChoiceClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which, boolean isChecked )
            {
                if( isChecked )
                {
                    selected.add( Integer.valueOf( players.keyAt( which ) ) );
                }
                else
                {
                    selected.remove( Integer.valueOf( players.keyAt( which ) ) );
                }
            }
        } );
        builder.setPositiveButton( getResources().getString( R.string.proceed ), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // Will be overridden
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton( AlertDialog.BUTTON_POSITIVE )
                .setOnClickListener( new TeamStartersConfirmationListener( mHomeTeam, dialog, selected ) );
    }

    public void setStarters( Team team, List< Integer > selected )
    {
        for( Integer number : selected )
        {
            team.addStarter( number.intValue() );
        }
    }

    public void startNewGame()
    {
        mGame = new Game( mGameClock, 5, mShotClockReset, mHomeTeam, mAwayTeam );
        mStatsFragment.initialize( mGame );
        mHomeInGameFragment = new TeamInGameFragment( mGame.getHomeTeam().getInGamePlayers() );
        mAwayInGameFragment = new TeamInGameFragment( mGame.getAwayTeam().getInGamePlayers() );
        initializeTeamInGameViews();
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

    private void initializeMainStatsFragmentView()
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.gameMainStats, mStatsFragment );
        transaction.commit();
    }

    private void initializeTeamInGameViews()
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.homeTeamInGameContainer, mHomeInGameFragment );
        transaction.replace( R.id.awayTeamInGameContainer, mAwayInGameFragment );
        transaction.commit();
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
                getHomeStarters();
            }
        } );
    }

    private void showFragment( int containerID, Fragment fragment )
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( containerID, fragment );
        transaction.commit();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setGameClock();
        setContentView( R.layout.activity_game );
        initializeMainStatsFragmentView();
    }

    private final class TeamStartersConfirmationListener implements OnClickListener
    {
        private final Team myTeam;

        private final AlertDialog myDialog;

        private final List< Integer > mySelected;

        private TeamStartersConfirmationListener( Team team, AlertDialog dialog, List< Integer > selected )
        {
            myTeam = team;
            myDialog = dialog;
            mySelected = selected;
        }

        @Override
        public void onClick( View v )
        {
            if( mySelected.size() != 5 )
            {
                Toast.makeText( GameActivity.this, getResources().getString( R.string.need_five_players ),
                        Toast.LENGTH_SHORT ).show();
            }
            else
            {
                setStarters( myTeam, mySelected );
                if( myTeam.equals( mHomeTeam ) )
                {
                    getAwayStarters();
                }
                else
                {
                    startNewGame();
                }
                myDialog.dismiss();
            }
        }
    }
}
