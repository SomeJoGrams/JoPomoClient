package com.pomodorojo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PomoTimeUnit implements Serializable {
    private PomoCalendar pomoCalendar;
    private TimeDescription timeDescription;
    private int curMaxUnits;
    private final long curMaxTime; // this should not be able to be changed during units
    private String category;
    private boolean finished;


    public PomoTimeUnit(long curMaxTime,String category){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = null;
        this.finished = false;
        this.pomoCalendar = new PomoCalendar();
    }
    public PomoTimeUnit(long curMaxTime,String category,TimeDescription descObj){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = descObj;
        this.finished = false;
        this.pomoCalendar = new PomoCalendar();
    }
    public PomoTimeUnit(long curMaxTime,String category,TimeDescription descObj, Date overallStartDate){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = descObj;
        this.finished = false;
        this.pomoCalendar = new PomoCalendar(overallStartDate);
    }

    public PomoTimeUnit(int curMaxTime,String category,TimeDescription descObj, Date overallStartDate,Date overallEndDate){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = descObj;
        this.finished = false;
        this.pomoCalendar = new PomoCalendar(overallStartDate,overallEndDate);
    }

    public void removeEmptyTimeDescription(){
        if (this.timeDescription != null && this.timeDescription.isEmpty()){
                this.timeDescription = null;
        }
    }

    public TimeDescription getTimeDescription(){
        return this.timeDescription;
    }
    public PomoCalendar getPomoCalendar(){ return this.pomoCalendar;}
    public void setFinished(boolean finished){
        this.finished = finished;
    }

}
