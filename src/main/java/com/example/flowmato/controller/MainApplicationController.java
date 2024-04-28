package com.example.flowmato.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainApplicationController {

    TimerController timer;

    @FXML
    private Label currentTime;

    @FXML
    private Label shortBreaksTaken;

    @FXML
    private Label longBreaksTaken;

    @FXML
    private Label pomodorosCompleted;

    @FXML
    private Button TimerButton;

    @FXML protected void switchAccount(ActionEvent event) {
        try {
            Parent mainAppRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/login-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainAppRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void openSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/flowmato/settings-view.fxml"));
            Parent settingsRoot = loader.load();

            SettingsController settingsController = loader.getController();
            settingsController.setupSettings(timer);

            Stage settingsStage = new Stage();
            settingsStage.setScene(new Scene(settingsRoot));
            settingsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        boolean timerStarted = timer.resume();
        if (timerStarted) {
            System.out.println("Timer Started/Resumed");
        } else {
            System.out.println("Timer already running");
        }
    }

    @FXML
    protected int getTime() {
        return ((timer.timerDuration * 1000) - timer.getTimeElapsed()) / 1000;
    }

    @FXML
    protected void toggleTimer() {
        if (timer.isPaused) {
            startTimer();
            TimerButton.setText("Pause Timer");
        } else {
            timer.pause();
            TimerButton.setText("Start Timer");
        }
    }

    @FXML
    protected void stopTimer() {
        timer.stop();
        TimerButton.setText("Start Timer");
    }

    protected void updateTime() {
        int seconds = getTime();
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        String minutesString = String.format("%02d", minutes);
        String secondsString = String.format("%02d", remainingSeconds);

        currentTime.setText(minutesString + ":" + secondsString);
    }

    public void updateStats() {
        shortBreaksTaken.setText("Short Breaks Taken: " + timer.shortBreaksTaken);
        longBreaksTaken.setText("Long Breaks Taken: " + timer.longBreaksTaken);
        pomodorosCompleted.setText("Pomodoros Completed: " + timer.pomodorosCompleted);
    }

    private void refreshUI() {
        updateTime();
        updateStats();
    }

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> refreshUI())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public MainApplicationController() {
        System.out.println("Initialising timer");
        timer = new TimerController();
    }
}
