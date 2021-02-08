package com.example.cricketscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TeamDetailsActivity extends AppCompatActivity {

    private ArrayList<String> teamAPlayers = new ArrayList<>();
    private ArrayList<String> teamBPlayers = new ArrayList<>();
    private ArrayList<Match> matches;
    private ArrayList<Player> players;

    private ListView listViewTeamA, listViewTeamB;
    private ImageButton editTeamA, editTeamB;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST = "PlayerList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_start);
        Bundle extras = getIntent().getExtras();
        int itemPosition = extras.getInt("ItemPosition");

        loadData();

        for (int i : matches.get(itemPosition).getTeamAPlayers()) {
            teamAPlayers.add(players.get(i).getName());
        }
        for (int j : matches.get(itemPosition).getTeamBPlayers()) {
            teamBPlayers.add(players.get(j).getName());
        }

        listViewTeamA = findViewById(R.id.list_view_team_a);
        listViewTeamB = findViewById(R.id.list_view_team_b);
        editTeamA = findViewById(R.id.btn_edit_team_a);
        editTeamB = findViewById(R.id.btn_edit_team_b);

        editTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamDetailsActivity.this, "Editing A", Toast.LENGTH_SHORT).show();
            }
        });

        editTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamDetailsActivity.this, "Editing B", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter adapterTeamA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamAPlayers);
        listViewTeamA.setAdapter(adapterTeamA);

        ArrayAdapter adapterTeamB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamBPlayers);
        listViewTeamB.setAdapter(adapterTeamB);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(matches);
        editor.putString(MATCH_LIST, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MATCH_LIST, null);
        String jsonPlayerList = sharedPreferences.getString(PLAYER_LIST, null);

        Type matchType = new TypeToken<ArrayList<Match>>() {
        }.getType();
        Type playerListType = new TypeToken<ArrayList<Player>>() {
        }.getType();

        matches = gson.fromJson(json, matchType);
        players = gson.fromJson(jsonPlayerList, playerListType);

        if (matches == null) {
            matches = new ArrayList<Match>();
        }

    }
}