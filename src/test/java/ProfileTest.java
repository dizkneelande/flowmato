import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.flowmato.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class for profile (ref 7.1 reading examples)
 */
public class ProfileTest {

    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile("smellynelly@email.com", "password", "archbtw");
    }

    @Test
    public void testConstructor() {
        assertEquals("smellynelly@email.com", profile.getEmail());
        assertEquals("password", profile.getPassword());
        assertEquals("archbtw", profile.getPreferredName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("smellynelly@email.com", profile.getEmail());
    }

    @Test
    public void testSetEmail() {
        profile.setEmail("jigglypuff@email.com");
        assertEquals("jigglypuff@email.com", profile.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", profile.getPassword());
    }

    @Test
    public void testSetPassword() {
        profile.setPassword("carrot");
        assertEquals("carrot", profile.getPassword());
    }

    @Test
    public void testGetPreferredName() {
        assertEquals("archbtw", profile.getPreferredName());
    }

    @Test
    public void testSetPreferredName() {
        profile.setPreferredName("windowsbtw");
        assertEquals("windowsbtw", profile.getPreferredName());
    }
}
