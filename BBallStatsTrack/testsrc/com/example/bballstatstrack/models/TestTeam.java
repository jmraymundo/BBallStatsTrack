package com.example.bballstatstrack.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import junit.framework.TestCase;

public class TestTeam extends TestCase
{
    Team mTeam;

    @Before
    public void setUp()
    {
        mTeam = new Team();
        mTeam.setName( "LA Lakers" );
        Player one = new Player( 1, "Player One" );
        Player two = new Player( 2, "Player Two" );
        Player three = new Player( 3, "Player Three" );
        Player four = new Player( 4, "Player Four" );
        Player five = new Player( 5, "Player Five" );
        mTeam.addPlayer( one );
        mTeam.addPlayer( two );
        mTeam.addPlayer( three );
        mTeam.addPlayer( four );
        mTeam.addPlayer( five );
    }
}
