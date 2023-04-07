package com.pomodorojo.controller;

import com.pomodorojo.model.PomoData;
import com.pomodorojo.model.PomoTimer;
import com.pomodorojo.model.TimeDescription;
import com.pomodorojo.model.TimeKind;

import java.time.Clock;
import java.util.Timer;
import java.util.TimerTask;

public class TimerController {
    private final PomoController pomoController;
    private final Timer taskTimerScheduler;

    public TimerController(PomoController pomoController){
        this.pomoController = pomoController;
        PomoData pomoData = this.pomoController.getPomoData();
        taskTimerScheduler = new Timer();
        // TODO load the settings of the timer controller!
        //this.pomoController.getPomoData().getTimer().calculateCurrentSessionTime(pomoData.getPomoClock());
        this.pomoController.getPomoData().getTimer().initSessionTime(pomoData.getPomoClock());

        createTimerTask();
    }
    public void startTimer(){
        // TODO when to load the previous timer state??,
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        if (!pomoTimer.isPaused()){
            return;
        }
        Clock clock = pomoData.getPomoClock();
        pomoTimer.createStartDate(clock);
        pomoTimer.startSession(clock);
        if (pomoTimer.timeLimitReached(clock)){ // add logic to additionally add unfinished minutes
            pomoTimer.next(clock);
        }
        else{
            pomoTimer.skipBreak(clock);
        }
    }

    public void stopTimer(){
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        Clock clock = pomoData.getPomoClock();
        pomoTimer.resetTimer(clock);
    }

    public void pauseTimerToggle(){
        PomoData pomoData = pomoController.getPomoData();
        Clock clock = pomoData.getPomoClock();
        PomoTimer pomoTimer = pomoData.getTimer();
        if (!pomoTimer.isPaused()){
        }

        pomoTimer.togglePaused(clock);
    }
    public void attachTimerDescription(String description,int personalRating,String reward){
        PomoData pomoData = pomoController.getPomoData();
        TimeDescription currentTimeDescription = pomoData.currentTimeUnit.getTimeDescription();
        if (currentTimeDescription == null){
            currentTimeDescription = new TimeDescription();
        }
        currentTimeDescription.setPersonalRating(personalRating);
        currentTimeDescription.setReward(reward);
        currentTimeDescription.setDescription(description);
    }

    /**
     *
     * @param description
     */
    public void updateTimerDescription(String description){
        PomoData pomoData = pomoController.getPomoData();
        TimeDescription currentTimeDescription = pomoData.currentTimeUnit.getTimeDescription();
        currentTimeDescription.setDescription(description);
    }

    public void createTimerTask(){
        Clock clock = pomoController.getPomoData().getPomoClock();
        TimerTask timeUpdate = new TimerTask() {
            @Override
            public void run() {
                PomoTimer pomoTimer = pomoController.getPomoData().getTimer();
                pomoTimer.calculateCurrentSessionTime(clock);
                if (pomoTimer.timeLimitReached(clock)){
                    pomoTimer.next(clock); // go inside the break, TODO should somehow update UI
                }
            }
        };
        taskTimerScheduler.scheduleAtFixedRate(timeUpdate,0,990); // we choose a time smaller than 1 second so that we dont skip a second
    }

    public void stopTimerTask(){
        taskTimerScheduler.cancel();
    }

}
