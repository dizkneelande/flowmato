package com.example.flowmato.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

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

    public void Stop() {
        Reset();

        currentStage = 1;
        breaksTaken = 0;
        shortBreaksTaken = 0;
        longBreaksTaken = 0;
    }

    private void Reset() {
        isPaused = true;

        task.cancel();
        timeElapsed = 0;

        startTime = null;
        pauseTime = null;
        timerDuration = sessionDuration;
    }

    public void Pause() {
        if (isPaused) {
            return;
        }

        isPaused = true;

        pauseTime = Instant.now();

        task.cancel();
    }

    public boolean setSessionDuration(int sessionDuration) {
        if (!isPaused) {
            return false;
        }

        this.sessionDuration = sessionDuration;
        return true;
    }

    public boolean setShortBreakDuration(int shortBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.shortBreakDuration = shortBreakDuration;
        return true;
    }

    public boolean setLongBreakDuration(int longBreakDuration) {
        if (!isPaused) {
            return false;
        }

        this.longBreakDuration = longBreakDuration;
        return true;
    }

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
