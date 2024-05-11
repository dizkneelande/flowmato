package com.example.flowmato.controller;

import com.example.flowmato.HelloApplication;
import com.example.flowmato.model.Notification;
import com.example.flowmato.model.SqliteProfileDAO;
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
import javafx.scene.layout.VBox;
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

    @FXML
    private VBox sidebar;

    private NotificationController notificationController;

    private AudioController audioController;

    /**
     * Switches the users account.
     * @param event The component's event of the user's action
     */
    @FXML protected void switchAccount(ActionEvent event) {
        // This currently just goes back to the login screen, no additional logic for "signing out" at this stage.
        try {
            audioController.stopMusic();
            Parent mainAppRoot = FXMLLoader.load(getClass().getResource("/com/example/flowmato/login-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainAppRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Settings panel.
     */
    @FXML protected void openSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/flowmato/settings-view.fxml"));
            Parent settingsRoot = loader.load();

            SettingsController settingsController = loader.getController();
            settingsController.setupSettings(timer, TimerButton);

            Stage settingsStage = new Stage();
            settingsStage.setScene(new Scene(settingsRoot));
            settingsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the Pomodoro timer.
     */
    private void startTimer() {
        boolean timerStarted = timer.resume();
        if (!timerStarted) {
            System.out.println("Timer already running");
        }
    }

    /**
     * Gets the current time of the timer.
     * @return the timers current time elapsed converted to seconds.
     */
    @FXML
    protected int getTime() {
        return ((timer.timerDuration * 1000) - timer.getTimeElapsed()) / 1000;
    }

    /**
     * Toggles the timer between paused/running.
     */
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

    /**
     * Stops the timer (i.e. cancels it and resets it back to initialisation values)
     */
    @FXML
    public void stopTimer() {
        timer.stop();
        TimerButton.setText("Start Timer");
    }

    /**
     * Updates the FXML component that displays the time remaining.
     */
    protected void updateTime() {
        int seconds = getTime();

        if (seconds < 60) {
            timer.notifyOfTransition();
        }

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        String minutesString = String.format("%02d", minutes);
        String secondsString = String.format("%02d", remainingSeconds);

        currentTime.setText(minutesString + ":" + secondsString);
    }

    /**
     * Updates the FXML components that display the statistics for the current Pomodoro session.
     */
    public void updateStats() {
        shortBreaksTaken.setText("Short Breaks Taken: " + timer.shortBreaksTaken);
        longBreaksTaken.setText("Long Breaks Taken: " + timer.longBreaksTaken);
        pomodorosCompleted.setText("Pomodoros Completed: " + timer.pomodorosCompleted);
    }

    /**
     * Calls the methods necessary to facilitate refreshing the user interface.
     */
    private void refreshUI() {
        updateTime();
        updateStats();
    }

    @FXML
    public void initialize() {
        AchievementsController achievementsController = null;
        SqliteProfileDAO dao = new SqliteProfileDAO();
        achievementsController = new AchievementsController(dao);

        notificationController = HelloApplication.notificationController;
        audioController = HelloApplication.audioController;
        timer = new TimerController(achievementsController, notificationController);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> refreshUI())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Calls the methods necessary to open sidebar in main app
     */

    @FXML
    private void toggleSidebar() {
        // Calculate new position of the sidebar
        double sidebarWidth = sidebar.getWidth();
        double currentTranslateX = sidebar.getTranslateX();
        double newTranslateX = currentTranslateX == 0 ? -sidebarWidth : 0;

        sidebar.setTranslateX(newTranslateX);
    }

}
