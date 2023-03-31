package com.pomodorojo.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainViewController {

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
    private SplitMenuButton categorySelection;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Button stopTimerButton;

    @FXML
    void onStartButtonClick(ActionEvent event) {

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
    void fbff00(ActionEvent event) {

    }

    @FXML
    void onExitButtonClick(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    void onSplitMenuClicked(ActionEvent event) {

    }

    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

}
