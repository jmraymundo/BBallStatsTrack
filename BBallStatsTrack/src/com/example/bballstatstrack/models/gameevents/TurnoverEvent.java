package com.example.bballstatstrack.models.gameevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;
import com.example.bballstatstrack.models.gameevents.foulevents.OffensiveFoulEvent;

public class TurnoverEvent extends GameEvent
{
    public static final String TURNOVER_TYPE = "turnoverType";

    private TurnoverType mTurnoverType;

    public TurnoverEvent( TurnoverType type, Player player, Team team )
    {
        super( Event.TURNOVER, player, team );
        mTurnoverType = type;
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( mAppended != null )
        {
            mAppended.append( appendedEvent );
            return;
        }
        if( appendedEvent instanceof StealEvent && mTurnoverType == TurnoverType.STEAL )
        {
            mAppended = appendedEvent;
            return;
        }
        else if( appendedEvent instanceof OffensiveFoulEvent && mTurnoverType == TurnoverType.OFFENSIVE_FOUL )
        {
            FoulType foulType = ( ( FoulEvent ) appendedEvent ).getFoulType();
            if( foulType == FoulType.OFFENSIVE )
            {
                mAppended = appendedEvent;
                return;
            }
        }
        super.append( appendedEvent );
    }

    @Override
    public void resolveEvent()
    {
        mPlayer.makeTurnover();
        if( mAppended != null )
        {
            mAppended.resolveEvent();
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put( TURNOVER_TYPE, mTurnoverType );
        return jsonObject;
    }

    @Override
    public String toString()
    {
        String output = "Turnover made by " + mPlayer.getFullName() + ". ";
        if( mAppended != null )
        {
            output = output.concat( mAppended.toString() );
        }
        return output.trim();
    }
}
