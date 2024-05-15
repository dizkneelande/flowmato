package com.tests;

import com.example.flowmato.model.Analytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class AnalyticsTest {
    private Analytics analytics;

    @BeforeEach
    void setUp() {
        analytics = new Analytics(1, 5, 300, 60);
    }

    @Test
    void testConstructorAndGetter() {
        assertEquals(1, analytics.getProfileId());
        assertEquals(5, analytics.getCompletedPomodoros());
        assertEquals(300, analytics.getTotalFocusTime());
        assertEquals(60, analytics.getTotalBreakTime());
    }

    @Test
    void testSetters() {
        analytics.setCompletedPomodoros(10);
        assertEquals(10, analytics.getCompletedPomodoros());
        analytics.setTotalFocusTime(400);
        assertEquals(400, analytics.getTotalFocusTime());
        analytics.setTotalBreakTime(120);
        assertEquals(120, analytics.getTotalBreakTime());
    }
}
