package com.example.flowmato.controller;

import com.example.flowmato.model.Analytics;
import com.example.flowmato.model.SessionManager;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AnalyticsController implements Initializable {

    @FXML
    private Label completedPomodorosLabel;

    @FXML
    private Label totalFocusTimeLabel;

    @FXML
    private Label totalBreakTimeLabel;

    private SqliteProfileDAO dao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new SqliteProfileDAO();
        Integer currentUserId = SessionManager.getInstance().getCurrentUserId();
        if (currentUserId != null) {
            Analytics analytics = dao.getAnalytics(currentUserId);
            if (analytics != null) {
                displayAnalytics(analytics);
            }
        }
    }

    private void displayAnalytics(Analytics analytics) {
        int completedPomodoros = analytics.getCompletedPomodoros();
        int totalFocusTime = analytics.getTotalFocusTime();
        int totalBreakTime = analytics.getTotalBreakTime();

        completedPomodorosLabel.setText("Completed Pomodoros: " + completedPomodoros);
        totalFocusTimeLabel.setText("Total Focus Time (seconds): " + totalFocusTime);
        totalBreakTimeLabel.setText("Total Break Time (seconds): " + totalBreakTime);
    }
}