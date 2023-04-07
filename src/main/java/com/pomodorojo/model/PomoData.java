package com.pomodorojo.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PomoData implements Serializable { // TODO make Externalizable
    @Serial
    private static final long serialVersionUID = 1L;
    private transient UserSession currentUserSession;
    private transient ObservableList<TimeCategory> observableTimeCategories;
    private transient PomoClock pomoClock;
    private transient ProgramSettings programSettings;
    private PomoTimer timer;
    private ArrayList<TimeCategory> timeCategories; // internal list that stores the timeCategories
    private HashMap<Date,ArrayList<PomoTimeUnit>> timeUnits;// the day is mapped to the pomodoros of the day
    private TimeCategory currentTimeCategory;
    private PomoTimeUnit currentTimeUnit;
    private ClientType clientType;

    public UserSession getCurrentUserSession() {
        return currentUserSession;
    }
    public ProgramSettings getProgramSettings() {
        return programSettings;
    }
    public PomoTimer getTimer() {
        return timer;
    }

    public HashMap<Date,ArrayList<PomoTimeUnit>> getTimeUnits() {
        return timeUnits;
    }

    public PomoData(){
        pomoClock = new PomoClock();
        timeUnits = new HashMap<>();// TODO load recent pomodoros
        timeCategories = new ArrayList<TimeCategory>();
        timeCategories.add(new TimeCategory("Standard",null)); // TODO set default categories Important do not edit the time categories directly!
        observableTimeCategories = FXCollections.observableList(timeCategories);
        timer = new PomoTimer(pomoClock);
//        currentTimeUnit = new PomoTimeUnit(timer.getCurrentMaxTime(),timeCategories.get(0).getCategoryString());
        currentTimeCategory = observableTimeCategories.get(0); // keep a reference to the active TimeCategory TODO load this from settings
        this.clientType = ClientType.PCCLIENTWINDOWS10;
    }

    public PomoClock getPomoClock() {
        return pomoClock;
    }

    public void setCurrentTimeUnit(PomoTimeUnit currentTimeUnit) {
        this.currentTimeUnit = currentTimeUnit;
    }

    public ObservableList<TimeCategory> getTimeCategories(){
        return observableTimeCategories;
    }
    public void addTimeCategory(String categoryName){ // TODO a call to this to a fitting controller
        observableTimeCategories.add(new TimeCategory(categoryName,Date.from(pomoClock.instant())));
    }

    public StringProperty getCurrentTimeCategoryProperty() {
        return currentTimeCategory.textProperty();
    }

    public PomoTimeUnit getCurrentTimeUnit(){
        return this.currentTimeUnit;
    }
    public ClientType getClientType() {
        return this.clientType;
    }

    public void setClientType(ClientType clientType){
        this.clientType = clientType;
    }
}
