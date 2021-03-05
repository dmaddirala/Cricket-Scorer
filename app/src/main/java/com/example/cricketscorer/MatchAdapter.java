package com.example.cricketscorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cricketscorer.data.Match;

import java.util.ArrayList;

public class MatchAdapter extends ArrayAdapter<Match> {

    Context context;
    ArrayList<Match> matches;

    public MatchAdapter(Activity context, ArrayList<Match> matches) {
        super(context, 0, matches);
        this.context = context;
        this.matches = matches;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_item, parent, false);
        }

        final Match currentMatch = getItem(position);

        final TextView matchName = (TextView) listItemView.findViewById(R.id.tv_match_name);
        final TextView serialNumber = (TextView) listItemView.findViewById(R.id.tv_serial_number);
        final TextView dateTv = (TextView) listItemView.findViewById(R.id.tv_date);
        final TextView timeTv = (TextView) listItemView.findViewById(R.id.tv_time);

        String matchNameString = currentMatch.getTeamAName() + "   vs   " + currentMatch.getTeamBName();
        String currentTime = currentMatch.getTime();
        String currentDate = currentMatch.getDate();

        dateTv.setText(currentDate);
        timeTv.setText("-"+currentTime);
        matchName.setText(matchNameString);
        serialNumber.setText( (position+1) + ") " );

        return listItemView;
    }
}
