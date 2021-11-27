package com.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<Player> {
    private final Activity context;
    private final ArrayList<Player> players;

    public PlayerListAdapter(Activity context, ArrayList<Player> players) {
        super(context, R.layout.list_row, players);
        this.context=context;
        this.players = players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listRowView = inflater.inflate(R.layout.list_row, null,true);

        TextView playerName = listRowView.findViewById(R.id.txtPlayerName);
        TextView playerWins = listRowView.findViewById(R.id.txtPlayerWins);
        TextView playerLosses = listRowView.findViewById(R.id.txtPlayerLosses);
        TextView playerTies = listRowView.findViewById(R.id.txtPlayerTies);

        Player currentPlayer = players.get(position);
        playerName.setText(currentPlayer.getName());
        playerWins.setText(String.valueOf(currentPlayer.getWins()));
        playerLosses.setText(String.valueOf(currentPlayer.getLosses()));
        playerTies.setText(String.valueOf(currentPlayer.getTies()));

        return listRowView;
    }
}
