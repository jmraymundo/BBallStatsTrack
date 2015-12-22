package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.AwayTeamFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class AddNewTeamActivity extends Activity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_team );
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );

        ActionBar.Tab tab1 = actionBar.newTab().setText( "Away Team" );
        ActionBar.Tab tab2 = actionBar.newTab().setText( "Home Team" );

        Fragment Fragment1Name = new AwayTeamFragment();
        Fragment Fragment2Name = new AwayTeamFragment();

        tab1.setTabListener( new MyTabsListener( Fragment1Name ) );
        tab2.setTabListener( new MyTabsListener( Fragment2Name ) );

        actionBar.addTab( tab1 );
        actionBar.addTab( tab2 );
    }
}

class MyTabsListener implements ActionBar.TabListener
{
    public Fragment mFragment;

    public MyTabsListener( Fragment fragment )
    {
        mFragment = fragment;
    }

    @Override
    public void onTabReselected( Tab tab, FragmentTransaction ft )
    {
        // do what you want when tab is reselected, I do nothing
    }

    @Override
    public void onTabSelected( Tab tab, FragmentTransaction ft )
    {
        ft.replace( R.id.newTeamContainer, mFragment );
    }

    @Override
    public void onTabUnselected( Tab tab, FragmentTransaction ft )
    {
        ft.remove( mFragment );
    }
}
