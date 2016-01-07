package com.example.bballstatstrack.models.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils
{
    public static int getIntSecondsFromMinSecFormattedString( String input )
    {
        String[] elements = input.split( ":" );
        int seconds = Integer.parseInt( elements[1] );
        int minutes = Integer.parseInt( elements[0] );
        return seconds + ( minutes * 60 );
    }

    public static String getLeadZeroFormattedString( int number )
    {
        if( number < 10 )
        {
            return "0" + number;
        }
        return String.valueOf( number );
    }

    public static String getMinSecFormattedString( int time )
    {
        int minutes = time / 60;
        int seconds = time - ( minutes * 60 );
        return getLeadZeroFormattedString( minutes ) + ":" + getLeadZeroFormattedString( seconds );
    }

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

    public static String getShotFraction( int made, int miss )
    {
        int total = made + miss;
        return made + "/" + total;
    }

    public static String getShotPercentage( double made, double miss )
    {
        double total = made + miss;
        if( total == 0 )
        {
            return "n/a";
        }
        double value = ( made / total ) * 100;
        DecimalFormat df = new DecimalFormat( "###.##" );
        df.setRoundingMode( RoundingMode.CEILING );
        return df.format( value ) + "%";
    }

    public static String getStringDate( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        int year = calendar.get( Calendar.YEAR ) % 100; // last 2 digits only
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );
        return getLeadZeroFormattedString( year ) + getLeadZeroFormattedString( month )
                + getLeadZeroFormattedString( day );
    }
}
