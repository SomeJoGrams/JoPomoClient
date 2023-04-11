package com.pomodorojo.view;

import com.pomodorojo.controller.PomoController;
import com.pomodorojo.controller.TimerController;
import com.pomodorojo.model.PomoData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
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

    private VBox finishedUnitSum;
    @FXML
    private HBox unitView;


    @FXML
    void onStartButtonClick(ActionEvent event) {
        TimerController timerController = this.pomoController.getTimerController();
        timerController.startTimer();
    }

    @FXML
    void onPauseButtonClick(ActionEvent event) {
        TimerController timerController = this.pomoController.getTimerController();
        timerController.pauseTimerToggle();
    }

    @FXML
    void onStopButtonClick(ActionEvent event) {
        TimerController timerController = this.pomoController.getTimerController();
        timerController.stopTimer();
    }

    @FXML
    void onSettingsButtonClicked(ActionEvent event) {
    }

    @FXML
    void onLoginButtonClicked(ActionEvent event) {
        SystemNotificationController.displayNotification("login started");
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

    @FXML
    void onWindowDrag(MouseEvent event){
        isDragging = true;
        xStartPosition = event.getScreenX();
        yStartPosition = event.getScreenY();
    }

    @FXML
    void onMouseReleased(MouseEvent event){
        isDragging = false;
        xStartPosition = 0;
        yStartPosition = 0;
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
    private SimpleIntegerProperty ratingProperty;
    private double xStartPosition;
    private double yStartPosition;
    private boolean isDragging;
    private double unitXSize;
    private double unitYSize;

    private ChangeListener<Number> maxUnitListener;
    private ChangeListener<Number> unitListener;

    public void addMaxUnits(){
        PomoData pomoData = this.pomoController.getPomoData();
        unitXSize = unitView.getPrefWidth() /  pomoData.getTimer().getMaxProperty().doubleValue();
        unitYSize = unitView.getPrefHeight();
        double smallerSize = Math.min(unitXSize, unitYSize) / 2;
        for (int newElement = 0; newElement < pomoData.getTimer().getMaxProperty().get(); newElement++) {
            unitView.getChildren().add(new Circle(smallerSize));
            unitView.getChildren().forEach(node ->{
                ((Circle) node).setStyle("-fx-fill:" + "DODGERBLUE;" + "-fx-stroke:" + "BLACK;" + "-fx-strokeType:" +"INSIDE;");
            });
        }

    }

    public void updateUnitVisibility(){
        PomoData pomoData = this.pomoController.getPomoData();
        int currentUnits = pomoData.getTimer().getUnitProperty().get();
        // only make the completed units visible
        unitView.getChildren().forEach(node -> node.setVisible(false));
        unitView.getChildren().stream().limit(currentUnits).forEach(node ->
            node.setVisible(true)
        );
    }

    public void addUnitListener() {
        PomoData pomoData = this.pomoController.getPomoData();
        pomoData.getTimer().getMaxProperty().addListener(new WeakChangeListener<>(
                maxUnitListener
        ));
        pomoData.getTimer().getUnitProperty().addListener(new WeakChangeListener<>(
            unitListener
        ));
    }

    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

    /**
     * init bindings and private variables
     * @param pomoController
     */
    public void setPomoController(PomoController pomoController) {
        this.pomoController = pomoController;
        PomoData pomoData = pomoController.getPomoData();
        ratingProperty = new SimpleIntegerProperty();
        ratingValue.textProperty().bind(ratingProperty.asString());

        categorySelection.getItems().setAll(pomoData.getTimeCategories());
        categorySelection.textProperty().bind(pomoData.getCurrentTimeCategoryProperty());


        timer.textProperty().bind(pomoData.getTimer().getDisplayedTimeProperty());

        // convert the max unit to circles
        maxUnitListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                unitXSize = unitView.getPrefWidth() / newValue.doubleValue();
                unitYSize = unitView.getPrefHeight();
                double smallerSize = Math.min(unitXSize, unitYSize);
                if (oldValue.intValue() < newValue.intValue()) {
                    for (int newElement = 0; newElement < newValue.intValue() - oldValue.intValue(); newElement++) {
                        unitView.getChildren().add(new Circle(smallerSize));
                    }
                } else {
                    int rangeStart = Math.max(newValue.intValue(), unitView.getChildren().size() - 1);
                    unitView.getChildren().remove(rangeStart, oldValue.intValue());
                }
                unitView.getChildren().forEach( node -> {
                    ((Circle)node).setRadius(smallerSize);
                });
                // only display the units that have to be displayed
                updateUnitVisibility();
            }
        };
        unitListener =  new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateUnitVisibility();
            }
        };
        addMaxUnits();
        updateUnitVisibility();
        addUnitListener();


        // load tray Symobl and Notification
        SystemNotificationController systemNotificationController = new SystemNotificationController();
        systemNotificationController.addSystemTraySymbol();

    }



}
