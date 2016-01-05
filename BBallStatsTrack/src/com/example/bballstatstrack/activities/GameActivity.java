package com.example.bballstatstrack.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.GameLogFragment;
import com.example.bballstatstrack.fragments.GameScoreBoardFragment;
import com.example.bballstatstrack.fragments.TeamInGameFragment;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;
import com.example.bballstatstrack.models.utils.PlayerNumberWatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity
{
    public static final String EXTRA_GAME_ID = "gameID";

    private Game mGame;

    private int mGameClock = -1;

    private int mShotClockReset = -1;

    private GameScoreBoardFragment mScoreBoardFragment = new GameScoreBoardFragment();

    private TeamInGameFragment mHomeInGameFragment;

    private TeamInGameFragment mAwayInGameFragment;

    private GameLogFragment mGameLogFragment;

    private Team mHomeTeam;

    private Team mAwayTeam;

    private Timer mTimer;

    private Button mTimeButton;

    private Button mCoachButton;

    private Button mFoulButton;

    private Button mTurnoverButton;

    private Button mShootButton;

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( GameActivity.this );
        builder.setTitle( R.string.on_back_button_title );
        builder.setMessage( R.string.on_back_button_message );
        builder.setPositiveButton( R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                finish();
            }
        } );
        builder.setNegativeButton( R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.dismiss();
            }
        } );
        builder.show();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        showMaxGameClockDialog();
        setContentView( R.layout.activity_game );
        mTimeButton = ( Button ) findViewById( R.id.timeRelatedButton );
        mCoachButton = ( Button ) findViewById( R.id.coachCommandsButton );
        mFoulButton = ( Button ) findViewById( R.id.foulButton );
        mTurnoverButton = ( Button ) findViewById( R.id.turnoverButton );
        mShootButton = ( Button ) findViewById( R.id.shootRelatedButton );
        initializeMainStatsFragmentView();
        setupButtonListeners();
    }

    private AlertDialog buildClockDialog( int detailedTextID )
    {
        Builder builder = new Builder( GameActivity.this );
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

    private AlertDialog createDialogFromLayout( int resourceId )
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setView( getLayoutInflater().inflate( resourceId, null ) );
        return builder.create();
    }

    private void fetchAwayStarters()
    {
        mAwayTeam = getTeam( AddPlayersToTeamsActivity.AWAY_TEAM_NAME, AddPlayersToTeamsActivity.AWAY_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.AWAY_TEAM_MEMBER_NAMES );
        selectStartingFive( mAwayTeam );
    }

    private void fetchHomeStarters()
    {
        mHomeTeam = getTeam( AddPlayersToTeamsActivity.HOME_TEAM_NAME, AddPlayersToTeamsActivity.HOME_TEAM_NUMBERS,
                AddPlayersToTeamsActivity.HOME_TEAM_MEMBER_NAMES );
        selectStartingFive( mHomeTeam );
    }

    private int getStringResIDFromEnum( TurnoverType type )
    {
        switch( type )
        {
            case OTHER:
            {
                return R.string.turnover_type_dialog_other_text;
            }
            case OFFENSIVE_FOUL:
            {
                return R.string.turnover_type_dialog_offensivefoul_text;
            }
            case STEAL:
            {
                return R.string.turnover_type_dialog_steal_text;
            }
            default:
            {
                return -1;
            }
        }
    }

    private Team getTeam( String teamNameExtra, String teamNumbersExtra, String teamPlayersExtra )
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

    private void initializeMainStatsFragmentView()
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.scoreBoardContainer, mScoreBoardFragment );
        transaction.commit();
    }

    private void initializeTeamInGameViews()
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.homeTeamInGameContainer, mHomeInGameFragment );
        transaction.replace( R.id.gameLogContainer, mGameLogFragment );
        transaction.replace( R.id.awayTeamInGameContainer, mAwayInGameFragment );
        transaction.commit();
    }

    private void initializeTimer()
    {
        mTimer = new Timer();
        TimerTask updateTimeTask = new UpdateTimeTask( mGame );
        mTimer.schedule( updateTimeTask, 0, 1000 );
    }

    private void pauseResumeGame()
    {
        if( mGame.isGameOngoing() )
        {
            mGame.pauseGame();
        }
        else
        {
            mGame.unpauseGame();
        }
    }

    private void selectStartingFive( Team team )
    {
        Builder builder = new Builder( GameActivity.this );
        String title = getResources().getString( R.string.select_starters ) + " ";
        builder.setTitle( title.concat( team.getName() ) );
        final SparseArray< Player > players = team.getPlayers();
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
                .setOnClickListener( new TeamStartersConfirmationListener( team, dialog, selected ) );
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

    private void setStarters( Team team, List< Integer > selected )
    {
        for( Integer number : selected )
        {
            team.addStarter( number.intValue() );
        }
    }

    private void setupButtonListeners()
    {
        mTimeButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showTimeButtonDialog();
            }
        } );
        mCoachButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showCoachButtonDialog();
            }
        } );
        mFoulButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // TODO Auto-generated method stub

            }
        } );
        mTurnoverButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showTurnoverEventDialog();
            }
        } );
        mShootButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // TODO Auto-generated method stub

            }
        } );
    }

    private void showCoachButtonDialog()
    {
        mGame.startNewEvent();
        final AlertDialog dialog = createDialogFromLayout( R.layout.dialog_coach_button );
        dialog.show();
        Button timeOutButton = ( Button ) dialog.findViewById( R.id.time_out_button );
        timeOutButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                TimeoutEvent event = new TimeoutEvent( mGame.getTeamWithPossession() );
                mGame.addNewEvent( event );
                dialog.dismiss();
            }
        } );
        Button substitutionButton = ( Button ) dialog.findViewById( R.id.substitution_button );
        substitutionButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showSubsitutionDialog();
                dialog.dismiss();
            }
        } );
        substitutionButton.setEnabled( !mGame.isGameOngoing() );
    }

    private void showJumpballDialog()
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( R.string.jump_ball_title );
        builder.setNegativeButton( mHomeTeam.getName(), new JumpBallDeciderListener( mHomeTeam ) );
        builder.setPositiveButton( mAwayTeam.getName(), new JumpBallDeciderListener( mAwayTeam ) );
        AlertDialog jumpballDialog = builder.create();
        jumpballDialog.show();
    }

    private void showMaxGameClockDialog()
    {
        final AlertDialog dialog = buildClockDialog( R.string.set_max_game_clock_title );
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
                showMaxShotClockResetDialog();
            }
        } );
    }

    private void showMaxShotClockResetDialog()
    {
        final AlertDialog dialog = buildClockDialog( R.string.set_reset_shot_clock_title );
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
                fetchHomeStarters();
            }
        } );
    }

    private void showResetGameClockDialog()
    {
    }

    private void showResetShotClockDialog()
    {
    }

    private void showStealEventDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.steal_player_question );
        Team team = mGame.getTeamWithoutPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new StealListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showSubsitutionDialog()
    {

    }

    private void showTimeButtonDialog()
    {
        final AlertDialog dialog = createDialogFromLayout( R.layout.dialog_time_button );
        dialog.show();
        Button resetGameClockButton = ( Button ) dialog.findViewById( R.id.reset_game_clock_button );
        resetGameClockButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showResetGameClockDialog();
                dialog.dismiss();
            }
        } );
        Button resetShotClockButton = ( Button ) dialog.findViewById( R.id.reset_shot_clock_button );
        resetShotClockButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showResetShotClockDialog();
                dialog.dismiss();
            }
        } );
        Button pauseResumeButton = ( Button ) dialog.findViewById( R.id.pauseResume_time_button );
        pauseResumeButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                pauseResumeGame();
                dialog.dismiss();
            }
        } );
        boolean gameOngoing = mGame.isGameOngoing();
        resetGameClockButton.setEnabled( !gameOngoing );
        resetShotClockButton.setEnabled( !gameOngoing );
        pauseResumeButton.setText( gameOngoing ? R.string.time_dialog_pause_text : R.string.time_dialog_resume_text );
    }

    private void showTurnoverEventDialog()
    {
        mGame.startNewEvent();
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.turnover_player_question );
        Team team = mGame.getTeamWithPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setOnClickListener( new TurnoverListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showTurnoverTypeDialog( Team team, Player player )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.turnover_type_question );
        AlertDialog dialog = builder.create();
        for( TurnoverType type : TurnoverType.values() )
        {
            Button button = new Button( GameActivity.this );
            button.setTag( type );
            button.setText( getStringResIDFromEnum( type ) );
            button.setOnClickListener( new TurnoverTypeListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void startNewGame()
    {
        mGame = new Game( mGameClock, 5, mShotClockReset, mHomeTeam, mAwayTeam );
        mScoreBoardFragment.initialize( mGame );
        mHomeInGameFragment = new TeamInGameFragment( mGame.getHomeTeam().getInGamePlayers() );
        mAwayInGameFragment = new TeamInGameFragment( mGame.getAwayTeam().getInGamePlayers() );
        mGameLogFragment = new GameLogFragment( mGame.getGameLog() );
        initializeTeamInGameViews();
        initializeTimer();
        showJumpballDialog();
    }

    private void updateScoreBoardUI()
    {
        mScoreBoardFragment.updateUI( mGame );
    }

    private void updateInGamePlayersUI()
    {
        mHomeInGameFragment.updateUI();
        mAwayInGameFragment.updateUI();
        updateGameLogUI();
    }

    private void updateGameLogUI()
    {
        mGameLogFragment.updateUI();
    }

    private final class CancelGameEventListener implements OnCancelListener
    {
        @Override
        public void onCancel( DialogInterface dialog )
        {
            mGame.endNewEvent();
        }
    }

    private class GameEventListener implements OnClickListener
    {
        protected Team mGameEventTeam;

        protected Player mGameEventPlayer;

        protected AlertDialog mParentDialog;

        public GameEventListener( AlertDialog parentDialog, Team team, Player player )
        {
            mParentDialog = parentDialog;
            mGameEventTeam = team;
            mGameEventPlayer = player;
        }

        @Override
        public void onClick( View v )
        {
            mParentDialog.dismiss();
        }
    }

    private class JumpBallDeciderListener implements DialogInterface.OnClickListener
    {
        Team mTeamWithPossession;

        public JumpBallDeciderListener( Team team )
        {
            mTeamWithPossession = team;
        }

        @Override
        public void onClick( DialogInterface dialog, int which )
        {
            mGame.setTeamWithPossession( mTeamWithPossession );
            mGame.unpauseGame();
        }

    }

    private class StealListener extends GameEventListener
    {
        public StealListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            StealEvent event = new StealEvent( mGameEventPlayer, mGameEventTeam );
            GameEvent parent = ( GameEvent ) v.getTag();
            parent.append( event );
            mGame.addNewEvent( event );
        }
    }

    private final class TeamStartersConfirmationListener implements OnClickListener
    {
        private final Team mListenerTeam;

        private final AlertDialog mParentDialog;

        private final List< Integer > mSelectedPlayers;

        private TeamStartersConfirmationListener( Team team, AlertDialog parent, List< Integer > selectedPlayers )
        {
            mListenerTeam = team;
            mParentDialog = parent;
            mSelectedPlayers = selectedPlayers;
        }

        @Override
        public void onClick( View v )
        {
            if( mSelectedPlayers.size() != 5 )
            {
                Toast.makeText( GameActivity.this, getResources().getString( R.string.need_five_players ),
                        Toast.LENGTH_SHORT ).show();
            }
            else
            {
                setStarters( mListenerTeam, mSelectedPlayers );
                if( mListenerTeam.equals( mHomeTeam ) )
                {
                    fetchAwayStarters();
                }
                else
                {
                    startNewGame();
                }
                mParentDialog.dismiss();
            }
        }
    }

    private class TurnoverListener extends GameEventListener
    {
        public TurnoverListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            showTurnoverTypeDialog( mGameEventTeam, mGameEventPlayer );
        }
    }

    private class TurnoverTypeListener extends GameEventListener
    {
        public TurnoverTypeListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            TurnoverType type = ( TurnoverType ) v.getTag();
            TurnoverEvent event = new TurnoverEvent( type, mGameEventPlayer, mGameEventTeam );
            if( type.equals( TurnoverType.STEAL ) )
            {
                showStealEventDialog( event );
                return;
            }
            else if( type.equals( TurnoverType.OFFENSIVE_FOUL ) )
            {
                OffensiveFoulEvent foulEvent = new OffensiveFoulEvent( mGameEventPlayer, mGameEventTeam );
                event.append( foulEvent );
            }
            mGame.addNewEvent( event );
            return;
        }
    }

    private class UpdateTimeTask extends TimerTask
    {
        Game mTimerTaskGame;

        public UpdateTimeTask( Game game )
        {
            mTimerTaskGame = game;
        }

        @Override
        public void run()
        {
            mTimerTaskGame.updateTime();
            runOnUiThread( new Runnable()
            {
                @Override
                public void run()
                {
                    updateScoreBoardUI();
                }
            } );
        }
    }
}
