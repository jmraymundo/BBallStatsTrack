package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class ShootingFoulEvent extends FoulEvent
{
    private static final String THREE_FREE_THROWS = "3 free throws. ";

    private static final String TWO_FREE_THROWS = "2 free throws. ";

    private static final String ONE_FREE_THROW = "1 free throw. ";

    public static final String SHOOTER = "shooter";

    private Player mShooter;

    public ShootingFoulEvent( Player player, Team team, Player shooter )
    {
        super( FoulType.SHOOTING, player, team );
        mShooter = shooter;
    }

    public Player getShooter()
    {
        return mShooter;
    }

    @Override
    public void append( GameEvent appendedEvent )
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        if( appendedEvent instanceof ShootEvent )
        {
            Player player = appendedEvent.getPlayer();
            if( mShooter != player )
            {
                return;
            }
            mAppended = appendedEvent;
            return;
        }
        super.append( appendedEvent );
    }

    @Override
    public String toString()
    {
        String output = mPlayer.getFullName() + " committed a shooting foul. ";
        if( mAppended != null )
        {
            ShotType shotType = ( ( ShootEvent ) mAppended ).getShotType();
            ShotClass shotClass = ( ( ShootEvent ) mAppended ).getShotClass();
            String additional = mShooter.getFullName() + " to shoot ";
            if( shotType == ShotType.MADE )
            {
                additional = additional.concat( ONE_FREE_THROW );
                output = output.concat( additional );
                output = output.concat( mAppended.toString() );
            }
            else if( shotClass == ShotClass.FG_2PT )
            {
                additional = additional.concat( TWO_FREE_THROWS );
                output = output.concat( additional );
            }
            else
            {
                additional = additional.concat( THREE_FREE_THROWS );
                output = output.concat( additional );
            }
        }
        return output.trim();
    }

    @Override
    public void resolve()
    {
        if( mAppended == null )
        {
            return;
        }
        super.resolve();
        mTeam.addFoul();
    }
}
