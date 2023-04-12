package com.pomodorojo.model;

import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PomoCalendar implements Serializable {
    private ArrayList<Date> startDates; // stores every end date except the first start date
    private ArrayList<Date> endDates; // stores every start date except the final end date
    private final Date overallStartDate;
    private Date overallEndDate;
    private long timeLength;
    private transient boolean nextDateIsStartDate;

    public PomoCalendar(){ // TODO maybe remove this constructor
        this.startDates = new ArrayList<>();
        this.endDates = new ArrayList<>();
        this.overallStartDate = null;
        this.overallEndDate = null;
        timeLength = this.getTimeLength();
        nextDateIsStartDate = true;
    }
    public PomoCalendar(Date start){
        this.startDates = new ArrayList<>();
        this.endDates = new ArrayList<>();
        this.overallStartDate = start;
        this.overallEndDate = null;
        timeLength = this.getTimeLength();
        nextDateIsStartDate = false;
    }

    public PomoCalendar(Date start, Date end){
        this.startDates = new ArrayList<>();
        this.endDates = new ArrayList<>();
        this.overallStartDate = start;
        this.overallEndDate = end;
        timeLength = this.getTimeLength();
        nextDateIsStartDate = true;
    }

    public Date getStartDate(){
        return overallStartDate;
    }
    public Date getEndDate(){
        return overallEndDate;
    }
    public void endTimeUnit(Clock clock){
        Date finalEndDate;
        if (this.endDates.size() == 0){
            finalEndDate = new Date(clock.instant().toEpochMilli());
        }
        else{
            finalEndDate = this.endDates.remove(this.endDates.size() - 1);
        }
        this.overallEndDate = finalEndDate;
    }

    public void addNextDate(Date date){
        if (nextDateIsStartDate){
            startDates.add(date);
        }
        else{
            endDates.add(date);
        }
        nextDateIsStartDate = !nextDateIsStartDate;
    }

    public void addFinalDate(Date endDate){
        this.overallEndDate = endDate; // TODO check that the size of start enddates is the same
    }

    public long getTimeLength(){
        if (this.overallEndDate == null){
            return 0;
        }
        return this.overallEndDate.getTime() - this.overallStartDate.getTime();
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject(); // somehow the arraylist is not correctly serialized?
    }
    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        if (endDates.size() <= startDates.size())
            nextDateIsStartDate = false;
        else{
            nextDateIsStartDate = true;
        }
        if (startDates.size() == 0){
            nextDateIsStartDate = true;
        }
    }
    @Serial
    private void readObjectNoData()
            throws ObjectStreamException {


    }
}
