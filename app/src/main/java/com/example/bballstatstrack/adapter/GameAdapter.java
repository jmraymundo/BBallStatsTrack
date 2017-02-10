package com.example.bballstatstrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bballstatstrack.R;
import com.example.bballstatstrack.models.Game;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<Game> {
    public GameAdapter(ArrayList<Game> games, Context context) {
        super(context, android.R.layout.simple_list_item_1, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_games, parent, false);
        }
        Game game = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.game_list_item_titleTextView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.game_list_item_dateTextView);
            convertView.setTag(holder);
        }
        holder.titleTextView.setText(game.getTitle());
        holder.dateTextView.setText(game.getDate().toString());

        return convertView;
    }

    private class ViewHolder {
        TextView titleTextView;

        TextView dateTextView;
    }
}
