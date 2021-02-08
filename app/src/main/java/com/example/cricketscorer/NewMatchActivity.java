package com.example.cricketscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewMatchActivity extends AppCompatActivity {

    private Button done, addPlayerA, addPlayerB;
    private EditText teamANameEt, teamBNameEt;
    private ArrayList<Match> matches;
    private Match newMatch;
    private ArrayList<Integer> teamAPlayers, teamBPlayers;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST_A = "playerListA";
    public static final String PLAYER_LIST_B = "playerListB";

    private String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    private String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        Bundle extras = getIntent().getExtras();
        String TeamNameA = extras.getString("TeamNameA");
        String TeamNameB = extras.getString("TeamNameB");
        try {
            teamAPlayers = (ArrayList<Integer>) getIntent().getSerializableExtra("TeamA");
            if(!teamAPlayers.isEmpty()){
                Log.i("TAG2", "teamAPlayers is not null");
                saveTeamDataA();
            }
        } catch (Exception e) { }

        try {
            teamBPlayers = (ArrayList<Integer>) getIntent().getSerializableExtra("TeamB");
            if(!teamBPlayers.isEmpty()){
                Log.i("TAG2", "teamBPlayers is not null");
                saveTeamDataB();
            }

        } catch (Exception e) { }


        Log.i("TAG2",teamAPlayers +".....BEFORE Load Data" +  teamBPlayers +  "......" );
        loadData();
        Log.i("TAG2",teamAPlayers +".....AFTER Load Data" +  teamBPlayers +  "......" );

        done = findViewById(R.id.btn_done);
        addPlayerA = findViewById(R.id.btn_add_player_A);
        addPlayerB = findViewById(R.id.btn_add_player_B);
        teamANameEt = findViewById(R.id.et_team_a_name);
        teamBNameEt = findViewById(R.id.et_team_b_name);

        teamANameEt.setText(TeamNameA);
        teamBNameEt.setText(TeamNameB);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teamAName = teamANameEt.getText().toString();
                String teamBName = teamBNameEt.getText().toString();

                if((teamAName.isEmpty()) || (teamBName.isEmpty())){
                    Toast.makeText(NewMatchActivity.this, "Name Field Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                newMatch = new Match(teamAName, teamBName);
                newMatch.setTime(currentTime);
                newMatch.setDate(currentDate);
                if (teamAPlayers != null){
                    newMatch.setTeamAPlayers(teamAPlayers);
                }
                if (teamBPlayers != null){
                    newMatch.setTeamBPlayers(teamBPlayers);
                }

                matches.add( newMatch );

                teamAPlayers = null;
                teamBPlayers = null;
                saveData();
                saveTeamDataA();
                saveTeamDataB();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        addPlayerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayersListActivity.class);
                intent.putExtra("TeamCode", 1);
                intent.putExtra("TeamNameA", teamANameEt.getText().toString());
                intent.putExtra("TeamNameB", teamBNameEt.getText().toString());
                startActivity(intent);

            }
        });

        addPlayerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayersListActivity.class);
                intent.putExtra("TeamCode", 2);
                intent.putExtra("TeamNameA", teamANameEt.getText().toString());
                intent.putExtra("TeamNameB", teamBNameEt.getText().toString());
                startActivity(intent);

            }
        });


    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonMatch = gson.toJson(matches);
        editor.putString(MATCH_LIST, jsonMatch);
        editor.apply();
    }
    private void saveTeamDataA(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String jsonTeamA = gson.toJson(teamAPlayers);
        editor.putString(PLAYER_LIST_A,jsonTeamA);
        editor.apply();
    }
    private void saveTeamDataB(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String jsonTeamB = gson.toJson(teamBPlayers);
        editor.putString(PLAYER_LIST_B,jsonTeamB);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonMatch = sharedPreferences.getString(MATCH_LIST, null);
        String jsonTeamA = sharedPreferences.getString(PLAYER_LIST_A, null);
        String jsonTeamB = sharedPreferences.getString(PLAYER_LIST_B, null);

        Type matchType = new TypeToken<ArrayList<Match>>() {}.getType();
        Type teamType = new TypeToken<ArrayList<Integer>>() {}.getType();
        matches = gson.fromJson(jsonMatch, matchType);
        teamAPlayers = gson.fromJson(jsonTeamA, teamType);
        teamBPlayers = gson.fromJson(jsonTeamB, teamType);
        if (matches==null){
            matches = new ArrayList<Match>();
        }

    }
}