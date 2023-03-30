package com.pomodorojo.model;


import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;

public class PomoTimer {

    private LocalDate sessionStartDate; // keep the duration of the session on the server and in the settings as seconds!
    private long currentPausedDuration;
    private int currentSessionUnit;
    public boolean isDuringPause;
    public boolean isDuringLongPause;
    private boolean isPaused;
    private Duration currentMaxTime; // given in minutes
    private Duration currentShortBreakTime;

    private int maximumSessionUnits;
    /**
     *
     */
    private Duration currentLongBreakTime;

    /**
     *
     * @param currentSessionTime
     * @param isPaused
     * @param currentMaxTime in seconds
     * @param currentShortBreakTime in seconds
     * @param currentLongBreakTime in seconds
     *
     */
    public PomoTimer(LocalDate currentSessionTime, boolean isPaused,long currentMaxTime,long currentShortBreakTime
            ,long currentLongBreakTime,int currentSessionUnit,int currentPauseDuration,int maximumSessionUnits){
        this.sessionStartDate = currentSessionTime;
        this.isPaused = isPaused;
        this.currentMaxTime = Duration.ofSeconds(currentMaxTime);
        this.currentShortBreakTime = Duration.ofSeconds(currentShortBreakTime);
        this.currentLongBreakTime = Duration.ofSeconds(currentLongBreakTime);
        this.currentSessionUnit = currentSessionUnit;
        this.currentPausedDuration = currentPauseDuration;
        this.maximumSessionUnits = maximumSessionUnits;
    }

    public void setCurrentPausedDuration(long currentPausedDuration) {
        this.currentPausedDuration = currentPausedDuration;
    }

    public void setDuringPause(boolean duringPause) {
        isDuringPause = duringPause;
    }

    public void setDuringLongPause(boolean duringLongPause) {
        isDuringLongPause = duringLongPause;
    }

    public void setMaximumSessionUnits(int maximumSessionUnits){
        // TODO maybe put the maximum check here ? like an absolute maximum of 500 idk
        this.maximumSessionUnits = maximumSessionUnits;
    }

    public void togglePaused() {
        isPaused = !isPaused;
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

    /**
     * this setter is not exposed, because it currentSessionUnit should only be changed internally
     * @param currentSessionUnit
     */
    private void setCurrentSessionUnit(int currentSessionUnit){
        this.currentSessionUnit = currentSessionUnit;
    }
    public LocalDate getTime(){
        return sessionStartDate;
    }

    public boolean isPaused() {
        return isPaused;
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
        LocalDate curDate = LocalDate.now(clock);
        Duration currentDuration = Duration.between(sessionStartDate,curDate);
        if (!isDuringPause && !isDuringLongPause){
            limitReached = currentDuration.compareTo(currentMaxTime); // f.e. the current duration is less -> negative value
        }
        else if (isDuringPause && !isDuringLongPause){
            limitReached = currentDuration.compareTo(currentShortBreakTime);
        }
        else{// is DuringLongPause
            limitReached = currentDuration.compareTo(currentLongBreakTime);
        }
        return limitReached >= 0;
    }

    public void next(){
        if (!isDuringPause && !isDuringLongPause){
            // either go into the long or the short pause depending on the amount of sessionUnits
            isDuringPause = true;
            if (currentSessionUnit == maximumSessionUnits){
                currentSessionUnit = 0;
                isDuringPause = false;
                isDuringLongPause = true;
            }
        }
        else if (isDuringPause && !isDuringLongPause){
            isDuringPause = false;
            currentSessionUnit += 1;
        }
        else{// is DuringLongPause
            isDuringLongPause = false;
            currentSessionUnit += 1;
        }
    }

}



