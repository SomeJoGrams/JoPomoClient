package com.pomodorojo.view;

import com.pomodorojo.controller.PomoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PomoApplication extends Application {

    private PomoController pomoController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PomoApplication.class.getResource("mainView.fxml"));
        Parent root = fxmlLoader.load();

        // let the main view now the stage to be able to close it and detect sizing and more events
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setCurrentStage(stage);
        this.createPomodoro();
        mainViewController.setPomoController(pomoController);


        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Pomodoro");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setOnCloseRequest(event -> {
            this.pomoController.getStateController().safeState();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void createPomodoro(){
        pomoController = new PomoController();
    }

}