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
    public boolean allowOverride;

    /**
     * Creates a notification with the supplied parameters
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     * @param message the message of the notification
     * @param position the position to render the notification at (check Notification.java for valid values)
     * @param displayTime the amount of time to display the notification for (in millis)
     */
    public Notification (String type, String message, String position, int displayTime) {
        setType(type);
        this.message = message;
        setPosition(type, position);
        this.displayTime = displayTime / 1000;

        if (displayTime == 0) {
            this.allowOverride = true;
        }
    }

    /**
     * Creates a notification with the supplied parameters
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     * @param message the message of the notification
     * @param displayTime the amount of time to display the notification for (in millis)
     */
    public Notification (String type, String message, int displayTime) {
        setType(type);
        if (type.equals("banner") || type.equals("alert")) {
            this.position = "TOP";
        } else {
            this.position = "TOP_RIGHT";
        }
        this.message = message;
        this.displayTime = displayTime / 1000;

        if (displayTime == 0) {
            this.allowOverride = true;
        }
    }

    /**
     * Sets the notifications display time.
     * @param displayTime the amount of time to display the notification for (in millis)
     */
    public void setDisplayTime(int displayTime) {
        this.displayTime = displayTime / 1000;
    }

    /**
     * Verifies and sets the type of the notification
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     */
    private void setType(String type) {
        switch (type) {
            case "banner":
            case "alert":
            case "toast":
                this.type = type;
                break;
            default:
                System.out.println("Invalid type selected, falling back to default type BANNER");
                this.type = "banner";
                break;
        }
    }

    /**
     * Verifies and sets the position of the notification
     * @param type the type of notification, which can be <b>"toast"</b>, <b>"banner"</b> or <b>"alert"</b>
     * @param position the position to render the notification at (check Notification.java for valid values)
     */
    public void setPosition(String type, String position) {
        switch (position) {
            case "TOP_LEFT":
            case "TOP_RIGHT":
            case "BOTTOM_LEFT":
            case "BOTTOM_RIGHT":
            case "TOP":
            case "BOTTOM":
                this.position = position;
                return;
            default:
                System.out.println("Invalid position provided, falling back to default position for " + type);
                switch (type) {
                    case "banner":
                    case "alert":
                        this.position = "TOP";
                        return;
                    case "toast":
                        this.position = "TOP_RIGHT";
                        return;
                    default:
                        break;
                }
        }
    }
}
