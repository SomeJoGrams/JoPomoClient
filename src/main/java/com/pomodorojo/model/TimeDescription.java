package com.pomodorojo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TimeDescription implements Serializable {

    private long id;
    private SimpleIntegerProperty personalRating; // 0 to 10
    private SimpleStringProperty description;
    private SimpleStringProperty reward;
    private static final int ratingBase = 5;
//    private TimeKind kind;
    // TODO idea add simple tags, as many as you want! category should also be able
    // to hold tags

    public TimeDescription(String descBase, String rewardBase){
        personalRating =  new SimpleIntegerProperty(ratingBase);
        description = new SimpleStringProperty(descBase);
        reward = new SimpleStringProperty(rewardBase);
    }

//    public TimeDescription(int personalRating, TimeKind kind){
//        personalRating =  new SimpleIntegerProperty(5);
//        description = new SimpleStringProperty("");
//        reward = new SimpleStringProperty("");
//    }

    public boolean addDescription(String description){
        if (description.length() <= 255){
            this.description.set(description);
            return true;
        }
        return false;
    }

    public String getDescription(){
        if (this.description == null){
            return "";
        }
        return this.description.get();
    }

    public boolean addReward(String reward){
        if (reward.length() <= 127){
            this.reward.set(reward);
            return true;
        }
        return false;
    }

    public String getReward(){
        if (this.reward.get() == null){
            return "";
        }
        return this.reward.get();
    }

    public int getRating(){
        return this.personalRating.get();
    }

    public void setPersonalRating(int i) {
        if (i > 10){
            this.personalRating.set(10);
            return;
        }
        if (i < 0){
            this.personalRating.set(0);
            return;
        }
        this.personalRating.set(i);
    }

    public void setReward(String reward) {
        this.reward.set(reward);
    }

    public void setDescription(String description) {
        if (description.length() > 255){
            description = description.substring(0,255); // TODO give an user warning only display 255 symobls max
        }
        this.description.set(description);
    }

    public StringProperty getDescriptionProperty(){
        return this.description;
    }
    public StringProperty getRewardProperty(){
        return this.reward;
    }
    public IntegerProperty getRatingProperty(){
        return this.personalRating;
    }

    public boolean isEmpty(){
        return this.description.get().isEmpty() &&
                this.personalRating.get() == 5 &&
                this.reward.get().isEmpty();
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject();
        out.writeObject(description.get());
        out.writeObject(personalRating.get());
        out.writeObject(reward.get());

    }
    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        description = new SimpleStringProperty((String)in.readObject());
        personalRating = new SimpleIntegerProperty((int)in.readObject());
        reward = new SimpleStringProperty((String)in.readObject());
    }
    @Serial
    private void readObjectNoData(){
        description = new SimpleStringProperty("");
        personalRating = new SimpleIntegerProperty(5);
        reward = new SimpleStringProperty("");


    }

}
