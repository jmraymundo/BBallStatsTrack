package com.example.bballstatstrack.activities;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.ListGamesFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class ListGamesActivity extends Activity
{
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fragment );
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById( R.id.fragmentContainer );

        if( fragment == null )
        {
            fragment = createFragment();
            manager.beginTransaction().add( R.id.fragmentContainer, fragment ).commit();
        }
    }

    protected Fragment createFragment()
    {
        return new ListGamesFragment();
    }

}
