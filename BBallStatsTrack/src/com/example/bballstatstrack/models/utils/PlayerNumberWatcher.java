package com.example.bballstatstrack.models.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class PlayerNumberWatcher implements TextWatcher
{
    String mCurrent = "";

    private final int MIN;

    private final int MAX;

    public PlayerNumberWatcher( int min, int max )
    {
        MIN = min;
        MAX = max;
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

    @Override
    public void afterTextChanged( Editable s )
    {
        if( s == null || s.length() == 0 )
        {
            return;
        }
        int input = Integer.parseInt( s.toString() );
        if( inRange( input ) )
        {
            return;
        }
        s.replace( 0, s.length(), mCurrent );
    }

    private boolean inRange( int input )
    {
        return MIN <= input && input <= MAX;
    }
}