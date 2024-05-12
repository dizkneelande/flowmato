package com.example.flowmato.controller;

import javafx.scene.media.MediaPlayer;

public class MockAudioController extends AudioController {
    public MockAudioController() {
        super(false);
    }
    @Override
    public void playNotification() {
        return;
    }

    @Override
    public void playLongBreak() {

    }

    @Override
    public void playShortBreak() {

    }

    @Override
    public void playMusic() {

    }

    @Override
    public void stopMusic() {

    }

    @Override
    public void muteSounds() {

    }

    @Override
    public void unmuteSounds() {

    }

    @Override
    public void muteMusic() {

    }

    @Override
    public void unmuteMusic() {

    }

    @Override
    public void setMusicVolume(double musicVolume) {

    }

    @Override
    public boolean setVolume(double volume) {
        return true;
    }

    @Override
    public void pauseMusic() {

    }

    @Override
    public void resumeMusic() {

    }
}
