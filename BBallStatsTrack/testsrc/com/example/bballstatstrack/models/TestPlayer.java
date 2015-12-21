package com.example.bballstatstrack.models;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestPlayer extends TestCase
{
    Player mTestPlayer;

    @Before
    public void setUp()
    {
        int number = 0;
        String firstName = "John";
        String lastName = "Doe";
        mTestPlayer = new Player( number, firstName + " " + lastName );
    }

    @Test
    public void testPlayer()
    {
        assertEquals( 0, mTestPlayer.getNumber() );
        assertEquals( "John Doe", mTestPlayer.getFullName() );
    }

    @Test
    public void testGet2ptFGMiss()
    {
        assertEquals( 0, mTestPlayer.get2ptFGMade() );
        assertEquals( 0, mTestPlayer.get2ptFGMiss() );
        mTestPlayer.shoot2pt( false );
        assertEquals( 0, mTestPlayer.get2ptFGMade() );
        assertEquals( 1, mTestPlayer.get2ptFGMiss() );
    }

    @Test
    public void testGet2ptFGMade()
    {
        assertEquals( 0, mTestPlayer.get2ptFGMade() );
        assertEquals( 0, mTestPlayer.get2ptFGMiss() );
        mTestPlayer.shoot2pt( true );
        assertEquals( 1, mTestPlayer.get2ptFGMade() );
        assertEquals( 0, mTestPlayer.get2ptFGMiss() );
    }

    @Test
    public void testGet3ptFGMiss()
    {
        assertEquals( 0, mTestPlayer.get3ptFGMade() );
        assertEquals( 0, mTestPlayer.get3ptFGMiss() );
        mTestPlayer.shoot3pt( false );
        assertEquals( 0, mTestPlayer.get3ptFGMade() );
        assertEquals( 1, mTestPlayer.get3ptFGMiss() );
    }

    @Test
    public void testGet3ptFGMade()
    {
        assertEquals( 0, mTestPlayer.get3ptFGMade() );
        assertEquals( 0, mTestPlayer.get3ptFGMiss() );
        mTestPlayer.shoot3pt( true );
        assertEquals( 1, mTestPlayer.get3ptFGMade() );
        assertEquals( 0, mTestPlayer.get3ptFGMiss() );
    }

    @Test
    public void testGetFTMiss()
    {
        assertEquals( 0, mTestPlayer.getFTMade() );
        assertEquals( 0, mTestPlayer.getFTMiss() );
        mTestPlayer.shootFT( false );
        assertEquals( 0, mTestPlayer.getFTMade() );
        assertEquals( 1, mTestPlayer.getFTMiss() );
    }

    @Test
    public void testGetFTMade()
    {
        assertEquals( 0, mTestPlayer.getFTMade() );
        assertEquals( 0, mTestPlayer.getFTMiss() );
        mTestPlayer.shootFT( true );
        assertEquals( 1, mTestPlayer.getFTMade() );
        assertEquals( 0, mTestPlayer.getFTMiss() );
    }

    @Test
    public void testGetOffRebound()
    {
        assertEquals( 0, mTestPlayer.getOffRebound() );
        assertEquals( 0, mTestPlayer.getDefRebound() );
        mTestPlayer.makeRebound( true );
        assertEquals( 1, mTestPlayer.getOffRebound() );
        assertEquals( 0, mTestPlayer.getDefRebound() );
    }

    @Test
    public void testGetDefRebound()
    {
        assertEquals( 0, mTestPlayer.getOffRebound() );
        assertEquals( 0, mTestPlayer.getDefRebound() );
        mTestPlayer.makeRebound( false );
        assertEquals( 0, mTestPlayer.getOffRebound() );
        assertEquals( 1, mTestPlayer.getDefRebound() );
    }

    @Test
    public void testGetAssist()
    {
        assertEquals( 0, mTestPlayer.getAssist() );
        mTestPlayer.makeAssist();
        assertEquals( 1, mTestPlayer.getAssist() );
    }

    @Test
    public void testGetTurnover()
    {
        assertEquals( 0, mTestPlayer.getTurnover() );
        mTestPlayer.makeTurnover();
        assertEquals( 1, mTestPlayer.getTurnover() );
    }

    @Test
    public void testGetSteal()
    {
        assertEquals( 0, mTestPlayer.getSteal() );
        mTestPlayer.makeSteal();
        assertEquals( 1, mTestPlayer.getSteal() );
    }

    @Test
    public void testGetBlock()
    {
        assertEquals( 0, mTestPlayer.getBlock() );
        mTestPlayer.makeBlock();
        assertEquals( 1, mTestPlayer.getBlock() );
    }

    @Test
    public void testGetFoulCount()
    {
        assertEquals( 0, mTestPlayer.getFoulCount() );
        mTestPlayer.makeFoul();
        assertEquals( 1, mTestPlayer.getFoulCount() );
    }

    @Test
    public void testGetPlayingTimeSec()
    {
        assertEquals( 0, mTestPlayer.getPlayingTimeSec() );
        mTestPlayer.incrementPlayingTime();
        assertEquals( 1, mTestPlayer.getPlayingTimeSec() );
    }

}
