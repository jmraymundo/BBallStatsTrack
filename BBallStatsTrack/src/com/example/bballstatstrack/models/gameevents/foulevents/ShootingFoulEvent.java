package com.example.bballstatstrack.models.gameevents.foulevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class ShootingFoulEvent extends FoulEvent
{
    public static final String SHOOTING_FOUL_TYPE = "shootingFoulType";

    public static final String SHOOTER = "shooter";

    private ShootingFoulType mShootingFoulType;

    private Player mShooter;

    public ShootingFoulEvent( ShootingFoulType type, Player player, Team team, Player shooter )
    {
        super( FoulType.SHOOTING, player, team );
        mShootingFoulType = type;
        mShooter = shooter;
    }

    public ShootingFoulType getShootingFoulType()
    {
        return mShootingFoulType;
    }

    public Player getShooter()
    {
        return mShooter;
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        if( appendedEvent instanceof ShootEvent )
        {
            switch( mShootingFoulType )
            {
                case NO_AND1:
                    throw new GameEventException( this, mShootingFoulType );
                case AND1:
                    mAppended = appendedEvent;
                    return;
            }
            return;
        }
        super.append( appendedEvent );
    }

    @Override
    public String toString()
    {
        String shootCountText;
        if( ShootingFoulType.NO_AND1 == mShootingFoulType )
        {
            shootCountText = "2 free throws. ";
        }
        else
        {
            shootCountText = "1 free throw. ";
        }
        String output = mPlayer.getFullName() + " committed a shooting foul. " + mShooter.getFullName() + " to shoot "
                + shootCountText;
        if( mAppended != null )
        {
            output = output.concat( mAppended.toString() );
        }
        return output.trim();
    }
}
