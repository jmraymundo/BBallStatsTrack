package com.example.bballstatstrack.models.gameevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class FoulEvent extends GameEvent
{
    public static final String FOUL_TYPE = "foulType";

    private FoulType mType;

    protected FoulEvent( FoulType type, Player player, Team team )
    {
        super( EventType.FOUL, player, team );
        mType = type;
    }

    @Override
    public void resolve()
    {
        mPlayer.makeFoul();
        mTeam.addFoul();
        if( mAppended != null )
        {
            mAppended.resolve();
        }
    }

    public FoulType getFoulType()
    {
        return mType;
    }

    @Override
    public String toString()
    {
        return null;
    }

}
