    package com.example.flowmato.controller;

    import com.example.flowmato.HelloApplication;
    import com.example.flowmato.model.Notification;
    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.control.Label;
    import javafx.scene.layout.StackPane;
    import javafx.scene.text.Text;
    import javafx.util.Duration;

    import java.io.IOException;
    import java.util.ArrayList;

    public class NotificationController {
        @FXML
        public StackPane banner;

        @FXML
        public StackPane toast_top_right;

        @FXML
        public StackPane toast_top_left;

        @FXML
        public Label bannerText;

        private Timeline timeline;

        private int currentKeyframe;
        private int timelineDuration;
        private int currentTime;

        /*
            REQUIREMENTS:
                - Notification for Session End
                - Notification of Pomodoro Completion
                - Notification for Short Break Completion
                - Notification for Long Break Completion
                - Pre-Alert Notification for Transition
            *  */
        ArrayList<Notification> notifications;
        boolean queueRunning;

        public NotificationController () {
            queueRunning = false;
            notifications = new ArrayList<>();

            System.out.println("CREATING NOTIFICATION CONTROLLER");
        }

        /**
         * Adds (a) notification(s) to the queue, and runs it.
         * <b>NOTE:</b> Alert notifications jump to the front of queue (exc. other alerts)
         * @param notifications any amount of notifications to be displayed, comma-separated
         */
        public void notify(Notification... notifications) {
            for (Notification notification : notifications) {
                switch (notification.type) {
                    case "alert":
                        addAlert(notification);
                        break;
                    case "toast":
                    case "banner":
                        this.notifications.add(notification);
                }
            }

            runQueue();
        }

        private void addAlert(Notification notification) {
            if (notifications.isEmpty()) {
                notifications.add(notification);
            } else {
                for (int i = 0; i < notifications.size(); i++) {
                    if (!notifications.get(i).type.equals("alert")) {
                        notifications.add(0, notification);
                        return;
                    }
                }
                notifications.add(notification);
            }
        }

        /**
         * Retrieves the list of notifications currently queued
         * @return the ArrayList<> of notifications
         */
        public ArrayList<Notification> getQueue() {
            return notifications;
        }

        /**
         * Clears the notification queue
         * @return <b>true</b> if the queue was cleared, <b>false</b> if it wasn't
         */
        public boolean clearQueue() {
            if (queueRunning) {
                return false;
            }

            notifications = new ArrayList<>();
            return true;
        }

        /**
         * Executes the notifications in the queue
         */
        private void runQueue() {
            if (queueRunning) {
                return;
            }

            queueRunning = true;
            banner.setVisible(true);

            timeline = new Timeline();
            timelineDuration = 0;
            currentKeyframe = 0;

            for (Notification notification : notifications) {
                timelineDuration += notification.displayTime;
                String message = notification.message;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(timelineDuration - notification.displayTime), e -> {
                    bannerText.setText(message);
                    currentTime += timelineDuration - notification.displayTime;
                    currentKeyframe++;
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timelineDuration)));

            timeline.setOnFinished(e -> {
                banner.setVisible(false);
                queueRunning = false;
                clearQueue();
            });

            timeline.play();
        }

        @FXML private void closeNotification() {
            if (currentKeyframe == notifications.size()) {
                timeline.jumpTo(Duration.millis(timelineDuration));
            } else {
                timeline.jumpTo(Duration.millis(currentTime - notifications.get(currentKeyframe).displayTime));
            }
        }

        @FXML
        private void initialize() {
            HelloApplication.notificationController = this;
        }
    }
