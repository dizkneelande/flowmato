import com.example.flowmato.controller.NotificationController;
import com.example.flowmato.model.Notification;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    private NotificationController notificationController;

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void initialize() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {

        }
    }

    @BeforeEach void setUp() {
        this.notificationController = new NotificationController();
    }

    @Test
    public void testToastNotification(){
        Notification toastNotification = new Notification("toast", "test notification", "TOP_RIGHT", 100);
        notificationController.notify(toastNotification);

        wait(1);

        assertFalse(notificationController.getQueue().isEmpty());
        assertEquals("toast", notificationController.getQueue().get(0).type);
        assertEquals("TOP_RIGHT", notificationController.getQueue().get(0).position);
    }

    @Test
    public void testBannerNotification(){
        Notification bannerNotification = new Notification("banner", "test notification", "TOP", 100);
        notificationController.notify(bannerNotification);

        wait(1);

        assertFalse(notificationController.getQueue().isEmpty());
        assertEquals("banner", notificationController.getQueue().get(0).type);
        assertEquals("TOP", notificationController.getQueue().get(0).position);
    }

    @Test
    public void testAlertNotification(){
        Notification alertNotification = new Notification("alert", "test notification", "TOP", 100);
        notificationController.notify(alertNotification);

        wait(1);

        assertFalse(notificationController.getQueue().isEmpty());
        assertEquals("alert", notificationController.getQueue().get(0).type);
        assertEquals("TOP", notificationController.getQueue().get(0).position);
    }

    @Test
    public void testMultipleNotifications(){
        Notification notification1 = new Notification("toast", "First Pomodoro Completed!", "TOP_RIGHT", 5000);
        Notification notification2 = new Notification("toast", "First Short Break Completed!", "TOP_RIGHT", 5000);
        Notification notification3 = new Notification("toast", "First Long Break Completed!", "TOP_RIGHT", 5000);
        notificationController.notify(notification1, notification2, notification3);

        wait(1);

        assertEquals(3, notificationController.getQueue().size());
    }

    @Test
    public void testAlertNotificationPriority(){
        Notification notification1 = new Notification("toast", "First Pomodoro Completed!", "TOP_RIGHT", 5000);
        Notification notification2 = new Notification("toast", "First Short Break Completed!", "TOP_RIGHT", 5000);
        Notification notification3 = new Notification("alert", "CRITICAL ERROR DETECTED", "TOP", 5000);
        notificationController.notify(notification1, notification2, notification3);

        wait(1);

        assertEquals("alert", notificationController.getQueue().get(0).type);
    }

    @Test
    public void testNotificationQueueing(){
        Notification notification1 = new Notification("toast", "First Pomodoro Completed!", "TOP_RIGHT", 10);
        Notification notification2 = new Notification("toast", "First Short Break Completed!", "TOP_RIGHT", 10);
        Notification notification3 = new Notification("alert", "CRITICAL ERROR DETECTED", "TOP", 10);
        notificationController.notify(notification1, notification2, notification3);

        wait(100);

        assertTrue(notificationController.getQueue().isEmpty());
    }

    @Test
    public void testClearQueue(){
        Notification toastNotification = new Notification("toast", "test notification", "TOP_RIGHT", 100);

        notificationController.notify(toastNotification);
        notificationController.haltQueue();
        notificationController.clearQueue();

        assertTrue(notificationController.getQueue().isEmpty());
    }

    @Test
    public void testCloseNotification(){
        Notification toastNotification = new Notification("toast", "test notification", "TOP_RIGHT", 5000);
        notificationController.notify(toastNotification);

        wait(1000);

        notificationController.closeNotification();

        wait(1000);

        assertTrue(notificationController.getQueue().isEmpty());
    }

    @Test
    public void testGetQueue(){
        Notification toastNotification = new Notification("toast", "test notification", "TOP_RIGHT", 5000);
        notificationController.notify(toastNotification);

        wait(10);

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(toastNotification);

        assertEquals(notifications, notificationController.getQueue());
    }

    @Test
    public void testHaltQueue(){
        Notification notification1 = new Notification("toast", "First Pomodoro Completed!", "TOP_RIGHT", 5000);
        Notification notification2 = new Notification("toast", "First Short Break Completed!", "TOP_RIGHT", 5000);
        Notification notification3 = new Notification("toast", "First Long Break Completed!", "TOP_RIGHT", 5000);
        notificationController.notify(notification1, notification2, notification3);

        wait(500);

        notificationController.haltQueue();

        wait(500);

        assertEquals(2, notificationController.getQueue().size());
    }

    @Test
    public void testIndefiniteNotification(){
        Notification indefiniteNotification = new Notification("banner", "test notification", "TOP", 0);
        notificationController.notify(indefiniteNotification);

        wait(100);

        assertFalse(notificationController.getQueue().isEmpty());

        Notification replacementNotification = new Notification("banner", "test notification", "TOP", 50);
        notificationController.notify(replacementNotification);

        wait(10);

        assertEquals(1, notificationController.getQueue().size());
    }

    @Test
    public void testPermanentNotification(){
        Notification permanentNotification = new Notification("banner", "test notification", "TOP", -1);
        notificationController.notify(permanentNotification);

        wait(100);

        assertFalse(notificationController.getQueue().isEmpty());

        Notification replacementNotification = new Notification("banner", "test notification", "TOP", 50);
        notificationController.notify(replacementNotification);

        wait(10);

        assertEquals(2, notificationController.getQueue().size());
    }
}
