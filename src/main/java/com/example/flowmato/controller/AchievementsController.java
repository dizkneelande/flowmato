package com.example.flowmato.controller;

import com.example.flowmato.model.Achievements;
import com.example.flowmato.model.SessionManager;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {
    private SqliteProfileDAO dao;

    @FXML
    private VBox achievementsContainer;

    public AchievementsController() {}

    public AchievementsController(SqliteProfileDAO dao) { this.dao = dao; }

    public void checkAndAwardAchievement(int profileId, int completedPomodoros) {
        if (completedPomodoros == 1) {  //first pomodoro
            Achievements achievement = new Achievements(profileId, "first tomato!", LocalDateTime.now());
            dao.saveAchievement(achievement);
        }
        //add more once we've decided on achievements
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new SqliteProfileDAO();
        Integer currentUserId = SessionManager.getInstance().getCurrentUserId();
        if (currentUserId != null) {
            List<Achievements> achievements = dao.getAchievementsForUser(currentUserId);
            System.out.println("achievements retrieved: " + achievements);
            displayAchievements(achievements);
        }
    }

    private void displayAchievements(List<Achievements> achievements) {
        achievementsContainer.getChildren().clear();
        if (achievements.isEmpty()) {
            Label noAchievementsLabel = new Label("no achievements u r a loser");
            achievementsContainer.getChildren().add(noAchievementsLabel);
        } else {
            for (Achievements achievement : achievements) {
                Label achievementLabel = new Label(achievement.getAchievementType() + " - " + achievement.getAchievedOn());
                achievementsContainer.getChildren().add(achievementLabel);
            }
        }
    }
}