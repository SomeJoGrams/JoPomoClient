package com.pomodorojo.model;

import java.io.Serializable;

public class TimeDescription implements Serializable {

    private long id;
    private char personalRating; // 0 to 10
    private String description;
    private String reward;
    private TimeKind kind;

    public TimeDescription(){
        this.personalRating = 5;
    }

    public TimeDescription(char personalRating, TimeKind kind){
        this.personalRating = personalRating;
        this.kind = kind;
    }

    public boolean addDescription(String description){
        if (description.length() <= 255){
            this.description = description;
            return true;
        }
        return false;
    }

    public String getDescription(){
        if (this.description == null){
            return "";
        }
        return this.description;
    }

    public boolean addReward(String reward){
        if (reward.length() <= 127){
            this.reward = reward;
            return true;
        }
        return false;
    }

    public String getReward(){
        if (this.reward == null){
            return "";
        }
        return this.reward;
    }

    public char getRating(){
        return this.personalRating;
    }


}
