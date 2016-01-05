package com.example.bballstatstrack.models.utils;

public class StringUtils
{
    public static String getPeriodString( int period )
    {
        switch( period )
        {
            case 0:
                return "1st Quarter";
            case 1:
                return "2nd Quarter";
            case 2:
                return "3rd Quarter";
            case 3:
                return "4th Quarter";
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
