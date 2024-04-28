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
    int pomodorosCompleted;
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
        // The following values should be removed once user settings persistency has been added
        sessionDuration = 1500;
        shortBreakDuration = 300;
        longBreakDuration = 1200;
        // =================== //
        currentStage = 1;
        timeElapsed = 0;
        breaksTaken = 0;
        timerDuration = sessionDuration;

        timer = new Timer();

        isPaused = true;
    }

    /**
     * Stops and resets the TimerController back to default values.
     */
    public void stop() {
        reset();

        currentStage = 1;
        breaksTaken = 0;
        shortBreaksTaken = 0;
        longBreaksTaken = 0;
        pomodorosCompleted = 0;
    }

    /**
     * Resets the timer back to its initialisation values, but does not modify the stage or break progress
     */
    private void reset() {
        isPaused = true;

        if (this.task != null) {
            task.cancel();
        }

        task = null;
        timeElapsed = 0;

        startTime = null;
        pauseTime = null;
        timerDuration = sessionDuration;
    }

    /**
     * Pauses the active timer.
     */
    public void pause() {
        if (isPaused) {
            return;
        }

        isPaused = true;

        pauseTime = Instant.now();

        task.cancel();
    }

    /**
     * Sets the duration of a Pomodoro session.
     * @param sessionDuration The duration to set the session duration (working stage of the Pomodoro) to
     * @return <b>true</b> if the duration was successfully modified, <b>false</b> if it wasn't modified
     */
    public boolean setSessionDuration(int sessionDuration) {
        if (!isPaused) {
            return false;
        }

        this.sessionDuration = sessionDuration;
        this.timerDuration = sessionDuration;
        reset();
        return true;
    }

    /**
     * Sets the duration of a short break.
     * @param shortBreakDuration The duration to set the short break to
     * @return <b>true</b> if the duration was successfully modified, <b>false</b> if it wasn't modified
     */
    public boolean setShortBreakDuration(int shortBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.shortBreakDuration = shortBreakDuration;
        reset();
        return true;
    }

    /**
     * Sets the duration of a long break.
     * @param longBreakDuration The duration to set the long break to
     * @return <b>true</b> if the duration was successfully modified, <b>false</b> if it wasn't modified
     */
    public boolean setLongBreakDuration(int longBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.longBreakDuration = longBreakDuration;
        reset();
        return true;
    }

    /**
     * Gets the amount of time elapsed since the timer was started.
     * @return a metric of how much time has elapsed since the timer started (in milliseconds).
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
     * Resumes/starts the timer.
     * @return <b>true</b> if the timer was started successfully, <b>false</b> if it wasn't started.
     */
    public boolean resume() {
        if (task != null && !isPaused) {
            return false;
        }

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
                nextStage();
            }
        };

        timer.schedule(task, (timerDuration * 1000L) - timeElapsed);

        isPaused = false;
        return true;
    }

    /**
     * Transitions the timer to the next "stage"; a stage is a discrete period of time that represents either
     * the work stage, the short rest stage or the long rest stage.
     */
    private void nextStage() {
        reset();

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
            pomodorosCompleted++;
            timerDuration = sessionDuration;
        }

        resume();
    }
}