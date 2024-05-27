package com.example.flowmato.controller;

import com.example.flowmato.model.Achievements;
import com.example.flowmato.model.SessionManager;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AchievementsController implements Initializable {
    @FXML
    Button exitAchievementsButton;
    @FXML
    protected void exitAchievements() {
        Stage stage = (Stage) exitAchievementsButton.getScene().getWindow();
        stage.close();
    }
    private SqliteProfileDAO dao;

    @FXML
    private VBox achievementsContainer;

    public AchievementsController() {}

    public AchievementsController(SqliteProfileDAO dao) { this.dao = dao; }

    //reinstate when fully implemented
    // public void checkAndAwardAchievement(int profileId, int completedPomodoros) {
    //     if (completedPomodoros == 1) {  //first pomodoro
    //         Achievements achievement = new Achievements(profileId, "first tomato!", LocalDateTime.now());
    //         dao.saveAchievement(achievement);
    //     }
    //     //add more once we've decided on achievements
    // }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new SqliteProfileDAO();
        Integer currentUserId = SessionManager.getInstance().getCurrentUserId();
        if (currentUserId != null) {
            List<Achievements> achievements = dao.getAchievementsForUser(currentUserId);
            //System.out.println("achievements retrieved: " + achievements);
            displayAchievements(achievements);
        }
    }

    private void displayAchievements(List<Achievements> achievements) {
        achievementsContainer.getChildren().clear();
        if (achievements.isEmpty()) {
            Label noAchievementsLabel = new Label("You have no achievements. You suck :'(");
            achievementsContainer.getChildren().add(noAchievementsLabel);
        } else {
            for (Achievements achievement : achievements) {
                HBox achievementBox = new HBox(10);
                achievementBox.setAlignment(Pos.CENTER_LEFT);

                ImageView iconView = new ImageView(new Image(achievement.getIconPath()));
                iconView.setFitWidth(32);
                iconView.setFitHeight(32);

                Label nameLabel = new Label(achievement.getAchievementType());
                nameLabel.setStyle("-fx-font-size: 16px;");

                Label dateLabel = new Label(achievement.getAchievedOn().format(DateTimeFormatter.ofPattern("MMM d, yyyy")));
                dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

                achievementBox.getChildren().addAll(iconView, nameLabel, dateLabel);
                achievementsContainer.getChildren().add(achievementBox);
            }
        }
    }
}