package com.example.flowmato.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

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

    private void StartTimer() {
        boolean timerStarted = timer.Resume();
        if (timerStarted) {
            System.out.println("Timer Started/Resumed");
        } else {
            System.out.println("Timer already running");
        }
    }

    @FXML
    protected int GetTime() {
        return timer.timerDuration * 60 - timer.getTimeElapsed();
    }

    @FXML
    protected void ToggleTimer() {
        if (timer.isPaused) {
            StartTimer();
            TimerButton.setText("Pause Timer");
        } else {
            timer.Pause();
            TimerButton.setText("Start Timer");
        }
    }

    @FXML
    protected void StopTimer() {
        timer.Stop();
        TimerButton.setText("Start Timer");
    }

    protected void updateTime() {
        float timeInSeconds = (float) GetTime() / 1000;
        int minutes = (int) (timeInSeconds / 60);
        int seconds = (int) (timeInSeconds % 60);
        int milliseconds = (int) ((timeInSeconds * 1000) % 1000);

        String roundedValue = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        currentTime.setText(roundedValue);
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

    //main app stuff goes here
}
