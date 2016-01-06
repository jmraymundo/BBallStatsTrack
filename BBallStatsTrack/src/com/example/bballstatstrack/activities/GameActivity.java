package com.example.bballstatstrack.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.GameLogFragment;
import com.example.bballstatstrack.fragments.GameScoreBoardFragment;
import com.example.bballstatstrack.fragments.TeamInGameFragment;
import com.example.bballstatstrack.model.GameDirectory;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.AssistEvent;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotClass;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.GameEvent.TurnoverType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.StealEvent;
import com.example.bballstatstrack.models.gameevents.SubstitutionEvent;
import com.example.bballstatstrack.models.gameevents.TimeoutEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;
import com.example.bballstatstrack.models.utils.PlayerNumberWatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

    public void newGamePauseEvent()
    {
        mGame.pauseGame();
        mGame.startNewEvent();
    }

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
                endActivity();
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
        setButtonListeners();
    }

    private void addNewEvent( GameEvent event )
    {
        mGame.addNewEvent( event );
        updateGameLogUI();
        if( event instanceof ShootEvent || event instanceof SubstitutionEvent )
        {
            updateInGamePlayersUI();
        }
    }

    private AlertDialog buildClockDialog( int detailedTextID )
    {
        Builder builder = new Builder( GameActivity.this );
        View dialogView = getLayoutInflater().inflate( R.layout.dialog_get_clock, null );
        builder.setView( dialogView );
        builder.setTitle( R.string.set_time_title );
        TextView detailedQuestionView = ( TextView ) dialogView.findViewById( R.id.requested_clock_text );
        detailedQuestionView.setText( detailedTextID );
        builder.setPositiveButton( android.R.string.ok, new EmptyDialogInterfaceOnClickListener() );
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

    private AlertDialog createYesNoDialogWithTitle( int stringResourceID )
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( stringResourceID );
        builder.setPositiveButton( R.string.yes, new EmptyDialogInterfaceOnClickListener() );
        builder.setNegativeButton( R.string.no, new EmptyDialogInterfaceOnClickListener() );
        final AlertDialog dialog = builder.show();
        return dialog;
    }

    private void endActivity()
    {
        mTimer.cancel();
        finish();
    }

    private void fetchAwayStarters()
    {
        mAwayTeam = getTeamFromIntent( AddPlayersToTeamsActivity.AWAY_TEAM_NAME,
                AddPlayersToTeamsActivity.AWAY_TEAM_NUMBERS, AddPlayersToTeamsActivity.AWAY_TEAM_MEMBER_NAMES );
        selectStartingFive( mAwayTeam );
    }

    private void fetchHomeStarters()
    {
        mHomeTeam = getTeamFromIntent( AddPlayersToTeamsActivity.HOME_TEAM_NAME,
                AddPlayersToTeamsActivity.HOME_TEAM_NUMBERS, AddPlayersToTeamsActivity.HOME_TEAM_MEMBER_NAMES );
        selectStartingFive( mHomeTeam );
    }

    private int getStringResIDFromEnum( TurnoverType type )
    {
        switch( type )
        {
            case OTHER:
            {
                return R.string.turnover_dialog_type_other_text;
            }
            case OFFENSIVE_FOUL:
            {
                return R.string.turnover_dialog_type_offensivefoul_text;
            }
            case STEAL:
            {
                return R.string.turnover_dialog_type_steal_text;
            }
            default:
            {
                return -1;
            }
        }
    }

    private Team getTeamFromIntent( String teamNameExtra, String teamNumbersExtra, String teamPlayersExtra )
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

    private void pauseUnpauseGame()
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
        builder.setPositiveButton( getResources().getString( R.string.proceed ),
                new EmptyDialogInterfaceOnClickListener() );
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton( AlertDialog.BUTTON_POSITIVE )
                .setOnClickListener( new TeamStartersConfirmationListener( team, dialog, selected ) );
    }

    private void setButtonListeners()
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
                showFoulButtonDialog();
            }
        } );
        mTurnoverButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showTurnoverButtonDialog();
            }
        } );
        mShootButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showShootButtonDialog();
            }
        } );
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

    private void showAssistCheckDialog( final GameEvent parent )
    {
        AlertDialog dialog = createYesNoDialogWithTitle( R.string.shoot_dialog_assist_check_question );
        dialog.getButton( DialogInterface.BUTTON_POSITIVE )
                .setOnClickListener( new AssistCheckDialogListener( dialog, parent, true ) );
        dialog.getButton( DialogInterface.BUTTON_NEGATIVE )
                .setOnClickListener( new AssistCheckDialogListener( dialog, parent, false ) );
    }

    private void showAssistDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.assist_dialog_player_question );
        Team team = mGame.getTeamWithPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new AssistEventListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showBallPossessionDeciderDialog()
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( R.string.ball_possession_question );
        builder.setNegativeButton( mHomeTeam.getName(), new BallPossessionDeciderListener( mHomeTeam ) );
        builder.setPositiveButton( mAwayTeam.getName(), new BallPossessionDeciderListener( mAwayTeam ) );
        AlertDialog ballPossessionDialog = builder.create();
        ballPossessionDialog.show();
    }

    private void showBlockCheckDialog( final GameEvent parent )
    {
        AlertDialog dialog = createYesNoDialogWithTitle( R.string.shoot_dialog_block_check_question );
        dialog.getButton( DialogInterface.BUTTON_POSITIVE )
                .setOnClickListener( new BlockCheckDialogListener( dialog, parent, true ) );
        dialog.getButton( DialogInterface.BUTTON_NEGATIVE )
                .setOnClickListener( new BlockCheckDialogListener( dialog, parent, false ) );
    }

    private void showBlockDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.block_dialog_player_question );
        Team team = mGame.getTeamWithoutPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new BlockEventListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
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
                mGame.pauseGame();
                TimeoutEvent event = new TimeoutEvent( mGame.getTeamWithPossession() );
                addNewEvent( event );
                dialog.dismiss();
            }
        } );
        Button substitutionButton = ( Button ) dialog.findViewById( R.id.substitution_button );
        substitutionButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showSubstitutionDialog();
                dialog.dismiss();
            }
        } );
        substitutionButton.setEnabled( !mGame.isGameOngoing() );
        dialog.setOnCancelListener( new CancelGameEventListener() );
    }

    private void showFoulButtonDialog()
    {
        newGamePauseEvent();
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.foul_dialog_player_question );
        Team team = mGame.getTeamWithoutPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setOnClickListener( new NonShootingFoulListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventUnpauseListener() );
        dialog.show();
    }

    private void showFreeThrowEventDialog( int ftCount, Team team, Player player )
    {
        if( ftCount == 0 )
        {
            return;
        }
        AlertDialog dialog = createYesNoDialogWithTitle( R.string.free_throw_dialog_question );
        dialog.getButton( DialogInterface.BUTTON_POSITIVE )
                .setOnClickListener( new FreeThrowEventListener( dialog, team, player, ftCount, true ) );
        dialog.getButton( DialogInterface.BUTTON_NEGATIVE )
                .setOnClickListener( new FreeThrowEventListener( dialog, team, player, ftCount, false ) );
    }

    private void showFreeThrowShooterDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        if( parent instanceof NonShootingFoulEvent )
        {
            builder.setTitle( R.string.free_throw_dialog_penalty );
        }
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.foul_dialog_fouled_question );
        Team team = mGame.getTeamWithPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new FreeThrowListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();

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

    private void showReboundDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( R.string.ball_possession_question );
        builder.setNegativeButton( mHomeTeam.getName(), new ReboundDialogListener( parent, mHomeTeam ) );
        builder.setPositiveButton( mAwayTeam.getName(), new ReboundDialogListener( parent, mAwayTeam ) );
        builder.show();
    }

    private void showReboundEventDialog( GameEvent parent, Team team )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.rebound_dialog_player_question );
        AlertDialog dialog = builder.create();
        ReboundType type;
        if( mGame.getTeamWithPossession().equals( team ) )
        {
            type = ReboundType.OFFENSIVE;
        }
        else
        {
            type = ReboundType.DEFENSIVE;
        }
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new ReboundEventListener( dialog, team, player, type ) );
            parentView.addView( button );
        }
        Button teamRebound = new Button( GameActivity.this );
        teamRebound.setText( R.string.rebound_dialog_team_rebound );
        teamRebound.setTag( parent );
        teamRebound.setOnClickListener( new ReboundEventListener( dialog, team, null, ReboundType.TEAM_REBOUND ) );
        parentView.addView( teamRebound );
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showResetGameClockDialog()
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( R.string.reset_game_clock_confirmation_question );
        builder.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                mGame.resetGameClock();
            }
        } );
        builder.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // do nothing
            }
        } );
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button noButton = dialog.getButton( DialogInterface.BUTTON_NEGATIVE );
        noButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                dialog.dismiss();
            }
        } );
    }

    private void showResetShotClockDialog()
    {
        Builder builder = new Builder( GameActivity.this );
        builder.setTitle( R.string.reset_shot_clock_confirmation_question );
        builder.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                mGame.resetShotClock24();
            }
        } );
        builder.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                // do nothing
            }
        } );
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button noButton = dialog.getButton( DialogInterface.BUTTON_NEGATIVE );
        noButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                dialog.dismiss();
            }
        } );
    }

    private void showSaveGameDialog()
    {
        AlertDialog dialog = createYesNoDialogWithTitle( R.string.save_dialog_confirmation_question );
        dialog.show();
        dialog.getButton( DialogInterface.BUTTON_POSITIVE ).setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                GameDirectory.get( GameActivity.this ).addGame( mGame );
                endActivity();
            }
        } );
    }

    private void showShootButtonDialog()
    {
        mGame.startNewEvent();
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.shoot_dialog_player_question );
        Team team = mGame.getTeamWithPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setOnClickListener( new ShootButtonDialogListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventUnpauseListener() );
        dialog.show();
    }

    private void showShotClassDialog( Team team, Player player )
    {
        final AlertDialog dialog = createDialogFromLayout( R.layout.dialog_shoot_class );
        dialog.show();
        Button pts2 = ( Button ) dialog.findViewById( R.id.shoot_dialog_class_2pt );
        pts2.setOnClickListener( new ShotClassDialogListener( dialog, team, player ) );
        pts2.setTag( ShotClass.FG_2PT );
        Button pts3 = ( Button ) dialog.findViewById( R.id.shoot_dialog_class_3pt );
        pts3.setOnClickListener( new ShotClassDialogListener( dialog, team, player ) );
        pts3.setTag( ShotClass.FG_3PT );
    }

    private void showShotTypeDialog( Team team, Player player, ShotClass shotCLass )
    {
        AlertDialog dialog = createYesNoDialogWithTitle( R.string.shoot_dialog_type_question );
        dialog.getButton( DialogInterface.BUTTON_POSITIVE )
                .setOnClickListener( new ShootEventListener( dialog, team, player, shotCLass, true ) );
        dialog.getButton( DialogInterface.BUTTON_NEGATIVE )
                .setOnClickListener( new ShootEventListener( dialog, team, player, shotCLass, false ) );
    }

    private void showStealEventDialog( GameEvent parent )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.steal_dialog_player_question );
        Team team = mGame.getTeamWithoutPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setTag( parent );
            button.setOnClickListener( new StealEventListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showSubstitutionDialog()
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_substitution_button,
                null );
        builder.setView( parentView );
        AlertDialog dialog = builder.show();
        final Team homeTeam = mGame.getHomeTeam();
        Button homeTeamButton = ( Button ) parentView.findViewById( R.id.substitution_dialog_home_button );
        homeTeamButton.setText( homeTeam.getName() );
        homeTeamButton.setOnClickListener( new SubstitutionDialogListener( dialog, homeTeam ) );
        final Team awayTeam = mGame.getAwayTeam();
        Button awayTeamButton = ( Button ) parentView.findViewById( R.id.substitution_dialog_away_button );
        awayTeamButton.setText( awayTeam.getName() );
        awayTeamButton.setOnClickListener( new SubstitutionDialogListener( dialog, awayTeam ) );
    }

    private void showSubstitutionInDialog( Team team, Player playerOut )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.substitution_dialog_player_out_question );
        AlertDialog dialog = builder.create();
        List< Player > inGamePlayers = team.getInGamePlayers();
        SparseArray< Player > allPlayers = team.getPlayers();
        for( int index = 0; index < allPlayers.size(); index++ )
        {
            Player playerIn = allPlayers.valueAt( index );
            if( inGamePlayers.contains( playerIn ) )
            {
                continue;
            }
            Button button = new Button( GameActivity.this );
            button.setText( playerIn.toString() );
            button.setOnClickListener( new SubstitutionInListener( dialog, team, playerOut, playerIn ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showSubstitutionOutDialog( Team team )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.substitution_dialog_player_out_question );
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setOnClickListener( new SubstitutionOutListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventListener() );
        dialog.show();
    }

    private void showTimeButtonDialog()
    {
        final AlertDialog dialog = createDialogFromLayout( R.layout.dialog_time_button );
        dialog.show();
        Button saveGameButton = ( Button ) dialog.findViewById( R.id.save_game_button );
        saveGameButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                showSaveGameDialog();
                dialog.dismiss();
            }
        } );
        Button nextPeriodButton = ( Button ) dialog.findViewById( R.id.next_period_button );
        nextPeriodButton.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                mGame.nextPeriod();
                showBallPossessionDeciderDialog();
                dialog.dismiss();
            }
        } );
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
                pauseUnpauseGame();
                dialog.dismiss();
            }
        } );
        boolean isPeriodOngoing = mGame.isPeriodOngoing();
        saveGameButton.setEnabled( !isPeriodOngoing );
        nextPeriodButton.setEnabled( !isPeriodOngoing );
        boolean gameOngoing = mGame.isGameOngoing();
        resetGameClockButton.setEnabled( !gameOngoing );
        resetShotClockButton.setEnabled( !gameOngoing );
        pauseResumeButton.setText( gameOngoing ? R.string.time_dialog_pause_text : R.string.time_dialog_resume_text );
        dialog.setOnCancelListener( new CancelGameEventListener() );
    }

    private void showTurnoverButtonDialog()
    {
        newGamePauseEvent();
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.turnover_dialog_player_question );
        Team team = mGame.getTeamWithPossession();
        AlertDialog dialog = builder.create();
        for( Player player : team.getInGamePlayers() )
        {
            Button button = new Button( GameActivity.this );
            button.setText( player.toString() );
            button.setOnClickListener( new TurnoverListener( dialog, team, player ) );
            parentView.addView( button );
        }
        dialog.setOnCancelListener( new CancelGameEventUnpauseListener() );
        dialog.show();
    }

    private void showTurnoverTypeDialog( Team team, Player player )
    {
        Builder builder = new Builder( GameActivity.this );
        LinearLayout parentView = ( LinearLayout ) getLayoutInflater().inflate( R.layout.dialog_container, null );
        builder.setView( parentView );
        TextView question = ( TextView ) parentView.findViewById( R.id.dialog_container_question_textview );
        question.setText( R.string.turnover_dialog_type_question );
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
        showBallPossessionDeciderDialog();
    }

    private void updateGameLogUI()
    {
        mGameLogFragment.updateUI();
    }

    private void updateInGamePlayersUI()
    {
        mHomeInGameFragment.updateUI();
        mAwayInGameFragment.updateUI();
    }

    private void updateScoreBoardUI()
    {
        if( mScoreBoardFragment.isVisible() )
        {
            mScoreBoardFragment.updateUI( mGame );
        }
    }

    public class ReboundEventListener extends GameEventListener
    {
        ReboundType mType;

        public ReboundEventListener( AlertDialog parentDialog, Team team, Player player, ReboundType type )
        {
            super( parentDialog, team, player );
            mType = type;
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            GameEvent parent = ( GameEvent ) v.getTag();
            ReboundEvent event = new ReboundEvent( mType, getPlayer(), getTeam() );
            parent.append( event );
            addNewEvent( parent );
        }

    }

    private class AssistCheckDialogListener implements OnClickListener
    {
        private AlertDialog mParentDialog;

        private GameEvent mParentEvent;

        private boolean mIsAssisted;

        public AssistCheckDialogListener( AlertDialog parentDialog, GameEvent parent, boolean isAssisted )
        {
            mParentDialog = parentDialog;
            mParentEvent = parent;
            mIsAssisted = isAssisted;
        }

        @Override
        public void onClick( View v )
        {
            if( mIsAssisted )
            {
                showAssistDialog( mParentEvent );
            }
            else
            {
                addNewEvent( mParentEvent );
            }
            mParentDialog.dismiss();
        }
    }

    private class AssistEventListener extends GameEventListener
    {
        public AssistEventListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            GameEvent parent = ( GameEvent ) v.getTag();
            AssistEvent event = new AssistEvent( getPlayer(), getTeam() );
            parent.append( event );
            addNewEvent( parent );
        }
    }

    private class BallPossessionDeciderListener implements DialogInterface.OnClickListener
    {
        Team mTeamWithPossession;

        public BallPossessionDeciderListener( Team team )
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

    private class BlockCheckDialogListener implements OnClickListener
    {
        private AlertDialog mParentDialog;

        private GameEvent mParentEvent;

        private boolean mIsBlocked;

        public BlockCheckDialogListener( AlertDialog parentDialog, GameEvent parent, boolean isBlocked )
        {
            mParentDialog = parentDialog;
            mParentEvent = parent;
            mIsBlocked = isBlocked;
        }

        @Override
        public void onClick( View v )
        {
            if( mIsBlocked )
            {
                showBlockDialog( mParentEvent );
            }
            else
            {
                showReboundDialog( mParentEvent );
            }
            mParentDialog.dismiss();
        }
    }

    private class BlockEventListener extends GameEventListener
    {
        public BlockEventListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            GameEvent parent = ( GameEvent ) v.getTag();
            BlockEvent event = new BlockEvent( getPlayer(), getTeam() );
            parent.append( event );
            showReboundDialog( parent );
        }
    }

    private class CancelGameEventListener implements OnCancelListener
    {
        @Override
        public void onCancel( DialogInterface dialog )
        {
            mGame.endNewEvent();
        }
    }

    private class CancelGameEventUnpauseListener extends CancelGameEventListener
    {
        @Override
        public void onCancel( DialogInterface dialog )
        {
            super.onCancel( dialog );
            mGame.unpauseGame();
        }
    }

    private class EmptyDialogInterfaceOnClickListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick( DialogInterface dialog, int which )
        {
            // do nothing
        }
    }

    private class FreeThrowEventListener extends GameEventListener
    {
        private int mFTCount;

        private boolean mFTMade;

        public FreeThrowEventListener( AlertDialog parentDialog, Team team, Player player, int ftCount, boolean ftMade )
        {
            super( parentDialog, team, player );
            mFTCount = ftCount;
            mFTMade = ftMade;
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            ShootEvent event = new ShootEvent( ShotClass.FT, mFTMade ? ShotType.MADE : ShotType.MISSED, getPlayer(),
                    getTeam() );
            addNewEvent( event );
            showFreeThrowEventDialog( mFTCount - 1, getTeam(), getPlayer() );
        }
    }

    private class FreeThrowListener extends GameEventListener
    {
        public FreeThrowListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            FoulEvent event = ( FoulEvent ) v.getTag();
            if( event instanceof NonShootingFoulEvent )
            {
                showFreeThrowEventDialog( 2, getTeam(), getPlayer() );
            }
            else if( event instanceof ShootingFoulEvent )
            {
                showFreeThrowEventDialog( ( ( ShootingFoulEvent ) event ).getFTCount(), getTeam(), getPlayer() );
            }
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

        protected Player getPlayer()
        {
            return mGameEventPlayer;
        }

        protected Team getTeam()
        {
            return mGameEventTeam;
        }
    }

    private class NonShootingFoulListener extends GameEventListener
    {
        public NonShootingFoulListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            boolean isPenalty = mGame.isPenalty( getTeam() );
            NonShootingFoulType type = isPenalty ? NonShootingFoulType.PENALTY : NonShootingFoulType.NON_PENALTY;
            NonShootingFoulEvent event = new NonShootingFoulEvent( type, getPlayer(), getTeam() );
            if( isPenalty )
            {
                showFreeThrowShooterDialog( event );
            }
            addNewEvent( event );
        }
    }

    private class ReboundDialogListener implements DialogInterface.OnClickListener
    {
        private Team mTeam;

        private GameEvent mParent;

        public ReboundDialogListener( GameEvent parent, Team team )
        {
            mParent = parent;
            mTeam = team;
        }

        @Override
        public void onClick( DialogInterface dialog, int which )
        {
            showReboundEventDialog( mParent, mTeam );
        }
    }

    private class ShootButtonDialogListener extends GameEventListener
    {

        public ShootButtonDialogListener( AlertDialog dialog, Team team, Player player )
        {
            super( dialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            showShotClassDialog( getTeam(), getPlayer() );
        }
    }

    private class ShootEventListener extends GameEventListener
    {
        boolean mShotMade;

        ShotClass mShotClass;

        public ShootEventListener( AlertDialog parentDialog, Team team, Player player, ShotClass shotCLass,
                boolean shotMade )
        {
            super( parentDialog, team, player );
            mShotClass = shotCLass;
            mShotMade = shotMade;
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            ShootEvent event;
            if( mShotMade )
            {
                event = new ShootEvent( mShotClass, ShotType.MADE, getPlayer(), getTeam() );
                showAssistCheckDialog( event );
            }
            else
            {
                event = new ShootEvent( mShotClass, ShotType.MISSED, getPlayer(), getTeam() );
                showBlockCheckDialog( event );
            }
        }
    }

    private class ShotClassDialogListener extends GameEventListener
    {

        public ShotClassDialogListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            ShotClass shotClass = ( ShotClass ) v.getTag();
            showShotTypeDialog( getTeam(), getPlayer(), shotClass );
        }
    }

    private class StealEventListener extends GameEventListener
    {
        public StealEventListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            StealEvent event = new StealEvent( getPlayer(), getTeam() );
            GameEvent parent = ( GameEvent ) v.getTag();
            parent.append( event );
            addNewEvent( parent );
            mGame.unpauseGame();
        }
    }

    private class SubstitutionDialogListener implements OnClickListener
    {
        private final Team mSelectedTeam;

        private AlertDialog mParentDialog;

        private SubstitutionDialogListener( AlertDialog parentDialog, Team team )
        {
            mSelectedTeam = team;
            mParentDialog = parentDialog;
        }

        @Override
        public void onClick( View v )
        {
            showSubstitutionOutDialog( mSelectedTeam );
            mParentDialog.dismiss();
        }
    }

    private class SubstitutionInListener extends GameEventListener
    {
        private Player mPlayerIn;

        public SubstitutionInListener( AlertDialog parentDialog, Team team, Player playerOut, Player playerIn )
        {
            super( parentDialog, team, playerOut );
            mPlayerIn = playerIn;
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            SubstitutionEvent event = new SubstitutionEvent( getPlayer(), mPlayerIn, getTeam() );
            addNewEvent( event );
        }

    }

    private class SubstitutionOutListener extends GameEventListener
    {

        public SubstitutionOutListener( AlertDialog parentDialog, Team team, Player player )
        {
            super( parentDialog, team, player );
        }

        @Override
        public void onClick( View v )
        {
            super.onClick( v );
            showSubstitutionInDialog( getTeam(), getPlayer() );
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
            showTurnoverTypeDialog( getTeam(), getPlayer() );
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
            TurnoverEvent event = new TurnoverEvent( type, getPlayer(), getTeam() );
            if( type.equals( TurnoverType.STEAL ) )
            {
                showStealEventDialog( event );
                return;
            }
            else if( type.equals( TurnoverType.OFFENSIVE_FOUL ) )
            {
                OffensiveFoulEvent foulEvent = new OffensiveFoulEvent( getPlayer(), getTeam() );
                event.append( foulEvent );
            }
            addNewEvent( event );
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