package com.tests;

import com.example.flowmato.controller.AudioController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.*;

/* This Class doesn't work in Github Actions because for whatever reason TestFX/Monocle doesn't
support JavaFX's MediaPlayer. These tests work in a local environment but will
not work on a headless system/Github Actions.
 */

@Disabled("Temporarily disabled") // Remove this to test AudioController locally (or just run this file separately)
@ExtendWith(ApplicationExtension.class)
public class AudioTest {
    private AudioController audioController;

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        this.audioController = new AudioController();
    }

    @Test
    public void testSetVolume(){
        audioController.setVolume(1.0);

        wait(1);

        assertEquals(1.0, audioController.volume);

        audioController.setVolume(0.5);

        wait(1);

        assertEquals(0.5, audioController.volume);
    }

    @Test
    public void testSetMusicVolume(){
        audioController.playMusic();

        wait(100);

        audioController.setMusicVolume(1.0);

        wait(10);

        assertEquals(1.0, audioController.musicPlayer.getVolume());

        audioController.setMusicVolume(0.5);

        wait(10);

        assertEquals(0.5, audioController.musicPlayer.getVolume());
    }

    @Test
    public void testMuteSounds(){
        audioController.muteSounds();

        wait(1);

        assertEquals(0.0, audioController.notificationSound.getVolume());
        assertEquals(0.0, audioController.shortBreakSound.getVolume());
        assertEquals(0.0, audioController.longBreakSound.getVolume());
    }

    @Test
    public void testUnmuteSounds(){
        audioController.unmuteSounds();

        wait(1);

        assertEquals(audioController.volume, audioController.notificationSound.getVolume());
        assertEquals(audioController.volume, audioController.shortBreakSound.getVolume());
        assertEquals(audioController.volume, audioController.longBreakSound.getVolume());
    }

    @Test
    public void testMuteMusic(){
        audioController.playMusic();

        wait(100);

        audioController.muteMusic();

        wait(100);

        assertEquals(0.0, audioController.musicPlayer.getVolume());
    }

    @Test
    public void testUnmuteMusic(){
        audioController.playMusic();

        wait(100);

        audioController.unmuteMusic();

        wait(100);

        assertEquals(audioController.volume, audioController.musicPlayer.getVolume());
    }

    @Test
    public void testStopMusic(){
        audioController.stopMusic();

        wait(1);

        assertFalse(audioController.playingMusic);
    }

    @Test
    public void testPlayMusic(){
        audioController.playMusic();

        wait(1);

        assertTrue(audioController.playingMusic);
    }

    @Test
    public void testPlayShortBreak(){
        audioController.playShortBreak();

        wait(300);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.shortBreakSound.getStatus().toString());
    }

    @Test
    public void testPlayLongBreak(){
        audioController.playLongBreak();

        wait(300);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.longBreakSound.getStatus().toString());
    }

    @Test
    public void testPlayNotification(){
        audioController.playNotification();

        wait(500);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.notificationSound.getStatus().toString());
    }

    @Test
    public void testPlayAlert(){
        audioController.playAlert();

        wait(500);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.alertSound.getStatus().toString());
    }

    @Test
    public void testStudyTime(){
        audioController.playStudyTime();

        wait(500);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.studyTimeSound.getStatus().toString());
    }
}
