package com.example.bballstatstrack.models.utils;

public class StringUtils
{
    public static String getPeriodString( int period )
    {
        switch( period )
        {
            case 0:
                return "1st";
            case 1:
                return "2nd";
            case 2:
                return "3rd";
            case 3:
                return "4th";
            case 4:
                return "OT";
            default:
                return ( period - 4 ) + " OT";
        }
    }

    public static String getMinSecFormattedString( int time )
    {
        int minutes = time / 60;
        int seconds = time - ( minutes * 60 );
        return getLeadZeroFormattedString( minutes ) + ":" + getLeadZeroFormattedString( seconds );
    }

    public static String getLeadZeroFormattedString( int number )
    {
        if( number < 10 )
        {
            return "0" + number;
        }
        return String.valueOf( number );
    }
}
