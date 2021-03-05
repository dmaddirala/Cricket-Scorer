package com.example.cricketscorer.data;

import java.util.ArrayList;

public class Over {
    ArrayList<Integer> runs;
    int overNumber;
    int bowlerId;
    int currentBall;

    public Over(int overNumber){
        this.runs = new ArrayList<>();
        this.overNumber = overNumber;
        this.currentBall = 0;
    }

    public ArrayList<Integer> getRuns() {
        return runs;
    }

    public void addRun(int run) {
        this.runs.add(run);
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public void setCurrentBall(int currentBall) { this.currentBall = currentBall; }

    public int getCurrentBall() { return currentBall; }

    public void incrementCurrentBall(){
        this.currentBall += 1;
    }

    public void decrementCurrentBall(){
        this.currentBall -= 1;
    }

}
