package com.example.cricketscorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {

    Context context;
    ArrayList<Player> players;

    public PlayerAdapter(Activity context, ArrayList<Player> players) {
        super(context, 0, players);
        this.context = context;
        this.players = players;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.player_list_view_item, parent, false);
        }

        final Player currentPlayer = getItem(position);

        final CheckBox playerCheckBox = listItemView.findViewById(R.id.cb_player);
        playerCheckBox.setText(currentPlayer.getName());
        playerCheckBox.setChecked(currentPlayer.getCheckBoxState());

        playerCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setCheckBoxState(playerCheckBox.isChecked());
            }
        });

        return listItemView;
    }
}
