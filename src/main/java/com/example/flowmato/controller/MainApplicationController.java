package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainApplicationController {

    TimerController timer;
    @FXML
    private Label welcomeLabel;

    @FXML
    protected void StartTimer() {
        System.out.println("Starting timer");
        timer.Resume();
    }

    @FXML
    protected void GetTime() {
        System.out.println(timer.timerDuration * 60 - timer.getTimeElapsed());
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

    public MainApplicationController() {
        System.out.println("Initialising timer");
        timer = new TimerController();
    }

    //main app stuff goes here
}
