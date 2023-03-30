package com.pomodorojo.model;

import java.util.ArrayList;

public class PomoData {
    private UserSession currentUserSession;
    private ProgramSettings programSettings;
    private PomoTimer timer;
    private ArrayList<TimeCategory> timeCategories;


    private ArrayList<PomoTimeUnit> timeUnits;

    public PomoData(){
        ArrayList<PomoTimeUnit> timeUnits = new ArrayList<PomoTimeUnit>();
    }

    public ArrayList<TimeCategories> getTimeCategories(){
        return null;
    }

}
