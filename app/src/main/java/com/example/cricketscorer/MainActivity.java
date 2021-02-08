package com.example.cricketscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MatchAdapter adapter;
    private ArrayList<Match> matches;
    private ArrayList<Player> players;
    private Match newMatch;
    private Dialog dialogNewMatch;
    private Button save, cancel;
    private EditText teamANameEt, teambNameEt;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST = "PlayerList";

    private String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        dialogNewMatch = new Dialog(MainActivity.this);
        dialogNewMatch.setContentView(R.layout.dialog_new_match);
        dialogNewMatch.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialogNewMatch.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogNewMatch.setCancelable(true);
        dialogNewMatch.getWindow().getAttributes().windowAnimations = R.style.animation;

        save = dialogNewMatch.findViewById(R.id.btn_save);
        cancel = dialogNewMatch.findViewById(R.id.btn_cancel);
        teamANameEt = dialogNewMatch.findViewById(R.id.et_team_a_name);
        teambNameEt = dialogNewMatch.findViewById(R.id.et_team_b_name);

        listView = findViewById(R.id.list_view);

        adapter = new MatchAdapter(this, matches);
        listView.setAdapter(adapter);

        setPlayersData();

        Log.i("TAG3", players+"");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), MatchStartActivity.class);
                intent.putExtra("ItemPosition", position);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameA = teamANameEt.getText().toString();
                String nameB = teambNameEt.getText().toString();

                if((nameA.isEmpty()) || (nameB.isEmpty())){
                    Toast.makeText(MainActivity.this, "Please Enter all Details !", Toast.LENGTH_SHORT).show();
                    return;
                }

                newMatch = new Match(nameA, nameB);
                newMatch.setTime(currentTime);
                newMatch.setDate(currentDate);

                matches.add( newMatch );

                saveData();
                dialogNewMatch.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNewMatch.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.action_clear:
                // COMPLETED (14) Pass in this as the ListItemClickListener to the GreenAdapter constructor
                clearData();
                return true;

            case R.id.action_new_match:
//                Intent intent = new Intent( getApplicationContext(), NewMatchActivity.class);
//                intent.putExtra("TeamNameA", "Team A");
//                intent.putExtra("TeamNameB", "Team B");
//                startActivity(intent);
                dialogNewMatch.show();
                clearPlayerCheckBox();

        }

        return super.onOptionsItemSelected(item);
    }

    private void setPlayersData() {
        if(players==null || players.isEmpty()){
            players = new ArrayList<>();
            players.add(new Player("Dhruv Maddirala"));
            players.add(new Player("Rohit Choudhary"));
            players.add(new Player("Chintamani Rane"));
            players.add(new Player("Sudhir Sawant"));
            players.add(new Player("Shailendra Kambli"));
            players.add(new Player("Shivam Samleti"));
            players.add(new Player("Pranay Yenagandula"));
            players.add(new Player("Prathamesh Sarmalkar"));
            players.add(new Player("Shailesh Gawde"));
            players.add(new Player("Akshay Sirimalla"));
            players.add(new Player("Bhagyaraj Tembulker"));
            players.add(new Player("Bhushan Gawde"));
            players.add(new Player("Kunal Manjrekar"));
            players.add(new Player("Ryan Dsouza"));
            players.add(new Player("Yagnesh Chawda"));
            players.add(new Player("Kishor Mestry"));
            players.add(new Player("Sachin Mestry"));
            players.add(new Player("Pratham Shivalkar"));
            players.add(new Player("Sai Rane"));
            players.add(new Player("Aryan Bhosle"));
            players.add(new Player("Akshat nagda"));
            players.add(new Player("Rugved Phansekar"));
            players.add(new Player("Chinmay Vichare"));
            players.add(new Player("Mupin Dicholkar"));
            players.add(new Player("Sumukh Adepu"));

            Collections.sort(players, Player.PlayerNameComparator);

            saveData();
        }
    }

    private void clearPlayerCheckBox(){
        for (Player p : players){
            p.setCheckBoxState(false);
        }
    }

    private void refresh() {
        listView.setAdapter(new MatchAdapter(this, matches));
    }

    private void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        players.clear();
        setPlayersData();
        String jsonPlayerList = gson.toJson(players);
        editor.putString(PLAYER_LIST, jsonPlayerList);
        editor.apply();

        matches = new ArrayList<Match>();
        saveData();
        refresh();

        Toast.makeText(this, "All Data Cleared", Toast.LENGTH_SHORT).show();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(matches);
        String jsonPlayerList = gson.toJson(players);

        editor.putString(MATCH_LIST, json);
        editor.putString(PLAYER_LIST, jsonPlayerList);
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