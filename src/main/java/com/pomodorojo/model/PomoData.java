package com.pomodorojo.model;

import java.io.Serializable;
import java.time.Clock;
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
    private PomoClock pomoClock;
    public PomoTimeUnit currentTimeUnit;
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
        // only the start?
        return timeUnits;
    }



    public PomoData(){
        timeUnits = new HashMap<>();// TODO load recent pomodoros
        timeCategories = new ArrayList<>(); // TODO load time categories
        timeCategories.add(new TimeCategory("standard",null)); // TODO set default categories
        currentTimeUnit = new PomoTimeUnit(timer.getCurrentMaxTime(),timeCategories.get(0).getCategoryString());
        pomoClock = new PomoClock();
    }

    public PomoClock getPomoClock() {
        return pomoClock;
    }

    public void setCurrentTimeUnit(PomoTimeUnit currentTimeUnit) {
        this.currentTimeUnit = currentTimeUnit;
    }

    public ArrayList<TimeCategory> getTimeCategories(){
        return timeCategories;
    }

}
