package com.example.bballstatstrack.adapter;

import java.util.ArrayList;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Game;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameAdapter extends ArrayAdapter< Game >
{
    Activity mActivity;

    public GameAdapter( ArrayList< Game > games, Activity activity )
    {
        super( activity, android.R.layout.simple_list_item_1, games );
        mActivity = activity;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        // if we weren't given a view, inflate one
        if( null == convertView )
        {
            convertView = mActivity.getLayoutInflater().inflate( R.layout.list_games, null );
        }

        // configure the view for this Crime
        Game game = getItem( position );

        TextView titleTextView = ( TextView ) convertView.findViewById( R.id.game_list_item_titleTextView );
        titleTextView.setText( game.getTitle() );
        TextView dateTextView = ( TextView ) convertView.findViewById( R.id.game_list_item_dateTextView );
        dateTextView.setText( game.getDate().toString() );

        return convertView;
    }
}
