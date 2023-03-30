package com.pomodorojo.model;

import java.io.Serializable;

public class TimeDescription implements Serializable {

    private long id;
    private int personalRating; // 0 to 10
    private String description;
    private String reward;
//    private TimeKind kind;

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

    public int getRating(){
        return this.personalRating;
    }

    public void setPersonalRating(int i) {
        if (i > 10){
            this.personalRating = 10;
            return;
        }
        if (i < 0){
            this.personalRating = 0;
            return;
        }
        this.personalRating = i;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public void setDescription(String description) {
        if (description.length() > 255){
            description = description.substring(0,255); // TODO give an user warning only display 255 symobls max
        }
        this.description = description;
    }
}
