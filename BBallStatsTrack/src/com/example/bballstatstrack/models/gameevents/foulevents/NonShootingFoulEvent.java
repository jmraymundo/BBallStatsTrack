package com.example.bballstatstrack.models.gameevents.foulevents;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bballstatstrack.models.Player;
import com.example.bballstatstrack.models.Team;
import com.example.bballstatstrack.models.gameevents.FoulEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent;
import com.example.bballstatstrack.models.gameevents.ShootEvent;
import com.example.bballstatstrack.models.gameevents.GameEvent.FoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.NonShootingFoulType;
import com.example.bballstatstrack.models.gameevents.GameEvent.ShootingFoulType;
import com.example.bballstatstrack.models.gameevents.exceptions.GameEventException;

public class NonShootingFoulEvent extends FoulEvent
{

    public static final String NON_SHOOTING_FOUL_TYPE = "nonShootingFoulType";

    private NonShootingFoulType mNonShootingFoulType;

    public NonShootingFoulEvent( NonShootingFoulType type, Player player, Team team )
    {
        super( FoulType.NON_SHOOTING, player, team );
        mNonShootingFoulType = type;
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
            switch( mNonShootingFoulType )
            {
                case NON_PENALTY:
                    throw new GameEventException( this, mNonShootingFoulType );
                case PENALTY:
                    mAppended = appendedEvent;
                    return;
            }
            return;
        }
        super.append( appendedEvent );
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put( NON_SHOOTING_FOUL_TYPE, mNonShootingFoulType );
        return jsonObject;
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
}
