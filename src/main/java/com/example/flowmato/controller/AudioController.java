package com.example.flowmato.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class AudioController {
    public final MediaPlayer notificationSound = new MediaPlayer(new Media(new File("media/notification.mp3").toURI().toString()));
    public final MediaPlayer shortBreakSound = new MediaPlayer(new Media(new File("media/shortbreak.mp3").toURI().toString()));
    public final MediaPlayer longBreakSound = new MediaPlayer(new Media(new File("media/longbreak.mp3").toURI().toString()));
    public MediaPlayer musicPlayer;
    Random random = new Random();
    int trackBeingPlayed = random.nextInt(4 - 1 + 1) + 1;
    public double volume = 1.0;

    public boolean playingAudio;
    public boolean playingMusic;
    public boolean notificationPlaying;

    /**
     * Plays the notification sound
     */
    public void playNotification() {
        if (playingAudio) {
            return;
        }
        notificationSound.seek(javafx.util.Duration.ZERO);

        notificationSound.setOnEndOfMedia(() -> {
            playingAudio = false;
            notificationPlaying = false;
            fadeVolume(musicPlayer, false);
        });

        fadeVolume(musicPlayer, true);

        notificationPlaying = true;
        playingAudio = true;
        notificationSound.play();
    }

    /**
     * Plays the Long Break sound
     */
    public void playLongBreak() {
        if (playingAudio && !notificationPlaying) {
            return;
        } else if (notificationPlaying) {
            notificationSound.stop();
        }
        longBreakSound.seek(javafx.util.Duration.ZERO);

        longBreakSound.setOnEndOfMedia(() -> {
            playingAudio = false;
            fadeVolume(musicPlayer, false);
        });

        fadeVolume(musicPlayer, true);

        playingAudio = true;
        longBreakSound.play();
    }

    /**
     * Plays the Short Break Sound
     */
    public void playShortBreak() {
        if (playingAudio && !notificationPlaying) {
            return;
        } else if (notificationPlaying) {
            notificationSound.stop();
        }
        shortBreakSound.seek(javafx.util.Duration.ZERO);

        shortBreakSound.setOnEndOfMedia(() -> {
            playingAudio = false;
            fadeVolume(musicPlayer, false);
        });

        fadeVolume(musicPlayer, true);

        playingAudio = true;
        shortBreakSound.play();
    }

    /**
     * Plays the Background Music
     */
    public void playMusic() {
        if (playingMusic) {
            return;
        }

        if (trackBeingPlayed < 1 || trackBeingPlayed > 5) {
            trackBeingPlayed = 1;
        }

        Media track = new Media(new File("media/track" + trackBeingPlayed + ".mp3").toURI().toString());
        musicPlayer = new MediaPlayer(track);

        musicPlayer.setOnEndOfMedia(() -> {
            stopMusic();
            playMusic();
        });

        playingMusic = true;
        musicPlayer.play();
    }

    /**
     * Stops the Background Music
     */
    public void stopMusic() {
        if (musicPlayer != null) {
            playingMusic = false;
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;

            // Get new track between 1-4, that isn't the same as the track that was previously played
            int newTrackToPlay = random.nextInt(4 - 1 + 1) + 1;
            while (trackBeingPlayed == newTrackToPlay) {
                newTrackToPlay = random.nextInt(4 - 1 + 1) + 1;
            }

            // Secret Rare Track (5% chance of playing)
            int secretRandom = random.nextInt(100 - 1 + 1) + 1;
            if (secretRandom <= 5) {
                newTrackToPlay = 5;
            }
            trackBeingPlayed = newTrackToPlay;
        }
    }

    /**
     * Mutes sound effects
     */
    public void muteSounds() {
        shortBreakSound.setVolume(0.0);
        longBreakSound.setVolume(0.0);
        notificationSound.setVolume(0.0);
    }

    /**
     * Unmutes sound effects
     */
    public void unmuteSounds() {
        shortBreakSound.setVolume(volume);
        longBreakSound.setVolume(volume);
        notificationSound.setVolume(volume);
    }

    /**
     * Mutes background music
     */
    public void muteMusic() {
        if (musicPlayer != null) {
            musicPlayer.setVolume(0.0);
        }
    }

    /**
     * Unmutes background music
     */
    public void unmuteMusic() {
        if (musicPlayer != null) {
            musicPlayer.setVolume(volume);
        }
    }

    /**
     * Sets the background music's volume
     * @param musicVolume the volume to set the music to
     */
    public void setMusicVolume(double musicVolume) {
        if (musicPlayer != null) {
            musicPlayer.setVolume(musicVolume);
        }
    }

    /**
     * Sets the volume of the AudioController
     * @param volume the volume to set the AudioController to (between 0.0 and 1.0)
     * @return <b>true</b> if the volume was modified, <b>false</b> if it wasn't
     */
    public boolean setVolume(double volume) {
        if (volume > 1.0 || volume < 0.0) {
            return false;
        }

        this.volume = volume;
        return true;
    }

    /**
     * Fades the volume of the provided MediaPlayer
     * @param mediaPlayer the MediaPlayer to fade the volume of
     * @param fadeDown whether to fade down or fade up the volume
     */
    private void fadeVolume(MediaPlayer mediaPlayer, boolean fadeDown) {
        if (mediaPlayer == null) {
            return;
        }

        Timeline timeline;
        if (fadeDown) {
            timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), volume)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(mediaPlayer.volumeProperty(), volume * 0.1))
            );
        } else {
            timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), volume * 0.1)),
                new KeyFrame(Duration.seconds(2.5), new KeyValue(mediaPlayer.volumeProperty(), volume))
            );
        }

        timeline.play();
    }

    /**
     * Pauses the background music
     */
    public void pauseMusic() {
        if (musicPlayer != null) {
            musicPlayer.pause();
            playingMusic = false;
        }
    }

    /**
     * Resumes the background music
     */
    public void resumeMusic() {
        if (playingMusic) {
            return;
        }

        if (musicPlayer != null) {
            musicPlayer.play();
            playingMusic = true;
        } else {
            playMusic();
        }
    }
}
