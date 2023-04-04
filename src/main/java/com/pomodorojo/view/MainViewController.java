package com.pomodorojo.view;

import com.pomodorojo.controller.PomoController;
import com.pomodorojo.controller.TimerController;
import com.pomodorojo.model.PomoData;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainViewController {

    private PomoController pomoController;
    private Stage currentStage;
    @FXML
    private Button statisticsButton;

    @FXML
    private Text interruptionCount;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button startTimerButton;

    @FXML
    private Text timer;

    @FXML
    private Text userNameField;

    @FXML
    private Button exitButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button pauseTimerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Text ratingValue;

    @FXML
    private MenuButton categorySelection;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Button stopTimerButton;

    private SimpleIntegerProperty ratingProperty;


    @FXML
    void onStartButtonClick(ActionEvent event) {
        TimerController timerController = this.pomoController.getTimerController();
        timerController.startTimer();
    }

    @FXML
    void onPauseButtonClick(ActionEvent event) {

    }

    @FXML
    void onStopButtonClick(ActionEvent event) {

    }

    @FXML
    void onSettingsButtonClicked(ActionEvent event) {

    }

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        ratingProperty.set(4);
        System.out.println(ratingProperty.getValue());
    }

    @FXML
    void onStatisticsButtonClicked(ActionEvent event) {

    }

    @FXML
    void onRatingDrag(ActionEvent event) {
    }

    @FXML
    void onRatingDragDone(ActionEvent event) {

    }

    @FXML
    void onDescriptionTextChanged(ActionEvent event) {

    }

    @FXML
    void onExitButtonClick(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    void onMenuButtonClicked(ActionEvent event) {

    }



    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

    public void setPomoController(PomoController pomoController) { // init bindings
        this.pomoController = pomoController;
        PomoData pomoData = pomoController.getPomoData();
        ratingProperty = new SimpleIntegerProperty();
        ratingValue.textProperty().bind(ratingProperty.asString());

        categorySelection.getItems().setAll(pomoData.getTimeCategories());
        categorySelection.textProperty().bind(pomoData.getCurrentTimeCategoryProperty());

        timer.textProperty().bind(pomoData.getTimer().getDisplayedTimeProperty());
    }



}
