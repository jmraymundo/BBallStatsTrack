package com.example.bballstatstrack.models.gameevents.foulevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;

public class ShootingFoulEvent extends FoulEvent
{
    private static final String THREE_FREE_THROWS = "3 free throws. ";

    private static final String TWO_FREE_THROWS = "2 free throws. ";

    private static final String ONE_FREE_THROW = "1 free throw. ";

    public static final String SHOOTER = "shooter";

    public static final String FT_COUNT = "free_throw_count";

    private Player mShooter;

    private int mFTCount;

    public ShootingFoulEvent( Player player, Team team, Player shooter, int ftCount )
    {
        super( FoulType.SHOOTING, player, team );
        mShooter = shooter;
        mFTCount = ftCount;
    }

    public Player getShooter()
    {
        return mShooter;
    }

    public int getFTCount()
    {
        return mFTCount;
    }

    @Override
    public String toString()
    {
        String output = mPlayer.getFullName() + " committed a shooting foul. ";
        if( mAppended != null )
        {
            String additional = mShooter.getFullName() + " to shoot ";
            switch( mFTCount )
            {
                case 1:
                    additional = additional.concat( ONE_FREE_THROW );
                    break;
                case 2:
                    additional = additional.concat( TWO_FREE_THROWS );
                    break;
                case 3:
                    additional = additional.concat( THREE_FREE_THROWS );
                    break;
                default:
                    break;
            }
            output = output.concat( additional );
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
