package com.pomodorojo.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class PomoTimer implements Serializable{

    private Date sessionStartDate; // keep the duration of the session on the server and in the settings as seconds!
    private transient SimpleIntegerProperty currentSessionUnit;
    private transient SimpleIntegerProperty maximumSessionUnits;
    private transient StringProperty displayedTime;

    public boolean isDuringBreak;
    public boolean isDuringLongBreak;
    private boolean isPaused;
    private Duration currentMaxTime; // given in minutes
    private Duration currentShortBreakTime;
    private Duration currentLongBreakTime;
    private Duration currentPausedDuration;
    private Date lastPausedMeasuredDate;
    private Date pauseStartDate;


    public PomoTimer(Date currentSessionTime, boolean isPaused,long currentMaxTime,long currentShortBreakTime
            ,long currentLongBreakTime,int currentSessionUnit,Duration currentPauseDuration,int maximumSessionUnits){
        this.sessionStartDate = Date.from(currentSessionTime.toInstant());
        this.isPaused = isPaused;
        this.currentMaxTime = Duration.ofSeconds(currentMaxTime);
        this.currentShortBreakTime = Duration.ofSeconds(currentShortBreakTime);
        this.currentLongBreakTime = Duration.ofSeconds(currentLongBreakTime);
        this.currentSessionUnit = new SimpleIntegerProperty(currentSessionUnit);
        this.currentPausedDuration = currentPauseDuration;
        this.maximumSessionUnits = new SimpleIntegerProperty(maximumSessionUnits);
        lastPausedMeasuredDate = new Date();
        this.displayedTime = new SimpleStringProperty();
    }

    public PomoTimer(){
        this.sessionStartDate = null;
        this.isPaused = true;
        this.currentMaxTime = Duration.ofMinutes(25); //Duration.ofSeconds(currentMaxTime);
        this.currentShortBreakTime =  Duration.ofMinutes(5);
        this.currentLongBreakTime =  Duration.ofMinutes(15);
        this.currentSessionUnit = new SimpleIntegerProperty(0);
        this.currentPausedDuration = Duration.ofSeconds(0);
        this.maximumSessionUnits = new SimpleIntegerProperty(3);
        this.displayedTime = new SimpleStringProperty();
        lastPausedMeasuredDate = null;
    }

    public void setCurrentPausedDuration(Duration currentPausedDuration) {
        this.currentPausedDuration = currentPausedDuration;
    }

    public void setDuringPause(boolean duringPause) {
        isDuringBreak = duringPause;
    }

    public void setDuringLongPause(boolean duringLongPause) {
        isDuringLongBreak = duringLongPause;
    }

    public void setMaximumSessionUnits(int maximumSessionUnits){
        // TODO maybe put the maximum check here? like an absolute maximum of 500 idk
        this.maximumSessionUnits.set(maximumSessionUnits);
    }



    public void startSession(Clock clock){
        return;
    }

    public void setCurrentMaxTime(Duration currentMaxTime) {
        this.currentMaxTime = currentMaxTime;
    }

    public void setCurrentShortBreakTime(Duration currentShortBreakTime) {
        this.currentShortBreakTime = currentShortBreakTime;
    }

    public void setCurrentLongBreakTime(Duration currentLongBreakTime) {
        this.currentLongBreakTime = currentLongBreakTime;
    }

    public StringProperty getDisplayedTimeProperty(){
        return this.displayedTime;
    }

    private void setCurrentSessionUnit(int currentSessionUnit){
        this.currentSessionUnit.set(currentSessionUnit);
    }
    public Date getTime(){
        return sessionStartDate;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public long getCurrentMaxTime() {
        return currentMaxTime.getSeconds();
    }

    public IntegerProperty getMaxProperty(){
        return this.maximumSessionUnits;
    }

    public IntegerProperty getUnitProperty(){
        return this.currentSessionUnit;
    }

    /**
     * check for the end of a time "session"
     * create a duration from clock from the stored start to the now,
     * check whether the duration is longer or equal to the max time
     * also checks the phase according to a pomodoro
     * @param clock
     * @return whether the current maximum time is over the time limit of time
     */
    public boolean timeLimitReached(Clock clock){
        int limitReached;
        Date curDate = Date.from(clock.instant());
        if (sessionStartDate == null){
            return false;
        }
        Duration currentDuration = Duration.between(sessionStartDate.toInstant(),curDate.toInstant()).minus(currentPausedDuration);
        if (!isDuringBreak && !isDuringLongBreak){
            limitReached = currentDuration.compareTo(currentMaxTime);// f.e. the current duration is less -> negative value
        }
        else if (isDuringBreak && !isDuringLongBreak){
            limitReached = currentDuration.compareTo(currentShortBreakTime);
        }
        else{// is DuringLongPause
            limitReached = currentDuration.compareTo(currentLongBreakTime);
        }
        return limitReached >= 0;
    }

    public void next(Clock clock){
        Date curDate = Date.from(clock.instant());
        currentPausedDuration = Duration.ZERO;
        lastPausedMeasuredDate = null;
        sessionStartDate = curDate;
        if (!isDuringBreak && !isDuringLongBreak){
            // either go into the long or the short pause depending on the amount of sessionUnits
            if (currentSessionUnit.get() == maximumSessionUnits.get()){
                currentSessionUnit.set(0);
                isDuringBreak = false;
                isDuringLongBreak = true;
            }
            else{
                if (isPaused && currentSessionUnit.get() == 0){ // start state
                    System.out.println("in start state");
                    this.getUnitProperty().set(this.getUnitProperty().get() + 1);
                }
                else{
//                if (this.getUnitProperty().get() == 0){
                    isDuringBreak = true; // TODO maybe remove going into the break here
                }
            }
        }
        else if (isDuringBreak && !isDuringLongBreak){
            isDuringBreak = false;
            currentSessionUnit.set(currentSessionUnit.get() + 1);
        }
        else {// is DuringLongPause
            isDuringLongBreak = false;
            currentSessionUnit.set(currentSessionUnit.get() + 1);
        }
        isPaused = false;

    }

    /**
     * if we are currently in a break or paused during the break this allows us to continue
     */
    public void skipBreak(Clock clock){
        this.next(clock);
//        if (isDuringBreak || isDuringLongBreak){
//            this.next(clock);
//        }
//        else{
//            // TODO think about a thing that could be done here perhaps just dont add the skipped time!
//        }
//                if (isDuringBreak || isDuringLongBreak){
//                    isDuringBreak = false;
//                    isDuringLongBreak = false;
//                }
//                if (isPaused){
//                    isPaused = false;
//                    currentPausedDuration = Duration.ZERO;
//
//                }
    }


    public void togglePaused(Clock clock) {
//        if (!isPaused){
//            //safe the startDate on pause
//            pauseStartDate = Date.from(clock.instant());
//            isPaused = true;
//            return;
//        }
        // safe the stopDate on rerun
//        Date pauseEndDate = Date.from(clock.instant());
//        // calculate the pausedDuration and fix the timer durations accordingly
//        if (pauseStartDate != null){
//            currentPausedDuration = currentPausedDuration.plus(Duration.between(pauseStartDate.toInstant(),pauseEndDate.toInstant()));
//        }
//        pauseStartDate = null;
        if (isPaused){
            lastPausedMeasuredDate = null;
        }


        isPaused = !isPaused;
    }

    public void calculateCurrentSessionTime(Clock clock){ // TODO remove the duplicated code
        Date curDate = Date.from(clock.instant());
        Duration currentDuration;
        if (sessionStartDate == null){
            initSessionTime(clock); // draw the amx time
            return;
        }
        if (isDuringBreak){
            if (!isPaused) {
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentShortBreakTime.minus(currentDuration);
            }
            else {
                if (lastPausedMeasuredDate == null) {
                    lastPausedMeasuredDate = curDate; // stores the date of when the pause started and was measured the last time
                }
                currentPausedDuration = currentPausedDuration.plus(Duration.between(lastPausedMeasuredDate.toInstant(), curDate.toInstant()));
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentShortBreakTime.minus(currentDuration);
                lastPausedMeasuredDate = curDate;
            }
        }
        else if (isDuringLongBreak){
            if (!isPaused){
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentLongBreakTime.minus(currentDuration); // todo also add the the paused duration!
            }
            else {
                if (lastPausedMeasuredDate == null) {
                    lastPausedMeasuredDate = curDate; // stores the date of when the pause started and was measured the last time
                }
                currentPausedDuration = currentPausedDuration.plus(Duration.between(lastPausedMeasuredDate.toInstant(), curDate.toInstant()));
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentLongBreakTime.minus(currentDuration);
                lastPausedMeasuredDate = curDate;
            }
        }
        else {
            if (!isPaused) {
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentMaxTime.minus(currentDuration);
            } else {
                // during the pause increase the pause duration by the passed time, calculate the currentDuration with it
                // time should stay with the same value
                if (lastPausedMeasuredDate == null) {
                    lastPausedMeasuredDate = curDate; // stores the date of when the pause started and was measured the last time
                }
                currentPausedDuration = currentPausedDuration.plus(Duration.between(lastPausedMeasuredDate.toInstant(), curDate.toInstant()));
                currentDuration = Duration.between(sessionStartDate.toInstant(), curDate.toInstant()).minus(currentPausedDuration);
                currentDuration = currentMaxTime.minus(currentDuration);
                lastPausedMeasuredDate = curDate;
            }
        }
        updateDisplayedTime(clock,currentDuration);
    }

    public void initSessionTime(Clock clock){// TODO should also load old values
        updateDisplayedTime(clock,currentMaxTime);
    }

    public void updateDisplayedTime(Clock clock, Duration duration){
        StringBuilder stringBuilder = new StringBuilder();
        String zeroString = "0";
        String minutes = String.valueOf(duration.toMinutesPart());
        String seconds = String.valueOf(duration.toSecondsPart());
        if (minutes.length() == 1){
            stringBuilder.append(zeroString);
            stringBuilder.append(minutes);
        }
        else{
            stringBuilder.append(minutes);
        }
        stringBuilder.append(":");
        if (seconds.length() == 1){
            stringBuilder.append(zeroString);
            stringBuilder.append(seconds);
        }
        else{
            stringBuilder.append(seconds);
        }
        this.displayedTime.set(stringBuilder.toString());
    }

    public void resetTimer(Clock clock){
        isPaused = true;
        isDuringBreak = false;
        isDuringLongBreak = false;
        currentSessionUnit.set(0);
        sessionStartDate = null;
        currentPausedDuration = Duration.ZERO;
        lastPausedMeasuredDate = null;
    }

    public void createStartDate(Clock clock){
        this.sessionStartDate = Date.from(clock.instant());
    }

    public Date getStartDate() {
        return this.sessionStartDate;
    }
    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException{
        out.defaultWriteObject();
        out.writeObject(currentSessionUnit.get());
        out.writeObject(maximumSessionUnits.get());
        out.writeObject(displayedTime.get());

    }
    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        currentSessionUnit = new SimpleIntegerProperty((Integer)in.readObject());
        maximumSessionUnits = new SimpleIntegerProperty((Integer)in.readObject());
        displayedTime = new SimpleStringProperty((String)in.readObject());

    }
    @Serial
    private void readObjectNoData()
            throws ObjectStreamException{
        currentMaxTime = Duration.ofMinutes(25); //Duration.ofSeconds(currentMaxTime);
        currentShortBreakTime =  Duration.ofMinutes(5);
        currentLongBreakTime =  Duration.ofMinutes(15);
        currentSessionUnit = new SimpleIntegerProperty(0);
        currentPausedDuration = Duration.ofSeconds(0);
        maximumSessionUnits = new SimpleIntegerProperty(3);
        displayedTime = new SimpleStringProperty();
    }


    public boolean duringLongBreak() {
        return isDuringLongBreak;
    }
}



