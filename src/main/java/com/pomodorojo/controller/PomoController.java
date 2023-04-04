package com.pomodorojo.controller;

import com.pomodorojo.model.PomoData;

public class PomoController {
    private TimerController timerController;
    private LoginController loginController;
    private IOController ioController;
    private UpdateController updateController;
    private TimeUnitController timeUnitController;
    private StatisticsController statisticsController;
    private SettingsController settingsController;
    private AudioController audioController;
    private NetworkController networkController;
    private PomoData pomoData;

    public TimerController getTimerController() {
        return timerController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public PomoData getPomoData() {
        return pomoData;
    }

    public IOController getIoController() {
        return ioController;
    }

    public UpdateController getUpdateController() {
        return updateController;
    }

    public TimeUnitController getTimeUnitController() {
        return timeUnitController;
    }

    public StatisticsController getStatisticsController() {
        return statisticsController;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public AudioController getAudioController() {
        return audioController;
    }

    public NetworkController getNetworkController() {
        return networkController;
    }


    public PomoController(){
        this.pomoData = new PomoData(); // should always be initialized first with the load controller!

        this.timerController = new TimerController(this);
    }

}
