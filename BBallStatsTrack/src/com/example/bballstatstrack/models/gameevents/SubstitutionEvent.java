package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;

public class SubstitutionEvent extends GameEvent
{
    public static final String NEW_PLAYER_NUMBER = "newPlayer";

    private Player mNewPlayer;

    public SubstitutionEvent( Player playerOut, Player playerIn, Team team )
    {
        super( EventType.SUBSTITUTION, playerOut, team );
        mNewPlayer = playerIn;
    }

    public Player getNewPlayer()
    {
        return mNewPlayer;
    }

    @Override
    public void resolve()
    {
        mTeam.substitutePlayer( mNewPlayer, mPlayer );
        if( mAppended != null )
        {
            mAppended.resolve();
        }
    }

    @Override
    public String toString()
    {
        return "Substitution by " + mTeam.getName() + ". " + mNewPlayer.getFullName() + " enters the game for "
                + mPlayer.getFullName() + ".";
    }
}
