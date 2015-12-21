package com.example.bballstatstrack.models.gameevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class SubstitutionEvent extends GameEvent
{
    public static final String NEW_PLAYER_NUMBER = "newPlayer";

    Player mNewPlayer;

    public SubstitutionEvent( Player playerOut, Player playerIn, Team team )
    {
        super( Event.SUBSTITUTION, playerOut, team );
        mNewPlayer = playerIn;
    }

    @Override
    public void resolveEvent()
    {
        mTeam.substitutePlayer( mNewPlayer, mPlayer );
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put( NEW_PLAYER_NUMBER, mNewPlayer.getNumber() );
        return jsonObject;
    }

    @Override
    public String toString()
    {
        return "Substitution by " + mTeam.getName() + ". " + mPlayer.getFullName() + " replaced by "
                + mNewPlayer.getFullName() + ".";
    }
}
