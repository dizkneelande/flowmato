package com.example.flowmato.controller;

import com.example.flowmato.model.Achievements;
import com.example.flowmato.model.SqliteProfileDAO;

import java.time.LocalDateTime;

public class AchievementsController {
    private SqliteProfileDAO dao;

    public AchievementsController(SqliteProfileDAO dao) {
        this.dao = dao;
    }

    public void checkAndAwardAchievement(int profileId, int completedPomodoros) {
        if (completedPomodoros == 1) {  //first pomoforo
            Achievements achievement = new Achievements(profileId, "first tomato!", LocalDateTime.now());
            dao.saveAchievement(achievement);
        }
        //add more once we've decided on achievements
    }
}
