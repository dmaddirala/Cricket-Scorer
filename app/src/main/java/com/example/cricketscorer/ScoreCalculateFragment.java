package com.example.cricketscorer;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cricketscorer.data.Match;
import com.example.cricketscorer.data.Over;
import com.example.cricketscorer.data.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoreCalculateFragment extends Fragment{

    private LinearLayout beforeMatchLinearLayout;
    private ArrayList<String> teamAPlayers = new ArrayList<>();
    private ArrayList<String> teamBPlayers = new ArrayList<>();
    private ArrayList<Match> matches;
    private ArrayList<Player> players;
    private Match currentMatch;
    private  Over currentOver;
    private Button startMatchBtn;
    private int itemPosition;
    private Dialog dialogBattingBowling;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn0;
    private Button btnNb,btnWd,btnOverthrow,btnOut, btnUndo;
    private Button batting, bowling;
    private Button endInningsBtn;
    private TextView runsWicketsTv, currentOverRunsTv, totalOversTv;
    private TextView choiceTv;
    private TextView inningsTv;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String MATCH_LIST = "matchList";
    public static final String PLAYER_LIST = "PlayerList";
    public static final int NB = 7;
    public static final int OUT = 9;
    public static final int WD = 8;
    public static final int OVERTHROW = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadData();
        Bundle extras = getActivity().getIntent().getExtras();
        itemPosition = extras.getInt("ItemPosition");

        View view = inflater.inflate(R.layout.fragment_score_calculate, container, false);
        beforeMatchLinearLayout = view.findViewById(R.id.ly_before_start);
        startMatchBtn = view.findViewById(R.id.btn_start_match);
        runsWicketsTv = view.findViewById(R.id.tv_runs_wickets);
        currentOverRunsTv = view.findViewById(R.id.tv_over_runs);
        totalOversTv = view.findViewById(R.id.tv_overs);
        btnUndo = view.findViewById(R.id.btn_undo);
        inningsTv = view.findViewById(R.id.tv_innings);
        endInningsBtn = view.findViewById(R.id.btn_end_innings);

        dialogBattingBowling = new Dialog(getContext());
        dialogBattingBowling.setContentView(R.layout.dialog_batting_bowling);
        dialogBattingBowling.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.background));
        dialogBattingBowling.setCancelable(true);
        dialogBattingBowling.getWindow().getAttributes().windowAnimations = R.style.animation;

        batting = dialogBattingBowling.findViewById(R.id.btn_batting);
        bowling = dialogBattingBowling.findViewById(R.id.btn_bowling);
        choiceTv = dialogBattingBowling.findViewById(R.id.tv_choice);

        btn0 = view.findViewById(R.id.btn_0);
        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        btn4 = view.findViewById(R.id.btn_4);
        btn5 = view.findViewById(R.id.btn_5);
        btn6 = view.findViewById(R.id.btn_6);
        btnNb = view.findViewById(R.id.btn_nb);
        btnWd = view.findViewById(R.id.btn_wd);
        btnOut = view.findViewById(R.id.btn_out);
        btnOverthrow = view.findViewById(R.id.btn_overthrow);


        currentMatch = matches.get(itemPosition);
        runsWicketsTv .setText(""+currentMatch.getInningScore());

        int innings = currentMatch.getInnings();
        if(innings==0){
            inningsTv.setVisibility(View.GONE);
        }else  if(innings == 1){
            inningsTv.setVisibility(View.VISIBLE);
            inningsTv.setText("1st innings");
        }else{
            inningsTv.setVisibility(View.VISIBLE);
            inningsTv.setText("2nd innings\n\nTarget: "+currentMatch.getTargetScore());
        }


        int size = currentMatch.getFirstInningOvers().size();
        if(size==0){
            currentMatch.getFirstInningOvers().add(new Over(1));
            currentOver = currentMatch.getFirstInningOvers().get(0);
        }else{
            currentOver = currentMatch.getFirstInningOvers().get(size-1);
        }

        if (matches.get(itemPosition).isMatchStarted()) {
            beforeMatchLinearLayout.setVisibility(View.GONE);
        } else {
            beforeMatchLinearLayout.setVisibility(View.VISIBLE);
        }

        batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMatch.setCurrentBattingTeam(currentMatch.getTeamAName());
                currentMatch.setCurrentBowlingTeam(currentMatch.getTeamBName());
                currentMatch.setInnings(1);
                beforeMatchLinearLayout.setVisibility(View.GONE);
                matches.get(itemPosition).setMatchStarted(true);
                saveData();
                dialogBattingBowling.dismiss();

            }
        });

        bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMatch.setCurrentBattingTeam(currentMatch.getTeamBName());
                currentMatch.setCurrentBowlingTeam(currentMatch.getTeamAName());
                currentMatch.setInnings(1);
                beforeMatchLinearLayout.setVisibility(View.GONE);
                matches.get(itemPosition).setMatchStarted(true);
                saveData();
                dialogBattingBowling.dismiss();

            }
        });

        endInningsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endInnings();
            }
        });

        startMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceTv.setText(currentMatch.getTeamAName() +" Chose to ?" );
                dialogBattingBowling.show();

            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(0);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(4);
            }
        });btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(5);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(6);
            }
        });
        btnNb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(NB);
            }
        });
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(OUT);
            }
        });
        btnWd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(WD);
            }
        });
        btnOverthrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(OVERTHROW);
            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int overSize = currentMatch.getFirstInningOvers().size();
                if(currentOver.getRuns().size()==0){
                    if ((overSize-2)<0){
                        Toast.makeText(getContext(), "Cannot undo anymore", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    currentMatch.getFirstInningOvers().remove(overSize-1);
                    overSize -= 1;
                    currentOver = currentMatch.getFirstInningOvers().get(overSize-1);

                }else{
                    int runSize = currentOver.getRuns().size();
                    int lastElement = currentOver.getRuns().get(runSize-1);
                    currentOver.getRuns().remove(runSize-1);
                    currentMatch.decrementInningScore(lastElement);
                    if (lastElement!=NB && lastElement!=WD) {
                        currentOver.decrementCurrentBall();
                    }
                }

                refresh();
            }
        });



        refresh();
        return view;
    }

    @Override
    public void onPause() {
        refresh();
        saveData();
        super.onPause();
    }

    @Override
    public void onStop() {
        refresh();
        saveData();
        super.onStop();
    }

    void refresh(){
        String runs = "";
        for(int i : currentOver.getRuns()){
            if (i==NB){
                runs = runs + "  NB";
            }else if(i==WD){
                runs = runs + "  WD";
            }else if(i==OUT){
                runs = runs + "  OUT";
            }else if(i==OVERTHROW){
                runs = runs + "  OT";
            }else{
                runs = runs + "  " + i;
            }
        }
        int size = currentMatch.getFirstInningOvers().size()-1;
        String overs = "("+size+"."+currentOver.getCurrentBall()+" overs)";
        totalOversTv.setText(overs);
        currentOverRunsTv.setText(runs);
        runsWicketsTv .setText(""+currentMatch.getInningScore());

        int innings = currentMatch.getInnings();
        if(innings==2){
            int targetScore = currentMatch.getTargetScore();
            int currentScore = currentMatch.getInningScore();
            if(currentScore >= targetScore){
                Toast.makeText(getContext(), "Batting team Wins the match", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void updateScore(int cellid){

        if(currentOver.getCurrentBall() >5){
            int size = currentMatch.getFirstInningOvers().size();
            currentMatch.getFirstInningOvers().add(new Over( size+1) );
            currentOver = currentMatch.getFirstInningOvers().get(size);
            refresh();
            return;
        }

        switch(cellid){
            case 0:
                currentMatch.incrementInningScore(0);
                currentOver.addRun(0);
                currentOver.incrementCurrentBall();
                break;
            case 1:
                currentMatch.incrementInningScore(1);
                currentOver.addRun(1);
                currentOver.incrementCurrentBall();
                break;
            case 2:
                currentMatch.incrementInningScore(2);
                currentOver.addRun(2);
                currentOver.incrementCurrentBall();
                break;
            case 3:
                currentMatch.incrementInningScore(3);
                currentOver.addRun(3);
                currentOver.incrementCurrentBall();
                break;
            case 4:
                currentMatch.incrementInningScore(4);
                currentOver.addRun(4);
                currentOver.incrementCurrentBall();
                break;
            case 5:
                currentMatch.incrementInningScore(5);
                currentOver.addRun(5);
                currentOver.incrementCurrentBall();
                break;
            case 6:
                currentMatch.incrementInningScore(6);
                currentOver.addRun(6);
                currentOver.incrementCurrentBall();
                break;
            case NB:
                currentMatch.incrementInningScore(1);
                currentOver.addRun(NB);
                break;
            case WD:
                currentMatch.incrementInningScore(1);
                currentOver.addRun(WD);
                break;
            case OUT:
                currentOver.addRun(OUT);
                currentOver.incrementCurrentBall();
                break;
            case OVERTHROW:
                currentOver.addRun(OVERTHROW);
                break;
            default:
                break;

        }

        if(currentOver.getCurrentBall() >5){
            int size = currentMatch.getFirstInningOvers().size();
            currentMatch.getFirstInningOvers().add(new Over( size+1) );
            currentOver = currentMatch.getFirstInningOvers().get(size);
            refresh();
        }
        refresh();
        saveData();

    }

    private void endInnings(){
        int innings = currentMatch.getInnings();
        String battingTeamName, bowlingTeamName;
        if (currentMatch.getCurrentBattingTeam().equals(currentMatch.getTeamAName())){
            battingTeamName = currentMatch.getTeamAName();
            bowlingTeamName = currentMatch.getTeamBName();
        }else{
            bowlingTeamName = currentMatch.getTeamAName();
            battingTeamName = currentMatch.getTeamBName();
        }

        if(innings==1){
            currentMatch.setTargetScore(currentMatch.getInningScore());
            currentMatch.setInnings(2);
            currentMatch.setInningScore(0);
            inningsTv.setVisibility(View.VISIBLE);
            inningsTv.setText("2nd innings\n\nTarget: "+currentMatch.getTargetScore());
            refresh();

        }else{
            Toast.makeText(getContext(), bowlingTeamName+" Wins the Match", Toast.LENGTH_SHORT).show();
        }
//        endInningsBtn.setVisibility(View.GONE);
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

    }

}