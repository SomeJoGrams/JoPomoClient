package com.pomodorojo.model;

import java.io.Serializable;
import java.util.Date;

public class PomoCalendar implements Serializable {
    private Date startDate;
    private Date endDate;

    private long timeLength;

    public PomoCalendar(Date start, Date end){
        this.startDate = start;
        this.endDate = start;
        this.timeLength = end.getTime() - start.getTime();
    }

    public long getTimeLength(){
        return timeLength;
    }
}
