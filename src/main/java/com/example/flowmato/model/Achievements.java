package com.example.flowmato.model;

import java.time.LocalDateTime;

public class Achievements {
    private int id;
    private int profileId;
    private String achievementType;
    private LocalDateTime achievedOn;

    public Achievements(int profileId, String achievementType, LocalDateTime achievedOn) {
        this.profileId = profileId;
        this.achievementType = achievementType;
        this.achievedOn = achievedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public LocalDateTime getAchievedOn() {
        return achievedOn;
    }

    public void setAchievedOn(LocalDateTime achievedOn) {
        this.achievedOn = achievedOn;
    }
}
