import com.example.flowmato.controller.TimerController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {

    @Test
    public void testStartTimer(){
        TimerController timerController = new TimerController();

        // start the timer
        timerController.resume();
        try{
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Assert that timer has started
        assertTrue(timerController.getTimeElapsed() > 0);
    }

    @Test
    public void testStopTimer(){
        TimerController timerController = new TimerController();

        // start the timer
        timerController.resume();
        // let it run
        try{
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // test the stop function
        timerController.stop();

        assertEquals(0, timerController.getTimeElapsed());

    }

    @Test
    public void testPauseTimer(){
        TimerController timerController = new TimerController();

        // Start the timer
        timerController.resume();

        // Let it run
        try{
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // pause the timer
        timerController.pause();

        assertTrue(timerController.isPaused());

    }
}
