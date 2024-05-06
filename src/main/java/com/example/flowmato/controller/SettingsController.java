package com.example.flowmato.controller;

import com.example.flowmato.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    Button GoBackButton;

    @FXML Spinner sessionDurationSpinner;
    @FXML Spinner shortBreakDurationSpinner;
    @FXML Spinner longBreakDurationSpinner;

    private TimerController timer;
    private NotificationController notificationController;

    /**
     * Initialises the Settings page with properties from the provided timer.
     * @param timer The timer object of the settings to modify.
     */
    public void setupSettings(TimerController timer) {
        this.timer = timer;

        int sessionDuration = timer.sessionDuration / 60;
        int shortBreakDuration = timer.shortBreakDuration / 60;
        int longBreakDuration = timer.longBreakDuration / 60;

        sessionDurationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, sessionDuration));
        shortBreakDurationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, shortBreakDuration));
        longBreakDurationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, longBreakDuration));
    }

    /**
     * Closes the Settings tab.
     */
    @FXML protected void goBack() {
        timer.stop();
        timer.setSessionDuration((Integer) sessionDurationSpinner.getValue() * 60);
        timer.setShortBreakDuration((Integer) shortBreakDurationSpinner.getValue() * 60);
        timer.setLongBreakDuration((Integer) longBreakDurationSpinner.getValue() * 60);

        Stage stage = (Stage) GoBackButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        notificationController = HelloApplication.notificationController;
    }
}
