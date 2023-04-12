package com.pomodorojo.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class PomoData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient UserSession currentUserSession;
    private transient ObservableList<TimeCategory> observableTimeCategories;
    private transient PomoClock pomoClock;
    private transient ProgramSettings programSettings;
    private PomoTimer timer;
    private ArrayList<TimeCategory> timeCategories; // internal list that stores the timeCategories
    // todo probably better to make this transient and only load some values like, the last 2 months
    private HashMap<Date,ArrayList<PomoTimeUnit>> timeUnits;// the day is mapped to the pomodoros of the day
    private TimeCategory currentTimeCategory;
    private PomoTimeUnit currentTimeUnit;
//    private ClientType clientType;

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
        timer = new PomoTimer();
//        currentTimeUnit = new PomoTimeUnit(timer.getCurrentMaxTime(),timeCategories.get(0).getCategoryString());
        currentTimeCategory = observableTimeCategories.get(0); // keep a reference to the active TimeCategory TODO load this from settings
        addPomoTimeUnit("",null);
    }

    public PomoClock getPomoClock() {
        return pomoClock;
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

    public String getCurrentTimeCategory(){
        return this.getCurrentTimeCategoryProperty().get();
    }

    public void setCurrentTimeUnit(PomoTimeUnit currentTimeUnit) {
        this.currentTimeUnit = currentTimeUnit;
    }

    public PomoTimeUnit getCurrentTimeUnit(){
        return this.currentTimeUnit;
    }

    public void addPomoTimeUnit(String description,Date startDate){
        if (this.currentTimeUnit == null){
            TimeDescription timeDescription = new TimeDescription(description,"");
            this.currentTimeUnit = new PomoTimeUnit(getTimer().getMaxProperty().get(),
                    currentTimeCategory.getText(),timeDescription,startDate);
        }
        else{ // create a new pomo time unit TODO change the view listeners!
            TimeDescription timeDescription = new TimeDescription(description,"");
            this.currentTimeUnit = new PomoTimeUnit(getTimer().getMaxProperty().get(),
                    currentTimeCategory.getText(),timeDescription,startDate);
        }
    }


    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject();
    }
    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        currentUserSession = new UserSession(); // TODO load differently
        observableTimeCategories = FXCollections.observableList(timeCategories);
//        observableTimeCategories.add(currentTimeCategory); // is not needed bc the array list is serializable
        pomoClock = new PomoClock();
        programSettings = new ProgramSettings();

    }
    @Serial
    private void readObjectNoData()
            throws ObjectStreamException {
        currentUserSession = new UserSession();//TODO load differently
        timeCategories = new ArrayList<>();
        observableTimeCategories = FXCollections.observableList(timeCategories);
        pomoClock = new PomoClock();
        programSettings = new ProgramSettings(); // TODO them program settings correctly in the state controller
        timer = new PomoTimer();
        timeUnits = new HashMap<>();
        timeCategories.add(new TimeCategory("Standard",null)); // TODO set default categories Important do not edit the time categories directly!
        currentTimeCategory = observableTimeCategories.get(0); // keep a reference to the active TimeCategory TODO load this from settings
        currentTimeUnit = null;

    }

//client type also removed from constructor
//    public ClientType getClientType() {
//        return this.clientType;
//    }
//
//    public void setClientType(ClientType clientType){
//        this.clientType = clientType;
//    }
}
