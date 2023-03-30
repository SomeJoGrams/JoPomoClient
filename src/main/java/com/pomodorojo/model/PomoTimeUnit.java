package com.pomodorojo.model;

import java.io.Serializable;
import java.util.Date;

public class PomoTimeUnit implements Serializable {
    private PomoCalendar pomoCalendar;
    private TimeDescription timeDescription;
    private long curMaxTime;
    private String category;

    private Date overallStartDate;

    private Date overallEndDate;

    private boolean finished;

    public PomoTimeUnit(long curMaxTime,String category){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = null;
        this.finished = false;
    }
    public PomoTimeUnit(int curMaxTime,String category,TimeDescription descObj){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = descObj;
        this.finished = false;
    }
    public PomoTimeUnit(int curMaxTime,String category,TimeDescription descObj, Date overallStartDate){
        this.curMaxTime = curMaxTime;
        this.category = category;
        this.timeDescription = descObj;
        this.finished = false;
        this.overallStartDate = overallStartDate;
    }

    public TimeDescription getTimeDescription(){
        return this.timeDescription;
    }
    public void setOverallStartDate(Date overallStartDate) {
        this.overallStartDate = overallStartDate;
    }
    public void setOverallEndDate(Date overallEndDate){
        this.overallEndDate = overallEndDate;
    }
    public void setFinished(boolean finished){
        this.finished = finished;
    }
}
