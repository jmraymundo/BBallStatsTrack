package com.example.bballstatstrack.listeners;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.AddPlayersToTeamsActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;

public class FragmentButtonListener implements OnClickListener
{
    private AddPlayersToTeamsActivity mActivity;

    private Fragment mFragment;

    public FragmentButtonListener( AddPlayersToTeamsActivity activity, Fragment fragment )
    {
        mActivity = activity;
        mFragment = fragment;
    }

    @Override
    public void onClick( View v )
    {
        if( mFragment.equals( getCurrentFragment() ) )
        {
            return;
        }
        mActivity.replaceCurrentFragment( mFragment );
        mActivity.updateView( mFragment );
    }

    private Fragment getCurrentFragment()
    {
        FragmentManager manager = mActivity.getFragmentManager();
        return manager.findFragmentById( R.id.fragmentContainer );
    }
}