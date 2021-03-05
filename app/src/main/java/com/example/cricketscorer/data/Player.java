package com.example.cricketscorer.data;

import java.util.Comparator;

public class Player {
    String name;
    boolean checkBoxState;
    int playerId;

    public Player(String name, int playerId){
        this.name = name;
        this.playerId = playerId;

    }
    /*Comparator for sorting the list by Student Name*/
    public static Comparator<Player> PlayerNameComparator = new Comparator<Player>() {

        public int compare(Player p1, Player p2) {
            String PlayerName1 = p1.getName().toUpperCase();
            String PlayerName2 = p2.getName().toUpperCase();

            //ascending order
            return PlayerName1.compareTo(PlayerName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    @Override
    public String toString() {
        return "[name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean getCheckBoxState() {
        return checkBoxState;
    }

    public void setCheckBoxState(boolean checkBoxState) {
        this.checkBoxState = checkBoxState;
    }
}
