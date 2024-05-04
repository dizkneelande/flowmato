import com.example.flowmato.controller.AchievementsController;
import com.example.flowmato.controller.TimerController;
import com.example.flowmato.model.SqliteProfileDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {

    private TimerController timerController;

    @BeforeEach
    public void setUp() {
        //initialise necessary dependencies here
        SqliteProfileDAO dao = new SqliteProfileDAO();
        AchievementsController achievementsController = new AchievementsController(dao);
        timerController = new TimerController(achievementsController);  //pass to timercontroller
    }


    @Test
    public void testStartTimer(){
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
