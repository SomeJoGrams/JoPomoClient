package com.pomodorojo.view;

import com.pomodorojo.controller.PomoController;
import com.pomodorojo.controller.TimerController;
import com.pomodorojo.model.PomoData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private ToolBar windowTopBar;

    private SimpleIntegerProperty ratingProperty;
    private double xStartPosition;
    private double yStartPosition;
    private boolean isDragging;

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

//    @FXML
//    void onWindowDragDone(DragEvent event){
//        isDragging = false;
//        xStartPosition = 0;
//        yStartPosition = 0;
//        System.out.println("ended");
//    }

    @FXML
    void onWindowDrag(MouseEvent event){
        isDragging = true;
        xStartPosition = event.getScreenX();
        yStartPosition = event.getScreenY();
    }

    @FXML
    public void onMouseDrag(MouseEvent event){
        if (isDragging) {
            currentStage.setX(currentStage.getX() + event.getScreenX() - xStartPosition);
            currentStage.setY(currentStage.getY() + event.getScreenY() - yStartPosition);
            xStartPosition = event.getScreenX();
            yStartPosition = event.getScreenY();
        }
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
        this.currentStage.addEventHandler(MouseEvent.MOUSE_RELEASED, event ->{
            isDragging = false;
//            System.out.println("ended");
        });

    }



}
