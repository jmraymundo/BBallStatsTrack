package com.example.bballstatstrack.models;

import org.junit.Test;

import com.example.bballstatstrack.models.utils.StringUtils;

import junit.framework.TestCase;

public class TestStringUtils extends TestCase
{

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
