package com.example.flowmato.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioController {
    private final MediaPlayer notificationSound = new MediaPlayer(new Media(new File("media/notification.mp3").toURI().toString()));
    private final MediaPlayer shortBreakSound = new MediaPlayer(new Media(new File("media/shortbreak.mp3").toURI().toString()));
    private final MediaPlayer longBreakSound = new MediaPlayer(new Media(new File("media/longbreak.mp3").toURI().toString()));

    public boolean playingAudio;

    public boolean notificationPlaying;

    public AudioController() {

    }

    public void playNotification() {
        if (playingAudio) {
            return;
        }
        notificationSound.seek(javafx.util.Duration.ZERO);

        notificationSound.setOnEndOfMedia(() -> {
            playingAudio = false;
            notificationPlaying = false;
        });

        notificationPlaying = true;
        playingAudio = true;
        notificationSound.play();
    }

    public void playLongBreak() {
        if (playingAudio && !notificationPlaying) {
            return;
        } else if (notificationPlaying) {
            notificationSound.stop();
        }
        longBreakSound.seek(javafx.util.Duration.ZERO);

        longBreakSound.setOnEndOfMedia(() -> {
            playingAudio = false;
        });

        playingAudio = true;
        longBreakSound.play();
    }

    public void playShortBreak() {
        if (playingAudio && !notificationPlaying) {
            return;
        } else if (notificationPlaying) {
            notificationSound.stop();
        }
        shortBreakSound.seek(javafx.util.Duration.ZERO);

        shortBreakSound.setOnEndOfMedia(() -> {
            playingAudio = false;
        });

        playingAudio = true;
        shortBreakSound.play();
    }

    public void mute() {
        shortBreakSound.setVolume(0.0);
        longBreakSound.setVolume(0.0);
        notificationSound.setVolume(0.0);
    }

    public void unmute() {
        shortBreakSound.setVolume(1.0);
        longBreakSound.setVolume(1.0);
        notificationSound.setVolume(1.0);
    }
}
