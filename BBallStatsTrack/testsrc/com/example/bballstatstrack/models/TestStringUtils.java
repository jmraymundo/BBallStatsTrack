package com.example.bballstatstrack.models;

import org.junit.Test;

import com.example.bballstatstrack.models.utils.StringUtils;

import junit.framework.TestCase;

public class TestStringUtils extends TestCase
{

    @Test
    public void testGetShotFraction()
    {
        assertEquals( "1/1", StringUtils.getShotFraction( 1, 0 ) );
        assertEquals( "3/4", StringUtils.getShotFraction( 3, 1 ) );
        assertEquals( "1/2", StringUtils.getShotFraction( 1, 1 ) );
        assertEquals( "1/4", StringUtils.getShotFraction( 1, 3 ) );
        assertEquals( "0/1", StringUtils.getShotFraction( 0, 1 ) );
        assertEquals( "0/0", StringUtils.getShotFraction( 0, 0 ) );
    }

    @Test
    public void testGetShotPercentage()
    {
        assertEquals( "100%", StringUtils.getShotPercentage( 1, 0 ) );
        assertEquals( "75%", StringUtils.getShotPercentage( 3, 1 ) );
        assertEquals( "50%", StringUtils.getShotPercentage( 1, 1 ) );
        assertEquals( "25%", StringUtils.getShotPercentage( 1, 3 ) );
        assertEquals( "0%", StringUtils.getShotPercentage( 0, 1 ) );
        assertEquals( "n/a", StringUtils.getShotPercentage( 0, 0 ) );
    }

    @Test
    public void testGetTimeMillisFromMinSecFormattedString()
    {
        assertEquals( 0, StringUtils.getIntSecondsFromMinSecFormattedString( "00:00" ) );
        assertEquals( 30, StringUtils.getIntSecondsFromMinSecFormattedString( "00:30" ) );
        assertEquals( 60, StringUtils.getIntSecondsFromMinSecFormattedString( "01:00" ) );
        assertEquals( 90, StringUtils.getIntSecondsFromMinSecFormattedString( "01:30" ) );
        assertEquals( 120, StringUtils.getIntSecondsFromMinSecFormattedString( "02:00" ) );
        assertEquals( 150, StringUtils.getIntSecondsFromMinSecFormattedString( "02:30" ) );
        assertEquals( 180, StringUtils.getIntSecondsFromMinSecFormattedString( "03:00" ) );
    }

}
