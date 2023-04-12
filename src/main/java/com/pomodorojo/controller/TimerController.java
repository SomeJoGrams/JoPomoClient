package com.pomodorojo.controller;

import com.pomodorojo.model.*;

import java.time.Clock;
import java.util.Date;
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
        PomoData pomoData = pomoController.getPomoData();
        PomoTimer pomoTimer = pomoData.getTimer();
        if (!pomoTimer.isPaused()){
            return;
        }
        Clock clock = pomoData.getPomoClock();
        pomoTimer.createStartDate(clock);// store the date of when we start the pomodoro
        pomoTimer.startSession(clock);//
        if (pomoTimer.timeLimitReached(clock)){ // add logic to additionally add unfinished minutes
            pomoTimer.next(clock);
            if (pomoTimer.duringLongBreak()){
                // add timer description to the storing data structure

                // create the new description object
                finishTimerDescription();
            }
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

    /**
     * the content of timer description and time units get handle during
     * creation and with properties. Here the case that a long break is finished
     * or the program is exited otherwise is handled to replace the timeDesciption
     */
    public void finishTimerDescription(){
        PomoHistory pomoHistory = pomoController.getPomoHistory();
        PomoData pomoData = pomoController.getPomoData();

        // store the old description
        PomoTimeUnit currentTimeUnit = pomoData.getCurrentTimeUnit();

        pomoHistory.addTimeUnit(currentTimeUnit); // TODO remove bindings! here or renew in view

        // create the new description
        long maxTime = pomoData.getTimer().getCurrentMaxTime();
        String category = pomoData.getCurrentTimeCategory(); // TODO reload if necessary
        TimeDescription timeDescription = new TimeDescription("","");
        Date startDate = new Date(pomoData.getPomoClock().instant().toEpochMilli());
        pomoData.setCurrentTimeUnit(new PomoTimeUnit(maxTime,category,timeDescription,startDate));
    }

//    /**
//     * should be called on exit and on startTimer
//     * @param description
//     * @param personalRating
//     * @param reward
//     */
//    public void attachTimerDescription(String description,int personalRating,String reward){
//        PomoData pomoData = pomoController.getPomoData();
////        TimeDescription currentTimeDescription = pomoData.getCurrentTimeUnit().getTimeDescription();
//        pomoData.addPomoTimeUnit(description,pomoData.getTimer().getStartDate());
////        if (currentTimeDescription == null){
////            currentTimeDescription = new TimeDescription(description,reward,personalRating);
////        }
////        currentTimeDescription.setPersonalRating(personalRating);
////        currentTimeDescription.setReward(reward);
////        currentTimeDescription.setDescription(description);
//    }
//
//    /**
//     *
//     * @param description
//     */
//    public void updateTimerDescription(String description){
//        PomoData pomoData = pomoController.getPomoData();
//        if (pomoData.getCurrentTimeUnit() == null){
//            //pomoData.setCurrentTimeUnit(new PomoTimeUnit()); // TODO read the time unit from interface + model!
//        }
//        TimeDescription currentTimeDescription = pomoData.getCurrentTimeUnit().getTimeDescription();
//        currentTimeDescription.setDescription(description);
//    }

    public void createTimerTask(){
        Clock clock = pomoController.getPomoData().getPomoClock();
        TimerTask timeUpdate = new TimerTask() {
            @Override
            public void run() {
                PomoTimer pomoTimer = pomoController.getPomoData().getTimer();
                pomoTimer.calculateCurrentSessionTime(clock);
                if (pomoTimer.timeLimitReached(clock)){
                    pomoTimer.next(clock); // go inside the break,
                }
            }
        };
        taskTimerScheduler.scheduleAtFixedRate(timeUpdate,0,990); // we choose a time smaller than 1 second so that we dont skip a second
    }

    public void cancel(){
        taskTimerScheduler.cancel();
    }

}
