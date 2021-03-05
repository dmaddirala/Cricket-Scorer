package com.example.cricketscorer.data;

import java.util.ArrayList;

public class Match {
    private String teamAName;
    private String teamBName;
    private ArrayList<Integer> teamAPlayers;
    private ArrayList<Integer> teamBPlayers;

    private ArrayList<Over> firstInningOvers;
    private ArrayList<Over> secondInningOvers;
    private boolean matchStarted;
    private int innings;
    private int inningScore;
    private int targetScore;
    private String currentBattingTeam, currentBowlingTeam;
    private String date;
    private String time;

    public Match(String teamAName, String teamBName){
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        matchStarted = false;
        innings = 0;
        firstInningOvers = new ArrayList<>();
        secondInningOvers = new ArrayList<>();
        teamAPlayers = new ArrayList<>();
        teamBPlayers = new ArrayList<>();

    }

    public void incrementInningScore(int run) { this.inningScore += run; }

    public void decrementInningScore(int run) { this.inningScore -= run; }

    public int getInningScore() { return inningScore; }

    public void setInningScore(int inningScore) { this.inningScore = inningScore; }

    public int getTargetScore() { return targetScore; }

    public void setTargetScore(int targetScore) { this.targetScore = targetScore; }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamAPlayers(ArrayList<Integer> teamAPlayers) { this.teamAPlayers = teamAPlayers; }

    public void setTeamBPlayers(ArrayList<Integer> teamBPlayers) { this.teamBPlayers = teamBPlayers; }

    public void addTeamAPlayers(ArrayList<Integer> teamAPlayers) { this.teamAPlayers.addAll(teamAPlayers); }

    public void addTeamBPlayers(ArrayList<Integer> teamBPlayers) { this.teamBPlayers.addAll(teamBPlayers); }

    public String getCurrentBattingTeam() { return currentBattingTeam; }

    public void setCurrentBattingTeam(String currentBattingTeam) { this.currentBattingTeam = currentBattingTeam; }

    public String getCurrentBowlingTeam() { return currentBowlingTeam; }

    public void setCurrentBowlingTeam(String currentBowlingTeam) { this.currentBowlingTeam = currentBowlingTeam; }

    public ArrayList<Integer> getTeamAPlayers() { return teamAPlayers; }

    public ArrayList<Integer> getTeamBPlayers() { return teamBPlayers; }

    public int getInnings() { return innings; }

    public void setInnings(int innings) { this.innings = innings; }

    public boolean isMatchStarted() { return matchStarted; }

    public void setMatchStarted(boolean matchStarted) { this.matchStarted = matchStarted; }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Over> getFirstInningOvers() { return firstInningOvers; }

    public ArrayList<Over> getSecondInningOvers() { return secondInningOvers; }
}
