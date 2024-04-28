package com.example.flowmato.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    Button GoBackButton;

    @FXML Spinner sessionDurationSpinner;
    @FXML Spinner shortBreakDurationSpinner;
    @FXML Spinner longBreakDurationSpinner;

    private TimerController timer;

    public void setMainPageTimer(TimerController timer) {
        this.timer = timer;
    }

    @FXML protected void GoBack() {
        timer.setSessionDuration((Integer) sessionDurationSpinner.getValue() * 60);
        timer.setShortBreakDuration((Integer) shortBreakDurationSpinner.getValue() * 60);
        timer.setLongBreakDuration((Integer) longBreakDurationSpinner.getValue() * 60);
        //timer.Refresh();

        Stage stage = (Stage) GoBackButton.getScene().getWindow();
        stage.close();
    }
}
