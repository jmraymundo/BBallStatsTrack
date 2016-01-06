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
        if( null == convertView )
        {
            convertView = mActivity.getLayoutInflater().inflate( R.layout.list_games, null );
        }
        Game game = getItem( position );
        ViewHolder holder = ( ViewHolder ) convertView.getTag();
        if( holder == null )
        {
            holder = new ViewHolder();
            holder.titleTextView = ( TextView ) convertView.findViewById( R.id.game_list_item_titleTextView );
            holder.dateTextView = ( TextView ) convertView.findViewById( R.id.game_list_item_dateTextView );
            convertView.setTag( holder );
        }
        holder.titleTextView.setText( game.getTitle() );
        holder.dateTextView.setText( game.getDate().toString() );

        return convertView;
    }

    private class ViewHolder
    {
        TextView titleTextView;

        TextView dateTextView;
    }
}
