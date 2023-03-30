package com.pomodorojo.controller;

import com.pomodorojo.model.PomoData;

public class TimerController {
    private PomoController pomoController;

    public TimerController(PomoController pomoController){
        this.pomoController = pomoController;
    }
    public void startTimer(){
        PomoData pomoData = pomoController.getPomoData();

        return;
    }
}
