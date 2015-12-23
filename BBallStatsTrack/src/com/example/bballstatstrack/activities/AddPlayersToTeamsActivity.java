package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.TeamFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class AddPlayersToTeamsActivity extends Activity
{
    SparseArray< String > mHomeTeam;

    SparseArray< String > mAwayTeam;

    Fragment mHomeTeamFragment;

    Fragment mAwayTeamFragment;

    Button mHomeButton;

    Button mAwayButton;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_team );
        mHomeTeamFragment = new TeamFragment( true );
        mAwayTeamFragment = new TeamFragment( false );
        mHomeButton = ( Button ) findViewById( R.id.homeTeamButton );
        mHomeButton.setOnClickListener( new setFragmentButtonListener( mHomeTeamFragment ) );
        mAwayButton = ( Button ) findViewById( R.id.awayTeamButton );
        mAwayButton.setOnClickListener( new setFragmentButtonListener( mAwayTeamFragment ) );
        replaceCurrentFragment( mHomeTeamFragment );
    }

    private void replaceCurrentFragment( Fragment fragment )
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace( R.id.fragmentContainer, fragment );
        transaction.commit();
    }

    private Fragment getCurrentFragment()
    {
        FragmentManager manager = getFragmentManager();
        return manager.findFragmentById( R.id.fragmentContainer );
    }

    private class setFragmentButtonListener implements OnClickListener
    {
        Fragment mFragment;

        public setFragmentButtonListener( Fragment fragment )
        {
            mFragment = fragment;
        }

        @Override
        public void onClick( View v )
        {
            if( mFragment.equals( getCurrentFragment() ) )
            {
                return;
            }
            replaceCurrentFragment( mFragment );
        }
    }
}