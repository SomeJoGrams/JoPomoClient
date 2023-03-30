package com.pomodorojo.controller;

import com.pomodorojo.model.PomoData;
import com.pomodorojo.model.PomoTimer;
import com.pomodorojo.model.TimeKind;

public class TimerController {
    private PomoController pomoController;


    public TimerController(PomoController pomoController){
        this.pomoController = pomoController;
    }
    public void startTimer(){
        // TODO when to load the previous timer state??
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        pomoTimer.next();
        return;
    }

    public void pauseTimer(){
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        pomoTimer.togglePaused();
    }
    public void attachTimerDescription(String description,char personalRating,TimeKind kind,String reward){
    }

}
