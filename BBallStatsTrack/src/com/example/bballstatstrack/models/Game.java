package com.example.bballstatstrack.models;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.game.GameLog;
import com.example.bballstatstrack.models.gameevents.BlockEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.ReboundType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShotType;
import com.example.bballstatstrack.models.gameevents.ReboundEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.TurnoverEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.NonShootingFoulEvent;
import com.example.bballstatstrack.models.gameevents.foulevents.ShootingFoulEvent;
import com.example.bballstatstrack.models.utils.GameEventDeserializer;
import com.example.bballstatstrack.models.utils.StringUtils;

import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;

public class Game
{
    private static final String B_BALL_STAT_TRACK = "BBallStatTrack";

    private static final int MAX_SHOT_CLOCK = 24;

    private Team mAwayTeam;

    private Team mHomeTeam;

    private Team mHasBallPossession;

    private boolean mGameOngoing = false;

    private int mPeriod = 0;

    private int mCurrentGameClock;

    private int mEventGameClock = -1;

    private int mCurrentShotClock;

    private final int mMaxGameClock;

    private final int mMaxOTGameClock;

    private final int mReducedMaxShotClock;

    private GameLog mGameLog;

    private SparseArray< GameEvent > mPeriodLog;

    private int mHomePeriodFouls = 0;

    private int mAwayPeriodFouls = 0;

    private UUID mID;

    private MyDate mDate;

    public Game( int maxGameClock, int maxOTGameCock, int resetShotClock, Team homeTeam, Team awayTeam )
    {
        mMaxGameClock = maxGameClock * 60;
        mMaxOTGameClock = maxOTGameCock * 60;
        mReducedMaxShotClock = resetShotClock;
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        String id = mAwayTeam.getName() + mHomeTeam.getName() + System.currentTimeMillis();
        mID = UUID.nameUUIDFromBytes( id.getBytes() );
        initializeClocks();
        initializeLogs();
        mDate = new MyDate();
    }

    public Game( JSONObject game )
    {
        mMaxGameClock = 0;
        mMaxOTGameClock = 0;
        mReducedMaxShotClock = 0;
        try
        {
            mID = UUID.fromString( game.getString( GameStats.ID.toString() ) );
            mAwayTeam = new Team( game.getJSONObject( GameStats.AWAY_TEAM.toString() ) );
            mHomeTeam = new Team( game.getJSONObject( GameStats.HOME_TEAM.toString() ) );
            GameEventDeserializer serializer = new GameEventDeserializer( this );
            mGameLog = serializer.getGameLog( game );
            mPeriod = mGameLog.size();
            mDate = new MyDate( game.getLong( GameStats.DATE.toString() ) );
        }
        catch( JSONException e )
        {
            e.printStackTrace();
            Log.e( B_BALL_STAT_TRACK, "Attribute missing from Game JSONObject!", e );
        }
    }

    public void addNewEvent( GameEvent event )
    {
        event.resolve();
        mPeriodLog.append( mEventGameClock, event );
        endNewEvent();
        checkTeamFoulEvent( event );
        checkTurnoverEvent( event );
        checkShootEvent( event );

    }

    public void endNewEvent()
    {
        mEventGameClock = -1;
    }

    public int getAwayPeriodFouls()
    {
        return mAwayPeriodFouls;
    }

    public Team getAwayTeam()
    {
        return mAwayTeam;
    }

    public int getCurrentGameClock()
    {
        return mCurrentGameClock;
    }

    public int getCurrentShotClock()
    {
        return mCurrentShotClock;
    }

    public MyDate getDate()
    {
        return mDate;
    }

    public long getDateMillis()
    {
        return mDate.getTime();
    }

    public GameLog getGameLog()
    {
        return mGameLog;
    }

    public int getHomePeriodFouls()
    {
        return mHomePeriodFouls;
    }

    public Team getHomeTeam()
    {
        return mHomeTeam;
    }

    public UUID getId()
    {
        return mID;
    }

    public int getPeriod()
    {
        return mPeriod;
    }

    public Team getTeamWithoutPossession()
    {
        Team team = getTeamWithPossession();
        if( mHomeTeam.equals( team ) )
        {
            return mAwayTeam;
        }
        else if( mAwayTeam.equals( team ) )
        {
            return mHomeTeam;
        }
        else
        {
            return null;
        }
    }

    public Team getTeamWithPossession()
    {
        return mHasBallPossession;
    }

    public String getTitle()
    {
        String home = mHomeTeam.getName();
        int homeScore = mHomeTeam.getTotalScore();
        String away = mAwayTeam.getName();
        int awayScore = mAwayTeam.getTotalScore();
        String date = StringUtils.getStringDate( mDate );
        return home + " " + homeScore + " - " + awayScore + " " + away + " on " + date;
    }

    public boolean isGameOngoing()
    {
        return mGameOngoing;
    }

