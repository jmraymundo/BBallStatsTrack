package com.example.bballstatstrack.models.gameevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class ReboundEvent extends GameEvent
{
    public static final String REBOUND_TYPE = "reboundType";

    ReboundType mReboundType;

    public ReboundEvent( ReboundType type, Player player, Team team )
    {
        super( Event.REBOUND, player, team );
        mReboundType = type;
    }

    @Override
    public void resolveEvent()
    {
        switch( mReboundType )
        {
            case OFFENSIVE:
                mPlayer.makeRebound( true );
                return;
            case DEFENSIVE:
                mPlayer.makeRebound( false );
                return;
            case TEAM_REBOUND:
                mTeam.makeTeamRebound();
                return;
        }
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put( REBOUND_TYPE, mReboundType );
        return jsonObject;
    }

    @Override
    public String toString()
    {
        String type = "";
        String rebounder = "";
        switch( mReboundType )
        {
            case OFFENSIVE:
                type = "Offensive";
                rebounder = mPlayer.getFullName();
                break;
            case DEFENSIVE:
                type = "Defensive";
                rebounder = mPlayer.getFullName();
                break;
            case TEAM_REBOUND:
                type = "Team";
                rebounder = mTeam.getName();
                break;
        }
        return type + " rebound by " + rebounder + ".";
    }
}
