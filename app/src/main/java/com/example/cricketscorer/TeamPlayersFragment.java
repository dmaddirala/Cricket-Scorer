package com.example.cricketscorer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cricketscorer.data.Match;
import com.example.cricketscorer.data.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TeamPlayersFragment extends Fragment {

    private ArrayList<String> teamAPlayers = new ArrayList<>();
    private ArrayList<String> teamBPlayers = new ArrayList<>();
    private ArrayList<Match> matches;
    private ArrayList<Player> players;
    private Match currentMatch;

    private ListView listViewTeamA, listViewTeamB;
    private ImageButton editTeamA,editTeamB;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST = "PlayerList";
    private int itemPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getActivity().getIntent().getExtras();
        itemPosition = extras.getInt("ItemPosition");

        View view = inflater.inflate(R.layout.fragment_team_players, container, false);
        loadData();

        currentMatch = matches.get(itemPosition);

        for (int i : currentMatch.getTeamAPlayers()) {
            teamAPlayers.add(getPlayerById(players, i).getName());
        }
        for (int j : currentMatch.getTeamBPlayers()) {
            teamBPlayers.add(getPlayerById(players, j).getName());
        }

        editTeamA = view.findViewById(R.id.btn_edit_team_a);
        editTeamB = view.findViewById(R.id.btn_edit_team_b);
        listViewTeamA = view.findViewById(R.id.list_view_team_a);
        listViewTeamB = view.findViewById(R.id.list_view_team_b);

        if(currentMatch.isMatchStarted()){
            editTeamA.setVisibility(View.GONE);
            editTeamB.setVisibility(View.GONE);
        }else{
            editTeamA.setVisibility(View.VISIBLE);
            editTeamB.setVisibility(View.VISIBLE);
        }

        editTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayersListActivity.class);
                intent.putExtra("TeamCode", 1);
                intent.putExtra("ItemPosition", itemPosition);
                startActivity(intent);
                
            }
        });

        editTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayersListActivity.class);
                intent.putExtra("TeamCode", 2);
                intent.putExtra("ItemPosition", itemPosition);
                startActivity(intent);
            }
        });

        ArrayAdapter adapterTeamA = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teamAPlayers);
        listViewTeamA.setAdapter(adapterTeamA);

        ArrayAdapter adapterTeamB = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teamBPlayers);
        listViewTeamB.setAdapter(adapterTeamB);

        return view ;
    }

    public Player getPlayerById(ArrayList<Player> players, int id){
        Player player;
        for(Player p : players){
            if(p.getPlayerId()==id){
                return p;
            }
        }
        return null;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(matches);
        editor.putString(MATCH_LIST, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE);
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
        Log.i("TAGA", ""+players);

    }
}