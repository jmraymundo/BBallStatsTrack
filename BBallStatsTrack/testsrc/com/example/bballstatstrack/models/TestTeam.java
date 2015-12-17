package com.example.bballstatstrack.models;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestTeam
{
    Team mTeam;

    @Before
    public void before()
    {
        String name = "LA Lakers";
        List< Player > playerList = new ArrayList< Player >();
        Player one = new Player( 1, "Player", "One" );
        Player two = new Player( 2, "Player", "Two" );
        Player three = new Player( 3, "Player", "Three" );
        Player four = new Player( 4, "Player", "Four" );
        Player five = new Player( 5, "Player", "Five" );
        playerList.add( one );
        playerList.add( two );
        playerList.add( three );
        playerList.add( four );
        playerList.add( five );
        mTeam = new Team( name, playerList );
    }

    @Test
    public void test()
    {
        fail( "Not yet implemented" );
    }

}
