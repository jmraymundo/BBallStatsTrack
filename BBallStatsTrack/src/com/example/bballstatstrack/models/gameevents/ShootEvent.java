package com.example.bballstatstrack.models.gameevents;

import java.util.Locale;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class ShootEvent extends GameEvent
{
    public static final String SHOT_TYPE = "shotType";

    public static final String SHOT_CLASS = "shotClass";

    ShotClass mShotClass;

    ShotType mShotType;

    public ShootEvent( ShotClass shotClass, ShotType shotType, Player player, Team team )
    {
        super( EventType.SHOOT, player, team );
        mShotClass = shotClass;
        mShotType = shotType;
    }

    @Override
    public void append( GameEvent appendedEvent )
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        switch( mShotType )
        {
            case MADE:
                if( appendedEvent instanceof AssistEvent )
                {
                    mAppended = appendedEvent;
                    return;
                }
                else if( appendedEvent instanceof BlockEvent || appendedEvent instanceof ReboundEvent )
                {
                    return;
                }
            case MISSED:
                if( appendedEvent instanceof BlockEvent || appendedEvent instanceof ReboundEvent )
                {
                    mAppended = appendedEvent;
                    return;
                }
                break;
        }
        super.append( appendedEvent );

    }

    public ShotClass getShotClass()
    {
        return mShotClass;
    }

    public ShotType getShotType()
    {
        return mShotType;
    }

    @Override
    public void resolve()
    {
        switch( mShotType )
        {
            case MADE:
                handleShot( true );
                break;
            case MISSED:
                handleShot( false );
                break;
        }
        if( mAppended != null )
        {
            mAppended.resolve();
        }
    }

    @Override
    public String toString()
    {
        String shotType = mShotType.name().toLowerCase( Locale.getDefault() );
        String shotClass = "";
        switch( mShotClass )
        {
            case FT:
                shotClass = "FT shot. ";
                break;
            case FG_2PT:
                shotClass = "2pt shot. ";
                break;
            case FG_3PT:
                shotClass = "3pt shot. ";
                break;
        }
        String output = mPlayer.getFullName() + " " + shotType + " a " + shotClass;
        if( mAppended != null )
        {
            output = output.concat( mAppended.toString() );
        }
        return output.trim();
    }

    private void handleShot( boolean shotMade )
    {
        switch( mShotClass )
        {
            case FT:
                mPlayer.shootFT( shotMade );
                return;
            case FG_2PT:
                mPlayer.shoot2pt( shotMade );
                return;
            case FG_3PT:
                mPlayer.shoot3pt( shotMade );
                return;
        }
    }
}
