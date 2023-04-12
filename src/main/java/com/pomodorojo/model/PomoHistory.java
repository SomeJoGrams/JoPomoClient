package com.pomodorojo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PomoHistory implements Serializable {
    private Date loadedFrom;
    private Date loadedUntil;
    private Calendar calendar;
    private HashMap<Day,ArrayList<PomoTimeUnit>> pomoTimeUnitHashMap;
    public PomoHistory(){
        calendar = Calendar.getInstance(); // todo set timezone for testing
        pomoTimeUnitHashMap = new HashMap<>();
    }

    public void addTimeUnit(PomoTimeUnit timeUnit){
        calendar.setTime(timeUnit.getPomoCalendar().getStartDate());
        Day day = new Day(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        ArrayList<PomoTimeUnit> pomoTimeUnitArrayList = pomoTimeUnitHashMap.computeIfAbsent(day, k -> new ArrayList<>());
        pomoTimeUnitArrayList.add(timeUnit);
            //timeUnit.getPomoCalendar().getStartDate()

    }



}
