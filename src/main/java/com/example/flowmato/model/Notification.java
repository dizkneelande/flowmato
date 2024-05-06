package com.example.flowmato.model;

public class Notification {
    public String type;
    public String message;
    public String position;
    /*
    VALID VALUES:
    - For type "toast":
      - "TOP_LEFT"
      - "TOP_RIGHT"
      - "BOTTOM_LEFT"
      - "BOTTOM_RIGHT"
    - For type "banner" and "alert":
      - "TOP"
      - "BOTTOM"
    * */
    public int displayTime;
    public boolean beenDisplayed;

    /**
     * Creates a notification with the supplied parameters
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     * @param message the message of the notification
     * @param position the position to render the notification at (check Notification.java for valid values)
     * @param displayTime the amount of time to display the notification for (in millis)
     */
    public Notification (String type, String message, String position, int displayTime) {
        this.type = type;
        this.message = message;
        this.position = position;
        this.displayTime = displayTime;
    }

    /**
     * Creates a notification with the supplied parameters
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     * @param message the message of the notification
     * @param displayTime the amount of time to display the notification for (in millis)
     */
    public Notification (String type, String message, int displayTime) {
        if (type.equals("banner")) {
            this.position = "TOP";
        } else {
            this.position = "TOP_RIGHT";
        }
        this.type = type;
        this.message = message;
        this.displayTime = displayTime;
    }
}
