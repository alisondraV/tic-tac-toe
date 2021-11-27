package com.example.tictactoe;

public class Player {
    private long id;
    private String name;
    private int wins;
    private int losses;
    private int ties;

    public Player() {
    }

    public Player(long id, String name, int wins, int losses, int ties) {
        this.id = id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    @Override
    public String toString() {
        return "Player " + id + ": " + name;
    }
}
