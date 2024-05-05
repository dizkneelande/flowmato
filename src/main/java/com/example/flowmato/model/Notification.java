package com.example.flowmato.model;

public class Notification {
    public String type;
    public String message;
    public String position;
    public int displayTime;
    //int delayTime;
    public boolean isRead;
    public Notification (String type, String message, String position, int displayTime) {
        this.type = type;
        this.message = message;
        this.position = position;
        this.displayTime = displayTime;
    }

    public Notification (String type, String message, int displayTime) {
        this.type = type;
        this.message = message;
        this.displayTime = displayTime;
    }
}
