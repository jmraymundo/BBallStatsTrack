package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class ShootEvent extends GameEvent
{
    ShotClass mShotClass;

    ShotType mShotType;

    public ShootEvent( ShotClass shotClass, ShotType shotType, Player player, Team team )
    {
        super( Event.SHOOT, player, team );
        mShotClass = shotClass;
        mShotType = shotType;
    }

    @Override
    public void resolveEvent()
    {
        switch( mShotType )
        {
            case MADE:
                handleShot( true );
                return;
            case MISSED:
                handleShot( false );
                return;
        }
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
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

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( appendedEvent instanceof BlockEvent || appendedEvent instanceof ReboundEvent )
        {
            switch( mShotType )
            {
                case MADE:
                    throw new GameEventException( this, mShotType );
                case MISSED:
                    mAppended = appendedEvent;
                    return;
            }
        }
        else
        {
            super.append( appendedEvent );
        }
    }
}
