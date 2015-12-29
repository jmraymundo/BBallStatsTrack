package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;

public class NonShootingFoulEvent extends FoulEvent
{

    public static final String NON_SHOOTING_FOUL_TYPE = "nonShootingFoulType";

    private NonShootingFoulType mNonShootingFoulType;

    public NonShootingFoulEvent( NonShootingFoulType type, Player player, Team team )
    {
        super( FoulType.NON_SHOOTING, player, team );
        mNonShootingFoulType = type;
    }

    public NonShootingFoulType getNonShootingFoulType()
    {
        return mNonShootingFoulType;
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
            switch( mNonShootingFoulType )
            {
                case NON_PENALTY:
                    return;
                case PENALTY:
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
        String output = mPlayer.getFullName() + " committed a foul. ";
        if( mAppended != null )
        {
            output = output.concat( mAppended.toString() );
        }
        return output.trim();
    }

    @Override
    public void resolve()
    {
        super.resolve();
        mTeam.addFoul();
    }
}
