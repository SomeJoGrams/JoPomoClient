package com.pomodorojo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PomoData implements Serializable {
    private UserSession currentUserSession;
    private ProgramSettings programSettings;
    private PomoTimer timer;
    private ArrayList<TimeCategory> timeCategories;
    private HashMap<Date,ArrayList<PomoTimeUnit>> timeUnits;// the day is mapped to the pomodoros of the day
    private TimeCategory currentCategory;
    public UserSession getCurrentUserSession() {
        return currentUserSession;
    }
    public ProgramSettings getProgramSettings() {
        return programSettings;
    }
    public PomoTimer getTimer() {
        return timer;
    }
    public HashMap<Date,ArrayList<PomoTimeUnit>> getTimeUnits() {
        return timeUnits;
    }

    public PomoData(){
        timeUnits = new HashMap<>();// TODO load recent pomodoros
        timeCategories.add(new TimeCategory("standard",null));
    }

    public ArrayList<TimeCategory> getTimeCategories(){
        return timeCategories;
    }

}
