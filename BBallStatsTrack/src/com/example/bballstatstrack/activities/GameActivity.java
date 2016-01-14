package com.example.bballstatstrack.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.builders.StartingFiveAwayDialogBuilder;
import com.example.bballstatstrack.builders.StartingFiveHomeDialogBuilder;
import com.example.bballstatstrack.dialogs.BackConfirmationDialog;
import com.example.bballstatstrack.dialogs.BallPossessionDeciderDialog;
import com.example.bballstatstrack.dialogs.MaxGameClockDialog;
import com.example.bballstatstrack.fragments.GameLogFragment;
import com.example.bballstatstrack.fragments.GameScoreBoardFragment;
import com.example.bballstatstrack.fragments.TeamInGameFragment;
import com.example.bballstatstrack.listeners.GameActivityButtonListnener;
import com.example.bballstatstrack.models.Game;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.UpdateTimeTask;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

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

    private Timer mTimer = null;

    private Button mTimeButton;

    private Button mCoachButton;

    private Button mFoulButton;

    private Button mTurnoverButton;

    private Button mShootButton;

    public void addNewEvent( GameEvent event )
    {
        mGame.addNewEvent( event );
        checkTeamFoulEvent( event );
    }

    public void endEvent()
    {
        mGame.endNewEvent();
    }

    public void fetchAwayStarters()
    {
        mAwayTeam = getIntent().getParcelableExtra( AddPlayersToTeamsActivity.AWAY_TEAM );
        AlertDialog dialog = new StartingFiveAwayDialogBuilder( GameActivity.this, mAwayTeam ).create();
        dialog.show();
    }

    public void fetchHomeStarters()
    {
        mHomeTeam = getIntent().getParcelableExtra( AddPlayersToTeamsActivity.HOME_TEAM );
        AlertDialog dialog = new StartingFiveHomeDialogBuilder( GameActivity.this, mHomeTeam ).create();
        dialog.show();
    }

    @Override
    public void finish()
    {
        timerStop();
        super.finish();
    }

    public Game getGame()
    {
        return mGame;
    }

    public int getGameClock()
    {
        return mGameClock;
    }

    public int getShotClockReset()
    {
        return mShotClockReset;
    }

    public boolean isPenalty( Team team )
    {
        return mGame.isPenalty( team );
    }

    public boolean isTimerStopped()
    {
        return mTimer == null;
    }

    public void nextPeriod()
    {
        timerStop();
        mGame.nextPeriod();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog dialog = new BackConfirmationDialog( GameActivity.this );
        dialog.show();
    }

    public void resetMidShotClock()
    {
        mGame.resetMidShotClock();
    }

    public void setGameClock( int gameClock )
    {
        mGameClock = gameClock;
    }

    public void setShotClockReset( int shotClockReset )
    {
        mShotClockReset = shotClockReset;
    }

    public void startEvent()
    {
        mGame.startNewEvent();
    }

    public void startNewGame()
    {
        mGame = new Game( mGameClock, 5, mShotClockReset, mHomeTeam, mAwayTeam );
        mScoreBoardFragment.initialize( mGame );
        mHomeInGameFragment = new TeamInGameFragment( mGame.getHomeTeam() );
        mAwayInGameFragment = new TeamInGameFragment( mGame.getAwayTeam() );
        mGameLogFragment = new GameLogFragment( mGame.getGameLog() );
        mGame.addObserver( mScoreBoardFragment );
        mGame.addObserver( mHomeInGameFragment );
        mGame.addObserver( mAwayInGameFragment );
        mGame.addObserver( mGameLogFragment );
        initializeTeamInGameViews();
        showBallPossessionDeciderDialog();
    }

    public void swapBallPossession()
    {
        mGame.swapBallPossession();
    }

    public void timerStart()
    {
        if( isTimerStopped() )
        {
            mTimer = new Timer();
            TimerTask updateTimeTask = new UpdateTimeTask( GameActivity.this );
            mTimer.schedule( updateTimeTask, 0, 1000 );
        }
    }

    public void timerStop()
    {
        if( isTimerStopped() )
        {
            return;
        }
        mTimer.cancel();
        mTimer = null;
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

    private void checkTeamFoulEvent( GameEvent event )
    {
        if( event == null )
        {
            return;
        }
        if( event instanceof ShootingFoulEvent || event instanceof NonShootingFoulEvent )
        {
            mGame.addPeriodFoul( event.getTeam() );
        }
        else
        {
            checkTeamFoulEvent( event.getAppended() );
        }
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

    private void setButtonListeners()
    {
        OnClickListener listener = new GameActivityButtonListnener( GameActivity.this );
        mTimeButton.setOnClickListener( listener );
        mCoachButton.setOnClickListener( listener );
        mFoulButton.setOnClickListener( listener );
        mTurnoverButton.setOnClickListener( listener );
        mShootButton.setOnClickListener( listener );
    }

    private void showBallPossessionDeciderDialog()
    {
        AlertDialog dialog = new BallPossessionDeciderDialog( GameActivity.this );
        dialog.show();
    }

    private void showMaxGameClockDialog()
    {
        AlertDialog dialog = new MaxGameClockDialog( GameActivity.this );
        dialog.show();
    }
}