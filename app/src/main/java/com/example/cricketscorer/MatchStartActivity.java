package com.example.cricketscorer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cricketscorer.data.Match;
import com.example.cricketscorer.data.Player;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MatchStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView teamANameTv, teamBNameTv;
    private ArrayList<Match> matches;
    private Match currentMatch;
    private int itemPosition;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_start);
        Bundle extras = getIntent().getExtras();
        itemPosition = extras.getInt("ItemPosition");
        loadData();
        currentMatch = matches.get(itemPosition);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.match_start_menu);

        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        teamANameTv = navigationView.getHeaderView(0).findViewById(R.id.tv_team_A);
        teamBNameTv = navigationView.getHeaderView(0).findViewById(R.id.tv_team_B);

        teamANameTv.setText("Dhruv1");
        teamBNameTv.setText("Rohit1");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TeamPlayersFragment()).commit();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_team_players:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TeamPlayersFragment()).commit();
                break;

            case R.id.nav_score_calculate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ScoreCalculateFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
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

        Type matchType = new TypeToken<ArrayList<Match>>() {}.getType();

        matches = gson.fromJson(json, matchType);
        if (matches==null){
            matches = new ArrayList<Match>();
        }

    }

}
