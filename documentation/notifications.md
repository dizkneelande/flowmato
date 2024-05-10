# Notifications

### Table of Contents
- [Setting up Notifications](#Setting-up-Notifications)
- [Using Notifications](#Using-Notifications)
    - [Alert Notification](#alert-notification)
    - [Banner Notification](#banner-notification)
    - [Toast Notification](#toast-notification)
    - [Displaying Multiple Notifications](#displaying-multiple-notifications)
    - [Indefinite Notifications](#indefinite-notifications)

### Setting up Notifications

First we need to add the Notification UI to our **.fxml** file.
To do this we need to ensure that the top-most element in the **.fxml** file is a ```<Group>```
with no parameters other than the bare-minimum needed for the **.fxml** file to function. Then
we need to add a reference to the Notification UI as the last element in the file (so it renders on-top of everything else).
Our file should look something like this:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- Import Statements Here -->

<Group xmlns="http://javafx.com/javafx/17.0.2-ea" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.example.flowmato.controller.ExampleController">
    <!-- Container for UI and other UI elements here -->
    <fx:include source="notification.fxml" />
</Group>
```

Now we need to add a reference to the NotificationController within the Controller file that is associated with the **.fxml** file.
Our controller file should look something like this:

```java
// Import Statements Here

public class ExampleController {
    private NotificationController notificationController;

    @FXML
    public void initialize() {
        notificationController = HelloApplication.notificationController;
    }
}
```

Now we can execute notifications from anywhere within this controller file!

### Using Notifications

The notification system is a queue based system that allows you to display notifications without them interfering with each other.
Below are some examples of different types/methods of using the notification system. This document does not cover all methods/usages of the notification system. Refer to the Javadoc comments in the ```NotificationController.java``` file for more info.

#### Alert Notification

Alert notifications supersede all other notifications in terms of which notification is displayed first (excluding other alert notifications already in the queue).

```java
public void notifyError() {
    Notification errorNotification = new Notification("alert", "An error occurred", "BOTTOM", 5000);
    notificationController.notify(errorNotification);
}
```

#### Banner Notification


```java
public void notifyUpdate() {
    Notification updateNotification = new Notification("banner", "Your version of Flowmato is out of date", "TOP", 5000);
    updateNotification.notify(updateNotification);
}
```

#### Toast Notification

```java
public void notifyAchievement() {
    Notification achievementNotification = new Notification("toast", "Achievement Get!", "TOP_RIGHT", 5000);
    notificationController.notify(achievementNotification);
}
```

#### Displaying Multiple Notifications

```java
public void notifyAchievements() {
    Notification notification1 = new Notification("toast", "First Pomodoro Completed!", "TOP_RIGHT", 5000);
    Notification notification2 = new Notification("toast", "First Short Break Completed!", "TOP_RIGHT", 5000);
    Notification notification3 = new Notification("toast", "First Long Break Completed!", "TOP_RIGHT", 5000);
    notificationController.notify(notification1, notification2, notification3);
}
```

#### Indefinite Notifications

You can make notifications last "forever" (in the current implementation the max time is 166 minutes) by setting the notification
**displayTime** to one of the below values:

- **0** - Displays the notification until the next notification is called (or 166 minutes elapse)
- **-1 and below** - Displays the notification until 166 minutes has elapsed
  - ***CAUTION:*** Setting a notification to this value will result in all subsequent notifications being stacked
  in the queue until this notification is closed, which depending on how long the user leaves the notification up, can result in a massive queue size,
  it is recommended to avoid setting this as the value. (It's main use is for testing indefinite notifications)