package com.example.flowmato.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioController {
    /* !!! IMPORTANT !!!
     * Any time you update this class in any capacity you should manually run the AudioTest file (make sure to directly
     * run the file or temporarily remove the @Disabled line) to ensure that AudioController functionality remains unbroken.
     * These tests cannot be run via GitHub Actions or headless systems, which is why it's important to run them manually.
     * !!!!!!!!!!!!!!!!!
     */
    public MediaPlayer notificationSound;
    public MediaPlayer shortBreakSound;
    public MediaPlayer longBreakSound;
    public MediaPlayer musicPlayer;
    Random random = new Random();
    int trackBeingPlayed;
    public double volume = 1.0;
    public boolean playingAudio;
    public boolean playingMusic;
    public boolean notificationPlaying;
    public int numberOfTracks;
    public int numberOfSecretTracks;
    boolean secretTrackQueued;
    boolean secretPlayed;
    List<Integer> tracksPlayed = new ArrayList<>();

    public AudioController() {
        File musicDirectory = new File("media/music");
        File secretDirectory = new File("media/secret");

        File[] musicFiles = musicDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        File[] secretFiles = secretDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        if (musicFiles != null) {
            numberOfTracks = musicFiles.length;
        }

        if (secretFiles != null) {
            numberOfSecretTracks = secretFiles.length;
        }

        setRandomTrack();

        notificationSound = new MediaPlayer(new Media(new File("media/sfx/notification.mp3").toURI().toString()));
        shortBreakSound = new MediaPlayer(new Media(new File("media/sfx/shortbreak.mp3").toURI().toString()));
        longBreakSound = new MediaPlayer(new Media(new File("media/sfx/longbreak.mp3").toURI().toString()));
    }

    public AudioController(boolean createPlayer) {
        // This constructor is deliberately left empty so that when MockAudioController uses it there is no MediaPlayer initialisations
    }

    /**
     * Sets the AudioController's tracKBeingPlayed to a new random track
     */
    private void setRandomTrack() {
        // If all tracks have been played (exc. secret tracks) we reset the list of played tracks
        if (tracksPlayed.size() == numberOfTracks) {
            tracksPlayed = new ArrayList<>();
            secretPlayed = false;
            if (!secretTrackQueued) {
                // Add the previously played track, so we don't get a repeat track after resetting the list
                tracksPlayed.add(trackBeingPlayed);
            }
        }

        // Get new track between 1-numberOfTracks, that hasn't been played yet
        int newTrackToPlay = random.nextInt(numberOfTracks - 1 + 1) + 1;
        while (tracksPlayed.contains(newTrackToPlay)) {
            newTrackToPlay = random.nextInt(numberOfTracks - 1 + 1) + 1;
        }

        // Secret Rare Track(s)
        int secretRandom = random.nextInt(100 - 1 + 1) + 1;
        if (secretRandom <= 5) {
            newTrackToPlay = random.nextInt(numberOfSecretTracks - 1 + 1) + 1;
            secretTrackQueued = true;
            secretPlayed = true;
        } else {
            secretTrackQueued = false;
        }

        if (!secretTrackQueued) {
            tracksPlayed.add(newTrackToPlay);
        }

        trackBeingPlayed = newTrackToPlay;
    }

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

        Media track;

        if (secretTrackQueued) {
            track = new Media(new File("media/secret/track" + trackBeingPlayed + ".mp3").toURI().toString());
        } else {
            track = new Media(new File("media/music/track" + trackBeingPlayed + ".mp3").toURI().toString());
        }

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

            setRandomTrack();
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
