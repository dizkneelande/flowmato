import com.example.flowmato.controller.AudioController;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AudioTest {
    private AudioController audioController;

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void initialize() {
        System.setProperty("java.awt.headless", "true");

        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {

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

        wait(200);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.shortBreakSound.getStatus().toString());
    }

    @Test
    public void testPlayLongBreak(){
        audioController.playLongBreak();

        wait(200);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.longBreakSound.getStatus().toString());
    }

    @Test
    public void testPlayNotification(){
        audioController.playNotification();

        wait(200);

        assertTrue(audioController.playingAudio);
        assertEquals("PLAYING", audioController.notificationSound.getStatus().toString());
    }
}
