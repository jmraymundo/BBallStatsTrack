package com.example.bballstatstrack.models.gameevents;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class TurnoverEvent extends GameEvent
{

    private TurnoverType mTurnoverType;

    public TurnoverEvent( TurnoverType type, Player player, Team team )
    {
        super( Event.TURNOVER, player, team );
        mTurnoverType = type;
        resolveEvent();
    }

    @Override
    public void append( GameEvent appendedEvent ) throws GameEventException
    {
        if( appendedEvent instanceof StealEvent && mTurnoverType == TurnoverType.STEAL )
        {
            mAppended = appendedEvent;
            return;
        }
        else if( appendedEvent instanceof FoulEvent && mTurnoverType == TurnoverType.OFFENSIVE_FOUL )
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
    }
}
