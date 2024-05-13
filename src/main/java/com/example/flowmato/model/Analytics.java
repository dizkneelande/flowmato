package com.example.flowmato.model;

/**
 * analytics data for a user's sessions
 */
public class Analytics {
    private int profileId;
    private int completedPomodoros;
    private int totalFocusTime;
    private int totalBreakTime;

    public Analytics(int profileId, int completedPomodoros, int totalFocusTime, int totalBreakTime) {
        this.profileId = profileId;
        this.completedPomodoros = completedPomodoros;
        this.totalFocusTime = totalFocusTime;
        this.totalBreakTime = totalBreakTime;
    }

    public int getProfileId() { return profileId; }
    public int getCompletedPomodoros() { return completedPomodoros; }
    public int getTotalFocusTime() { return totalFocusTime; }
    public int getTotalBreakTime() { return totalBreakTime; }

    //setters for when modification is needed
    public void setCompletedPomodoros(int completedPomodoros) {
        this.completedPomodoros = completedPomodoros;
    }
    public void setTotalFocusTime(int totalFocusTime) {
        this.totalFocusTime = totalFocusTime;
    }
    public void setTotalBreakTime(int totalBreakTime) {
        this.totalBreakTime = totalBreakTime;
    }
}
