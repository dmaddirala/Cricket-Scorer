package com.example.cricketscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlayersListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Match> matches;
    private ArrayList<Player> players;
    private PlayerAdapter adapter;
    private String TeamNameA, TeamNameB;
    private int teamCode;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST = "PlayerList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        Bundle extras = getIntent().getExtras();
        teamCode = extras.getInt("TeamCode");
        TeamNameA = extras.getString("TeamNameA");
        TeamNameB = extras.getString("TeamNameB");

        loadData();
        listView = findViewById(R.id.list_view);
        adapter = new PlayerAdapter(this, players);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_done:

                ArrayList<Integer> teamPlayers = getPlayerList();
                Intent intent = new Intent( getApplicationContext(), NewMatchActivity.class);

                if (teamCode==1){
                    intent.putExtra("TeamA", teamPlayers);
                }else if(teamCode==2){
                    intent.putExtra("TeamB", teamPlayers);
                }
                Log.i("TAG", teamPlayers+" ");
                intent.putExtra("TeamNameA", TeamNameA);
                intent.putExtra("TeamNameB", TeamNameB);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Integer> getPlayerList(){

        ArrayList<Integer> teamPlayers = new ArrayList<>();
        for(int i=0 ; i< adapter.getCount(); i++){

//            ViewGroup linearLayout = (LinearLayout) listView.getChildAt(i);
//            CheckBox playerCheckBox = (CheckBox) linearLayout.getChildAt(0);

            Player currentPlayer = (Player) adapter.getItem(i);
            String name = currentPlayer.getName();

            if (currentPlayer.getCheckBoxState()){
//                Log.i("TAG", name + " is Checked");
                teamPlayers.add(i);

            }
        }
        return teamPlayers;
    }

    private void refresh() {
        listView.setAdapter(new MatchAdapter(this, matches));
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(matches);
        editor.putString(MATCH_LIST, json);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MATCH_LIST, null);
        String jsonPlayerList = sharedPreferences.getString(PLAYER_LIST, null);

        Type matchType = new TypeToken<ArrayList<Match>>() {}.getType();
        Type playerListType = new TypeToken<ArrayList<Player>>() {}.getType();

        matches = gson.fromJson(json, matchType);
        players = gson.fromJson(jsonPlayerList, playerListType);
        if (matches==null){
            matches = new ArrayList<Match>();
        }

    }
}