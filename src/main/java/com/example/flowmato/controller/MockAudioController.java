package com.example.flowmato.controller;

/*
    This class overrides all the methods of the AudioController class and is used to prevent GitHub Actions (and other
    headless systems) from failing due to it being unable to create a MediaPlayer. For more information see:
    nobody uses JavaFX in the real world so somehow this bug is yet to be fixed
 */
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
        return;
    }

    @Override
    public void playShortBreak() {
        return;
    }

    @Override
    public void playMusic() {
        return;
    }

    @Override
    public void stopMusic() {
        return;
    }

    @Override
    public void muteSounds() {
        return;
    }

    @Override
    public void unmuteSounds() {
        return;
    }

    @Override
    public void muteMusic() {
        return;
    }

    @Override
    public void unmuteMusic() {
        return;
    }

    @Override
    public void setMusicVolume(double musicVolume) {
        return;
    }

    @Override
    public boolean setVolume(double volume) {
        return true;
    }

    @Override
    public void pauseMusic() {
        return;
    }

    @Override
    public void resumeMusic() {
        return;
    }
}