    public boolean isPenalty( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            return( mAwayPeriodFouls >= 5 );
        }
        else if( team.equals( mHomeTeam ) )
        {
            return( mHomePeriodFouls >= 5 );
        }
        return false;
    }

    public void nextPeriod()
    {
        mGameLog.nextPeriod();
        mPeriod = mGameLog.getCurrentPeriod();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
        initializeClocks();
        resetPeriodFouls();
    }

    public void pauseGame()
    {
        mGameOngoing = false;
    }

    public void resetGameClock()
    {
        mCurrentGameClock = mMaxGameClock;
    }

    public void resetShotClock24()
    {
        mCurrentShotClock = MAX_SHOT_CLOCK;
    }

    public void setTeamWithPossession( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            mHasBallPossession = mAwayTeam;
        }
        else if( team.equals( mHomeTeam ) )
        {
            mHasBallPossession = mHomeTeam;
        }
    }

    public void startNewEvent()
    {
        mEventGameClock = mCurrentGameClock;
    }

    public void unpauseGame()
    {
        mGameOngoing = true;
    }

    public void updateTime()
    {
        if( isClocksValid() && isGameOngoing() )
        {
            mAwayTeam.updatePlayingTime();
            mHomeTeam.updatePlayingTime();
            mCurrentGameClock--;
            mCurrentShotClock--;
        }
        else
        {
            pauseGame();
        }
    }

    private void addPeriodFoul( Team team )
    {
        if( team.equals( mAwayTeam ) )
        {
            mAwayPeriodFouls++;
        }
        else if( team.equals( mHomeTeam ) )
        {
            mHomePeriodFouls++;
        }
    }

    private void checkReboundEvent( GameEvent event )
    {
        GameEvent appended = event.getAppended();
        if( appended == null )
        {
            return;
        }
        if( appended instanceof BlockEvent )
        {
            checkReboundEvent( appended );
        }
        else if( appended instanceof ReboundEvent )
        {
            ReboundEvent rEvent = ( ReboundEvent ) appended;
            ReboundType type = rEvent.getReboundType();
            switch( type )
            {
                case DEFENSIVE:
                    swapBallPossession();
                    return;
                case OFFENSIVE:
                    resetMidShotClock();
                    return;
                case TEAM_REBOUND:
                    checkTeamReboundEvent( event );
                    return;
            }
        }
    }

    private void checkShootEvent( GameEvent event )
    {
        if( event instanceof ShootEvent )
        {
            if( isShotMade( event ) )
            {
                swapBallPossession();
            }
            else
            {
                checkReboundEvent( event );
            }
        }
    }

    private void checkTeamFoulEvent( GameEvent event )
    {
        if( event == null )
        {
            return;
        }
        if( event instanceof ShootingFoulEvent || event instanceof NonShootingFoulEvent )
        {
            addPeriodFoul( event.getTeam() );
        }
        else
        {
            checkTeamFoulEvent( event.getAppended() );
        }
    }

    private void checkTeamReboundEvent( GameEvent event )
    {
        pauseGame();
        if( getTeamWithPossession().equals( event.getTeam() ) )
        {
            resetMidShotClock();
        }
        else
        {
            swapBallPossession();
        }
    }

    private void checkTurnoverEvent( GameEvent event )
    {
        if( event == null )
        {
            return;
        }
        if( event instanceof TurnoverEvent )
        {
            swapBallPossession();
        }
    }

    private void initializeClocks()
    {
        pauseGame();
        resetGameClock();
        resetShotClock24();
    }

    private void initializeLogs()
    {
        mGameLog = new GameLog();
        mPeriodLog = mGameLog.getCurrentPeriodLog();
        mPeriod = mGameLog.getCurrentPeriod();
    }

    private boolean isClocksValid()
    {
        return mCurrentGameClock > 0 && mCurrentShotClock > 0;
    }

    private boolean isShotMade( GameEvent event )
    {
        ShotType type = ( ( ShootEvent ) event ).getShotType();
        switch( type )
        {
            case MADE:
                return true;
            case MISSED:
                return false;
        }
        return false;
    }

    private void resetMidShotClock()
    {
        mCurrentShotClock = mReducedMaxShotClock;
    }

    private void resetPeriodFouls()
    {
        mHomePeriodFouls = 0;
        mAwayPeriodFouls = 0;
    }

    private void swapBallPossession()
    {
        if( mHasBallPossession.equals( mHomeTeam ) )
        {
            mHasBallPossession = mAwayTeam;
        }
        else
        {
            mHasBallPossession = mHomeTeam;
        }
        resetShotClock24();
    }

    public enum GameStats
    {

        ID( "id" ), AWAY_TEAM( "awayTeam" ), HOME_TEAM( "homeTeam" ), GAME_LOG( "gameLog" ), PERIOD_LOG(
                "periodLog" ), DATE( "date" );

        private final String mConstant;

        private GameStats( String constant )
        {
            mConstant = constant;
        }

        @Override
        public String toString()
        {
            return mConstant;
        }
    }

    public class MyDate extends Date
    {
        public MyDate()
        {
            super();
        }

        public MyDate( long milliseconds )
        {
            super( milliseconds );
        }

        @Override
        public String toString()
        {
            return DateFormat.format( "EEE, MMM dd, yyyy", this ).toString();
        }
    }
}
