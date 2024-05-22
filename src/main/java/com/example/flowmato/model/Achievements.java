package com.example.flowmato.model;

import java.time.LocalDateTime;

public class Achievements {
    private int id;
    private int profileId;
    private String achievementType;
    private String iconPath;
    private LocalDateTime achievedOn;

    public Achievements(int profileId, String achievementType, LocalDateTime achievedOn, String iconPath) {
        this.profileId = profileId;
        this.achievementType = achievementType;
        this.achievedOn = achievedOn;
        this.iconPath = iconPath;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getProfileId() { return profileId; }

    public void setProfileId(int profileId) { this.profileId = profileId; }

    public void setIconPath(String iconPath) { this.iconPath = iconPath; }

    public String getAchievementType() { return achievementType; }

    public String getIconPath() {return iconPath; }

    public void setAchievementType(String achievementType) { this.achievementType = achievementType; }

    public LocalDateTime getAchievedOn() { return achievedOn; }

    public void setAchievedOn(LocalDateTime achievedOn) { this.achievedOn = achievedOn; }
}
