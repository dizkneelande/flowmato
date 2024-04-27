package com.example.flowmato.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A controller class that controls a timer.
 */
public class TimerController {
    int timerDuration;
    int sessionDuration;
    int shortBreakDuration;
    int longBreakDuration;
    int currentStage;
    int shortBreaksTaken;
    int longBreaksTaken;
    int breaksTaken;
    boolean isPaused;
    private int timeElapsed;
    private Timer timer;
    private TimerTask task;
    private Instant startTime;
    private Instant pauseTime;

    /**
     * A method that initialises the TimerController variables.
     */
    public TimerController() {
        sessionDuration = 150;
        shortBreakDuration = 200; //600;
        longBreakDuration = 250; //1800;
        currentStage = 1;
        timeElapsed = 0;
        breaksTaken = 0;
        timerDuration = sessionDuration;

        timer = new Timer();

        isPaused = true;
    }

    /**
     * A method that stops and resets the TimerController back to default values.
     */
    public void Stop() {
        Reset();

        currentStage = 1;
        breaksTaken = 0;
        shortBreaksTaken = 0;
        longBreaksTaken = 0;
    }

    /**
     * A method that resets the timer back to its initialisation values, but does not modify the stage or break progress
     */
    private void Reset() {
        isPaused = true;

        task.cancel();
        timeElapsed = 0;

        startTime = null;
        pauseTime = null;
        timerDuration = sessionDuration;
    }

    /**
     * A method that pauses the active timer.
     */
    public void Pause() {
        if (isPaused) {
            return;
        }

        isPaused = true;

        pauseTime = Instant.now();

        task.cancel();
    }

    /**
     * A method that sets the duration of a long break.
     * @param sessionDuration The duration to set the session duration (working stage of the Pomodoro) to
     * @return true if the duration was successfully modified, false if it wasn't modified
     */
    public boolean setSessionDuration(int sessionDuration) {
        if (!isPaused) {
            return false;
        }

        this.sessionDuration = sessionDuration;
        return true;
    }

    /**
     * A method that sets the duration of a long break.
     * @param shortBreakDuration The duration to set the short break to
     * @return true if the duration was successfully modified, false if it wasn't modified
     */
    public boolean setShortBreakDuration(int shortBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.shortBreakDuration = shortBreakDuration;
        return true;
    }

    /**
     * A method that sets the duration of a long break.
     * @param longBreakDuration The duration to set the long break to
     * @return true if the duration was successfully modified, false if it wasn't modified
     */
    public boolean setLongBreakDuration(int longBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.longBreakDuration = longBreakDuration;
        return true;
    }

    /**
     * A simple model class representing a contact with a first name, last name, email, and phone number.
     * @return a metric of how much time has elapsed since the timer started.
     */
    public int getTimeElapsed() {
        if (startTime == null) {
            return 0;
        } else {
            if (isPaused) {
                return (int) Duration.between(startTime, pauseTime).toMillis() + timeElapsed;
            } else {
                if (pauseTime != null && pauseTime.isAfter(startTime)) {
                    return (int) (Duration.between(pauseTime, Instant.now()).toMillis() + timeElapsed);
                } else {
                    return (int) (Duration.between(startTime, Instant.now()).toMillis() + timeElapsed);
                }
            }
        }
    }

    /**
     * A method to resume/start the timer.
     */
    public void Resume() {
        if ((pauseTime != null) && (pauseTime.isAfter(startTime))) {
            timeElapsed = (int) (Duration.between(startTime, pauseTime).toMillis() + timeElapsed);
            startTime = Instant.now();
        }

        if (startTime == null) {
            startTime = Instant.now();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer finished!");
                nextStage();
            }
        };

        System.out.println("TIME LEFT:");
        System.out.println((timerDuration * 60) - timeElapsed);
        System.out.println("TIMER START:");
        System.out.println(startTime);
        timer.schedule(task, (timerDuration * 60L) - timeElapsed);

        isPaused = false;
    }

    /**
     * A method that transitions the timer to the next "stage"; a stage is a discrete period of time that represents either
     * the work stage, the short rest stage or the long rest stage.
     */
    public void nextStage() {
        Reset();

        currentStage++;

        if (currentStage % 2 == 0) {
            breaksTaken++;
            if (breaksTaken % 4 == 0 && breaksTaken != 0) {
                timerDuration = longBreakDuration;
                longBreaksTaken++;
            } else {
                timerDuration = shortBreakDuration;
                shortBreaksTaken++;
            }
        } else {
            timerDuration = sessionDuration;
        }

        Resume();
    }
}
