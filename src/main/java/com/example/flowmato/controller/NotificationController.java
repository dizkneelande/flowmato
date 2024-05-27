    package com.example.flowmato.controller;

    import com.example.flowmato.HelloApplication;
    import com.example.flowmato.model.Notification;
    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.layout.StackPane;
    import javafx.util.Duration;

    import java.util.ArrayList;
    import java.util.Objects;
    import java.util.stream.Stream;

    public class NotificationController {
        @FXML
        public StackPane banner;
        @FXML
        public StackPane toast_top_right;
        @FXML
        public StackPane toast_top_left;
        @FXML
        public Label bannerText;
        @FXML
        public Label toastTopLeftText;
        @FXML
        public Label toastTopRightText;
        @FXML
        public StackPane banner_bottom;
        @FXML
        public Label bannerBottomText;
        @FXML
        public StackPane toast_bottom_right;
        @FXML
        public Label toastBottomRightText;
        @FXML
        public StackPane toast_bottom_left;
        @FXML
        public Label toastBottomLeftText;

        private Timeline timeline;

        private int currentKeyframe;
        private int timelineDuration;
        private int currentTime;
        private int queueSize;
        private boolean indefiniteNotificationDisplayed;
        private Notification indefiniteNotification;
        private AudioController audioController;
        ArrayList<Notification> notifications;
        boolean queueRunning;
        String alertColor = "red";
        String bannerBaseColor = "#dadada";


        public NotificationController () {
            queueRunning = false;
            notifications = new ArrayList<>();

            if (HelloApplication.audioController == null) {
                this.audioController = new MockAudioController();
            } else {
                this.audioController = HelloApplication.audioController;
            }
        }

        /**
         * Adds (a) notification(s) to the queue, and runs it.
         * <b>NOTE:</b> Alert notifications jump to the front of queue (exc. other alerts)
         * @param notifications any amount of notifications to be displayed, comma-separated
         */
        public void notify(Notification... notifications) {
            if (indefiniteNotificationDisplayed && indefiniteNotification.allowOverride) {
                haltQueue();
            }

            for (Notification notification : notifications) {
                if (notification.displayTime <= 0) {
                    if (notifications.length > 1) {
                        return;
                    }

                    notification.setDisplayTime(10000 * 1000);
                    indefiniteNotificationDisplayed = true;
                    indefiniteNotification = notification;
                }
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

        /**
         * Adds an alert notification to the queue
         * <b>NOTE:</b> Alert notifications jump to the front of queue (exc. other alerts)
         * @param notification the alert notification to be added to the queue
         */
        private void addAlert(Notification notification) {
            if (notifications.isEmpty()) {
                notifications.add(notification);
            } else {
                if (queueRunning) {
                    haltQueue();
                }
                for (int i = 0; i < notifications.size(); i++) {
                    if (!notifications.get(i).type.equals("alert")) {
                        notifications.add(i, notification);
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

            queueSize = 0;
            timelineDuration = 0;
            currentKeyframe = 0;
            notifications = new ArrayList<>();
            return true;
        }

        /**
         * Stops the current UI and queue and removes the notifications that were already displayed from the queue
         */
        public void haltQueue() {
            hideUI();
            timeline.stop();
            queueRunning = false;
            notifications.subList(0, currentKeyframe).clear();
        }

        /**
         * Executes the notifications in the queue
         */
        private void runQueue() {
            if (queueRunning) {
                return;
            }

            queueRunning = true;

            timeline = new Timeline();
            timelineDuration = 0;
            currentKeyframe = 0;

            queueSize = notifications.size();

            for (Notification notification : notifications) {
                timelineDuration += notification.displayTime;
                Duration keyframeLength = Duration.seconds(timelineDuration - notification.displayTime);
                KeyFrame keyFrame = new KeyFrame(keyframeLength, e -> {
                    updateNotificationUI(notification);
                    currentTime += timelineDuration - notification.displayTime;
                    currentKeyframe++;
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timelineDuration)));

            timeline.setOnFinished(e -> {
                hideUI();
                queueRunning = false;

                // If the number of notifications has changed since we created the timeline, we remove the notifications
                // that have already been shown and rerun the queue.
                if (queueSize != notifications.size()) {
                    notifications.subList(0, queueSize).clear();
                    runQueue();
                } else {
                    clearQueue();
                }
            });

            if (notifications.get(0).type.equals("alert")) {
                audioController.playAlert();
            } else {
                audioController.playNotification();
            }

            timeline.play();
        }

        /**
         * Updates the notification UI with the data from the supplied notification.
         * @param notification the notification to be displayed on the UI
         */
        private void updateNotificationUI(Notification notification) {
            if (Stream.of(banner, banner_bottom, toast_top_right, toast_top_left, toast_bottom_left, toast_bottom_right).anyMatch(Objects::isNull)) {
                return;
            }

            hideUI();

            String message = notification.message;
            switch (notification.type) {
                case "alert":
                case "banner":
                    if (notification.type.equals("alert")) {
                        banner.setStyle("-fx-background-color: " + alertColor + ";");
                    } else {
                        banner.setStyle("-fx-background-color: " + bannerBaseColor + ";");
                    }
                    switch (notification.position) {
                        case "BOTTOM":
                            banner_bottom.setVisible(true);
                            bannerBottomText.setText(message);
                            break;
                        case "TOP":
                            banner.setVisible(true);
                            bannerText.setText(message);
                            break;
                    }
                    break;
                case "toast":
                    switch (notification.position) {
                        case "TOP_LEFT":
                            toast_top_left.setVisible(true);
                            toastTopLeftText.setText(message);
                            break;
                        case "BOTTOM_LEFT":
                            toast_bottom_left.setVisible(true);
                            toastBottomLeftText.setText(message);
                            break;
                        case "BOTTOM_RIGHT":
                            toast_bottom_right.setVisible(true);
                            toastBottomRightText.setText(message);
                            break;
                        default:
                            toast_top_right.setVisible(true);
                            toastTopRightText.setText(message);
                            break;
                    }
                    break;
            }
            notification.beenDisplayed = true;
        }

        /**
         * Hides the UI container elements for the notifications
         */
        private void hideUI() {
            if (Stream.of(banner, banner_bottom, toast_top_right, toast_top_left, toast_bottom_left, toast_bottom_right).anyMatch(Objects::isNull)) {
                return;
            }

            banner.setVisible(false);
            banner_bottom.setVisible(false);
            toast_top_right.setVisible(false);
            toast_top_left.setVisible(false);
            toast_bottom_left.setVisible(false);
            toast_bottom_right.setVisible(false);
        }

        /**
         * Closes the notification by jumping to the next keyframe in the timeline.
         */
        @FXML public void closeNotification() {
            if (currentKeyframe == notifications.size() || indefiniteNotificationDisplayed) {
                timeline.jumpTo(Duration.seconds(timelineDuration));
            } else {
                timeline.jumpTo(Duration.seconds(currentTime - notifications.get(currentKeyframe).displayTime));
            }
        }

        @FXML
        private void initialize() {
            HelloApplication.notificationController = this;
        }
    }
