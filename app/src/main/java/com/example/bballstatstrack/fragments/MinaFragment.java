package com.example.bballstatstrack.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.activities.ListGamesActivity;

/**
 * Created by Jan Michael Raymundo on 12/02/2017.
 */
public class MinaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mina, container, false);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ((ListGamesActivity) getActivity()).setListFragment();
                    return false;
                }
            });
        }
        return view;
    }
}
