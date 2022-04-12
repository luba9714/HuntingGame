package com.example.huntinggame;

public class GameManager {

    private int colHaunted,rowHaunted;
    private int colHunter,rowHunter;
    private int colSnitch,rowSnitch;
    private boolean isEnd=false;
    private String direction="DOWN";
    public static final int MAXROW=5;
    public static final int MAXCOL=3;
    private int score=0;
    private int lives=3;


    public boolean isEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public int getColSnitch() {
        return colSnitch;
    }

    public void setColSnitch(int colSnitch) {
        this.colSnitch = colSnitch;
    }

    public int getRowSnitch() {
        return rowSnitch;
    }

    public void setRowSnitch(int rowSnitch) {
        this.rowSnitch = rowSnitch;
    }


    public int getLives() {
        return lives;
    }

    public void loseLives() {
        lives--;
    }

    public GameManager() {
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getColHaunted() {
        return colHaunted;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setColHaunted(int colHaunted) {
        this.colHaunted = colHaunted;
    }

    public int getRowHaunted() {
        return rowHaunted;

    }

    public int getColHunter() {
        return colHunter;
    }

    public void setColHunter(int colHunter) {
        this.colHunter = colHunter;
    }

    public int getRowHunter() {
        return rowHunter;
    }

    public void setRowHunter(int rowHunter) {
        this.rowHunter = rowHunter;
    }

    public void setRowHaunted(int rowHaunted) {
        this.rowHaunted = rowHaunted;
    }




}

