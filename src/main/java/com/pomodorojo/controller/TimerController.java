package com.pomodorojo.controller;

import com.pomodorojo.model.PomoData;
import com.pomodorojo.model.PomoTimer;
import com.pomodorojo.model.TimeDescription;
import com.pomodorojo.model.TimeKind;

import java.time.Clock;

public class TimerController {
    private PomoController pomoController;


    public TimerController(PomoController pomoController){
        this.pomoController = pomoController;
    }
    public void startTimer(Clock clock){
        // TODO when to load the previous timer state??,
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        if (pomoTimer.timeLimitReached(clock)){
            pomoTimer.next(clock);
        }
        else{
            pomoTimer.skipBreak(clock);
        }
    }

    public void pauseTimerToggle(Clock clock){ //TODO where to get the clock from
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
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

}
