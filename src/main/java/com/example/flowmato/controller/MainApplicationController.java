package com.example.flowmato.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MainApplicationController {

    TimerController timer;

    @FXML Label currentTime;
    @FXML
    private Label welcomeLabel;

    @FXML
    protected void StartTimer() {
        System.out.println("Starting timer");
        timer.Resume();
    }

    @FXML
    protected int GetTime() {
        return timer.timerDuration * 60 - timer.getTimeElapsed();
    }

    @FXML
    protected void PauseTimer() {
        System.out.println("Pausing timer");
        timer.Pause();
    }

    @FXML
    protected void StopTimer() {
        System.out.println("Stopping timer");
        timer.Stop();
    }

    protected void updateTime() {
        float timeInSeconds = (float) GetTime() / 1000;
        int minutes = (int) (timeInSeconds / 60);
        int seconds = (int) (timeInSeconds % 60);
        int milliseconds = (int) ((timeInSeconds * 1000) % 1000);

        String roundedValue = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        currentTime.setText(roundedValue);
    }

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> updateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public MainApplicationController() {
        System.out.println("Initialising timer");
        timer = new TimerController();
    }

    //main app stuff goes here
}
