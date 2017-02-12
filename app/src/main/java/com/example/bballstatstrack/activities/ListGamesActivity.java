package com.example.bballstatstrack.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.fragments.ListGamesFragment;
import com.example.bballstatstrack.fragments.MinaFragment;

public class ListGamesActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setImageFragment();
    }

    private void setImageFragment() {
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        Fragment imageFragment = createImageFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (fragment == null) {
            fragmentTransaction.add(R.id.fragmentContainer, imageFragment);
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, imageFragment);
        }
        fragmentTransaction.commit();
    }

    public void setListFragment() {
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        Fragment listFragment = createListFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if (fragment == null) {
            fragmentTransaction.add(R.id.fragmentContainer, listFragment);
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, listFragment);
        }
        fragmentTransaction.commit();
    }

    protected Fragment createListFragment() {
        return new ListGamesFragment();
    }

    protected Fragment createImageFragment() {
        return new MinaFragment();
    }

}
