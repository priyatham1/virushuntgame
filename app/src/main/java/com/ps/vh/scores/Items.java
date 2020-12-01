package com.ps.vh.scores;

public class Items {

    private String name, score, picUrl;

    public Items() {
    }

    public Items(String name, String score, String picUrl) {

        this.picUrl = picUrl;
        this.name = name;
        this.score = score;

    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
