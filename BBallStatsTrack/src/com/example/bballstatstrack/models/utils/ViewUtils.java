package com.example.bballstatstrack.models.utils;

import com.example.bballstatstrack.listeners.touch.HideKeyboardOnTouchListener;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ViewUtils
{
    public static void setupKeyboardHiding( Activity activity, View view )
    {
        if( view instanceof EditText )
        {
            return;
        }
        else if( view instanceof ViewGroup )
        {
            int childCount = ( ( ViewGroup ) view ).getChildCount();
            for( int i = 0; i < childCount; i++ )
            {
                View innerView = ( ( ViewGroup ) view ).getChildAt( i );
                setupKeyboardHiding( activity, innerView );
            }
        }
        view.setOnTouchListener( new HideKeyboardOnTouchListener( activity ) );
    }
}
