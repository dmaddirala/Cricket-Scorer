package com.example.cricketscorer;

import java.util.ArrayList;

public class Match {
    private String teamAName;
    private String teamBName;
    private ArrayList<Integer> teamAPlayers;
    private ArrayList<Integer> teamBPlayers;
    private String date;
    private String time;

    public Match(String teamAName, String teamBName){
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        teamAPlayers = new ArrayList<>();
        teamBPlayers = new ArrayList<>();

    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamAName(String teamAName) { this.teamAName = teamAName; }

    public void setTeamBName(String teamBName) { this.teamBName = teamBName; }

    public void setTeamAPlayers(ArrayList<Integer> teamAPlayers) { this.teamAPlayers = teamAPlayers; }

    public void setTeamBPlayers(ArrayList<Integer> teamBPlayers) { this.teamBPlayers = teamBPlayers; }

    public ArrayList<Integer> getTeamAPlayers() { return teamAPlayers; }

    public ArrayList<Integer> getTeamBPlayers() { return teamBPlayers; }

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
}
