package com.tests;

import com.example.flowmato.controller.AchievementsController;
import com.example.flowmato.controller.NotificationController;
import com.example.flowmato.controller.TimerController;
import com.example.flowmato.model.SqliteProfileDAO;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TimerTest {

    private TimerController timerController;

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        //initialise necessary dependencies here
        SqliteProfileDAO dao = new SqliteProfileDAO();
        AchievementsController achievementsController = new AchievementsController(dao);
        timerController = new TimerController(achievementsController, new NotificationController());  //pass to timercontroller
    }


    @Test
    public void testStartTimer(){
        // start the timer
        timerController.resume();

        wait(1);

        // Assert that timer has started
        assertTrue(timerController.getTimeElapsed() > 0);
    }

    @Test
    public void testStopTimer(){
        // start the timer
        timerController.resume();
        // let it run
        wait(1);
        // test the stop function
        timerController.stop();

        assertEquals(0, timerController.getTimeElapsed());

    }

    @Test
    public void testPauseTimer(){
        // Start the timer
        timerController.resume();

        // Let it run
        wait(1);

        // pause the timer
        timerController.pause();

        assertTrue(timerController.isPaused());

    }

    @Test
    public void testDurations() {
        timerController.pause();
        timerController.setSessionDuration(1);
        timerController.setShortBreakDuration(2);
        timerController.setLongBreakDuration(3);

        timerController.resume();

        assertEquals(1, timerController.timerDuration);

        wait(1500);

        assertEquals(2, timerController.timerDuration);

        wait (2500);

        assertEquals(1, timerController.timerDuration);

        timerController.breaksTaken = 3;

        wait(1500);

        assertEquals(3, timerController.timerDuration);
    }
}
