package com.example.bballstatstrack.models.utils;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Team;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

public class TeamNameWatcher implements TextWatcher
{
    private Team mTeam;

    private String mCurrent = "";

    private Context mContext;

    public TeamNameWatcher( Context context, Team team )
    {
        mContext = context;
        mTeam = team;
    }

    @Override
    public void afterTextChanged( Editable s )
    {
        if( s == null || s.length() == 0 )
        {
            return;
        }
        if( s.length() > 12 )
        {
            s.replace( 0, s.length(), mCurrent );
            Toast.makeText( mContext, mContext.getText( R.string.team_name_character_limit ), Toast.LENGTH_SHORT )
                    .show();
            return;
        }
        mTeam.setName( s.toString() );
    }

    @Override
    public void beforeTextChanged( CharSequence s, int start, int count, int after )
    {
        if( s == null || s.length() == 0 )
        {
            mCurrent = "";
            return;
        }
        mCurrent = s.toString();
    }

    @Override
    public void onTextChanged( CharSequence s, int start, int before, int count )
    {
        // TODO Auto-generated method stub
    }
}
