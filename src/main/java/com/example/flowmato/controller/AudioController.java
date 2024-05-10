package com.example.flowmato.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

public class AudioController {
    private final MediaPlayer notificationSound = new MediaPlayer(new Media(new File("media/notification.mp3").toURI().toString()));
    private final MediaPlayer shortBreakSound = new MediaPlayer(new Media(new File("media/shortbreak.mp3").toURI().toString()));
    private final MediaPlayer longBreakSound = new MediaPlayer(new Media(new File("media/longbreak.mp3").toURI().toString()));
    MediaPlayer musicPlayer;
    Random random = new Random();
    int trackBeingPlayed = random.nextInt(4 - 1 + 1) + 1;

    public boolean playingAudio;
    public boolean playingMusic;
    public boolean notificationPlaying;

    public void playNotification() {
        if (playingAudio) {
            return;
        }
        notificationSound.seek(javafx.util.Duration.ZERO);

        notificationSound.setOnEndOfMedia(() -> {
            playingAudio = false;
            notificationPlaying = false;
            unmuteMusic();
        });

        setMusicVolume(0.1);

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
            unmuteMusic();
        });

        setMusicVolume(0.1);

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
            unmuteMusic();
        });

        setMusicVolume(0.1);

        playingAudio = true;
        shortBreakSound.play();
    }

    public void playMusic() {
        if (playingMusic) {
            return;
        }

        Media track = new Media(new File("media/track" + trackBeingPlayed + ".mp3").toURI().toString());
        musicPlayer = new MediaPlayer(track);

        musicPlayer.setOnEndOfMedia(() -> {
            trackBeingPlayed++;
            if (trackBeingPlayed > 4) {
                trackBeingPlayed = 1;
            }
            musicPlayer.stop();
            musicPlayer.dispose();
            playMusic();
        });

        musicPlayer.play();
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;

            // Get new track between 1-4, that isn't the same as the track that was previously played
            int newTrackToPlay = random.nextInt(4 - 1 + 1) + 1;
            while (trackBeingPlayed == newTrackToPlay) {
                newTrackToPlay = random.nextInt(4 - 1 + 1) + 1;
            }
            trackBeingPlayed = newTrackToPlay;
        }
    }

    public void muteSounds() {
        shortBreakSound.setVolume(0.0);
        longBreakSound.setVolume(0.0);
        notificationSound.setVolume(0.0);
    }

    public void unmuteSounds() {
        shortBreakSound.setVolume(1.0);
        longBreakSound.setVolume(1.0);
        notificationSound.setVolume(1.0);
    }

    public void muteMusic() {
        if (musicPlayer != null) {
            musicPlayer.setVolume(0.0);
        }
    }

    public void unmuteMusic() {
        if (musicPlayer != null) {
            musicPlayer.setVolume(1.0);
        }
    }

    public void setMusicVolume(double musicVolume) {
        if (musicPlayer != null) {
            musicPlayer.setVolume(musicVolume);
        }
    }

    public void pauseMusic() {
        if (musicPlayer != null) {
            musicPlayer.pause();
            playingMusic = false;
        }
    }

    public void unPauseMusic() {
        if (musicPlayer != null) {
            musicPlayer.play();
            playingMusic = true;
        } else {
            playMusic();
        }
    }
}
