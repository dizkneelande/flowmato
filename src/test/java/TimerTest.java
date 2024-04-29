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
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Assert that timer has started
        assertTrue(timerController.getTimeElapsed() > 0);
    }
}
